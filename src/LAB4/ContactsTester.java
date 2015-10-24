package LAB4;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class ContactsTester {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int tests = scanner.nextInt();
        Faculty faculty = null;

        int rvalue = 0;
        long rindex = -1;

        DecimalFormat df = new DecimalFormat("0.00");

        for (int t = 0; t < tests; t++) {

            rvalue++;
            String operation = scanner.next();

            switch (operation) {
                case "CREATE_FACULTY": {
                    String name = scanner.nextLine().trim();
                    int N = scanner.nextInt();

                    Student[] students = new Student[N];

                    for (int i = 0; i < N; i++) {
                        rvalue++;

                        String firstName = scanner.next();
                        String lastName = scanner.next();
                        String city = scanner.next();
                        int age = scanner.nextInt();
                        long index = scanner.nextLong();

                        if ((rindex == -1) || (rvalue % 13 == 0)) {
                            rindex = index;
                        }

                        Student student = new Student(firstName, lastName, city,
                                age, index);
                        students[i] = student;
                    }

                    faculty = new Faculty(name, students);
                    break;
                }

                case "ADD_EMAIL_CONTACT": {
                    long index = scanner.nextInt();
                    String date = scanner.next();
                    String email = scanner.next();

                    rvalue++;

                    if ((rindex == -1) || (rvalue % 3 == 0)) {
                        rindex = index;
                    }

                    faculty.getStudent(index).addEmailContact(date, email);
                    break;
                }

                case "ADD_PHONE_CONTACT": {
                    long index = scanner.nextInt();
                    String date = scanner.next();
                    String phone = scanner.next();

                    rvalue++;

                    if ((rindex == -1) || (rvalue % 3 == 0)) {
                        rindex = index;
                    }

                    faculty.getStudent(index).addPhoneContact(date, phone);
                    break;
                }

                case "CHECK_SIMPLE": {
                    System.out.println("Average number of contacts: "
                            + df.format(faculty.getAverageNumberOfContacts()));

                    rvalue++;

                    String city = faculty.getStudent(rindex).getCity();
                    System.out.println("Number of students from " + city + ": "
                            + faculty.countStudentsFromCity(city));

                    break;
                }

                case "CHECK_DATES": {

                    rvalue++;

                    System.out.print("Latest contact: ");
                    Contact latestContact = faculty.getStudent(rindex)
                            .getLatestContact();
                    if (latestContact.getType().equals("Email")) {
                        System.out.println(((EmailContact) latestContact)
                                .getEmail());
                    }
                    if (latestContact.getType().equals("Phone")) {
                        System.out.println(((PhoneContact) latestContact)
                                .getPhone()
                                + " ("
                                + ((PhoneContact) latestContact).getOperator()
                                .toString() + ")");
                    }

                    if (faculty.getStudent(rindex).getEmailContacts().length > 0 && faculty.getStudent(rindex).getPhoneContacts().length > 0) {
                        System.out.print("Number of email and phone contacts: ");
                        System.out
                                .println(faculty.getStudent(rindex)
                                        .getEmailContacts().length
                                        + " "
                                        + faculty.getStudent(rindex)
                                        .getPhoneContacts().length);

                        System.out.print("Comparing dates: ");
                        int posEmail = rvalue
                                % faculty.getStudent(rindex).getEmailContacts().length;
                        int posPhone = rvalue
                                % faculty.getStudent(rindex).getPhoneContacts().length;

                        System.out.println(faculty.getStudent(rindex)
                                .getEmailContacts()[posEmail].isNewerThan(faculty
                                        .getStudent(rindex).getPhoneContacts()[posPhone]));
                    }

                    break;
                }

                case "PRINT_FACULTY_METHODS": {
                    System.out.println("Faculty: " + faculty.toString());
                    System.out.println("Student with most contacts: "
                            + faculty.getStudentWithMostContacts().toString());
                    break;
                }

            }

        }

        scanner.close();
    }

    private static class Faculty {

        String name;
        Student[] students;
        ArrayList<Student> studentsArrayList = new ArrayList<>();

        public Faculty(String name, Student[] students) {
            this.name = name;
            this.students = students;
            for (Student s : students) {
                studentsArrayList.add(s);
            }
        }

        public int countStudentsFromCity(String cityName) {
            int counterFromCity = 0;
            for (Student student : students) {
                if (student.city.compareTo(cityName) == 0) {
                    counterFromCity++;
                }
            }
            return counterFromCity;
        }

        public Student getStudent(long index) {
            for (Student student : students) {
                if (student.index == index) {
                    return student;
                }
            }
            return null;
        }

        public double getAverageNumberOfContacts() {
            int totalContacts = 0;
            for (Student student : students) {
                totalContacts += student.getSizeOfContactsArray();
            }

            return (double) totalContacts / (double) students.length;
        }

        public Student getStudentWithMostContacts() {
            studentsArrayList.sort((Student o1, Student o2) -> {
                if (o1.getSizeOfContactsArray() > o2.getSizeOfContactsArray()) {
                    return 1;
                } else if (o1.getSizeOfContactsArray() < o2.getSizeOfContactsArray()) {
                    return -1;
                } else {
                    return 0;
                }
            });
            return studentsArrayList.get(0);
        }

    }

    private abstract class Contact {

        String date;

        public Contact(String date) {
            this.date = date;
        }

        public boolean isNewerThan(Contact c) {
            return c.date.compareTo(this.date) == 1;
        }

        abstract String getType();

    }

    public static class EmailContact extends Contact {

        String email;

        public EmailContact(String date, String email) {
            super(date);
            this.email = email;
        }

        public String getEmail() {
            return this.email;
        }

        @Override
        String getType() {
            return "Email";
        }

    }

    public static final class PhoneContact extends Contact {

        public enum Operator {

            VIP, ONE, TMOBILE
        }
        String phoneNumber;
        Operator op;

        public PhoneContact(String date, String phoneNumber) {
            super(date);
            this.phoneNumber = phoneNumber;
            op = getOperator();
        }

        @Override
        public String getType() {
            return "Phone";
        }

        public String getPhone() {
            return phoneNumber;
        }

        public Operator getOperator() {
            char check = phoneNumber.charAt(2);
            switch (check) {
                case '0':
                    return Operator.TMOBILE;
                case '1':
                    return Operator.TMOBILE;
                case '2':
                    return Operator.TMOBILE;
                case '5':
                    return Operator.TMOBILE;
                case '6':
                    return Operator.ONE;
                case '7':
                    return Operator.VIP;
                case '8':
                    return Operator.VIP;
                default:
                    this.op = null;
                    return null;
            }

        }

    }

    public static class Student {

        String firstName;
        String lastName;
        String city;
        int age;
        long index;
        public ArrayList<Contact> contactsArray;

        public Student(String firstName, String lastName, String city, int age, long index) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.city = city;
            this.age = age;
            this.index = index;
            contactsArray = new ArrayList<>();
        }

        public void addEmailContact(String date, String email) {
            contactsArray.add(new EmailContact(date, email));
        }

        public void addPhoneContact(String date, String phone) {
            contactsArray.add(new PhoneContact(date, phone));
        }

        public Contact[] getEmailContacts() {
            ArrayList<Contact> emailsToReturn = new ArrayList<>();

            for (Contact toCheck : contactsArray) {
                if (toCheck.getType().equals("email")) {
                    emailsToReturn.add(toCheck);
                }
            }
            Contact[] emailsToRe = new Contact[emailsToReturn.size()];
            emailsToReturn.toArray(emailsToRe);

            return emailsToRe;
        }

        public Contact[] getPhoneContacts() {
            ArrayList<Contact> phonesToReturn = new ArrayList<>();
            for (Contact toCheck : contactsArray) {
                if (toCheck.getType().equals("phone")) {
                    phonesToReturn.add(toCheck);
                }
            }
            return (Contact[]) phonesToReturn.toArray();
        }

        public String getCity() {
            return city;
        }

        public String getFullName() {
            StringBuilder sb = new StringBuilder();
            sb.append(firstName);
            sb.append(" ");
            sb.append(this.lastName);
            return sb.toString();
        }

        public long getIndex() {
            return index;
        }

        public Contact getLatestContact() {
            contactsArray.sort((Contact o1, Contact o2) -> o2.date.compareTo(o1.date));
            return contactsArray.get(0);
        }

        public String emailsToJSON() {
            StringBuilder emailsFormat = new StringBuilder("[");
            EmailContact[] emailContacts = (EmailContact[]) getEmailContacts();

            for (int i = 0; i < emailContacts.length; i++) {
                if (i < emailContacts.length - 1) {
                    emailsFormat.append(String.format("\"%s\"", emailContacts[i].email));
                    emailsFormat.append(", ");
                } else {
                    emailsFormat.append(String.format("\"%s\"", emailContacts[i].email));
                }

            }
            emailsFormat.append("]");

            return emailsFormat.toString();
        }

        public String phoneToJSON() {

            StringBuilder phoneNumbersFormat = new StringBuilder("[\"");
            PhoneContact[] phoneContacts = (PhoneContact[]) getPhoneContacts();

            for (int i = 0; i < phoneContacts.length; i++) {
                if (i < phoneContacts.length - 1) {
                    phoneNumbersFormat.append(String.format("\"%s\"", phoneContacts[i].phoneNumber));
                    phoneNumbersFormat.append(", ");
                } else {
                    phoneNumbersFormat.append(String.format("\"%s\"", phoneContacts[i].phoneNumber));
                }

            }
            phoneNumbersFormat.append("]");
            return phoneNumbersFormat.toString();
        }

        public int getSizeOfContactsArray() {
            return contactsArray.size();
        }

        @Override
        public String toString() {

            String FnameTemp = String.format("{\"ime\":\"%s\", ", this.firstName);
            String LnameTemp = String.format("\"prezime\":\"%s\", ", this.lastName);
            String AgeTemp = String.format("\"vozrast\":\"%d\", ", this.age);
            String CityTemp = String.format("\"grad\":\"%s\", ", this.city);
            String IndexTemp = String.format("\"indeks\":\"%d\", ", this.index);
            String PnumbersTemp = String.format("\"telefonskiKontakti\":%s, ", phoneToJSON());
            String EmailsTemp = String.format("\"emailKontakti\":%s}", emailsToJSON());

            StringBuilder sb = new StringBuilder();

            sb.append(FnameTemp);
            sb.append(LnameTemp);
            sb.append(AgeTemp);
            sb.append(CityTemp);
            sb.append(IndexTemp);
            sb.append(PnumbersTemp);
            sb.append(EmailsTemp);

            return sb.toString();

        }

    }
}
