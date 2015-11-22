package LAB12;

import java.util.ArrayList;
import java.util.Scanner;

public class IntegerListTest {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if (k == 0) { // test standard methods
            int subtest = jin.nextInt();
            if (subtest == 0) {
                IntegerList list = new IntegerList();
                while (true) {
                    int num = jin.nextInt();
                    if (num == 0) {
                        list.add(jin.nextInt(), jin.nextInt());
                    }
                    if (num == 1) {
                        list.remove(jin.nextInt());
                    }
                    if (num == 2) {
                        print(list);
                    }
                    if (num == 3) {
                        break;
                    }
                }
            }
            if (subtest == 1) {
                int n = jin.nextInt();
                Integer a[] = new Integer[n];
                for (int i = 0; i < n; ++i) {
                    a[i] = jin.nextInt();
                }
                IntegerList list = new IntegerList(a);
                print(list);
            }
        }
        if (k == 1) { // test count,remove duplicates, addValue
            int n = jin.nextInt();
            Integer a[] = new Integer[n];
            for (int i = 0; i < n; ++i) {
                a[i] = jin.nextInt();
            }
            IntegerList list = new IntegerList(a);
            while (true) {
                int num = jin.nextInt();
                if (num == 0) { // count
                    System.out.println(list.count(jin.nextInt()));
                }
                if (num == 1) {
                    list.removeDuplicates();
                }
                if (num == 2) {
                    print(list.addValue(jin.nextInt()));
                }
                if (num == 3) {
                    list.add(jin.nextInt(), jin.nextInt());
                }
                if (num == 4) {
                    print(list);
                }
                if (num == 5) {
                    break;
                }
            }
        }
        if (k == 2) { // test shiftRight, shiftLeft, sumFirst , sumLast
            int n = jin.nextInt();
            Integer a[] = new Integer[n];
            for (int i = 0; i < n; ++i) {
                a[i] = jin.nextInt();
            }
            IntegerList list = new IntegerList(a);
            while (true) {
                int num = jin.nextInt();
                if (num == 0) { // count
                    list.shiftLeft(jin.nextInt(), jin.nextInt());
                }
                if (num == 1) {
                    list.shiftRight(jin.nextInt(), jin.nextInt());
                }
                if (num == 2) {
                    System.out.println(list.sumFirst(jin.nextInt()));
                }
                if (num == 3) {
                    System.out.println(list.sumLast(jin.nextInt()));
                }
                if (num == 4) {
                    print(list);
                }
                if (num == 5) {
                    break;
                }
            }
        }
    }

    public static void print(IntegerList il) {
        if (il.size() == 0) {
            System.out.print("EMPTY");
        }
        for (int i = 0; i < il.size(); ++i) {
            if (i > 0) {
                System.out.print(" ");
            }
            System.out.print(il.get(i));
        }
        System.out.println();
    }

}

class IntegerList {

    protected ArrayList<Integer> numbers;

    public IntegerList() {
        this.numbers = new ArrayList<>();
    }

    public IntegerList(Integer... integers) {
        this.numbers = new ArrayList<>();
        for (Integer number : integers) {
            numbers.add(number);
        }
    }

    public void add(int element, int idx) {
        if (idx == numbers.size()) {
            numbers.add(element);
        } else if (idx > numbers.size()) {
            for (int i = numbers.size(); i < idx; i++) {
                numbers.add(0);
            }
            numbers.add(element);
        } else {
            numbers.add(0);
            for (int i = numbers.size() - 1; i > idx; i--) {
                numbers.set(i, numbers.get(i - 1));
            }
            numbers.set(idx, element);
        }

    }

    public int remove(int idx) throws ArrayIndexOutOfBoundsException {
        if (idx > numbers.size() - 1) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int temp = numbers.get(idx);
        numbers.remove(idx);
        return temp;
    }

    public void set(int element, int idx) throws ArrayIndexOutOfBoundsException {
        if (idx > numbers.size() - 1) {
            throw new ArrayIndexOutOfBoundsException();
        }
        numbers.set(idx, element);
    }

    public int get(int idx) throws ArrayIndexOutOfBoundsException {
        if (idx > numbers.size() - 1) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return numbers.get(idx);
    }

    public int size() {
        return numbers.size();
    }

    public int count(int element) {
        int toReturn = 0;
        Integer temp = element;
        for (Integer number : numbers) {
            if (number.equals(temp)) {
                toReturn++;
            }
        }
        return toReturn;
    }

    public void removeDuplicates() {
        ArrayList<Integer> temp = new ArrayList<>();
        for (int i = numbers.size() - 1; i >= 0; i--) {
            if (!temp.contains(numbers.get(i))) {
                temp.add(0, numbers.get(i));
            }
        }
        numbers = temp;
    }

    public int sumFirst(int k) throws ArrayIndexOutOfBoundsException {
        int toReturn = 0;
        if (k > numbers.size()) {
            for (int i = 0; i < numbers.size(); i++) {
                toReturn += numbers.get(i);
            }
        } else {
            for (int i = 0; i < k; i++) {
                toReturn += numbers.get(i);
            }
        }
        return toReturn;
    }

    public int sumLast(int k) throws ArrayIndexOutOfBoundsException {
        int toReturn = 0;
        if (k > numbers.size()) {
            for (int i = 0; i < numbers.size(); i++) {
                toReturn += numbers.get(i);
            }
        } else {
            for (int i = 0; i < k; i++) {
                toReturn += numbers.get(numbers.size() - 1 - i);
            }
        }
        return toReturn;
    }

    public void shiftRight(int idx, int k) throws ArrayIndexOutOfBoundsException {
        if (idx > numbers.size() - 1) {
            throw new ArrayIndexOutOfBoundsException();
        }

        for (int j = 0; j < k; j++) {
            if (idx == numbers.size() - 1) {
                Integer temp = numbers.get(numbers.size() - 1);
                for (int i = numbers.size() - 1; i > 0; i--) {
                    numbers.set(i, numbers.get(i - 1));
                }
                numbers.set(0, temp);
            } else {
                Integer temp = numbers.get(idx);
                numbers.set(idx, numbers.get((idx + 1)));
                numbers.set((idx + 1), temp);
            }
            idx++;
            if (idx == numbers.size()) {
                idx = 0;
            }
        }
    }

    public void shiftLeft(int idx, int k) throws ArrayIndexOutOfBoundsException {
        if (idx > numbers.size() - 1) {
            throw new ArrayIndexOutOfBoundsException();
        }
        for (int j = 0; j < k; j++) {
            if (idx != 0) {
                Integer temp = numbers.get(idx);
                numbers.set(idx, numbers.get((idx - 1)));
                numbers.set((idx - 1), temp);
                idx--;
            } else {
                Integer temp = numbers.get(0);
                for (int i = 0; i < numbers.size() - 1; i++) {
                    numbers.set(i, numbers.get(i + 1));
                }
                numbers.set(numbers.size() - 1, temp);
                idx = numbers.size() - 1;
            }
        }
    }

    public IntegerList addValue(int value) {
        IntegerList toReturn = new IntegerList();
        for (Integer number : numbers) {
            toReturn.numbers.add(number + value);
        }
        return toReturn;
    }
}
