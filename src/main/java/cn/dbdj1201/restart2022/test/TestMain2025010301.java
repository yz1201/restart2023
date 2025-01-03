package cn.dbdj1201.restart2022.test;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
public class TestMain2025010301 {

    private static final String API_KEY = "RGAPI-285144dd-41ec-4da2-8630-5fc926a3860a";
    private static final String REGION = "kr";
    private static final String ENDPOINT = "https://" + REGION + ".api.riotgames.com";

    public static void main(String[] args) {
        try {
            // 1. 获取最强王者、宗师和大师段位的排行榜数据
            List<JSONObject> topPlayers = new ArrayList<>();
            topPlayers.addAll(getRankingData("challenger"));
//            topPlayers.addAll(getRankingData("grandmaster"));
//            topPlayers.addAll(getRankingData("master"));

            // 2. 按胜点排序
            topPlayers.sort(Comparator.comparingInt(player -> -player.getInt("leaguePoints")));

            // 3. 打印排行榜
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

    private static JSONObject getApiResponse(String apiUrl) throws Exception {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("X-Riot-Token", API_KEY);

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
        } else {
            throw new Exception("请求失败，状态码: " + responseCode);
        }
    }
}
