import java.util.*;
import java.time.*;
import java.time.temporal.*;

// ════════════════════════════════════════════════════════════════
//  CAR RENTAL SERVICE — Single File Java OOP Project
//  OOP Concepts: Abstraction | Encapsulation | Inheritance | Polymorphism
// ════════════════════════════════════════════════════════════════

// ── Payment Interface (Abstraction) ─────────────────────────────
interface Payment {
    boolean processPayment(double amount);
    String  getPaymentMode();
}

// ── CashPayment (Implements Payment) ────────────────────────────
class CashPayment implements Payment {
    private String receivedBy;
    public CashPayment(String receivedBy) { this.receivedBy = receivedBy; }

    @Override
    public boolean processPayment(double amount) {
        System.out.printf("  [CASH] Rs.%.2f received by %s%n", amount, receivedBy);
        return true;
    }
    @Override public String getPaymentMode() { return "Cash"; }
}

// ── CardPayment (Implements Payment) ────────────────────────────
class CardPayment implements Payment {
    private String cardHolder, last4;
    public CardPayment(String cardHolder, String last4) {
        this.cardHolder = cardHolder;
        this.last4      = last4;
    }

    @Override
    public boolean processPayment(double amount) {
        System.out.printf("  [CARD] Rs.%.2f charged to card **** %s (%s)%n", amount, last4, cardHolder);
        return true;
    }
    @Override public String getPaymentMode() { return "Card (**** " + last4 + ")"; }
}

// ── Abstract Vehicle (Abstraction + Encapsulation) ───────────────
abstract class Vehicle {
    private final String vehicleId, brand, model, numberPlate;
    private final int year;
    private boolean isAvailable;
    private static int idCtr = 200;

    public Vehicle(String brand, String model, int year, String plate) {
        this.vehicleId   = "VH" + (++idCtr);
        this.brand       = brand;
        this.model       = model;
        this.year        = year;
        this.numberPlate = plate;
        this.isAvailable = true;
    }

    // Abstract methods — subclass decides the rate and type (Polymorphism)
    public abstract double getRentalRatePerDay();
    public abstract String getVehicleType();

    public double calculateCost(int days) { return getRentalRatePerDay() * days; }

    public void markRented()   { isAvailable = false; }
    public void markReturned() { isAvailable = true;  }

    public String  getVehicleId()   { return vehicleId;   }
    public String  getBrand()       { return brand;       }
    public String  getModel()       { return model;       }
    public String  getNumberPlate() { return numberPlate; }
    public boolean isAvailable()    { return isAvailable; }

    @Override
    public String toString() {
        return String.format("[%-4s] %-6s | %-10s %-12s (%d) | Plate: %-12s | Rs.%.0f/day | %s",
            vehicleId, getVehicleType(), brand, model, year, numberPlate,
            getRentalRatePerDay(), isAvailable ? "Available" : "Rented");
    }
}

// ── Car (Inheritance + Polymorphism) ────────────────────────────
class Car extends Vehicle {
    private double ratePerDay;

    public Car(String brand, String model, int year, String plate, int seats, double rate) {
        super(brand, model, year, plate);
        this.ratePerDay = rate;
    }
    @Override public double getRentalRatePerDay() { return ratePerDay; }
    @Override public String getVehicleType()      { return "Car"; }
}

// ── Bike (Inheritance + Polymorphism) ───────────────────────────
class Bike extends Vehicle {
    private double ratePerDay;

    public Bike(String brand, String model, int year, String plate, String bikeType, double rate) {
        super(brand, model, year, plate);
        this.ratePerDay = rate;
    }
    @Override public double getRentalRatePerDay() { return ratePerDay; }
    @Override public String getVehicleType()      { return "Bike"; }
}

// ── SUV (Inheritance + Polymorphism) ────────────────────────────
class SUV extends Vehicle {
    private double ratePerDay;

    public SUV(String brand, String model, int year, String plate, boolean has4WD, double rate) {
        super(brand, model, year, plate);
        this.ratePerDay = rate;
    }
    @Override public double getRentalRatePerDay() { return ratePerDay; }
    @Override public String getVehicleType()      { return "SUV"; }
}

