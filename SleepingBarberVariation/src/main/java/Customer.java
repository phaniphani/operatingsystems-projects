/**
 * Created by PV029500 on 12/23/2016.
 */
public class Customer implements Runnable{

    int id;
    InitialRun.Movie movie;

    public Customer (int id) {
        this.id = id;
    }

    public void run() {

        try {
            InitialRun.customer.acquire();
//
//            InitialRun.ticketTakerLock.acquire();
//            if (InitialRun.ticketTakerQ.size() < InitialRun.entranceQSize) {
////                    InitialRun.ticketTakerQ.offer(customer);
//                } else {
////                    InitialRun.auxiliaryCustomerQ.offer(customer);
//                }
//            InitialRun.ticketTakerLock.release();

        } catch (Exception e) {
            System.out.println(e.getMessage());
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
