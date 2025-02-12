package io.swagger.client.client;
import io.swagger.client.ApiClient;
import io.swagger.client.api.SkiersApi;
import io.swagger.client.record.RequestRecord;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


/**
 * MultiThreadClient
 * Create a multi-thread client to send requests to the server
 */

public class MultiThreadClient {
  private static final int NUM_THREADS = 32;
  private static final int NUM_REQUESTS = 200_000;
  private static final int REQUESTS_PER_THREAD = 1000;

  private static final List<RequestRecord> records = Collections.synchronizedList(new ArrayList<>());

  /**
   * Main method to start the client
   * @param args
   * @throws InterruptedException
   */
  public static void main(String[] args) throws InterruptedException {
    EventGenerator eventGenerator = new EventGenerator();
    ExecutorService eventExecutor = Executors.newSingleThreadExecutor();
    eventExecutor.execute(eventGenerator);
    eventExecutor.shutdown();


    ApiClient apiClient = new ApiClient();
//    apiClient.setBasePath("http://localhost:8081/6650Lab2_war_exploded");
//    apiClient.setBasePath("http://107.22.1.205:8080/6650Lab2-1.0-SNAPSHOT");
//    apiClient.setBasePath("http://ec2-user@ec2-user@ec2-35-87-100-165.us-west-2.compute.amazonaws.com:8080/cs6650lab2_war/skiers/");
    apiClient.setBasePath("http://ec2-user@ec2-34-217-179-217.us-west-2.compute.amazonaws.com:8080/cs6650lab2_war/skiers/");

    long startTime = System.currentTimeMillis();

    AtomicInteger successCount = new AtomicInteger(0);
    AtomicInteger failCount = new AtomicInteger(0);
    AtomicInteger totalRequests = new AtomicInteger(0);

    ExecutorService requestExecutor = Executors.newFixedThreadPool(NUM_THREADS);

    for (int i = 0; i < NUM_THREADS; i++) {
      RequestTask requestTask = new RequestTask(new SkiersApi(apiClient), successCount, failCount, REQUESTS_PER_THREAD, records);
      requestExecutor.execute(requestTask);
    }

    requestExecutor.shutdown();

    try {
      requestExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    } catch (InterruptedException e) {
      System.out.println("Thread interrupted");
      Thread.currentThread().interrupt();
    }

    totalRequests.set(successCount.get() + failCount.get());
    System.out.println("Total requests: " + totalRequests.get());

    while (successCount.get() + failCount.get() < NUM_REQUESTS) {
      ExecutorService newRequestExecutor = Executors.newFixedThreadPool(200);
      for (int i = 0; i < 200; i++) {
        RequestTaskWithoutLimit requestTask = new RequestTaskWithoutLimit(new SkiersApi(apiClient), successCount, failCount, records);
        newRequestExecutor.execute(requestTask);
      }
      newRequestExecutor.shutdown();

      try {
        newRequestExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
      } catch (InterruptedException e) {
        System.out.println("Thread interrupted");
        Thread.currentThread().interrupt();
      }
    }

    long endTime = System.currentTimeMillis();
    long totalDurationInMs = endTime - startTime;
    double totalDurationInSec = totalDurationInMs / 1000.0;
    double throughput = NUM_REQUESTS / totalDurationInSec;

    System.out.println("All requests finished");
    System.out.println("Successful requests: " + successCount.get());
    System.out.println("Failed requests: " + failCount.get());
    System.out.println("Total duration: " + totalDurationInMs + "ms");
    System.out.println("Throughput: " + throughput + " requests/second");

    writeRecordsToCSV(records);
    analyzePerformance(records);
  }

  /**
   * Write the request records to a CSV file
   * @param records
   */
  private static void writeRecordsToCSV(List<RequestRecord> records) {
    try (PrintWriter writer = new PrintWriter("request_records.csv")) {
      writer.println("start_time,request_type,latency,response_code");
      for (RequestRecord record : records) {
        writer.println(record.getStartTime() + ","
            + record.getRequestType() + ","
            + record.getLatency() + ","
            + record.getResponseCode());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  /**
   * Analyze the performance of the requests
   * @param records
   */
  private static void analyzePerformance(List<RequestRecord> records) {
    if (records.isEmpty()) {
      System.out.println("No records to analyze.");
      return;
    }

    List<Long> latencies = records.stream()
        .map(RequestRecord::getLatency)
        .sorted()
        .collect(Collectors.toList());

    long totalLatency = latencies.stream().mapToLong(Long::longValue).sum();
    double mean = (double) totalLatency / latencies.size();

    long median = latencies.size() % 2 == 0
        ? (latencies.get(latencies.size() / 2) + latencies.get(latencies.size() / 2 - 1)) / 2
        : latencies.get(latencies.size() / 2);

    long p99 = latencies.get((int) Math.ceil(latencies.size() * 0.99) - 1);
    long min = latencies.get(0);
    long max = latencies.get(latencies.size() - 1);

    System.out.println("Mean response time: " + mean + " ms");
    System.out.println("Median response time: " + median + " ms");
    System.out.println("P99 response time: " + p99 + " ms");
    System.out.println("Min response time: " + min + " ms");
    System.out.println("Max response time: " + max + " ms");
  }
}
