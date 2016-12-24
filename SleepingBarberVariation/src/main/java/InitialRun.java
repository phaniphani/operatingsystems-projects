import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.Semaphore;

/**
 * Created by PV029500 on 12/23/2016.
 */
public class InitialRun {

    static List<Movie> movies = new ArrayList<Movie>();

    static Semaphore cashier = new Semaphore(1);
    static Semaphore customer = new Semaphore(0);
    static int customerCount = 10;
    static Queue<Customer> customerQueue = new LinkedList<Customer>();

    public static void main(String args[]) throws FileNotFoundException{

        Scanner scanner = new Scanner(new File("src/main/resources/Movies.txt"));
        while (scanner.hasNextLine()) {
            String[] movieStr = scanner.nextLine().split("\\\t");
            movies.add(new Movie(movieStr[0], Integer.parseInt(movieStr[1])));
        }

        Customer[] customers = new Customer[10];
        for (int i = 0; i < 10; i++) {
            customers[i] = new Customer(i);
            customerQueue.offer(customers[i]);
            (new Thread(customers[i])).start();
        }

        Thread cashier1 = new Thread(new Cashier("cashier1"));
        Thread cashier2 = new Thread(new Cashier("cashier2"));
        cashier1.start();
        cashier2.start();


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
