package io.swagger.client.client;
import io.swagger.client.api.SkiersApi;
import io.swagger.client.record.RequestRecord;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RequestTaskWithoutLimit extends Request {
  public RequestTaskWithoutLimit(SkiersApi apiInstance, AtomicInteger successCount,
      AtomicInteger failCount, List<RequestRecord> records) {
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
