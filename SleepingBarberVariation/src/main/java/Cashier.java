import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * Created by PV029500 on 12/23/2016.
 */
public class Cashier implements Runnable{

    public static Semaphore movieTicketLock = new Semaphore(1);
    static Semaphore cashier = new Semaphore(2);
    static Semaphore customerQueueLock = new Semaphore(1);
    public String cashierName;

    public Cashier(String cashierName) {
        this.cashierName = cashierName;
    }

    public void run() {
        System.out.println("Box Office Agent " + this.cashierName + " created");
        try {
            while (InitialRun.customerCount > 0) {
                    cashier.acquire();
                    customerQueueLock.acquire();
                    Customer customer = InitialRun.customerQueue.poll();
                    InitialRun.customerCount--;
                    customerQueueLock.release();
                    if (InitialRun.customerCount >= 0) {
                        System.out.println("Box Office " + cashierName + " is serving customer " + customer.getId());
                        int movieId = (new Random()).nextInt(5);
                        InitialRun.Movie movie = InitialRun.movies.get(movieId);
                        while(movie.ticketsLeft == 0){
                            movieId = (new Random()).nextInt(5);
                            movie = InitialRun.movies.get(movieId);
                        }
                        customer.setMovie(movie);
                        movieTicketLock.acquire();
                        movie.ticketsLeft = movie.ticketsLeft - 1;
                        System.out.println("Customer " + customer.getId() + " buys " + customer.getMovie().movieName
                                + " from Box Office " + this.cashierName);
                        movieTicketLock.release();
                    }
                    Customer.customer.release();
                    cashier.release();
                    Customer.gotoTicketTaker(customer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
