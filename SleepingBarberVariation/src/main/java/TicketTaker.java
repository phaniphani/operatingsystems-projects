/**
 * Created by PV029500 on 12/25/2016.
 */
public class TicketTaker implements Runnable{

    public void run() {
        try {

            while(InitialRun.ticketTakerQ.size() > 0) {

                Customer customer = InitialRun.ticketTakerQ.poll();
                System.out.println("Ticket taker takes ticket from customer " + customer.getId());
            }
        } catch (Exception e) {

        }
    }
}
