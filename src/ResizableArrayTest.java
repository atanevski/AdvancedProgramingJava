
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.List;

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
            boolean temp = false;
            for (int i = 0; i < array.length; i++) {
                if (array[i].equals(element)) {
                    array[i] = null;
                    temp = true;
                    count--;
                    break;
                }
            }
            return temp;
        }

        public boolean contains(T element) {
            for (int i = 0; i < array.length; i++) {
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
            T[] tempArray = (T[]) new Object[dest.count + src.count];

            int destCount = dest.count;
            int srcCount = src.count;

            for (int i = 0; i < destCount; i++) {
                tempArray[i] = (T) dest.elementAt(i);
            }

            for (int i = 0; i < srcCount; i++) {
                tempArray[i + destCount] = src.elementAt(i);
            }


        }

    }

}
