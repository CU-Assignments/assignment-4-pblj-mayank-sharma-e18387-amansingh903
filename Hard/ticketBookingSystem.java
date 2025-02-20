import java.util.*;

class TicketBookingSystem {
    private static final int TOTAL_SEATS = 10;
    private final boolean[] seats = new boolean[TOTAL_SEATS];

    public synchronized boolean bookSeat(int seatNumber, String customerType) {
        if (seatNumber < 0 || seatNumber >= TOTAL_SEATS) {
            System.out.println(customerType + " booking failed: Invalid seat number.");
            return false;
        }
        if (!seats[seatNumber]) {
            seats[seatNumber] = true;
            System.out.println(customerType + " successfully booked seat " + seatNumber);
            return true;
        } else {
            System.out.println(customerType + " booking failed: Seat " + seatNumber + " is already booked.");
            return false;
        }
    }
}

class BookingThread extends Thread {
    private final TicketBookingSystem system;
    private final int seatNumber;
    private final String customerType;

    public BookingThread(TicketBookingSystem system, int seatNumber, String customerType, int priority) {
        this.system = system;
        this.seatNumber = seatNumber;
        this.customerType = customerType;
        setPriority(priority);
    }

    @Override
    public void run() {
        system.bookSeat(seatNumber, customerType);
    }
}

public class Main {
    public static void main(String[] args) {
        TicketBookingSystem system = new TicketBookingSystem();
        Random random = new Random();

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            int seatNumber = random.nextInt(10);
            String customerType = (i % 3 == 0) ? "VIP" : "Regular";
            int priority = (customerType.equals("VIP")) ? Thread.MAX_PRIORITY : Thread.MIN_PRIORITY;
            threads.add(new BookingThread(system, seatNumber, customerType, priority));
        }

        for (Thread thread : threads) {
            thread.start();
        }
    }
}
