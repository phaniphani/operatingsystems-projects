package sample;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;

/**
 * Created by PV029500 on 12/23/2016.
 */
class CrunchifySemaphoreMutexTutorial {

    static LinkedList<String> crunchifyList = new LinkedList<String>();
    static Semaphore semaphore = new Semaphore(0);
    static Semaphore mutex = new Semaphore(1);
    static class CrunchifyProducer extends Thread {
        public void run() {
            int counter = 1;
            try {
                while (counter < 11) {
                    String threadName = Thread.currentThread().getName();
                    mutex.acquire();
                    crunchifyList.add(threadName + String.valueOf(counter));
                    System.out.println("Producer is producing new value: " + threadName);
                    counter++;
                    semaphore.release();
                }
            } catch (Exception x) {
                x.printStackTrace();
            }
        }
    }
    static class CrunchifyConsumer extends Thread {
        String consumerName;
        public CrunchifyConsumer(String name) {
            this.consumerName = name;
        }
        public void run() {
            try {
                semaphore.acquire();
                String result = "";
                for (String value : crunchifyList) {
                    result = value + ",";
                }
                System.out.println(consumerName + " consumes value: " + result + "crunchifyList.size(): "
                        + crunchifyList.size() + "\n");
                mutex.release();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
