import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

/**
 * Created by PV029500 on 12/23/2016.
 */
public class Customer implements Runnable{

    int id;
    InitialRun.Movie movie;
    static Semaphore customer = new Semaphore(0);
    static Queue<Customer> auxiliaryCustomerQ = new LinkedList<Customer>();
    static Queue<Customer> ticketTakerQ = new LinkedList<Customer>();
    static Semaphore ticketTakerLock = new Semaphore(1);
    static int entranceQSize = 10;
    static int totalCustomers = InitialRun.customerCount;
    static int customerCountAtTicket = 0;

    public Customer (int id) {
        this.id = id;
    }

    public void run() {

        try {
            customer.acquire();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void gotoTicketTaker(Customer customer) {
        try {
            ticketTakerLock.acquire();
            if (ticketTakerQ.size() < entranceQSize) {
                System.out.println("Customer " + customer.getId() + " in line to see ticket taker");
                ticketTakerQ.offer(customer);
            } else {
                auxiliaryCustomerQ.offer(customer);
            }
            ticketTakerLock.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public InitialRun.Movie getMovie() {
        return movie;
    }

    public void setMovie(InitialRun.Movie movie) {
        this.movie = movie;
    }
}
