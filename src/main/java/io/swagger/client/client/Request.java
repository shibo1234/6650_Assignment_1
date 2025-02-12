package io.swagger.client.client;
import io.swagger.client.ApiException;
import io.swagger.client.api.SkiersApi;
import io.swagger.client.model.LiftRide;
import io.swagger.client.record.RequestRecord;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Request implements Runnable {
  protected final SkiersApi apiInstance;
  protected final Random random = new Random();
  protected final AtomicInteger successCount;
  protected final AtomicInteger failCount;
  protected final List<RequestRecord> records;

  public Request(SkiersApi apiInstance, AtomicInteger successCount, AtomicInteger failCount, List<RequestRecord> records) {
    this.apiInstance = apiInstance;
    this.successCount = successCount;
    this.failCount = failCount;
    this.records = records;
  }

  private boolean sendRequest(LiftRide liftride) {

    long startTime = System.currentTimeMillis();

    int retry = 0;
    while (retry < 5) {
      Integer resortID = random.nextInt(10) + 1;
      String seasonID = "2025";
      String dayID = "1";
      Integer skierID = random.nextInt(100_000) + 1;
      try {
        apiInstance.writeNewLiftRide(liftride, resortID, seasonID, dayID, skierID);

        long endTime = System.currentTimeMillis();
        long latency = endTime - startTime;

        synchronized (records) {
          records.add(new RequestRecord(startTime, latency, 201, "POST"));
        }
        return true;
      } catch (ApiException e) {
        retry++;
        if (retry == 5) {
          System.out.println("http status code" + e.getCode());
          System.out.println("retry count: " + retry);

          synchronized (records) {
            records.add(new RequestRecord(startTime, 0, e.getCode(), "POST"));
          }
          e.printStackTrace();
        }
      }
    }
    return false;
  }

  protected boolean processRequest() {
    try {
      LiftRide liftRide = SharedQueue.queue.poll(5, TimeUnit.SECONDS);
      if (liftRide == null) {
        return false;
      }
      boolean success = sendRequest(liftRide);
      if (success) {
        successCount.incrementAndGet();
      } else {
        failCount.incrementAndGet();
      }
      return true;
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
//      throw new RuntimeException(e);
      return false;
    }
  }

  @Override
  public abstract void run();
}
