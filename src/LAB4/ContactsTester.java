package LAB4;

import java.text.DecimalFormat;
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

        public Faculty(String name, Student[] students) {
            this.name = name;
            this.students = new Student[students.length];
            for (int i = 0; i < students.length; i++) {
                this.students[i] = students[i];
            }

        }

        public int countStudentsFromCity(String cityName) {
            int counterFromCity = 0;
            for (int i = 0; i < students.length; i++) {
                if (cityName.equals(students[i].getCity())) {
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

            double totalContacts = 0;
            for (int i = 0; i < students.length; i++) {
                totalContacts += students[i].contacts.length;
            }
            return totalContacts / students.length;
        }

        public Student getStudentWithMostContacts() {
            int StudentWithMost = 0;
            for (int i = 0; i < students.length; i++) {
                if (students[i].contacts.length > students[StudentWithMost].contacts.length) {
                    StudentWithMost = i;
                }
                if (students[i].contacts.length == students[StudentWithMost].contacts.length) {
                    if (students[i].getIndex() > students[StudentWithMost].getIndex()) {
                        StudentWithMost = i;
                    }
                }
            }
            return students[StudentWithMost];

        }

        @Override
        public String toString() {

            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < students.length; i++) {
                if (i == students.length - 1) {
                    sb.append(students[i].toString());
                } else {
                    sb.append(students[i].toString());
                    sb.append(", ");
                }

            }
            return String.format("{\"fakultet\":\"%s\", \"studenti\":[%s]}", name, sb.toString());

        }

    }

    private abstract static class Contact {

        String date;

        public Contact(String date) {
            this.date = date;
        }

        public boolean isNewerThan(Contact c) {
            int a = this.date.compareTo(c.date);
            if (a < 0) {
                return false;
            } else if (a > 0) {
                return true;
            } else {
                return false;
            }

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

        public String getDate() {
            return date;
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

        public String getDate() {
            return date;
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
        Contact[] contacts;

        public Student(String firstName, String lastName, String city, int age, long index) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.city = city;
            this.age = age;
            this.index = index;
            this.contacts = new Contact[0];
        }

        public void addEmailContact(String date, String email) {

            if (contacts.length == 0) {
                contacts = new Contact[1];
                contacts[0] = new EmailContact(date, email);
            } else {
                Contact[] temp = new Contact[contacts.length + 1];
                for (int i = 0; i < contacts.length; i++) {
                    temp[i] = contacts[i];
                }
                temp[contacts.length] = new EmailContact(date, email);
                contacts = temp;
            }

        }

        public void addPhoneContact(String date, String phone) {
            if (contacts.length == 0) {
                contacts = new Contact[1];
                contacts[0] = new PhoneContact(date, phone);
            } else {
                Contact[] temp = new Contact[contacts.length + 1];
                for (int i = 0; i < contacts.length; i++) {
                    temp[i] = contacts[i];
                }
                temp[contacts.length] = new PhoneContact(date, phone);
                contacts = temp;
            }
        }

        public Contact[] getEmailContacts() {
            int counter = 0;
            for (Contact contact : contacts) {
                if (contact.getType().equals("Email")) {
                    counter++;
                }
            }

            Contact[] emailArray = new Contact[counter];
            int temp = 0;

            for (Contact contact : contacts) {
                if (contact.getType().equals("Email")) {
                    emailArray[temp] = new EmailContact(((EmailContact) contact).getDate(), ((EmailContact) contact).getEmail());
                    temp++;
                }
            }
            return emailArray;

        }

        public Contact[] getPhoneContacts() {
            int counter = 0;
            for (int i = 0; i < contacts.length; i++) {
                if ("Phone".equals(contacts[i].getType())) {
                    counter++;
                }
            }
            PhoneContact[] phoneArray = new PhoneContact[counter];
            int temp = 0;
            for (Contact contact : contacts) {
                if ("Phone".equals(contact.getType())) {
                    phoneArray[temp] = new PhoneContact(((PhoneContact) contact).getDate(), ((PhoneContact) contact).getPhone());
                    temp++;
                }
            }
            return phoneArray;
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
            int latestContactIndex = 0;
            for (int i = 0; i < contacts.length; i++) {
                if (contacts[i].isNewerThan(contacts[latestContactIndex])) {
                    latestContactIndex = i;
                }
            }
            return contacts[latestContactIndex];
        }

        public String emailsToJSON() {
            StringBuilder emailsFormat = new StringBuilder("[");
            Contact[] emailContacts = getEmailContacts();

            for (int i = 0; i < emailContacts.length; i++) {
                EmailContact temp = (EmailContact) emailContacts[i];
                if (i < emailContacts.length - 1) {
                    emailsFormat.append(String.format("\"%s\"", temp.email));
                    emailsFormat.append(", ");
                } else {
                    emailsFormat.append(String.format("\"%s\"", temp.email));
                }

            }
            emailsFormat.append("]");

            return emailsFormat.toString();
        }

        public String phoneToJSON() {

            StringBuilder phoneNumbersFormat = new StringBuilder("[");
            Contact[] phoneContacts = getPhoneContacts();

            for (int i = 0; i < phoneContacts.length; i++) {
                PhoneContact temp = (PhoneContact) phoneContacts[i];
                if (i < phoneContacts.length - 1) {
                    phoneNumbersFormat.append(String.format("\"%s\"", temp.phoneNumber));
                    phoneNumbersFormat.append(", ");
                } else {
                    phoneNumbersFormat.append(String.format("\"%s\"", temp.phoneNumber));
                }

            }
            phoneNumbersFormat.append("]");
            return phoneNumbersFormat.toString();
        }

        @Override
        public String toString() {

            String FnameTemp = String.format("{\"ime\":\"%s\", ", this.firstName);
            String LnameTemp = String.format("\"prezime\":\"%s\", ", this.lastName);
            String AgeTemp = String.format("\"vozrast\":%d, ", this.age);
            String CityTemp = String.format("\"grad\":\"%s\", ", this.city);
            String IndexTemp = String.format("\"indeks\":%s, ", String.valueOf(index));
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
