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
                Customer customer = InitialRun.customerQueue.poll();
                if (customer != null) {
                    int movieId = (new Random()).nextInt(2);
                    InitialRun.Movie movie = InitialRun.movies.get(movieId);
                    InitialRun.cashier.acquire();
                    InitialRun.customerCount--;
                    customer.setMovie(movie);
                    InitialRun.cashier.release();
                    System.out.println("Customer " + customer.getId() + " buys " + customer.getMovie().movieName
                    + " from cashier " + this.cashierName);
                    InitialRun.customer.release();
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
