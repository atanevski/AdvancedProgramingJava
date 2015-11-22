package other;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class F1Test {

    public static void main(String[] args) {
        F1Race f1Race = new F1Race();
        f1Race.readResults(System.in);
        f1Race.printSorted(System.out);
    }

}

class F1Race {

    ArrayList<Racer> racers = new ArrayList<>();

    void readResults(InputStream in) {
        Scanner scan = new Scanner(in);

        try {
            while (scan.hasNextLine()) {
                String driver = scan.nextLine();
                if ("".equals(driver)) {
                    break;
                }
                String[] split = driver.split(" ");
                Racer r = new Racer(split[0], split[1], split[2], split[3]);
                racers.add(r);

            }
        } catch (Exception e) {

        }
        scan.close();
    }

    void printSorted(OutputStream out) {
        Collections.sort(racers);
        int count = 1;
        PrintWriter pw = new PrintWriter(out);
        for (Racer r : racers) {
            pw.print(String.format("%s. %-10s%10s\n", count, r.name, r.bestLap));
            count++;
        }
        pw.close();

    }

}

class Racer implements Comparable<Racer> {

    String name;
    String lap1;
    String lap2;
    String lap3;
    String bestLap;

    public Racer(String name, String lap1, String lap2, String lap3) {
        this.name = name;
        this.lap1 = lap1;
        this.lap2 = lap2;
        this.lap3 = lap3;
        bestLap = BestTime();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLap1() {
        return lap1;
    }

    public void setLap1(String lap1) {
        this.lap1 = lap1;
    }

    public String getLap2() {
        return lap2;
    }

    public void setLap2(String lap2) {
        this.lap2 = lap2;
    }

    public String getLap3() {
        return lap3;
    }

    public void setLap3(String lap3) {
        this.lap3 = lap3;
    }

    @Override
    public String toString() {
        return "Racer{" + "name=" + name + ", lap1=" + lap1 + ", lap2=" + lap2 + ", lap3=" + lap3 + '}';
    }

    @Override
    public int compareTo(Racer o) {
        return this.bestLap.compareTo(o.bestLap);
    }

    private String BestTime() {
        ArrayList<String> bestTime = new ArrayList<>();
        bestTime.add(lap1);
        bestTime.add(lap2);
        bestTime.add(lap3);

        Collections.sort(bestTime);
        return bestTime.get(0);

    }

}
