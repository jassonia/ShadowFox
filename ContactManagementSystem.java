import java.util.ArrayList;
import java.util.Scanner;

// Contact class
class Contact {
    String name;
    String phone;
    String email;

    // Constructor
    Contact(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    // Display contact details
    void display(int index) {
        System.out.println(index + ". Name: " + name +
                " | Phone: " + phone +
                " | Email: " + email);
    }
}

// Main class
public class ContactManagementSystem {

    static ArrayList<Contact> contacts = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {

            System.out.println("\n-------------------------------------");
            System.out.println("     CONTACT MANAGEMENT SYSTEM");
            System.out.println("-------------------------------------");
            System.out.println("1. Add Contact");
            System.out.println("2. View Contacts");
            System.out.println("3. Update Contact");
            System.out.println("4. Delete Contact");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = readInt();

            switch (choice) {

                case 1:
                    addContact();
                    break;

                case 2:
                    viewContacts();
                    break;

                case 3:
                    updateContact();
                    break;

                case 4:
                    deleteContact();
                    break;

                case 5:
                    System.out.println("Exiting program...");
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    static void addContact() {

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        String phone;

        while (true) {

            System.out.print("Enter Phone Number: ");
            phone = sc.nextLine();

            if (!phone.matches("[6-9]\\d{9}")) {
                System.out.println("Invalid phone number!");
                continue;
            }

            boolean duplicate = false;

            for (Contact c : contacts) {
                if (c.phone.equals(phone)) {
                    duplicate = true;
                    break;
                }
            }

            if (duplicate) {
                System.out.println("Phone number already exists!");
            } else {
                break;
            }
        }

        String email;

        while (true) {

            System.out.print("Enter Email: ");
            email = sc.nextLine();

            if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
                System.out.println("Invalid email!");
                continue;
            }

            boolean duplicate = false;

            for (Contact c : contacts) {
                if (c.email.equalsIgnoreCase(email)) {
                    duplicate = true;
                    break;
                }
            }

            if (duplicate) {
                System.out.println("Email already exists!");
            } else {
                break;
            }
        }

        contacts.add(new Contact(name, phone, email));

        System.out.println("Contact added successfully!");
    }

    static void viewContacts() {

        if (contacts.isEmpty()) {
            System.out.println("No contacts found.");
            return;
        }

        for (int i = 0; i < contacts.size(); i++) {
            contacts.get(i).display(i + 1);
        }
    }

    static void updateContact() {

        viewContacts();

        if (contacts.isEmpty())
            return;

        System.out.print("Enter contact number to update: ");

        int index = readInt() - 1;

        if (index < 0 || index >= contacts.size()) {
            System.out.println("Invalid contact number!");
            return;
        }

        Contact c = contacts.get(index);

        System.out.print("Enter New Name: ");
        c.name = sc.nextLine();

        System.out.print("Enter New Phone: ");
        c.phone = sc.nextLine();

        System.out.print("Enter New Email: ");
        c.email = sc.nextLine();

        System.out.println("Contact updated successfully!");
    }

    static void deleteContact() {

        viewContacts();

        if (contacts.isEmpty())
            return;

        System.out.print("Enter contact number to delete: ");

        int index = readInt() - 1;

        if (index < 0 || index >= contacts.size()) {
            System.out.println("Invalid contact number!");
            return;
        }

        contacts.remove(index);

        System.out.println("Contact deleted successfully!");
    }

    static int readInt() {

        while (!sc.hasNextInt()) {

            System.out.print("Invalid input! Enter number: ");
            sc.next();
        }

        int num = sc.nextInt();
        sc.nextLine();

        return num;
    }
}