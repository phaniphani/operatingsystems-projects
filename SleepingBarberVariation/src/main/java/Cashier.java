import com.sun.org.apache.xml.internal.security.Init;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * Created by PV029500 on 12/23/2016.
 */
public class Cashier implements Runnable{

    static Semaphore cashier = new Semaphore(InitialRun.cashierCount);
    static Semaphore customerQueueLock = new Semaphore(1);
    private String cashierName;

    Cashier(String cashierName) {
        this.cashierName = cashierName;
    }

    public void run() {
        try {
//            InitialRun.gate.await();
//            System.out.println("Box Office Agent " + this.cashierName + " created");
            while (InitialRun.customerCount > 0) {
                    cashier.acquire();
                    customerQueueLock.acquire();
                    Customer customer = InitialRun.customerQueue.poll();
                    InitialRun.customerCount--;
                    customerQueueLock.release();
                    if (InitialRun.customerCount >= 0) {
//                        System.out.println("customer " + InitialRun.customerCount);
//                        System.out.println("Box Office " + cashierName + " is serving customer " + customer.getId());
//                        int movieId = (new Random()).nextInt(5);
                        InitialRun.Movie movie = InitialRun.movies.get(0);
//                        while(movie.ticketsLeft == 0){
////                            movieId = (new Random()).nextInt(5);
////                            movie = InitialRun.movies.get(movieId);
//                        }
                        customer.setMovie(movie);
                        movie.ticketsLeft = movie.ticketsLeft - 1;
//                        System.out.println("Customer " + customer.getId() + " buys " + customer.getMovie().movieName
//                                + " from Box Office " + this.cashierName);
                    }
//                    Customer.customer.release();
                    cashier.release();
//                    if (customer != null)
//                    Customer.gotoTicketTaker(customer);
            }
//            System.out.println("Ended " + (System.currentTimeMillis() - InitialRun.beginTime));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
