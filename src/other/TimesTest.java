package other;


import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class TimesTest {

    public static void main(String[] args) {
        TimeTable timeTable = new TimeTable();
        try {
            timeTable.readTimes(System.in);
        } catch (UnsupportedFormatException e) {
            System.out.println("UnsupportedFormatException: " + e.getMessage());
        } catch (InvalidTimeException e) {
            System.out.println("InvalidTimeException: " + e.getMessage());
        }
        System.out.println("24 HOUR FORMAT");
        timeTable.writeTimes(System.out, TimeFormat.FORMAT_24);
        System.out.println("AM/PM FORMAT");
        timeTable.writeTimes(System.out, TimeFormat.FORMAT_AMPM);
    }

    public static class TimeTable {

        ArrayList<Time> arrayTime = new ArrayList<>();

        void readTimes(InputStream inputStream) throws InvalidTimeException, UnsupportedFormatException {
            Scanner s = new Scanner(inputStream);

            while (s.hasNext()) {
                String line = s.next();

                System.out.println(line);
                if (line.contains("\\.")) {
                    String temp[] = line.split("\\.");
                    System.out.println(Arrays.toString(temp));
                } 
                if (line.contains(":")) {
                    String temp[] = line.split(":");
                    System.out.println(Arrays.toString(temp));
                } 

            }
            s.close();

        }

        void writeTimes(OutputStream outputStream, TimeFormat format) {
            PrintWriter pw = new PrintWriter(outputStream);

            for (Time t : arrayTime) {
                System.out.println(t.toString());
            }

            for (Time t : arrayTime) {
                if (format == TimeFormat.FORMAT_24) {
                    pw.println(t.h + ":" + t.m);
                } else {
                    if (t.h == 0) {
                        int tt = t.h + 12;
                        pw.println(tt + ":" + t.m + " AM");
                    } else if (t.h >= 1 && t.h <= 11) {
                        pw.println(t.h + ":" + t.m + " AM");
                    } else if (t.h == 12) {
                        pw.println(t.h + ":" + t.m + " PM");
                    } else if (t.h >= 13 && t.h <= 23) {
                        int tt = t.h - 12;
                        pw.println(tt + ":" + t.m + " PM");
                    }
                }
            }
            pw.flush();
        }

    }

    public static class Time {

        @Override
        public String toString() {
            return "Time{" + "h=" + h + ", m=" + m + '}';
        }

        int h;
        int m;

        public Time(int h, int m) {
            this.h = h;
            this.m = m;
        }

        public int getH() {
            return h;
        }

        public int getM() {
            return m;
        }

        public void setH(int h) {
            this.h = h;
        }

        public void setM(int m) {
            this.m = m;
        }

    }

    private static class InvalidTimeException extends Exception {

        public InvalidTimeException() {
        }
    }

    private static class UnsupportedFormatException extends Exception {

        public UnsupportedFormatException() {
        }
    }
}

enum TimeFormat {

    FORMAT_24, FORMAT_AMPM
}
