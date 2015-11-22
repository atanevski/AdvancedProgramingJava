package LAB11;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class SuperStringTest {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if (k == 0) {
            SuperString s = new SuperString();
            while (true) {
                int command = jin.nextInt();
                if (command == 0) {//append(String s)
                    s.append(jin.next());
                }
                if (command == 1) {//insert(String s)
                    s.insert(jin.next());
                }
                if (command == 2) {//contains(String s)
                    System.out.println(s.contains(jin.next()));
                }
                if (command == 3) {//reverse()
                    s.reverse();
                }
                if (command == 4) {//toString()
                    System.out.println(s);
                }
                if (command == 5) {//removeLast(int k)
                    s.removeLast(jin.nextInt());
                }
                if (command == 6) {//end
                    break;
                }
            }
        }
    }

    private static class SuperString {

        LinkedList<String> list;
        LinkedList<String> listControl;

        public SuperString() {
            list = new LinkedList<>();
            listControl = new LinkedList<>();
        }

        public void append(String s) {
            list.add(s);
            listControl.add(s);
        }

        public void insert(String s) {
            list.addFirst(s);
            listControl.add(s);
        }

        public boolean contains(String s) {
            return toString().contains(s);
        }

        public void reverse() {
            Iterator<String> iter = list.descendingIterator();
            LinkedList<String> tempList = new LinkedList<>();

            while (iter.hasNext()) {

                String element = iter.next();
                String reverse = new StringBuilder(element).reverse().toString();
                listControl.set(listControl.indexOf(element), reverse);
                tempList.add(reverse);

            }

            list = tempList;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            Iterator<String> iter = list.iterator();
            while (iter.hasNext()) {
                sb.append(iter.next());
            }

            return sb.toString();
        }

        public void removeLast(int k) {
            int tempK = k;
            while (k != 0) {
                list.remove(listControl.pollLast());
                k--;
            }
        }

    }

}
