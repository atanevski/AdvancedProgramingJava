package LAB3;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Scanner;

public class FloatMatrixTester {

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        int tests = scanner.nextInt();
        FloatMatrix fm = null;

        float[] info = null;

        DecimalFormat format = new DecimalFormat("0.00");

        for (int t = 0; t < tests; t++) {

            String operation = scanner.next();

            switch (operation) {
                case "READ": {
                    int N = scanner.nextInt();
                    int R = scanner.nextInt();
                    int C = scanner.nextInt();

                    float[] f = new float[N];

                    for (int i = 0; i < f.length; i++) {
                        f[i] = scanner.nextFloat();
                    }

                    try {
                        fm = new FloatMatrix(f, R, C);
                        info = Arrays.copyOf(f, f.length);

                    } catch (InsufficientElementsException e) {
                        System.out.println("Exception caught: " + e.getMessage());
                    }

                    break;
                }

                case "INPUT_TEST": {
                    int R = scanner.nextInt();
                    int C = scanner.nextInt();

                    StringBuilder sb = new StringBuilder();

                    sb.append(R + " " + C + "\n");

                    scanner.nextLine();

                    for (int i = 0; i < R; i++) {
                        sb.append(scanner.nextLine() + "\n");
                    }

                    fm = MatrixReader.readFloatMatrix(new ByteArrayInputStream(sb
                            .toString().getBytes()));

                    info = new float[R * C];
                    Scanner tempScanner = new Scanner(new ByteArrayInputStream(sb
                            .toString().getBytes()));
                    tempScanner.nextFloat();
                    tempScanner.nextFloat();
                    for (int z = 0; z < R * C; z++) {
                        info[z] = tempScanner.nextFloat();
                    }

                    tempScanner.close();

                    break;
                }

                case "PRINT": {
                    System.out.println(fm.toString());
                    break;
                }

                case "DIMENSION": {
                    System.out.println("Dimensions: " + fm.getMatrixDimensions());
                    break;
                }

                case "COUNT_ROWS": {
                    System.out.println("Rows: " + fm.numRows());
                    break;
                }

                case "COUNT_COLUMNS": {
                    System.out.println("Columns: " + fm.numColumns());
                    break;
                }

                case "MAX_IN_ROW": {
                    int row = scanner.nextInt();
                    try {
                        System.out.println("Max in row: "
                                + format.format(fm.maxElementAtRow(row)));
                    } catch (InvalidRowNumberException e) {
                        System.out.println("Exception caught: " + e.getMessage());
                    }
                    break;
                }

                case "MAX_IN_COLUMN": {
                    int col = scanner.nextInt();
                    try {
                        System.out.println("Max in column: "
                                + format.format(fm.maxElementAtColumn(col)));
                    } catch (InvalidColumnNumberException e) {
                        System.out.println("Exception caught: " + e.getMessage());
                    }
                    break;
                }

                case "SUM": {
                    System.out.println("Sum: " + format.format(fm.sum()));
                    break;
                }

                case "CHECK_EQUALS": {
                    int val = scanner.nextInt();

                    int maxOps = val % 7;

                    for (int z = 0; z < maxOps; z++) {
                        float work[] = Arrays.copyOf(info, info.length);

                        int e1 = (31 * z + 7 * val + 3 * maxOps) % info.length;
                        int e2 = (17 * z + 3 * val + 7 * maxOps) % info.length;

                        if (e1 > e2) {
                            float temp = work[e1];
                            work[e1] = work[e2];
                            work[e2] = temp;
                        }

                        FloatMatrix f1 = fm;
                        FloatMatrix f2 = new FloatMatrix(work, fm.numRows(),
                                fm.numColumns());
                        System.out
                                .println("Equals check 1: "
                                        + f1.equals(f2)
                                        + " "
                                        + f2.equals(f1)
                                        + " "
                                        + (f1.hashCode() == f2.hashCode() && f1
                                        .equals(f2)));
                    }

                    if (maxOps % 2 == 0) {
                        FloatMatrix f1 = fm;
                        FloatMatrix f2 = new FloatMatrix(new float[]{3.0f, 5.0f,
                            7.5f}, 1, 1);

                        System.out
                                .println("Equals check 2: "
                                        + f1.equals(f2)
                                        + " "
                                        + f2.equals(f1)
                                        + " "
                                        + (f1.hashCode() == f2.hashCode() && f1
                                        .equals(f2)));
                    }

                    break;
                }

                case "SORTED_ARRAY": {
                    float[] arr = fm.toSortedArray();

                    String arrayString = "[";

                    if (arr.length > 0) {
                        arrayString += format.format(arr[0]) + "";
                    }

                    for (int i = 1; i < arr.length; i++) {
                        arrayString += ", " + format.format(arr[i]);
                    }

                    arrayString += "]";

                    System.out.println("Sorted array: " + arrayString);

                    break;
                }

            }

        }

