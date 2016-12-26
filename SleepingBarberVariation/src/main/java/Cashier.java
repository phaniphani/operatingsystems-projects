import java.util.Random;

/**
 * Created by PV029500 on 12/23/2016.
 */
public class Cashier implements Runnable{

    public String cashierName;

    public Cashier(String cashierName) {
        this.cashierName = cashierName;
    }

    public void run() {
        try {
            while (InitialRun.customerCount > 0) {
                    InitialRun.cashier.acquire();
                    InitialRun.customerQueueLock.acquire();
                    Customer customer = InitialRun.customerQueue.poll();
                    InitialRun.customerQueueLock.release();
                    InitialRun.customerCount--;
                    if (InitialRun.customerCount >= 0) {
                        int movieId = (new Random()).nextInt(5);
                        InitialRun.Movie movie = InitialRun.movies.get(movieId);
                        while(movie.ticketsLeft == 0){
                            movieId = (new Random()).nextInt(5);
                            movie = InitialRun.movies.get(movieId);
                            System.out.println("Cashier " + cashierName + " is serving customer " + customer.getId());
                            System.out.println(this.cashierName + " is in the loop ");
                        }
                        customer.setMovie(movie);
                        movie.ticketsLeft = movie.ticketsLeft - 1;
                        System.out.println("Customer " + customer.getId() + " buys " + customer.getMovie().movieName
                                + " from cashier " + this.cashierName + " tickets remaining " + movie.ticketsLeft + " customer count "
                                + InitialRun.customerCount);
                    }
                    InitialRun.cashier.release();
                    InitialRun.customer.release();
            }
            System.out.println("Total time: " + (System.currentTimeMillis() - InitialRun.startTime));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
