package io.swagger.client;

import io.swagger.client.api.SkiersApi;
import io.swagger.client.model.LiftRide;
import java.util.Random;

public class Main {
  public static void main(String[] args) {

    ApiClient apiClient = new ApiClient();
//    apiClient.setBasePath("http://localhost:8081/6650Lab2_war_exploded");

    apiClient.setBasePath("http://54.82.64.37:8080/6650Lab2-1.0-SNAPSHOT");
    SkiersApi apiInstance = new SkiersApi(apiClient);

    int successCount = 0;
    int failCount = 0;

    long startTime = System.currentTimeMillis();
    Random random = new Random();
    for (int i = 0; i < 1000; i++) {
      LiftRide liftRide = new LiftRide();
      liftRide.setTime(random.nextInt(360) + 1);
      liftRide.setLiftID(random.nextInt(40) + 1);

      Integer resortID = random.nextInt(10) + 1;
      String seasonID = "2025";
      String dayID = "1";
      Integer skierID = random.nextInt(100_000) + 1;

      boolean success = false;
      for (int retry = 0; retry < 5; retry++) {
        try {
          apiInstance.writeNewLiftRide(liftRide, resortID, seasonID, dayID, skierID);
          success = true;
          successCount++;
          break;
        } catch (ApiException e) {
          System.out.println("http status code" + e.getCode());
          System.out.println("retry count: " + (retry + 1));
          e.printStackTrace();
          if (retry == 4) {
            failCount++;
          }
        }
      }
    }
    long endTime = System.currentTimeMillis();
    long duration = endTime - startTime;

    System.out.println("请求发送完成！");
    System.out.println("成功请求数: " + successCount);
    System.out.println("失败请求数: " + failCount);
    System.out.println("总运行时间（毫秒）: " + duration);
  }
}
