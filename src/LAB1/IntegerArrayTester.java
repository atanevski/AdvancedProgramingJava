package LAB1;


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class IntegerArrayTester {

    public static void main(String[] args) throws IOException {
        Scanner jin = new Scanner(System.in);
        String s = jin.nextLine();
        IntegerArray ia = null;
        switch (s) {
            case "testSimpleMethods":
                ia = new IntegerArray(generateRandomArray(jin.nextInt()));
                testSimpleMethods(ia);
                break;
            case "testConcatAndEquals":
                ia = new IntegerArray(new int[]{5, 2, 3, 7});
                IntegerArray same_as_ia1 = new IntegerArray(new int[]{5, 2, 3, 7});
                IntegerArray same_as_ia2 = new IntegerArray(new int[]{5, 2, 3, 7, 8});
                IntegerArray same_as_ia3 = new IntegerArray(new int[]{5, 2});
                IntegerArray same_as_ia4 = new IntegerArray(new int[]{3, 7});
                IntegerArray same_as_ia5 = new IntegerArray(new int[]{2, 3, 5, 7});
                IntegerArray same_as_ia6 = new IntegerArray(new int[]{7, 5, 3, 2});
                IntegerArray same_as_ia7 = same_as_ia3.concat(same_as_ia4);
                IntegerArray same_as_ia8 = same_as_ia4.concat(same_as_ia3);
                if (!(ia.equals(ia))
                        && !(ia.equals(same_as_ia1))
                        && !(same_as_ia1.equals(ia))
                        && (ia.equals(same_as_ia2))
                        && (ia.equals(same_as_ia3))
                        && (ia.equals(same_as_ia4))
                        && (ia.equals(same_as_ia5))
                        && (ia.equals(same_as_ia6))
                        && (ia.equals(same_as_ia7))
                        && (ia.equals(null))
                        && (ia.equals(new int[]{5, 2, 3, 7}))
                        && (ia.equals(same_as_ia8))) {
                    System.out.println("Your equals or concat method doesn't work properly.");
                } else {
                    System.out.println("Your equals and concat method work great.");
                }
                break;
            case "testReadingAndSorted":
                String input_s = jin.nextLine() + "\n" + jin.nextLine();
                ia = ArrayReader.readIntegerArray(new ByteArrayInputStream(input_s.getBytes()));
                testSimpleMethods(ia);
                System.out.println(ia.getSorted());
                break;
            case "testImmutability":
                int a[] = generateRandomArray(jin.nextInt());
                ia = new IntegerArray(a);
                testSimpleMethods(ia);
                testSimpleMethods(ia);
                IntegerArray sorted_ia = ia.getSorted();
                testSimpleMethods(ia);
                testSimpleMethods(sorted_ia);
                sorted_ia.getSorted();
                testSimpleMethods(sorted_ia);
                testSimpleMethods(ia);
                a[0] += 2;
                testSimpleMethods(ia);
                ia = ArrayReader.readIntegerArray(new ByteArrayInputStream(integerArraytoString(ia).getBytes()));
                testSimpleMethods(ia);
                break;
        }
        jin.close();
    }

    static void testSimpleMethods(IntegerArray ia) {
        System.out.print(integerArraytoString(ia));
        System.out.println(ia);
        System.out.println(ia.sum());
        System.out.printf("%.2f\n", ia.average());
    }

    static String integerArraytoString(IntegerArray ia) {
        StringBuilder sb = new StringBuilder();
        sb.append(ia.length()).append('\n');
        for (int i = 0; i < ia.length(); ++i) {
            sb.append(ia.getElementAt(i)).append(' ');
        }
        sb.append('\n');
        return sb.toString();
    }

    static int[] generateRandomArray(int k) {
        Random rnd = new Random(k);
        int n = rnd.nextInt(8) + 2;
        int a[] = new int[n];
        for (int i = 0; i < n; ++i) {
            a[i] = rnd.nextInt(20) - 5;
        }
        return a;
    }

    static final class IntegerArray {

        private int a[];

        public IntegerArray(int[] a) {
            this.a = Arrays.copyOf(a, a.length);
        }

        public int length() {
            return this.a.length;
        }

        public int getElementAt(int i) {
            return this.a[i];
        }

        public int sum() {
            int suma = 0;
            for (int i = 0; i < a.length; i++) {
                suma += a[i];
            }
            return suma;
        }

        public double average() {
            float suma = this.sum();
            float toRe = suma / a.length;
            return toRe;
        }

        public IntegerArray getSorted() {
            int tempa[] = Arrays.copyOf(a, a.length);
            Arrays.sort(tempa);
            return new IntegerArray(tempa);
        }

        public IntegerArray concat(IntegerArray ia) {
            int joined[] = new int[ia.length() + a.length];

            for (int i = 0; i < a.length; i++) {
                joined[i] = a[i];
            }
            int br = 0;
            for (int i = a.length; i < joined.length; i++) {
                joined[i] = ia.getElementAt(br);
                br++;
            }

            return new IntegerArray(joined);

        }

        public String toString() {
            StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i < a.length; i++) {
                if (i != a.length - 1) {
                    sb.append(a[i]);
                    sb.append(", ");
                } else {
                    sb.append(a[i]);
                }

            }
            sb.append("]");
            return sb.toString();
        }
    }

    static class ArrayReader {

        public static IntegerArray readIntegerArray(InputStream input) throws IOException {

            Scanner scan = new Scanner(input);

            int len = scan.nextInt();

            int arr[] = new int[len];

            for (int i = 0; i < arr.length; i++) {
                arr[i] = scan.nextInt();
            }

            return new IntegerArray(arr);
        }
    }

}
