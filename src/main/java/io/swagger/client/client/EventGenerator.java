package io.swagger.client.client;

import io.swagger.client.model.LiftRide;
import java.util.Random;

/**
 * EventGenerator
 * Assign a specific thread to generate all the events
 */
public class EventGenerator implements Runnable{

  private final Random random = new Random();
  private static final int NUM_REQUESTS = 200_000;

  /**
   * Precompute all the events
   * Generate 200,000 events
   * Single thread, so we can use int instead of AtomicInteger
   */
  @Override
  public void run() {
    int eventCount = 0;
    while (eventCount < NUM_REQUESTS) {
      try {
        LiftRide liftRide = new LiftRide();
        liftRide.setTime(random.nextInt(360) + 1);
        liftRide.setLiftID(random.nextInt(40) + 1);
        SharedQueue.queue.put(liftRide);
        eventCount++;
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        throw new RuntimeException(e);
      }
    }
    System.out.println("Event generation finished");
  }
}
