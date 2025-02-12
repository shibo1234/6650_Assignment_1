package io.swagger.client.client;
import io.swagger.client.ApiException;
import io.swagger.client.api.SkiersApi;
import io.swagger.client.model.LiftRide;
import io.swagger.client.record.RequestRecord;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * RequestTask
 * Send a batch of requests to the server
 */
public class RequestTask extends Request {
  private final int requestsPerThread;

  public RequestTask(SkiersApi apiInstance, AtomicInteger successCount, AtomicInteger failCount, int requestsPerThread, List<RequestRecord> records) {
    super(apiInstance, successCount, failCount, records);
    this.requestsPerThread = requestsPerThread;
  }

  @Override
  public void run() {
    int completedRequests = 0;
    while (completedRequests < requestsPerThread) {
//      processRequest();
      if (!processRequest()) {
        break;
      }
      completedRequests++;
    }
  }
}