// ── Customer (Encapsulation) ─────────────────────────────────────
class Customer {
    private final String customerId, name, phone, licenseNo;
    private int activeRentals = 0;
    private static int ctr = 300;

    public Customer(String name, String phone, String license) {
        this.customerId = "C" + (++ctr);
        this.name       = name;
        this.phone      = phone;
        this.licenseNo  = license;
    }

    public void incrementRentals()   { activeRentals++; }
    public void decrementRentals()   { if (activeRentals > 0) activeRentals--; }
    public boolean canRent()         { return activeRentals < 2; }

    public String getCustomerId()    { return customerId;    }
    public String getName()          { return name;          }
    public String getPhone()         { return phone;         }
    public String getLicenseNo()     { return licenseNo;     }
    public int    getActiveRentals() { return activeRentals; }

    @Override
    public String toString() {
        return customerId + " | " + name + " | " + phone + " | DL: " + licenseNo;
    }
}

// ── Rental (Association: Customer <-> Vehicle) ───────────────────
class Rental {
    private final String    rentalId;
    private final Customer  customer;
    private final Vehicle   vehicle;
    private final LocalDate startDate;
    private LocalDate returnDate;
    private boolean   isReturned = false;
    private double    totalAmount;
    private String    paymentMode;
    private static int rCtr = 500;

    public Rental(Customer c, Vehicle v, LocalDate start) {
        this.rentalId  = "R" + (++rCtr);
        this.customer  = c;
        this.vehicle   = v;
        this.startDate = start;
    }

    public double closeRental(LocalDate ret, String mode) {
        this.returnDate  = ret;
        this.isReturned  = true;
        this.paymentMode = mode;
        long days = ChronoUnit.DAYS.between(startDate, ret);
        if (days < 1) days = 1;
        this.totalAmount = vehicle.calculateCost((int) days);
        return totalAmount;
    }

    public void printReceipt() {
        System.out.println("\n  ========== RENTAL RECEIPT ==========");
        System.out.println("  Rental ID : " + rentalId);
        System.out.println("  Customer  : " + customer.getName());
        System.out.println("  Vehicle   : " + vehicle.getBrand() + " " + vehicle.getModel()
                           + " [" + vehicle.getNumberPlate() + "]");
        System.out.println("  From      : " + startDate);
        if (isReturned) {
            long days = ChronoUnit.DAYS.between(startDate, returnDate);
            if (days < 1) days = 1;
            System.out.println("  To        : " + returnDate);
            System.out.println("  Days      : " + days);
            System.out.printf ("  Rate/Day  : Rs.%.2f%n", vehicle.getRentalRatePerDay());
            System.out.printf ("  Total     : Rs.%.2f%n", totalAmount);
            System.out.println("  Payment   : " + paymentMode);
            System.out.println("  Status    : CLOSED");
        } else {
            System.out.println("  Status    : ACTIVE");
        }
        System.out.println("  =====================================\n");
    }

    public String   getRentalId()    { return rentalId;    }
    public Customer getCustomer()    { return customer;    }
    public Vehicle  getVehicle()     { return vehicle;     }
    public boolean  isReturned()     { return isReturned;  }
    public double   getTotalAmount() { return totalAmount; }

    @Override
    public String toString() {
        return rentalId + " | " + customer.getName() + " -> "
            + vehicle.getBrand() + " " + vehicle.getModel()
            + " | From: " + startDate
            + " | " + (isReturned ? "Returned" : "Active");
    }
}

// ── RentalService ────────────────────────────────────────────────
class RentalService {
    private final String agencyName;
    private final List<Vehicle>  fleet     = new ArrayList<>();
    private final List<Customer> customers = new ArrayList<>();
    private final List<Rental>   rentals   = new ArrayList<>();

    public RentalService(String name) { this.agencyName = name; }

    public void addVehicle(Vehicle v) {
        fleet.add(v);
        System.out.println("  Added: " + v.getBrand() + " " + v.getModel() + " [" + v.getVehicleId() + "]");
    }

    public Customer registerCustomer(String name, String phone, String license) {
        Customer c = new Customer(name, phone, license);
        customers.add(c);
        System.out.println("  Registered: " + c.getName() + " [" + c.getCustomerId() + "]");
        return c;
    }

