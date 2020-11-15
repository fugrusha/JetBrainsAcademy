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
    private static final String MAIN_MENU = "\n[menu] Enter action (add, list, search, count, exit):";
    private static final String CONTACT_MENU = "\n[record] Enter action (edit, delete, menu):";
    private static final String LIST_MENU = "\n[list] Enter action ([number], back):";
    private static final List<Contact> contactList = new ArrayList<>();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            handleMainMenuCommands();
        }
    }

    private static void handleMainMenuCommands() {
        String input = getStringReplyToMsg(MAIN_MENU);

        switch (input) {
            case "add":
                createContact();
                break;
            case "search":
                search();
                break;
            case "count":
                printSizeOfContactList();
                break;
            case "list":
                listCommand();
                break;
            case "exit":
                System.exit(0);
                break;
            default:
                input = sc.nextLine();
        }
    }

    private static void search() {
        String reply = getStringReplyToMsg("Enter search query:");
        Pattern pattern = Pattern.compile(reply, Pattern.CASE_INSENSITIVE);

        List<Contact> searchResult = new ArrayList<>();

        for (Contact contact : contactList) {
            Matcher matcher = pattern.matcher(contact.toString());
            if (matcher.find()) {
                searchResult.add(contact);
            }
        }

        printList(searchResult);

        reply = getStringReplyToMsg("\n[search] Enter action ([number], back, again):");

        if ("again".equals(reply)) {
            search();
        } else if (reply.equals("back")) {
            print(MAIN_MENU);
        } else if (isDigit(reply)) {
            int index = Integer.parseInt(reply) - 1;
            Contact contact = searchResult.get(index);
            print(contact.toString());

            handleContactMenuCommands(contact);
        }
    }

    private static void handleContactMenuCommands(Contact contact) {
        String reply = getStringReplyToMsg(CONTACT_MENU);

        switch (reply) {
            case "delete":
                deleteContact(contact);
                break;
            case "edit":
                editContact(contact);
                break;
            case "menu":
                handleMainMenuCommands();
        }
    }

    private static void editContact(Contact contact) {
        String fieldName = getStringReplyToMsg("Select a field " + contact.getFieldsAbleToChange());
        String fieldValue = getStringReplyToMsg("Enter " + fieldName);

        contact.setValueByFieldName(fieldName, fieldValue);

        print("Saved");

        print(contact.toString());
        handleContactMenuCommands(contact);
    }

    private static void deleteContact(Contact contact) {
        contactList.remove(contact);
        print("The record removed!");
    }

    private static void listCommand() {

        printList(contactList);

        String reply = getStringReplyToMsg(LIST_MENU);

        if ("back".equals(reply)) {
            handleMainMenuCommands();
        } else if (isDigit(reply)) {
            int index = Integer.parseInt(reply) - 1;
            Contact contact = contactList.get(index);
            print(contact.toString());

            handleContactMenuCommands(contact);
        } else {
            handleMainMenuCommands();
        }
    }

    private static void printList(List<Contact> contactList) {
        for (int i = 0; i < contactList.size(); i++) {
            Contact contact = contactList.get(i);
            if (contact.isPerson()) {
                Person person = (Person) contact;
                print(i + 1 + ". " + person.getName() + " " + person.getSurname());
            } else {
                print(i + 1 + ". " + contact.getName());
            }
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
                print(MAIN_MENU);
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
        organization.setNumber(number);
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
        person.setNumber(number);
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

    private static boolean isDigit(String reply) {
        return reply.matches("\\d+");
    }

    static abstract class Contact {

        protected String name;
        protected String number;
        protected boolean isPerson;
        protected LocalDateTime createdAt;
        protected LocalDateTime lastModifiedAt;

        protected abstract String getFieldsAbleToChange();

        protected abstract void setValueByFieldName(String fieldName, String value);

        Contact() {
            this.number = "";
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

        public String getNumber() {
            return hasNumber() ? number : "[no number]";
        }

        public void setNumber(String number) {
            if (validatePhone(number)) {
                this.number = number;
            } else {
                this.number = "";
                print("Wrong number format!");
            }
        }

        public boolean hasNumber() {
            return number != null && !number.trim().isEmpty();
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
                    + "Number: " + getNumber() + "\n"
                    + "Time created: " + getCreatedAt() + "\n"
                    + "Time last edit: " + getLastModifiedAt();
        }

        @Override
        public String getFieldsAbleToChange() {
            return "(name, address, number)";
        }

        @Override
        protected void setValueByFieldName(String fieldName, String value) {
            switch (fieldName) {
                case "name":
                    this.setName(value);
                    break;
                case "address":
                    this.setAddress(value);
                    break;
                case "number":
                    this.setNumber(value);
                    break;
            }
        }
    }

    static class Person extends Contact {
        private String surname;
        private LocalDate birthday;
        private String gender;

        @Override
        public String getFieldsAbleToChange() {
            return "(name, surname, number, birthday, gender)";
        }

        @Override
        protected void setValueByFieldName(String fieldName, String value) {
            switch (fieldName) {
                case "name":
                    this.setName(value);
                    break;
                case "surname":
                    this.setSurname(value);
                    break;
                case "number":
                    this.setNumber(value);
                    break;
                case "birthday":
                    this.setBirthday(value);
                    break;
                case "gender":
                    this.setGender(value);
                    break;
            }
        }

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
                    + "Number: " + getNumber() + "\n"
                    + "Time created: " + getCreatedAt() + "\n"
                    + "Time last edit: " + getLastModifiedAt();
        }
    }
}
