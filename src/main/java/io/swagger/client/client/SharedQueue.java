package io.swagger.client.client;

import io.swagger.client.model.LiftRide;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * SharedQueue
 * Thread-safe shared queue for storing LiftRide objects
 * EventGenerator will generate LiftRide objects and put them into the queue
 * Request threads will take LiftRide objects from the queue and send them to the server
 */

public class SharedQueue {
  public static final BlockingDeque<LiftRide> queue = new LinkedBlockingDeque<>(200_000);
}
