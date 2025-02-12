package io.swagger.client.record;

public class RequestRecord {
  private final long startTime;
  private final long latency;
  private final int responseCode;
  private final String requestType;

  public RequestRecord(long startTime, long latency, int responseCode, String requestType) {
    this.startTime = startTime;
    this.latency = latency;
    this.responseCode = responseCode;
    this.requestType = requestType;
  }

  public long getStartTime() {
    return startTime;
  }

  public long getLatency() {
    return latency;
  }

  public int getResponseCode() {
    return responseCode;
  }

  public String getRequestType() {
    return requestType;
  }

  @Override
  public String toString() {
    return startTime + ",POST," + latency + "," + responseCode;
  }
}