        scanner.close();
    }

    public static class MatrixReader {

        public MatrixReader() {
        }

        public static FloatMatrix readFloatMatrix(InputStream input) throws Exception {
            Scanner s = new Scanner(input);
            int rows = s.nextInt();
            int columns = s.nextInt();
            float[] arr = new float[columns * rows];
            int counter = 0;
            while (s.hasNext()) {
                arr[counter] = s.nextFloat();
                counter++;
            }
            s.close();
            return new FloatMatrix(arr, rows, columns);
        }
    }

    public static final class FloatMatrix {

        float a[];
        int m, n;
        int rowMatrix, columnMatrix;

        public FloatMatrix(float[] a, int m, int n) throws Exception {
            int mXn = m * n;
            if (a.length == mXn) {
                this.a = Arrays.copyOf(a, a.length);
            } else if (a.length > mXn) {

                int offset = a.length - mXn;
                float[] b = new float[mXn];

                for (int i = 0; i < mXn; i++) {
                    b[i] = a[i + offset];
                }

                this.a = Arrays.copyOf(b, b.length);

            } else {
                throw new InsufficientElementsException();
            }

            this.m = m;
            this.n = n;
            this.rowMatrix = m;
            this.columnMatrix = n;
        }

        public String getMatrixDimensions() {
            return String.format("[%d x %d]", this.m, this.n);
        }

        public int numRows() {
            return m;
        }

        public int numColumns() {
            return n;
        }

        public float maxElementAtRow(int row) throws InvalidRowNumberException {
            if (row > rowMatrix || row == 0) {
                throw new InvalidRowNumberException();
            } else {

                float[] rowArray1 = new float[columnMatrix];

                for (int i = 0; i < columnMatrix; i++) {

                    rowArray1[i] = this.a[(row - 1) * columnMatrix + i];
                }

                Arrays.sort(rowArray1);
                return rowArray1[rowArray1.length - 1];

            }

        }

        public float maxElementAtColumn(int column) throws InvalidColumnNumberException {

            if (column > columnMatrix || column <= 0) {
                throw new InvalidColumnNumberException();
            } else {

                float[] rowArray1 = new float[rowMatrix];

                for (int i = 0; i < rowMatrix; i++) {
                    rowArray1[i] = this.a[(i * columnMatrix) + (column - 1)];
                }

                Arrays.sort(rowArray1);

                return rowArray1[rowArray1.length - 1];

            }

        }

        public float sum() {

            float sum = (float) 0.0;

            for (int i = 0; i < a.length; i++) {
                sum += a[i];

            }

            return sum;

        }

        public float[] toSortedArray() {

            float[] temp;
            temp = Arrays.copyOf(this.a, this.a.length);

            Arrays.sort(temp);

            float[] tempReverse = new float[temp.length];
            for (int i = 0; i < temp.length; i++) {
                tempReverse[i] = temp[temp.length - i - 1];
            }

            return Arrays.copyOf(tempReverse, tempReverse.length);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            String temp = "";
            for (int i = 0; i < rowMatrix; i++) {
                temp = "";
                for (int j = 0; j < columnMatrix; j++) {
                    temp += String.format("%.2f\t", a[i * columnMatrix + j]);
                }
                temp = temp.trim();

                sb.append(temp);
                sb.append("\n");
            }

            return sb.toString().trim();
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 67 * hash + Arrays.hashCode(this.a);
            hash = 67 * hash + this.m;
            hash = 67 * hash + this.n;
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final FloatMatrix other = (FloatMatrix) obj;
            if (!Arrays.equals(this.a, other.a)) {
                return false;
            }
            if (this.m != other.m) {
                return false;
            }
            if (this.n != other.n) {
                return false;
            }
            return true;
        }

    }

    public static class InsufficientElementsException extends Exception {

        public InsufficientElementsException() {
            super("Insufficient number of elements");
        }
    }

    public static class InvalidRowNumberException extends Exception {

        public InvalidRowNumberException() {
            super("Invalid row number");
        }
    }

    public static class InvalidColumnNumberException extends Exception {

        public InvalidColumnNumberException() {
            super("Invalid column number");
        }
    }

}
