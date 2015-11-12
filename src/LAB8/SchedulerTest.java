package LAB8;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;
import java.util.TimeZone;

public class SchedulerTest {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if (k == 0) { //test Timestamp with String
            Timestamp<String> t = new Timestamp<String>(new Date(123456), jin.next());
            System.out.println(t);
            System.out.println(t.getDate());
            System.out.println(t.getElement());
        }
        if (k == 1) { //test Timestamp with ints
            Timestamp<Integer> t1 = new Timestamp<Integer>(new Date(897165), jin.nextInt());
            System.out.println(t1);
            System.out.println(t1.getDate());
            System.out.println(t1.getElement());
            Timestamp<Integer> t2 = new Timestamp<Integer>(new Date(18975), jin.nextInt());
            System.out.println(t2);
            System.out.println(t2.getDate());
            System.out.println(t2.getElement());
            System.out.println(t1.compareTo(t2));
            System.out.println(t2.compareTo(t1));
            System.out.println(t1.equals(t2));
            System.out.println(t2.equals(t1));
        }
        if (k == 2) {//test Timestamp with String, complex
            Timestamp<String> t1 = new Timestamp<String>(new Date(jin.nextLong()), jin.next());
            System.out.println(t1);
            System.out.println(t1.getDate());
            System.out.println(t1.getElement());
            Timestamp<String> t2 = new Timestamp<String>(new Date(jin.nextLong()), jin.next());
            System.out.println(t2);
            System.out.println(t2.getDate());
            System.out.println(t2.getElement());
            System.out.println(t1.compareTo(t2));
            System.out.println(t2.compareTo(t1));
            System.out.println(t1.equals(t2));
            System.out.println(t2.equals(t1));
        }
        if (k == 3) { //test Scheduler with String
            Scheduler<String> scheduler = new Scheduler<String>();
            Date now = new Date();
            scheduler.add(new Timestamp<String>(new Date(now.getTime() - 7200000), jin.next()));
            scheduler.add(new Timestamp<String>(new Date(now.getTime() - 3600000), jin.next()));
            scheduler.add(new Timestamp<String>(new Date(now.getTime() - 14400000), jin.next()));
            scheduler.add(new Timestamp<String>(new Date(now.getTime() + 7200000), jin.next()));
            scheduler.add(new Timestamp<String>(new Date(now.getTime() + 14400000), jin.next()));
            scheduler.add(new Timestamp<String>(new Date(now.getTime() + 3600000), jin.next()));
            scheduler.add(new Timestamp<String>(new Date(now.getTime() + 18000000), jin.next()));
            System.out.println(scheduler.next().getElement());
            System.out.println(scheduler.last().getElement());
            ArrayList<Timestamp<String>> res = scheduler.getAll(new Date(now.getTime() - 10000000), new Date(now.getTime() + 17000000));
            Collections.sort(res);
            for (Timestamp<String> t : res) {
                System.out.print(t.getElement() + " , ");
            }
        }
        if (k == 4) {//test Scheduler with ints complex
            Scheduler<Integer> scheduler = new Scheduler<Integer>();
            int counter = 0;
            ArrayList<Timestamp<Integer>> to_remove = new ArrayList<Timestamp<Integer>>();

            while (jin.hasNextLong()) {
                Timestamp<Integer> ti = new Timestamp<Integer>(new Date(jin.nextLong()), jin.nextInt());
                if ((counter & 7) == 0) {
                    to_remove.add(ti);
                }
                scheduler.add(ti);
                ++counter;
            }
            jin.next();

            while (jin.hasNextLong()) {
                Date l = new Date(jin.nextLong());
                Date h = new Date(jin.nextLong());
                ArrayList<Timestamp<Integer>> res = scheduler.getAll(l, h);
                Collections.sort(res);
                System.out.println(l + " <: " + print(res) + " >: " + h);
            }
            System.out.println("test");
            ArrayList<Timestamp<Integer>> res = scheduler.getAll(new Date(0), new Date(Long.MAX_VALUE));
            Collections.sort(res);
            System.out.println(print(res));
            for (Timestamp<Integer> ti : to_remove) {
                scheduler.remove(ti);
            }
            res = scheduler.getAll(new Date(0), new Date(Long.MAX_VALUE));
            Collections.sort(res);
            System.out.println(print(res));
        }
    }

    private static <T> String print(ArrayList<Timestamp<T>> res) {
        if (res == null || res.size() == 0) {
            return "NONE";
        }
        StringBuffer sb = new StringBuffer();
        for (Timestamp<?> t : res) {
            sb.append(t.getElement() + " , ");
        }
        return sb.substring(0, sb.length() - 3);
    }

    public static class Timestamp<T> implements Comparable<Timestamp<T>> {

        final Date date;
        final T element;

        public Timestamp(Date date, T element) {
            TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
            this.date = date;
            this.element = element;
        }

        public Date getDate() {
            return date;
        }

        public T getElement() {
            return element;
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 83 * hash + Objects.hashCode(this.date);
            hash = 83 * hash + Objects.hashCode(this.element);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            Timestamp<?> other = (Timestamp<?>) obj;

            if (this.date.equals(other.date) && this.element.equals(other.element)) {
                return true;
            } else {
                return false;
            }
        }

        @Override
        public int compareTo(Timestamp<T> o) {
            return this.date.compareTo(o.date);
        }

        @Override
        public String toString() {
            return String.format("%s %s", date.toString(), element.toString());
        }

    }

    public static class Scheduler<T> {

        ArrayList<Timestamp<T>> a;

        public Scheduler() {
            a = new ArrayList<>();

        }

        public void add(Timestamp<T> time) {
            a.add(time);
        }

        public boolean remove(Timestamp<T> t) {
            return a.remove(t);
        }

        public Timestamp<T> next() {
            Date now = new Date();
            Collections.sort(a);
            long razlika = 0;
            int index = 0;
            long minRazlika = Long.MAX_VALUE;

            for (int i = 0; i < a.size(); i++) {
                if (a.get(i).date.compareTo(now) == 0) {
                    index = i;
                } else if (a.get(i).date.compareTo(now) == 1) {
                    index = i;
                    break;
                }

            }

            return a.get(index);

        }

        public Timestamp<T> last() {
            Collections.sort(a);
            Timestamp<T> h = this.next();
            int temp = a.indexOf(h);
            return a.get(temp - 1);

        }

        public ArrayList<Timestamp<T>> getAll(Date begin, Date end) {
            ArrayList<Timestamp<T>> toRE = new ArrayList<>();

            for (Timestamp t : a) {
                if (t.date.after(begin) && t.date.before(end)) {
                    toRE.add(t);
                }
            }

            return toRE;
        }
    }
}
