package LAB10;

import java.util.Arrays;
import java.util.Scanner;
import java.util.LinkedList;

public class ResizableArrayTest {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int test = jin.nextInt();
        if (test == 0) { //test ResizableArray on ints
            ResizableArray<Integer> a = new ResizableArray<Integer>();
            System.out.println(a.count());
            int first = jin.nextInt();
            a.addElement(first);
            System.out.println(a.count());
            int last = first;
            while (jin.hasNextInt()) {
                last = jin.nextInt();
                a.addElement(last);
            }
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(a.removeElement(first));
            System.out.println(a.contains(first));
            System.out.println(a.count());
        }
        if (test == 1) { //test ResizableArray on strings
            ResizableArray<String> a = new ResizableArray<String>();
            System.out.println(a.count());
            String first = jin.next();
            a.addElement(first);
            System.out.println(a.count());
            String last = first;
            for (int i = 0; i < 4; ++i) {
                last = jin.next();
                a.addElement(last);
            }
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(a.removeElement(first));
            System.out.println(a.contains(first));
            System.out.println(a.count());
            ResizableArray<String> b = new ResizableArray<String>();
            ResizableArray.copyAll(b, a);
            System.out.println(b.count());
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(b.contains(first));
            System.out.println(b.contains(last));
            ResizableArray.copyAll(b, a);
            System.out.println(b.count());
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(b.contains(first));
            System.out.println(b.contains(last));
            System.out.println(b.removeElement(first));
            System.out.println(b.contains(first));
            System.out.println(b.removeElement(first));
            System.out.println(b.contains(first));

            System.out.println(a.removeElement(first));
            ResizableArray.copyAll(b, a);
            System.out.println(b.count());
            System.out.println(a.count());
            System.out.println(a.contains(first));
            System.out.println(a.contains(last));
            System.out.println(b.contains(first));
            System.out.println(b.contains(last));
        }
        if (test == 2) { //test IntegerArray
            IntegerArray a = new IntegerArray();
            System.out.println(a.isEmpty());
            while (jin.hasNextInt()) {
                a.addElement(jin.nextInt());
            }
            jin.next();
            System.out.println(a.sum());
            System.out.println(a.mean());
            System.out.println(a.countNonZero());
            System.out.println(a.count());
            IntegerArray b = a.distinct();
            System.out.println(b.sum());
            IntegerArray c = a.increment(5);
            System.out.println(c.sum());
            if (a.sum() > 100) {
                ResizableArray.copyAll(a, a);
            } else {
                ResizableArray.copyAll(a, b);
            }
            System.out.println(a.sum());
            System.out.println(a.removeElement(jin.nextInt()));
            System.out.println(a.sum());
            System.out.println(a.removeElement(jin.nextInt()));
            System.out.println(a.sum());
            System.out.println(a.removeElement(jin.nextInt()));
            System.out.println(a.sum());
            System.out.println(a.contains(jin.nextInt()));
            System.out.println(a.contains(jin.nextInt()));
        }
        if (test == 3) { //test insanely large arrays
            LinkedList<ResizableArray<Integer>> resizable_arrays = new LinkedList<ResizableArray<Integer>>();
            for (int w = 0; w < 500; ++w) {
                ResizableArray<Integer> a = new ResizableArray<Integer>();
                int k = 2000;
                int t = 1000;
                for (int i = 0; i < k; ++i) {
                    a.addElement(i);
                }

                a.removeElement(0);
                for (int i = 0; i < t; ++i) {
                    a.removeElement(k - i - 1);
                }
                resizable_arrays.add(a);
            }
            System.out.println("You implementation finished in less then 3 seconds, well done!");
        }
    }

    public static class ResizableArray<T> {

        private T[] array;
        int count;

        public ResizableArray() {
            array = (T[]) new Object[10];
            count = 0;
        }

        public void addElement(T element) {

            if (count == array.length) {
                T[] tempArray = (T[]) new Object[array.length * 2];
                for (int i = 0; i < array.length; i++) {
                    tempArray[i] = array[i];
                }
                tempArray[array.length] = element;
                array = tempArray;
                count++;
            } else {
                for (int i = 0; i < array.length; i++) {
                    if (array[i] == null) {
                        array[i] = element;
                        count++;
                        break;
                    }

                }
            }

        }

        public boolean removeElement(T element) {
            int index = -1;
            for (int i = 0; i < count; i++) {
                if (array[i].equals(element)) {
                    index = i;
                }
            }
            if (index == -1) {
                return false;
            } else {
                T[] temp = (T[]) new Object[count - 1];
                for (int i = 0; i < index; i++) {
                    temp[i] = array[i];
                }
                for (int i = index + 1; i < count; i++) {
                    temp[i - 1] = array[i];
                }
                count--;
                array = temp;
                return true;
            }
        }

        public boolean contains(T element) {
            for (int i = 0; i < count; i++) {
                if (array[i].equals(element)) {
                    return true;
                }
            }
            return false;
        }

        public Object[] toArray() {
            return Arrays.copyOf(array, array.length);
        }

        public int count() {
            return count;
        }

        public T elementAt(int idx) {
            try {
                return array[idx];
            } catch (Exception e) {
                throw new ArrayIndexOutOfBoundsException();
            }

        }

        public static <T> void copyAll(ResizableArray<? super T> dest, ResizableArray<? extends T> src) {

            T[] temp = (T[]) new Object[dest.count + src.count];

            int destIndex = dest.count;

            for (int i = 0; i < dest.count; i++) {
                temp[i] = (T) dest.elementAt(i);
            }
            for (int i = 0; i < src.count; i++) {
                temp[destIndex] = src.elementAt(i);
                destIndex++;
            }
            dest.count = dest.count + src.count;
            dest.array = temp;

        }

        public boolean isEmpty() {
            return count == 0;

        }
    }

    public static class IntegerArray extends ResizableArray<Integer> {

        public double sum() {
            double total = 0;

            for (int i = 0; i < this.count; i++) {
                total += elementAt(i);

            }

            return total;
        }

        public double mean() {
            double total = sum();
            return total / this.count;
        }

        public int countNonZero() {
            int counter = 0;
            for (int i = 0; i < this.count; i++) {
                if (elementAt(i) != 0) {
                    counter++;
                }

            }
            return counter;
        }

        public IntegerArray distinct() {

            IntegerArray toRE = new IntegerArray();

            for (int i = 0; i < this.count; i++) {
                if (!toRE.contains(elementAt(i))) {
                    toRE.addElement(elementAt(i));
                }
            }
            return toRE;
        }

        public IntegerArray increment(int offset) {
            IntegerArray toRE = new IntegerArray();
            for (int i = 0; i < super.count(); i++) {
                toRE.addElement(super.elementAt(i) + offset);
            }
            return toRE;
        }

    }
}
