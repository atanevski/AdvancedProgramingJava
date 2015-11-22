package other;




import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class ArchiveStoreTest {

    public static void main(String[] args) {
        ArchiveStore store = new ArchiveStore();
        Date date = new Date(113, 10, 7);
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        int n = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        int i;
        for (i = 0; i < n; ++i) {
            int id = scanner.nextInt();
            long days = scanner.nextLong();
            Date dateToOpen = new Date(date.getTime() + (days * 24 * 60
                    * 60 * 1000));
            LockedArchive lockedArchive = new LockedArchive(id, dateToOpen);
            store.archiveItem(lockedArchive, date);
        }
        scanner.nextLine();
        scanner.nextLine();
        n = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        for (i = 0; i < n; ++i) {
            int id = scanner.nextInt();
            int maxOpen = scanner.nextInt();
            SpecialArchive specialArchive = new SpecialArchive(id, maxOpen);
            store.archiveItem(specialArchive, date);
        }
        scanner.nextLine();
        scanner.nextLine();
        while (scanner.hasNext()) {
            int open = scanner.nextInt();
            try {
                store.openItem(open, date);
            } catch (NonExistingItemException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(store.getLog());
    }

    private static class NonExistingItemException extends Exception {

        public NonExistingItemException(int id) {
            super(String.format("Item with id %d doesn't exist", id));
        }
    }

    public static class ArchiveStore {

        ArrayList<Archive> archives;

        public ArchiveStore() {
            archives = new ArrayList<>();
        }

        public void archiveItem(Archive item, Date date) {
            item.dateArchived = date;
            archives.add(item);
            System.out.println(String.format("Item %d archived at %s", item.id, date.toString()));
        }

        public void openItem(int id, Date date) throws NonExistingItemException {
            boolean found = false;
            for (Archive a : archives) {
                if (a.dateArchived.equals(date) && a.id == id) {
                    a.openArchive();
                    found = true;
                }
            }
            if (!found) {
                throw new NonExistingItemException(id);
            }
        }

        private String getLog() {
            return "getLOG";
        }

    }

    public static abstract class Archive {

        int id;
        Date dateArchived;
        boolean opened;

        public abstract String getTypeOfArchive();

        public abstract void openArchive();

    }

    public static class LockedArchive extends Archive {

        private Date dateToOpen;

        public LockedArchive(int id, Date dateToOpen) {
            this.id = id;
            this.dateToOpen = dateToOpen;

            this.opened = false;
            this.dateArchived = null;
        }

        @Override
        public String getTypeOfArchive() {
            return "Locked";
        }

        @Override
        public void openArchive() {
            Date now = new Date();
            long test = now.getTime() - dateToOpen.getTime();
            System.out.println( "=====" +dateToOpen.toString()+ "-------" + test);
            if (test < 0) {
                System.out.println(String.format("Item %d cannot be opened before %s", id, dateToOpen.toString()));
            } else {
                System.out.println(String.format("Item %d opened at %s", id, now.toString()));
            }
        }

    }

    public static class SpecialArchive extends Archive {

        int maxOpen;
        int tempMaxOpen;

        SpecialArchive(int id, int maxOpen) {
            this.id = id;
            this.maxOpen = maxOpen;
            this.tempMaxOpen = maxOpen;
            this.opened = false;
            this.dateArchived = null;
        }

        @Override
        public String getTypeOfArchive() {
            return "Special";
        }

        @Override
        public void openArchive() {
            if (tempMaxOpen > 0) {
                tempMaxOpen--;
                System.out.println(String.format("Item %d opened at %s", id, new Date().toString()));
            } else {
                System.out.println(String.format("Item %d cannot be opened more than %d times",id, maxOpen));
            }
        }
    }

}

// вашиот код овде

