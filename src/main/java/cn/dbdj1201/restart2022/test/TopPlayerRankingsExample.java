package cn.dbdj1201.restart2022.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.concurrent.*;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;

@Slf4j
public class TopPlayerRankingsExample {

    private static final String API_KEY = "RGAPI-285144dd-41ec-4da2-8630-5fc926a3860a";
    private static final String REGION = "kr";
    private static final String ENDPOINT = "https://" + REGION + ".api.riotgames.com";
    private static final Map<String, String> summonerNameCache = new HashMap<>(); // 缓存玩家名称
    private static final int MAX_RETRIES = 3; // 最大重试次数
    private static final int REQUEST_DELAY = 1000; // 请求之间的延迟（毫秒）

    public static void main(String[] args) {
        try {
            // 1. 获取最强王者、宗师和大师段位的排行榜数据
            List<JSONObject> topPlayers = new ArrayList<>();
            topPlayers.addAll(getRankingData("challenger"));
            topPlayers.addAll(getRankingData("grandmaster"));
            topPlayers.addAll(getRankingData("master"));

            // 2. 按胜点排序
            topPlayers.sort(Comparator.comparingInt(player -> -player.getInt("leaguePoints")));

            // 3. 异步获取玩家名称
            ExecutorService executor = Executors.newFixedThreadPool(10); // 使用线程池
            List<Future<?>> futures = new ArrayList<>();

            for (JSONObject player : topPlayers) {
                futures.add(executor.submit(() -> {
                    String summonerId = player.getString("summonerId");
                    String summonerName = null; // 获取玩家名称
                    try {
                        summonerName = getSummonerName(summonerId);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    player.put("summonerName", summonerName); // 添加到玩家数据中
                }));
            }

            // 等待所有任务完成
            for (Future<?> future : futures) {
                future.get();
            }
            executor.shutdown();

            // 4. 打印排行榜
            System.out.println("韩服顶分段排行：");
            for (JSONObject player : topPlayers) {
                String summonerName = player.getString("summonerName");
                String tier = player.getString("tier");
                int leaguePoints = player.getInt("leaguePoints");
                int wins = player.getInt("wins");
                int losses = player.getInt("losses");
                double winRate = (double) wins / (wins + losses) * 100;

                System.out.println("玩家名称: " + summonerName);
                System.out.println("段位: " + tier);
                System.out.println("胜点: " + leaguePoints);
                System.out.println("胜率: " + String.format("%.2f", winRate) + "%");
                System.out.println("胜场数: " + wins);
                System.out.println("败场数: " + losses);
                System.out.println("-----------------------------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<JSONObject> getRankingData(String tier) throws Exception {
        String apiUrl = ENDPOINT + "/lol/league/v4/" + tier + "leagues/by-queue/RANKED_SOLO_5x5";
        JSONObject response = getApiResponse(apiUrl);
        JSONArray entries = response.getJSONArray("entries");

        List<JSONObject> players = new ArrayList<>();
        for (int i = 0; i < entries.length(); i++) {
            JSONObject player = entries.getJSONObject(i);
            player.put("tier", response.getString("tier")); // 添加段位信息
            players.add(player);
        }
        return players;
    }

    private static String getSummonerName(String summonerId) throws Exception {
        // 检查缓存中是否已有玩家名称
        if (summonerNameCache.containsKey(summonerId)) {
            return summonerNameCache.get(summonerId);
        }

        // 调用 Summoner-v4 接口获取玩家名称
        String apiUrl = ENDPOINT + "/lol/summoner/v4/summoners/" + summonerId;
        JSONObject response = getApiResponse(apiUrl);
        String summonerName = response.getString("name");

        // 将玩家名称添加到缓存中
        summonerNameCache.put(summonerId, summonerName);
        return summonerName;
    }

    private static JSONObject getApiResponse(String apiUrl) throws Exception {
        int retries = 0;
        while (retries < MAX_RETRIES) {
            try {
                // 添加请求之间的延迟
                Thread.sleep(REQUEST_DELAY);

                URL url = new URL(apiUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("X-Riot-Token", API_KEY);
                connection.setConnectTimeout(5000); // 设置连接超时
                connection.setReadTimeout(5000); // 设置读取超时

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    return new JSONObject(response.toString());
                } else if (responseCode == 429) { // 速率限制
                    System.out.println("速率限制触发，等待后重试...");
                    Thread.sleep(60000); // 等待 60 秒后重试
                } else {
                    throw new Exception("请求失败，状态码: " + responseCode);
                }
            } catch (Exception e) {
                retries++;
                log.error("请求 {} 失败， 明细 >>>>>>>>>>>>>> {}", apiUrl, e.getMessage());
                System.out.println("请求失败，重试次数: " + retries);
                if (retries >= MAX_RETRIES) {
                    throw e;
                }
            }
        }
        throw new Exception("请求失败，已达到最大重试次数");
    }
}
