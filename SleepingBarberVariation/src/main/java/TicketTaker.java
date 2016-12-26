import java.util.concurrent.Semaphore;

/**
 * Created by PV029500 on 12/25/2016.
 */
public class TicketTaker implements Runnable{

    public static Semaphore takeTicket = new Semaphore(1);
    static int customerCountAtTicket = 0;

    public void run() {
        try {
            while(Customer.totalCustomers != customerCountAtTicket) {
                takeTicket.acquire();
                Customer customer = Customer.ticketTakerQ.poll();
                if (customer != null) {
                    System.out.println("Ticket taker takes ticket from customer " + customer.getId());
                    customerCountAtTicket++;
                }
                if (Customer.auxiliaryCustomerQ.size() > 0)
                    Customer.ticketTakerQ.offer(Customer.auxiliaryCustomerQ.poll());
                takeTicket.release();
            }
            System.out.println("total time: " + (System.currentTimeMillis() - InitialRun.startTime));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
