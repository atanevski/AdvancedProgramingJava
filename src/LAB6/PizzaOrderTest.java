package LAB6;


import java.util.ArrayList;
import java.util.Scanner;

public class PizzaOrderTest {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if (k == 0) { //test Item
            try {
                String type = jin.next();
                String name = jin.next();
                Item item = null;
                if (type.equals("Pizza")) {
                    item = new PizzaItem(name);
                } else {
                    item = new ExtraItem(name);
                }
                System.out.println(item.getPrice());
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
        }
        if (k == 1) { // test simple order
            Order order = new Order();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    Item item = null;
                    if (type.equals("Pizza")) {
                        item = new PizzaItem(name);
                    } else {
                        item = new ExtraItem(name);
                    }
                    if (!jin.hasNextInt()) {
                        break;
                    }
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            jin.next();
            System.out.println(order.getPrice());
            order.displayOrder();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    Item item = null;
                    if (type.equals("Pizza")) {
                        item = new PizzaItem(name);
                    } else {
                        item = new ExtraItem(name);
                    }
                    if (!jin.hasNextInt()) {
                        break;
                    }
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            System.out.println(order.getPrice());
            order.displayOrder();
        }
        if (k == 2) { // test order with removing
            Order order = new Order();
            while (true) {

                try {
                    String type = jin.next();
                    String name = jin.next();
                    Item item = null;
                    if (type.equals("Pizza")) {
                        item = new PizzaItem(name);
                    } else {
                        item = new ExtraItem(name);
                    }
                    if (!jin.hasNextInt()) {
                        break;
                    }
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            jin.next();
            System.out.println(order.getPrice());
            order.displayOrder();
            while (jin.hasNextInt()) {
                try {
                    int idx = jin.nextInt();
                    order.removeItem(idx);
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            System.out.println(order.getPrice());
            order.displayOrder();
        }
        if (k == 3) { //test locking & exceptions
            Order order = new Order();
            try {
                order.lock();
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.addItem(new ExtraItem("Coke"), 1);
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.lock();
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.addItem(new PizzaItem("Standard"), 1);
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.lock();
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.addItem(new PizzaItem("Standard"), 1);
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.removeItem(0);
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
        }
    }

    private static class InvalidExtraTypeException extends Exception {

        public InvalidExtraTypeException() {
        }
    }

    private static class InvalidPizzaTypeException extends Exception {

        public InvalidPizzaTypeException() {
        }
    }

    private static class ItemOutOfStockException extends Exception {

        public ItemOutOfStockException(Item item) {
        }
    }

    private static class ArrayIndexOutOfBоundsException extends Exception {

        public ArrayIndexOutOfBоundsException(int idx) {
        }
    }

    private static class NoPizzaException extends Exception {

        public NoPizzaException() {
        }
    }

    private static class OrderLockedException extends Exception {

        public OrderLockedException() {
        }
    }

    public static abstract class Item implements Comparable<Item> {

        int Price;
        String name;
        int Count;

        public Item(String name) {
            this.name = name;
            this.Count = 0;
        }

        public int getPrice() {
            return Price;
        }

        public abstract String type();

        @Override
        public int compareTo(Item o) {
            return this.name.compareTo(o.name);
        }
    }

    public static class ExtraItem extends Item {

        public ExtraItem(String name) throws InvalidExtraTypeException {
            super(name);
            if ("Coke".equals(name) || "Ketchup".equals(name)) {
                if ("Coke".equals(name)) {
                    Price = 5;
                } else {
                    Price = 3;
                }
            } else {
                throw new InvalidExtraTypeException();
            }
        }

        @Override
        public String type() {
            return "Extra";
        }

    }

    public static class PizzaItem extends Item {

        final String Pizzas = "Standard Pepperoni Vegetarian";

        public PizzaItem(String name) throws InvalidPizzaTypeException {
            super(name);

            if (Pizzas.contains(name)) {
                if ("Standard".equals(name)) {
                    Price = 10;
                } else if ("Pepperoni".equals(name)) {
                    Price = 12;
                } else if ("Vegetarian".equals(name)) {
                    Price = 8;
                }
            } else {
                throw new InvalidPizzaTypeException();
            }

        }

        @Override
        public String type() {
            return "Pizza";
        }

    }

    public static class Order {

        ArrayList<Item> orderItems;
        boolean isLocked = false;

        public Order() {
            orderItems = new ArrayList<>();
        }

        public void addItem(Item item, int count) throws ItemOutOfStockException, OrderLockedException {
            if (!isLocked) {
                if (count > 10) {
                    throw new ItemOutOfStockException(item);
                } else {

                    int tempIndex = -1;
                    boolean replaceItem = false;

                    for (Item i : orderItems) {
                        if (i.name.equals(item.name)) {
                            tempIndex = orderItems.indexOf(i);
                            orderItems.remove(i);
                            replaceItem = true;
                            break;
                        }

                    }
                    if (replaceItem) {
                        orderItems.add(tempIndex, item);
                        item.Count = count;
                    } else {
                        orderItems.add(item);
                        item.Count = count;
                    }

                }
            } else {
                throw new OrderLockedException();
            }
        }

        public int getPrice() {
            int totalPrice = 0;
            for (Item i : orderItems) {
                totalPrice += i.getPrice() * i.Count;
            }
            return totalPrice;
        }

        public void displayOrder() {
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < orderItems.size(); i++) {
                Item it = orderItems.get(i);
                System.out.println(String.format("%d.%-15sx%d   %d$", i + 1, it.name, it.Count, it.Price * it.Count));

            }
            System.out.println(String.format("Total %16d$", this.getPrice()));
        }

        public void removeItem(int idx) throws ArrayIndexOutOfBоundsException, OrderLockedException {
            if (!isLocked) {
                Item it = orderItems.get(idx);
                boolean remove = orderItems.contains(it);
                if (remove) {
                    orderItems.remove(it);
                } else {
                    throw new ArrayIndexOutOfBоundsException(idx);
                }
            } else {
                throw new OrderLockedException();
            }
        }

        public void lock() throws EmptyOrder, NoPizzaException {
            if (orderItems.size() > 0) {
                if (hasPizza()) {
                    isLocked = true;
                } else {
                    throw new NoPizzaException();
                }
            } else {
                throw new EmptyOrder();
            }
        }

        private boolean hasPizza() {
            for (Item i : orderItems) {
                if (i.type().equals("Pizza")) {
                    return true;
                }
            }
            return false;
        }
    }

    private static class EmptyOrder extends Exception {

        public EmptyOrder() {
        }
    }

}
