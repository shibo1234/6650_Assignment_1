package io.swagger.client.client;
import io.swagger.client.api.SkiersApi;
import io.swagger.client.record.RequestRecord;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class RequestTaskWithoutLimit extends Request {
  public RequestTaskWithoutLimit(SkiersApi apiInstance, AtomicInteger successCount,
      AtomicInteger failCount, BlockingQueue<RequestRecord> records) {
    super(apiInstance, successCount, failCount, records);
  }

  @Override
  public void run() {
    while (true) {
//      processRequest();
      if (!processRequest()) {
        break;
      }
    }
  }
}
