package controller;

import java.util.concurrent.ConcurrentLinkedQueue;
import static java.lang.Thread.currentThread;

public class LinearCalculationThread implements Runnable {

    private Integer x;
    private Integer rightThreshold;
    private final ConcurrentLinkedQueue<Integer> queue;

    public LinearCalculationThread(Integer leftThreshold, Integer rightThreshold, ConcurrentLinkedQueue<Integer> queue) {
        this.x = leftThreshold;
        this.rightThreshold = rightThreshold;
        this.queue = queue;
    }

    @Override
    public void run() {
        while (x <= rightThreshold && !Thread.interrupted()) {
            int y = 5*x + 3;
            queue.add(y);

            try {
                Thread.sleep(250);
            }
            catch (InterruptedException e) {
                break;
            }
            x += 1;
        }
        currentThread().interrupt();
    }

}
