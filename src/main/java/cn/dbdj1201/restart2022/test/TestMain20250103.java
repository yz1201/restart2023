package cn.dbdj1201.restart2022.test;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
public class TestMain20250103 {

    // 你的 Riot Games API 密钥
    private static final String API_KEY = "RGAPI-3f9b6eab-7c0e-4e3a-9dc3-b4eaa1725496";

    // 韩服 API 端点
    private static final String REGION = "kr";
    private static final String ENDPOINT = "https://" + REGION + ".api.riotgames.com";

    // 获取排行榜数据的接口
    private static final String QUEUE = "RANKED_SOLO_5x5";
    private static final String API_URL = ENDPOINT + "/lol/league/v4/challengerleagues/by-queue/" + QUEUE;

    public static void main(String[] args) {
        try {
            // 创建 URL 对象
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // 设置请求方法
            connection.setRequestMethod("GET");

            // 添加 API 密钥到请求头
            connection.setRequestProperty("X-Riot-Token", API_KEY);

            // 获取响应状态码
            int responseCode = connection.getResponseCode();
            System.out.println("响应状态码: " + responseCode);

            // 读取响应数据
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // 解析 JSON 数据
                System.out.println("排行榜数据: " + response); // 格式化输出
            } else {
                // 请求失败时输出错误信息
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                String errorLine;
                StringBuilder errorResponse = new StringBuilder();

                while ((errorLine = errorReader.readLine()) != null) {
                    errorResponse.append(errorLine);
                }
                errorReader.close();

                System.out.println("请求失败，错误信息: " + errorResponse.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