    public void showAvailableVehicles() {
        System.out.println("\n  --- Available Vehicles ---");
        boolean found = false;
        for (Vehicle v : fleet) {
            if (v.isAvailable()) { System.out.println("  " + v); found = true; }
        }
        if (!found) System.out.println("  No vehicles available right now.");
    }

    public void showAllVehicles() {
        System.out.println("\n  --- Complete Fleet ---");
        for (Vehicle v : fleet) System.out.println("  " + v);
    }

    public void showAllCustomers() {
        System.out.println("\n  --- Customers ---");
        for (Customer c : customers)
            System.out.println("  " + c + " | Active Rentals: " + c.getActiveRentals());
    }

    public Rental rentVehicle(Customer customer, String vehicleId, LocalDate start) {
        if (!customer.canRent()) {
            System.out.println("  ERROR: " + customer.getName() + " already has 2 active rentals.");
            return null;
        }
        Vehicle v = findVehicleById(vehicleId);
        if (v == null)        { System.out.println("  ERROR: Vehicle " + vehicleId + " not found."); return null; }
        if (!v.isAvailable()) { System.out.println("  ERROR: Vehicle is already rented."); return null; }

        v.markRented();
        customer.incrementRentals();
        Rental r = new Rental(customer, v, start);
        rentals.add(r);
        System.out.println("  SUCCESS: " + customer.getName() + " rented "
            + v.getBrand() + " " + v.getModel() + " | Rental ID: " + r.getRentalId());
        return r;
    }

    public void returnVehicle(String rentalId, LocalDate ret, Payment payment) {
        Rental r = findRentalById(rentalId);
        if (r == null)      { System.out.println("  ERROR: Rental ID not found."); return; }
        if (r.isReturned()) { System.out.println("  This rental is already closed."); return; }

        double amount = r.closeRental(ret, payment.getPaymentMode());
        r.getVehicle().markReturned();
        r.getCustomer().decrementRentals();
        System.out.println("  Processing return...");
        if (payment.processPayment(amount)) r.printReceipt();
    }

    public void showRentalHistory() {
        System.out.println("\n  --- Rental History ---");
        if (rentals.isEmpty()) { System.out.println("  No rentals yet."); return; }
        for (Rental r : rentals) System.out.println("  " + r);
    }

    public void showActiveRentals() {
        System.out.println("\n  --- Active Rentals ---");
        boolean found = false;
        for (Rental r : rentals) {
            if (!r.isReturned()) { System.out.println("  " + r); found = true; }
        }
        if (!found) System.out.println("  No active rentals.");
    }

    public void showRevenue() {
        double total = 0; int count = 0;
        for (Rental r : rentals) if (r.isReturned()) { total += r.getTotalAmount(); count++; }
        System.out.println("\n  --- Revenue Summary: " + agencyName + " ---");
        System.out.println("  Completed Rentals : " + count);
        System.out.printf ("  Total Revenue     : Rs.%.2f%n", total);
    }

    public void polymorphismDemo() {
        System.out.println("\n  --- POLYMORPHISM DEMO ---");
        System.out.println("  Same getRentalRatePerDay() call, different result per object:");
        System.out.println("  " + "-".repeat(55));
        for (Vehicle v : fleet)
            System.out.printf("  %-6s | %-15s %-12s -> Rs.%.0f/day%n",
                v.getVehicleType(), v.getBrand(), v.getModel(), v.getRentalRatePerDay());
        System.out.println("  " + "-".repeat(55));
        System.out.println("  All called via Vehicle reference -> each runs its OWN version.");
        System.out.println("  This is Runtime Polymorphism!\n");
    }

    public Vehicle findVehicleById(String id) {
        for (Vehicle v : fleet) if (v.getVehicleId().equalsIgnoreCase(id)) return v;
        return null;
    }
    public Rental findRentalById(String id) {
        for (Rental r : rentals) if (r.getRentalId().equalsIgnoreCase(id)) return r;
        return null;
    }
    public String        getAgencyName() { return agencyName; }
    public List<Vehicle> getFleet()      { return fleet;      }
}

