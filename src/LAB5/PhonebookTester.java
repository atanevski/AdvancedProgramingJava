package LAB5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class PhonebookTester {

    public static void main(String[] args) throws Exception {
        Scanner jin = new Scanner(System.in);
        String line = jin.nextLine();
        switch (line) {
            case "test_contact":
                testContact(jin);
                break;
            case "test_phonebook_exceptions":
                testPhonebookExceptions(jin);
                break;
            case "test_usage":
                testUsage(jin);
                break;
        }
    }

    private static void testFile(Scanner jin) throws Exception {
        PhoneBook phonebook = new PhoneBook();
        while (jin.hasNextLine()) {
            phonebook.addContact(new Contact(jin.nextLine(), jin.nextLine().split("\\s++")));
        }
        String text_file = "phonebook.txt";
        PhoneBook.saveAsTextFile(phonebook, text_file);
        PhoneBook pb = PhoneBook.loadFromTextFile(text_file);
        if (!pb.equals(phonebook)) {
            System.out.println("Your file saving and loading doesn't seem to work right");
        } else {
            System.out.println("Your file saving and loading works great. Good job!");
        }
    }

    private static void testUsage(Scanner jin) throws Exception {
        PhoneBook phonebook = new PhoneBook();
        while (jin.hasNextLine()) {
            String command = jin.nextLine();
            switch (command) {
                case "add":
                    phonebook.addContact(new Contact(jin.nextLine(), jin.nextLine().split("\\s++")));
                    break;
                case "remove":
                    phonebook.removeContact(jin.nextLine());
                    break;
                case "print":
                    System.out.println(phonebook.numberOfContacts());
                    System.out.println(Arrays.toString(phonebook.getContacts()));
                    System.out.println(phonebook.toString());
                    break;
                case "get_name":
                    System.out.println(phonebook.getContactForName(jin.nextLine()));
                    break;
                case "get_number":
                    System.out.println(Arrays.toString(phonebook.getContactsForNumber(jin.nextLine())));
                    break;
            }
        }
    }

    private static void testPhonebookExceptions(Scanner jin) {
        PhoneBook phonebook = new PhoneBook();
        boolean exception_thrown = false;
        try {
            while (jin.hasNextLine()) {
                phonebook.addContact(new Contact(jin.nextLine()));
            }
        } catch (InvalidNameException e) {
            System.out.println(e.name);
            exception_thrown = true;
        } catch (Exception e) {
        }
        if (!exception_thrown) {
            System.out.println("Your addContact method doesn't throw InvalidNameException");
        }
        /*
         exception_thrown = false;
         try {
         phonebook.addContact(new Contact(jin.nextLine()));
         } catch ( MaximumSizeExceddedException e ) {
         exception_thrown = true;
         }
         catch ( Exception e ) {}
         if ( ! exception_thrown ) System.out.println("Your addContact method doesn't throw MaximumSizeExcededException");
         */
    }

    private static void testContact(Scanner jin) throws Exception {
        boolean exception_thrown = true;
        String names_to_test[] = {"And\nrej", "asd", "AAAAAAAAAAAAAAAAAAAAAA", "Ð�Ð½Ð´Ñ€ÐµÑ˜A123213", "Andrej#", "Andrej<3"};
        for (String name : names_to_test) {
            try {
                new Contact(name);
                exception_thrown = false;
            } catch (InvalidNameException e) {
                exception_thrown = true;
            }
            if (!exception_thrown) {
                System.out.println("Your Contact constructor doesn't throw an InvalidNameException");
            }
        }
        String numbers_to_test[] = {"+071718028", "number", "078asdasdasd", "070asdqwe", "070a56798", "07045678a", "123456789", "074456798", "073456798", "079456798"};
        for (String number : numbers_to_test) {
            try {
                new Contact("Andrej", number);
                exception_thrown = false;
            } catch (InvalidNumberException e) {
                exception_thrown = true;
            }
            if (!exception_thrown) {
                System.out.println("Your Contact constructor doesn't throw an InvalidNumberException");
            }
        }
        String nums[] = new String[10];
        for (int i = 0; i < nums.length; ++i) {
            nums[i] = getRandomLegitNumber();
        }
        try {
            new Contact("Andrej", nums);
            exception_thrown = false;
        } catch (MaximumSizeExceddedException e) {
            exception_thrown = true;
        }
        if (!exception_thrown) {
            System.out.println("Your Contact constructor doesn't throw a MaximumSizeExceddedException");
        }
        Random rnd = new Random(5);
        Contact contact = new Contact("Andrej", getRandomLegitNumber(rnd), getRandomLegitNumber(rnd), getRandomLegitNumber(rnd));
        System.out.println(contact.getName());
        System.out.println(Arrays.toString(contact.getNumbers()));
        System.out.println(contact.toString());
        contact.addNumber(getRandomLegitNumber(rnd));
        System.out.println(Arrays.toString(contact.getNumbers()));
        System.out.println(contact.toString());
        contact.addNumber(getRandomLegitNumber(rnd));
        System.out.println(Arrays.toString(contact.getNumbers()));
        System.out.println(contact.toString());
    }

    static String[] legit_prefixes = {"070", "071", "072", "075", "076", "077", "078"};
    static Random rnd = new Random();

    private static String getRandomLegitNumber() {
        return getRandomLegitNumber(rnd);
    }

    private static String getRandomLegitNumber(Random rnd) {
        StringBuilder sb = new StringBuilder(legit_prefixes[rnd.nextInt(legit_prefixes.length)]);
        for (int i = 3; i < 9; ++i) {
            sb.append(rnd.nextInt(10));
        }
        return sb.toString();
    }

    private static class InvalidNameException extends Exception {

        String name;

        public InvalidNameException(String name) {
            this.name = name;
        }

    }

    private static class MaximumSizeExceddedException extends Exception {

        public MaximumSizeExceddedException() {
        }
    }

    private static class InvalidNumberException extends Exception {

        public InvalidNumberException() {
        }
    }

    public static class Contact implements Comparable<Contact> {

        String[] prefixes = {"070", "071", "072", "075", "076", "077", "078"};
        String name;
        ArrayList<String> phoneArray;

        public Contact(String name, String... phonenumber) throws InvalidNameException, MaximumSizeExceddedException, InvalidNumberException {
            if (name.length() >= 4 && name.length() <= 10) {
                for (int i = 0; i < name.length(); i++) {
                    if (!((int) name.charAt(i) >= 65 && (int) name.charAt(i) <= 122)) {
                        throw new InvalidNameException(name);
                    }
                }
                this.name = name;
            } else {
                throw new InvalidNameException(name);
            }
            phoneArray = new ArrayList<>();

            if (phonenumber.length > 5) {
                throw new MaximumSizeExceddedException();
            } else {
                for (int i = 0; i < phonenumber.length; i++) {
                    String temp = phonenumber[i];
                    boolean checkedPhoneNum = checkNumber(temp);
                    if (checkedPhoneNum) {
                        phoneArray.add(temp);
                    } else {
                        throw new InvalidNumberException();
                    }

                }
            }
        }

        public boolean checkNumber(String num) {
            if (num.length() == 9) {

                boolean checkDig = true;
                for (int i = 0; i < num.length(); i++) {
                    if (!Character.isDigit(num.charAt(i))) {
                        return false;
                    }
                }

                boolean checkOp = false;

                for (String prefixe : prefixes) {
                    if (num.startsWith(prefixe)) {
                        checkOp = true;
                        break;
                    }
                }
                return checkDig && checkOp;
            } else {
                return false;
            }

        }

        public String getName() {
            return name;
        }

        public String[] getNumbers() {

            String[] numbers = phoneArray.toArray(new String[phoneArray.size()]);
            Arrays.sort(numbers);
            return Arrays.copyOf(numbers, numbers.length);
        }

        public void addNumber(String phonenumber) throws MaximumSizeExceddedException {
            if (phoneArray.size() == 5) {
                throw new MaximumSizeExceddedException();
            } else if (phoneArray.size() < 5) {
                phoneArray.add(phonenumber);
            }
        }

        @Override
        public String toString() {
            String[] temp = phoneArray.toArray(new String[phoneArray.size()]);
            Arrays.sort(temp);
            StringBuilder sb = new StringBuilder();
            sb.append(name);
            sb.append("\n");
            sb.append(temp.length);
            sb.append("\n");
            for (int i = 0; i < temp.length; i++) {
                sb.append(temp[i]);
                sb.append("\n");
            }

            return sb.toString();
        }

        @Override
        public int compareTo(Contact o) {
            return o.name.compareTo(this.name);
        }

    }

    public static class PhoneBook {

        ArrayList<Contact> contacts;

        public PhoneBook() {
            contacts = new ArrayList<>();
        }

        public void addContact(Contact contact) throws MaximumSizeExceddedException, InvalidNameException {

            if (contacts.size() < 250) {
                for (Contact c : contacts) {
                    if (c.getName().equals(contact.name)) {
                        throw new InvalidNameException(c.name);
                    }
                }
                contacts.add(contact);
            } else {
                throw new MaximumSizeExceddedException();
            }
        }

        public Contact getContactForName(String name) {
            for (Contact c : contacts) {
                if (c.name.equals(name)) {
                    return c;
                }
            }
            return null;
        }

        public int numberOfContacts() {
            return contacts.size();
        }

        public Contact[] getContacts() {
            Contact[] toRe = contacts.toArray(new Contact[contacts.size()]);
            Arrays.sort(toRe);
            return Arrays.copyOf(toRe, toRe.length);
        }

        public boolean removeContact(String name) {
            for (Contact c : contacts) {
                if (c.getName().equals(name)) {
                    contacts.remove(c);
                    return true;
                }
            }
            return false;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            Contact[] toPrint = contacts.toArray(new Contact[contacts.size()]);
            Arrays.sort(toPrint);
            for (int i = 0; i < toPrint.length; i++) {
                sb.append(toPrint[i].toString());
                sb.append("\n");
            }
            return sb.toString();
        }

        public static boolean saveAsTextFile(PhoneBook phonebook, String path) {
            return false;
        }

        public static PhoneBook loadFromTextFile(String path) {
            return null;
        }

        public Contact[] getContactsForNumber(String number_prefix) {
            ArrayList<Contact> toRe = new ArrayList<>();

            for (int i = 0; i < contacts.size(); i++) {
                Contact toCheck = contacts.get(i);
                String[] numbers = toCheck.getNumbers();
                for (int j = 0; j < numbers.length; j++) {
                    if (numbers[j].startsWith(number_prefix)) {
                        toRe.add(contacts.get(i));
                    }

                }
            }

            Contact[] toReturn = toRe.toArray(new Contact[toRe.size()]);
            Arrays.sort(toReturn);
            return Arrays.copyOf(toReturn, toReturn.length);
        }

    }

}
