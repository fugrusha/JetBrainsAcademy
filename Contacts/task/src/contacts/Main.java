package contacts;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static final Pattern PATTERN = Pattern.compile("\\+?((\\(\\w+\\)([\\s-]\\w{2,})?)|(\\w+([\\s-]\\(\\w{2,}\\))?))([\\s-]\\w{2,}){0,3}");
    private static final String ENTER_ACTION = "Enter action (add, remove, edit, count, list, exit):";
    private static final List<Contact> contactList = new ArrayList<>();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        print(ENTER_ACTION);
        String input = sc.nextLine();

        while (true) {
            handleUserInput(input);

            print(ENTER_ACTION);
            input = sc.nextLine();
        }
    }

    static class Contact {
        String name;
        String surname;
        String phone;

        Contact() {
            this.phone = "";
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            if (validate(phone)) {
                this.phone = phone;
            } else {
                this.phone = "";
                print("Wrong number format!");
            }
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }

        public boolean hasNumber() {
            return phone != null && !phone.trim().isEmpty();
        }

        private boolean validate(String phone) {
            Matcher matcher = PATTERN.matcher(phone);
            return matcher.matches();
        }
    }

    private static void handleUserInput(String input) {
        switch (input) {
            case "add":
                createContact();
                break;
            case "remove":
                deleteContact();
                break;
            case "edit":
                editContact();
                break;
            case "count":
                printSizeOfContactList();
                break;
            case "list":
                printContactList();
                break;
            case "exit":
                System.exit(0);
                break;
            default:
                print("Use following commands...");
                print(ENTER_ACTION);
        }
    }

    private static void editContact() {
        if (contactList.size() == 0) {
            print("No records to edit!");
            return;
        }

        printContactList();

        int index = Integer.parseInt(getStringReplyToMsg("Select a record:"));
        Contact contact = contactList.get(index - 1);

        String field = getStringReplyToMsg("Select a field (name, surname, number):");

        switch (field) {
            case "name":
                String newName = getStringReplyToMsg("Enter name:");
                contact.setName(newName);
                print("The record updated!");
                break;
            case "surname":
                String newSurname = getStringReplyToMsg("Enter surname:");
                contact.setSurname(newSurname);
                print("The record updated!");
                break;
            case "number":
                String newNumber = getStringReplyToMsg("Enter number:");
                contact.setPhone(newNumber);
                print("The record updated!");
                break;
            default:
                print("Use following commands...");
                print(ENTER_ACTION);
        }
    }

    private static void deleteContact() {
        if (contactList.size() == 0) {
            print("No records to remove!");
            return;
        }

        printContactList();

        int index = Integer.parseInt(getStringReplyToMsg("Select a record:"));

        contactList.remove(index - 1);
        print("The record removed!");
    }

    private static void printContactList() {
        int index = 0;

        for (Contact c : contactList) {
            String number = c.hasNumber() ? c.getPhone() : "[no number]";

            print(++index + ". " + c.getName() + " " + c.getSurname() + ", " + number);
        }
    }

    private static void printSizeOfContactList() {
        int size = contactList.size();
        print("The Phone Book has " + size + " records.");
    }

    private static void createContact() {
        String name = getStringReplyToMsg("Enter the name:");
        String surname = getStringReplyToMsg("Enter the surname:");
        String number = getStringReplyToMsg("Enter the number:");

        Contact contact = new Contact();
        contact.setName(name);
        contact.setSurname(surname);
        contact.setPhone(number);

        contactList.add(contact);

        print("A record added.");
    }

    private static String getStringReplyToMsg(String message) {
        print(message);
        return sc.nextLine();
    }

    private static void print(String text) {
        System.out.println(text);
    }
}