// ── Main Entry Point ─────────────────────────────────────────────
public class CarRentalService {

    static RentalService agency = new RentalService("SpeedWheels Rentals");
    static Scanner sc = new Scanner(System.in);
    static Customer amit, sneha;

    public static void main(String[] args) {
        printBanner();
        seedData();

        int choice = -1;
        while (choice != 0) {
            printMenu();
            System.out.print("  Enter choice: ");
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
                handleChoice(choice);
            } catch (Exception e) {
                System.out.println("  Error: " + e.getMessage());
            }
        }
        System.out.println("\n  Drive safe!\n");
        sc.close();
    }

    static void printBanner() {
        System.out.println("\n  ==========================================");
        System.out.println("       SPEEDWHEELS RENTALS");
        System.out.println("     Car Rental -- Java OOP Project");
        System.out.println("  ==========================================");
    }

    static void printMenu() {
        System.out.println("\n  --- MAIN MENU ---");
        System.out.println("  1. Available vehicles");
        System.out.println("  2. All vehicles (fleet)");
        System.out.println("  3. All customers");
        System.out.println("  4. Rent a vehicle");
        System.out.println("  5. Return a vehicle");
        System.out.println("  6. Active rentals");
        System.out.println("  7. Rental history");
        System.out.println("  8. Revenue summary");
        System.out.println("  9. Polymorphism demo");
        System.out.println("  0. Exit");
    }

    static void handleChoice(int c) {
        switch (c) {
            case 1: agency.showAvailableVehicles(); break;
            case 2: agency.showAllVehicles();       break;
            case 3: agency.showAllCustomers();      break;
            case 4: {
                agency.showAvailableVehicles();
                System.out.println("  Customers:  1. " + amit.getName() + "   2. " + sneha.getName());
                System.out.print("  Select customer (1 or 2): ");
                int cc = Integer.parseInt(sc.nextLine().trim());
                Customer sel = (cc == 1) ? amit : sneha;
                System.out.print("  Enter Vehicle ID (e.g. VH201): ");
                String vid = sc.nextLine().trim().toUpperCase();
                agency.rentVehicle(sel, vid, LocalDate.now());
                break;
            }
            case 5: {
                agency.showActiveRentals();
                System.out.print("  Enter Rental ID (e.g. R501): ");
                String rid = sc.nextLine().trim().toUpperCase();
                System.out.print("  Payment mode (1=Cash, 2=Card): ");
                int pm = Integer.parseInt(sc.nextLine().trim());
                Payment pay = (pm == 2)
                    ? new CardPayment("Customer", "4242")
                    : new CashPayment("Front Desk");
                agency.returnVehicle(rid, LocalDate.now(), pay);
                break;
            }
            case 6: agency.showActiveRentals(); break;
            case 7: agency.showRentalHistory(); break;
            case 8: agency.showRevenue();       break;
            case 9: agency.polymorphismDemo();  break;
            case 0: break;
            default: System.out.println("  Invalid choice, try again.");
        }
    }

    static void seedData() {
        System.out.println("\n  Loading demo data...");
        agency.addVehicle(new Car ("Maruti",        "Swift Dzire", 2022, "MH12-AB-1234", 5,    1200));
        agency.addVehicle(new Car ("Hyundai",       "Creta",       2023, "MH14-CD-5678", 5,    1800));
        agency.addVehicle(new Bike("Royal Enfield", "Classic 350", 2021, "MH15-EF-9012", "Cruiser", 600));
        agency.addVehicle(new Bike("Honda",         "Activa 6G",   2023, "MH16-GH-3456", "Scooter", 350));
        agency.addVehicle(new SUV ("Toyota",        "Fortuner",    2022, "MH17-IJ-7890", true,  3500));
        agency.addVehicle(new SUV ("Mahindra",      "Scorpio N",   2023, "MH18-KL-2345", false, 2800));
        amit  = agency.registerCustomer("Amit Sharma", "9876543210", "MH0120210012345");
        sneha = agency.registerCustomer("Sneha Patil",  "9123456789", "MH0220220067890");
        System.out.println("  6 vehicles + 2 customers ready.\n");
    }
}