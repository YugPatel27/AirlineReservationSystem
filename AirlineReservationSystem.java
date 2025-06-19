import java.util.Scanner;

class Flight {
    private final String flightNumber;
    private final String origin;
    private final String destination;
    private final int totalSeats;
    private int availableSeats;
    private final double ticketPrice;
    private final String time;
    private final String venue;
    private final double discount;

    public Flight(String flightNumber, String origin, String destination, int totalSeats, double ticketPrice, String time, String venue, double discount) {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
        this.ticketPrice = ticketPrice;
        this.time = time;
        this.venue = venue;
        this.discount = discount;
    }

    public boolean bookSeat() throws Exception {
        if (availableSeats > 0) {
            availableSeats--;
            return true;
        } else {
            throw new Exception("No available seats on this flight.");
        }
    }

    public void displayFlightDetails() {
        System.out.println("Flight Number: " + flightNumber);
        System.out.println("Origin: " + origin);
        System.out.println("Destination: " + destination);
        System.out.println("Total Seats: " + totalSeats);
        System.out.println("Available Seats: " + availableSeats);
        System.out.println("Ticket Price: $" + ticketPrice);
        System.out.println("Time: " + time);
        System.out.println("Venue: " + venue);
        System.out.println("Discount: " + discount + "%");
        System.out.println("---------------------------------------------------");
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public String getFlightNumber() {
        return flightNumber;
    }
}

class Passenger {
    private final String name;
    private final int age;
    private final String passportNumber;
    private Flight bookedFlight;

    public Passenger(String name, int age, String passportNumber) {
        this.name = name;
        this.age = age;
        this.passportNumber = passportNumber;
    }

    public void bookFlight(Flight flight) throws Exception {
        if (flight == null) {
            throw new Exception("Invalid flight selected.");
        }
        this.bookedFlight = flight;
    }

    public void displayPassengerInfo() {
        System.out.println("Passenger Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Passport Number: " + passportNumber);
    }

    public Flight getBookedFlight() {
        return bookedFlight;
    }
}

public class AirlineReservationSystem {
    private Flight[] flights = new Flight[3];
    private Passenger[] passengers = new Passenger[10];
    private int passengerCount = 0;

    public static void main(String[] args) {
        new AirlineReservationSystem().start();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        initializeFlights();

        System.out.println("\nWelcome to the Airline Reservation System!");

        while (true) {
            System.out.println("\nPlease select an option:");
            System.out.println("1. View Flight Details");
            System.out.println("2. Book a Flight");
            System.out.println("3. Display All Passengers");
            System.out.println("4. View Booked Tickets");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch(choice) {
                case 1:
                    viewFlightDetails();
                    break;
                case 2:
                    bookFlight(scanner);
                    break;
                case 3:
                    displayAllPassengers();
                    break;
                case 4:
                    displayBookedTickets();
                    break;
                case 5:
                    System.out.println("Thank you for using the Airline Reservation System!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
                    break;
            }
        }
    }

    private void initializeFlights() {
        flights[0] = new Flight("AI201", "Delhi", "Mumbai", 100, 3500, "09:00 AM", "Indira Gandhi International", 10);
        flights[1] = new Flight("AI202", "Bangalore", "Chennai", 150, 2200, "11:30 AM", "Kempegowda International", 12);
        flights[2] = new Flight("AI203", "Kolkata", "Hyderabad", 200, 2800, "02:30 PM", "Netaji Subhas Chandra Bose International", 8);
    }

    private void viewFlightDetails() {
        System.out.println("\nAvailable Flights:");
        for (Flight flight : flights) {
            flight.displayFlightDetails();
        }
    }

    private void bookFlight(Scanner scanner) {
        System.out.print("\nEnter Passenger Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Passenger Age: ");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Passport Number: ");
        String passportNumber = scanner.nextLine();

        Passenger newPassenger = new Passenger(name, age, passportNumber);

        System.out.println("\nAvailable Flights for Booking:");
        boolean foundAvailableFlight = false;

        for (int i = 0; i < flights.length; i++) {
            Flight flight = flights[i];
            if (flight.getAvailableSeats() > 0) {
                foundAvailableFlight = true;
                System.out.println((i + 1) + ". Flight " + flight.getFlightNumber() + " - $" + flight.getTicketPrice() + " - " + flight.getAvailableSeats() + " seats available");
            }
        }

        if (!foundAvailableFlight) {
            System.out.println("Sorry, no flights are available for booking.");
            return;
        }

        System.out.print("\nSelect flight number to book (1, 2, or 3): ");
        int flightChoice = scanner.nextInt();
        scanner.nextLine();

        if (flightChoice >= 1 && flightChoice <= flights.length) {
            Flight selectedFlight = flights[flightChoice - 1];

            try {
                if (selectedFlight.bookSeat()) {
                    System.out.println("Booking successful! Your seat has been reserved.");
                    newPassenger.bookFlight(selectedFlight);

                    if (passengerCount < passengers.length) {
                        passengers[passengerCount] = newPassenger;
                        passengerCount++;
                    } else {
                        System.out.println("Passenger list is full. Unable to add the new passenger.");
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Invalid flight choice. Please try again.");
        }
    }

    private void displayAllPassengers() {
        System.out.println("\nAll Passengers:");
        if (passengerCount == 0) {
            System.out.println("No passengers have booked flights yet.");
        } else {
            for (int i = 0; i < passengerCount; i++) {
                passengers[i].displayPassengerInfo();
            }
        }
    }

    private void displayBookedTickets() {
        System.out.println("\nBooked Tickets:");
        if (passengerCount == 0) {
            System.out.println("No tickets have been booked yet.");
        } else {
            for (int i = 0; i < passengerCount; i++) {
                Passenger passenger = passengers[i];
                Flight bookedFlight = passenger.getBookedFlight();

                try {
                    if (bookedFlight != null) {
                        System.out.println("\nPassenger Details:\n");
                        passenger.displayPassengerInfo();
                        System.out.println("Booked Flight: " + bookedFlight.getFlightNumber());
                        System.out.println("Available Seats: " + bookedFlight.getAvailableSeats());
                        System.out.println("Ticket Price: $" + bookedFlight.getTicketPrice());
                        System.out.println("Flight Number: " + bookedFlight.getFlightNumber());
                        System.out.println("---------------------------------------------------");
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}