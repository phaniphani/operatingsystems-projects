import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

/**
 * Created by PV029500 on 12/23/2016.
 */
public class InitialRun {

    static List<Movie> movies = new ArrayList<Movie>();
    static int customerCount = 30;
    static Queue<Customer> customerQueue = new LinkedList<Customer>();
    static long startTime = System.currentTimeMillis();
    static Semaphore cashierLock = new Semaphore(0);
    static final CyclicBarrier gate = new CyclicBarrier(3);
    public static void main(String args[]) throws FileNotFoundException, InterruptedException, BrokenBarrierException{

        Scanner scanner = new Scanner(new File("src/main/resources/Movies.txt"));
        while (scanner.hasNextLine()) {
            String[] movieStr = scanner.nextLine().split("\\\t");
            movies.add(new Movie(movieStr[0], Integer.parseInt(movieStr[1])));
        }

        Customer[] customers = new Customer[customerCount];
        for (int i = 0; i < customerCount; i++) {
            customers[i] = new Customer(i);
            customerQueue.offer(customers[i]);
        }

        Thread cashier1 = new Thread(new Cashier("Agent 1"));
        Thread cashier2 = new Thread(new Cashier("Agent 2"));
        cashier1.start();
        cashier2.start();
        gate.await();
        (new Thread(new TicketTaker())).start();

        for (int i = 0; i < customerCount; i++)
            (new Thread(customers[i])).start();

    }

    public static class Movie {

        String movieName;
        int ticketsLeft;

        public Movie(String movieName, int ticketsLeft) {
            this.movieName = movieName;
            this.ticketsLeft = ticketsLeft;
        }
    }
}
