package other;


import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FrontPageTest {

    public static void main(String[] args) {
        // Reading
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        String[] parts = line.split(" ");
        Category[] categories = new Category[parts.length];
        for (int i = 0; i < categories.length; ++i) {
            categories[i] = new Category(parts[i]);
        }
        int n = scanner.nextInt();
        scanner.nextLine();
        FrontPage frontPage = new FrontPage(categories);
        Calendar cal = Calendar.getInstance();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            cal = Calendar.getInstance();
            int min = scanner.nextInt();
            cal.add(Calendar.MINUTE, -min);
            Date date = cal.getTime();
            scanner.nextLine();
            String text = scanner.nextLine();
            int categoryIndex = scanner.nextInt();
            scanner.nextLine();
            TextNewsItem tni = new TextNewsItem(title, date, categories[categoryIndex], text);
            frontPage.addNewsItem(tni);
        }

        n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            int min = scanner.nextInt();
            cal = Calendar.getInstance();
            cal.add(Calendar.MINUTE, -min);
            scanner.nextLine();
            Date date = cal.getTime();
            String url = scanner.nextLine();
            int views = scanner.nextInt();
            scanner.nextLine();
            int categoryIndex = scanner.nextInt();
            scanner.nextLine();
            MediaNewsItem mni = new MediaNewsItem(title, date, categories[categoryIndex], url, views);
            frontPage.addNewsItem(mni);
        }
        // Execution
        String category = scanner.nextLine();
        System.out.println(frontPage);
        for (Category c : categories) {
            System.out.println(frontPage.listByCategory(c).size());
        }
        try {
            System.out.println(frontPage.listByCategoryName(category).size());
        } catch (CategoryNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static class Category {

        String name;

        public Category(String name) {
            this.name = name;
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 11 * hash + Objects.hashCode(this.name);
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
            final Category other = (Category) obj;

            if (!this.name.equals(other.name)) {
                return false;
            }
            return true;
        }

    }

    private static class CategoryNotFoundException extends Exception {

        public CategoryNotFoundException(String category) {
            super(category);
        }
    }

    public static abstract class NewsItem {

        String naslov;
        Date date;
        Category category;

        public NewsItem(String naslov, Date date, Category category) {
            this.naslov = naslov;
            this.date = date;
            this.category = category;
        }

        public abstract String getTeaser();
    }

    public static class TextNewsItem extends NewsItem {

        String text;

        public TextNewsItem(String naslov, Date date, Category category, String text) {
            super(naslov, date, category);
            this.text = text;
        }

        @Override
        public String getTeaser() {
            String mintext = null;
            Date now = new Date();
            int min = (int) (now.getTime() - date.getTime() / 1000 / 60 );
            if (text.length() > 80) {
                mintext = text.substring(0, 80);
            } else {
                mintext = text;
            }
            return String.format("%s\n%d\n%s", naslov, min, mintext);
        }
    }

    public static class MediaNewsItem extends NewsItem {

        String url;
        int views;

        public MediaNewsItem(String naslov, Date date, Category category, String url, int views) {
            super(naslov, date, category);
            this.url = url;
            this.views = views;
        }

        @Override
        public String getTeaser() {
            Date now = new Date();
            int min = (int) (now.getTime() - date.getTime() / 1000 / 60);
            return String.format("%s\n%d\n%s\n%d", naslov, min, url, views);
        }
    }

    public static class FrontPage {

        ArrayList<NewsItem> news;
        Category[] categories;

        public FrontPage(Category[] categories) {
            this.categories = categories;
            news = new ArrayList<>();
        }

        public void addNewsItem(NewsItem newsItem) {
            news.add(newsItem);
        }

        public List<NewsItem> listByCategory(Category category) {
            ArrayList<NewsItem> toReturn = new ArrayList<>();

            for (NewsItem item : news) {
                if (item.category.equals(category)) {
                    toReturn.add(item);
                }
            }

            return toReturn;
        }

        public List<NewsItem> listByCategoryName(String category) throws CategoryNotFoundException {
            ArrayList<NewsItem> toReturn = new ArrayList<>();

            boolean checkCat = false;

            for (int i = 0; i < categories.length; i++) {
                if (categories[i].name.equals(category)) {
                    checkCat = true;
                }

            }

            if (checkCat) {
                for (NewsItem item : news) {
                    if (item.category.name.equals(category)) {
                        toReturn.add(item);
                    }
                }
                return toReturn;
            } else {
                throw new CategoryNotFoundException(category);
            }

        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();

            for (NewsItem item : news) {
                sb.append(item.getTeaser());
                sb.append("\n");
            }

            return sb.toString().trim();

        }

    }
}


// Vasiot kod ovde
