package contacts;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static final Pattern PHONE_PATTERN = Pattern.compile("\\+?((\\(\\w+\\)([\\s-]\\w{2,})?)|(\\w+([\\s-]\\(\\w{2,}\\))?))([\\s-]\\w{2,}){0,3}");
    private static final Pattern DATE_PATTERN = Pattern.compile("^(0?[1-9]|[12][0-9])\\.(0?[1-9]|1[012])\\.(19|20)\\d\\d$");
    private static final String ENTER_ACTION = "\nEnter action (add, remove, edit, count, info, exit):";
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
            case "info":
                showInfo();
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

        if (contact.isPerson()) {
            Person person = (Person) contact;
            editPerson(person);
        } else {
            Organization organization = (Organization) contact;
            editOrganization(organization);
        }
    }

    private static void editOrganization(Organization organization) {
        String field = getStringReplyToMsg("Select a field (name, address, number):");

        switch (field) {
            case "name":
                String newName = getStringReplyToMsg("Enter name:");
                organization.setName(newName);
                break;
            case "address":
                String newAddress = getStringReplyToMsg("Enter address:");
                organization.setAddress(newAddress);
                break;
            case "number":
                String newNumber = getStringReplyToMsg("Enter number:");
                organization.setPhone(newNumber);
                break;
            default:
                print(ENTER_ACTION);
        }

        organization.setLastModifiedAt(LocalDateTime.now());
        print("The record updated!");
    }

    private static void editPerson(Person person) {
        String field = getStringReplyToMsg("Select a field (name, surname, birth, gender, number):");

        switch (field) {
            case "name":
                String newName = getStringReplyToMsg("Enter name:");
                person.setName(newName);
                break;
            case "surname":
                String newSurname = getStringReplyToMsg("Enter surname:");
                person.setSurname(newSurname);
                break;
            case "birth":
                String newBirthday = getStringReplyToMsg("Enter birth date:");
                person.setBirthday(newBirthday);
                break;
            case "gender":
                String newGender = getStringReplyToMsg("Enter gender:");
                person.setGender(newGender);
                break;
            case "number":
                String newNumber = getStringReplyToMsg("Enter number:");
                person.setPhone(newNumber);
                break;
            default:
                print(ENTER_ACTION);
        }

        person.setLastModifiedAt(LocalDateTime.now());
        print("The record updated!");
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

    private static void showInfo() {
        printContactList();

        int index = Integer.parseInt(getStringReplyToMsg("Enter index to show info:"));

        Contact contact = contactList.get(index - 1);

        if (contact.isPerson()) {
            Person person = (Person) contact;
            print(person.toString());
        } else {
            Organization organization = (Organization) contact;
            print(organization.toString());
        }
    }

    private static void printContactList() {
        for (int i = 0; i < contactList.size(); i++) {
            print(i + 1 + ". " + contactList.get(i).getName());
        }
    }

    private static void printSizeOfContactList() {
        int size = contactList.size();
        print("The Phone Book has " + size + " records.");
    }

    private static void createContact() {
        String type = getStringReplyToMsg("Enter the type (person, organization):");

        switch (type) {
            case "person":
                createPerson();
                break;
            case "organization":
                createOrganization();
                break;
            default:
                print("Unknown type");
                print(ENTER_ACTION);
        }
    }

    private static void createOrganization() {
        String name = getStringReplyToMsg("Enter the organization name:");
        String address = getStringReplyToMsg("Enter the address:");
        String number = getStringReplyToMsg("Enter the number:");

        Organization organization = new Organization();
        organization.setPerson(false);
        organization.setName(name);
        organization.setAddress(address);
        organization.setPhone(number);
        organization.setCreatedAt(LocalDateTime.now());
        organization.setLastModifiedAt(LocalDateTime.now());

        contactList.add(organization);

        print("A record added.");
    }

    private static void createPerson() {
        String name = getStringReplyToMsg("Enter the name:");
        String surname = getStringReplyToMsg("Enter the surname:");
        String birthDate = getStringReplyToMsg("Enter the birth date:");
        String gender = getStringReplyToMsg("Enter the gender (M, F):");
        String number = getStringReplyToMsg("Enter the number:");

        Person person = new Person();
        person.setPerson(true);
        person.setName(name);
        person.setSurname(surname);
        person.setBirthday(birthDate);
        person.setGender(gender);
        person.setPhone(number);
        person.setCreatedAt(LocalDateTime.now());
        person.setLastModifiedAt(LocalDateTime.now());

        contactList.add(person);

        print("A record added.");
    }

    private static String getStringReplyToMsg(String message) {
        print(message);
        return sc.nextLine();
    }

    private static void print(String text) {
        System.out.println(text);
    }

    static abstract class Contact {

        protected String name;
        protected String phone;
        protected boolean isPerson;
        protected LocalDateTime createdAt;
        protected LocalDateTime lastModifiedAt;

        Contact() {
            this.phone = "";
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isPerson() {
            return isPerson;
        }

        public void setPerson(boolean person) {
            isPerson = person;
        }

        public LocalDateTime getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
        }

        public LocalDateTime getLastModifiedAt() {
            return lastModifiedAt;
        }

        public void setLastModifiedAt(LocalDateTime lastModifiedAt) {
            this.lastModifiedAt = lastModifiedAt;
        }

        public String getPhone() {
            return hasNumber() ? phone : "[no number]";
        }

        public void setPhone(String phone) {
            if (validatePhone(phone)) {
                this.phone = phone;
            } else {
                this.phone = "";
                print("Wrong number format!");
            }
        }

        public boolean hasNumber() {
            return phone != null && !phone.trim().isEmpty();
        }

        private boolean validatePhone(String phone) {
            Matcher matcher = PHONE_PATTERN.matcher(phone);
            return matcher.matches();
        }
    }

    static class Organization extends Contact {
        private String address;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        @Override
        public String toString() {
            return "Organization name: " + getName() + "\n"
                    + "Address: " + getAddress() + "\n"
                    + "Number: " + getPhone() + "\n"
                    + "Time created: " + getCreatedAt() + "\n"
                    + "Time last edit: " + getLastModifiedAt();
        }
    }

    static class Person extends Contact {
        private String surname;
        private LocalDate birthday;
        private String gender;

        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }

        public String getBirthday() {
            if (this.birthday == null) {
                return "[no data]";
            } else {
                return birthday.toString();
            }
        }

        public void setBirthday(String birthday) {
            if (validateDate(birthday)) {
                this.birthday = LocalDate.parse(birthday);
            } else {
                this.birthday = null;
                print("Bad birth date!");
            }
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            if ("M".equals(gender) || "F".equals(gender)) {
                this.gender = gender;
            } else {
                this.gender = "[no data]";
                print("Bad gender!");
            }
        }

        private boolean validateDate(String date) {
            Matcher matcher = DATE_PATTERN.matcher(date);
            return matcher.matches();
        }

        @Override
        public String toString() {
            return "Name: " + getName() + "\n"
                    + "Surname: " + getSurname() + "\n"
                    + "Birth date: " + getBirthday() + "\n"
                    + "Gender: " + getGender() + "\n"
                    + "Number: " + getPhone() + "\n"
                    + "Time created: " + getCreatedAt() + "\n"
                    + "Time last edit: " + getLastModifiedAt();
        }
    }
}
