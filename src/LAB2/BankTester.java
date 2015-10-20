package LAB2;


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class BankTester {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        String test_type = jin.nextLine();
        switch (test_type) {
            case "typical_usage":
                testTypicalUsage(jin);
                break;
            case "equals":
                testEquals();
                break;
        }
        jin.close();
    }

    private static void testEquals() {
        Account a1 = new Account("Andrej", "20.00$");
        Account a2 = new Account("Andrej", "20.00$");
        Account a3 = new Account("Andrej", "30.00$");
        Account a4 = new Account("Gajduk", "20.00$");
        if (!(a1.equals(a1) && !a1.equals(a2) && !a2.equals(a1) && !a3.equals(a1)
                && !a4.equals(a1)
                && !a1.equals(null))) {
            System.out.println("Your account equals method does not work properly.");
            return;
        }
        if (a1.getID() == a2.getID() || a1.getID() == a3.getID() || a1.getID() == a4.getID()
                || a2.getID() == a3.getID() || a2.getID() == a4.getID() || a3.getID() == a4.getID()) {
            System.out.println("Different accounts have the same IDS. This is not allowed");
            return;
        }
        FlatAmountProvisionTransaction fa1 = new FlatAmountProvisionTransaction(10, 20, "20.00$", "10.00$");
        FlatAmountProvisionTransaction fa2 = new FlatAmountProvisionTransaction(10, 20, "20.00$", "10.00$");
        FlatAmountProvisionTransaction fa3 = new FlatAmountProvisionTransaction(20, 10, "20.00$", "10.00$");
        FlatAmountProvisionTransaction fa4 = new FlatAmountProvisionTransaction(10, 20, "50.00$", "50.00$");
        FlatAmountProvisionTransaction fa5 = new FlatAmountProvisionTransaction(30, 40, "20.00$", "10.00$");
        FlatPercentProvisionTransaction fp1 = new FlatPercentProvisionTransaction(10, 20, "20.00$", 10);
        FlatPercentProvisionTransaction fp2 = new FlatPercentProvisionTransaction(10, 20, "20.00$", 10);
        FlatPercentProvisionTransaction fp3 = new FlatPercentProvisionTransaction(20, 10, "20.00$", 10);
        FlatPercentProvisionTransaction fp4 = new FlatPercentProvisionTransaction(10, 20, "50.00$", 10);
        FlatPercentProvisionTransaction fp5 = new FlatPercentProvisionTransaction(10, 20, "20.00$", 30);
        FlatPercentProvisionTransaction fp6 = new FlatPercentProvisionTransaction(30, 40, "20.00$", 10);
        if (fa1.equals(fa1)
                && !fa2.equals(null)
                && fa2.equals(fa1)
                && fa1.equals(fa2)
                && fa1.equals(fa3)
                && !fa1.equals(fa4)
                && !fa1.equals(fa5)
                && !fa1.equals(fp1)
                && fp1.equals(fp1)
                && !fp2.equals(null)
                && fp2.equals(fp1)
                && fp1.equals(fp2)
                && fp1.equals(fp3)
                && !fp1.equals(fp4)
                && !fp1.equals(fp5)
                && !fp1.equals(fp6)) {
            System.out.println("Your transactions equals methods do not work properly.");
            return;
        }
        Account accounts[] = new Account[]{a1, a2, a3, a4};
        Account accounts1[] = new Account[]{a2, a1, a3, a4};
        Account accounts2[] = new Account[]{a1, a2, a3};
        Account accounts3[] = new Account[]{a1, a2, a3, a4};

        Bank b1 = new Bank("Test", accounts);
        Bank b2 = new Bank("Test", accounts1);
        Bank b3 = new Bank("Test", accounts2);
        Bank b4 = new Bank("Sample", accounts);
        Bank b5 = new Bank("Test", accounts3);
        if (!(b1.equals(b1)
                && !b1.equals(null)
                && !b1.equals(b2)
                && !b2.equals(b1)
                && !b1.equals(b3)
                && !b3.equals(b1)
                && !b1.equals(b4)
                && b1.equals(b5))) {
            System.out.println("Your bank equals method do not work properly.");
            return;
        }
        accounts[2] = a1;
        if (!b1.equals(b5)) {
            System.out.println("Your bank equals method do not work properly.");
            return;
        }
        long from_id = a2.getID();
        long to_id = a3.getID();
        Transaction t = new FlatAmountProvisionTransaction(from_id, to_id, "3.00$", "3.00$");
        b1.makeTransaction(t);
        if (b1.equals(b5)) {
            System.out.println("Your bank equals method do not work properly.");
            return;
        }
        b5.makeTransaction(t);
        if (!b1.equals(b5)) {
            System.out.println("Your bank equals method do not work properly.");
            return;
        }
        System.out.println("All your equals methods work properly.");
    }

    private static void testTypicalUsage(Scanner jin) {
        String bank_name = jin.nextLine();
        int num_accounts = jin.nextInt();
        jin.nextLine();
        Account accounts[] = new Account[num_accounts];
        for (int i = 0; i < num_accounts; ++i) {
            accounts[i] = new Account(jin.nextLine(), jin.nextLine());
        }
        Bank bank = new Bank(bank_name, accounts);
        while (true) {
            String line = jin.nextLine();
            switch (line) {
                case "stop":
                    return;
                case "transaction":
                    String descrption = jin.nextLine();
                    String amount = jin.nextLine();
                    String parameter = jin.nextLine();
                    int from_idx = jin.nextInt();
                    int to_idx = jin.nextInt();
                    jin.nextLine();
                    Transaction t = getTransaction(descrption, from_idx, to_idx, amount, parameter, bank);
                    System.out.println("Transaction amount:" + t.getAmount());
                    System.out.println("transaction description:" + t.getDescription());
                    System.out.println("Transaction succesfull? " + bank.makeTransaction(t));
                    break;
                case "print":
                    System.out.println(bank.toString());
                    System.out.println("Total provisions:" + bank.totalProvision());
                    System.out.println("Total transfers:" + bank.totalTransfers());
                    System.out.println();
                    break;
            }
        }
    }

    private static Transaction getTransaction(String description, int from_idx, int to_idx, String amount, String o, Bank bank) {
        switch (description) {
            case "FlatAmount":
                return new FlatAmountProvisionTransaction(bank.accounts[from_idx].getID(), bank.accounts[to_idx].getID(), amount, o);
            case "FlatPercent":
                return new FlatPercentProvisionTransaction(bank.accounts[from_idx].getID(), bank.accounts[to_idx].getID(), amount, Integer.parseInt(o));
        }
        return null;
    }

    private static abstract class Transaction {

        long fromID;
        long toID;
        String description;
        String amount;

        private Transaction(long fromID, long toID, String description, String amount) {
            this.fromID = fromID;
            this.toID = toID;
            this.description = description;
            this.amount = amount;
        }

        public long getFromAccountID() {
            return fromID;
        }

        public long getToAccountID() {
            return toID;
        }

        public String getAmount() {
            return amount;
        }

        public String getDescription() {
            return description;
        }

        public abstract String getProvision();

        public abstract String getProvisionWODollarSign();

        @Override
        public String toString() {
            return String.format("Amount:%s \nProvision:%s \nDescription:%s \nFrom:%d \nTo:%d\n",
                    amount, getProvision(), description, fromID, toID);

        }

    }
    static long counterID = 0;

    private static class Account {

        String UserName;
        String Balance;
        long ID;

        public Account(String UserName, String Balance) {
            this.UserName = UserName;
            this.Balance = Balance;
            ID = counterID++;
        }

        public String getUserName() {
            return UserName;
        }

        public String getBalance() {

            return Balance;
        }

        public Long getID() {
            return ID;
        }

        public void setBalance(String Balance) {
            this.Balance = Balance;
        }

        @Override
        public String toString() {
            return "\nName:" + UserName + "\nBalance:" + Balance;
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 13 * hash + Objects.hashCode(this.UserName);
            hash = 13 * hash + Objects.hashCode(this.Balance);
            hash = 13 * hash + (int) (this.ID ^ (this.ID >>> 32));
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            Account comp = (Account) obj;
            if (ID != comp.getID()) {
                return false;
            }
            if (!Balance.equals(comp.Balance)) {
                return false;
            }
            return true;
        }

    }

    private static class FlatAmountProvisionTransaction extends Transaction {

        String flatProvision;

        public FlatAmountProvisionTransaction(long fromID, long toID, String amount, String flat_amount_provision) {
            super(fromID, toID, "FlatAmount", amount);
            flatProvision = flat_amount_provision;
        }

        public String getFlatAmount() {
            return flatProvision;
        }

        @Override
        public String getProvision() {
            return flatProvision;
        }

        @Override
        public String getProvisionWODollarSign() {
            String flatProvisionWODollarSign = flatProvision.replace("$", "");
            return flatProvisionWODollarSign;
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 67 * hash + Objects.hashCode(this.flatProvision);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            FlatAmountProvisionTransaction tmp = (FlatAmountProvisionTransaction) obj;
            if (this.getFromAccountID() != tmp.getFromAccountID()) {
                return false;
            }
            if (this.getToAccountID() != tmp.getToAccountID()) {
                return false;
            }
            if (!this.getAmount().equals(tmp.getAmount())) {
                return false;
            }
            if (!this.getFlatAmount().equals(getFlatAmount())) {
                return false;
            }
            return true;
        }

    }

    private static class FlatPercentProvisionTransaction extends Transaction {

        int percentProvision;

        public FlatPercentProvisionTransaction(long fromID, long toID, String amount, int cents_per_dolar) {
            super(fromID, toID, "FlatPercent", amount);
            percentProvision = cents_per_dolar;
        }

        public int getPercent() {
            return percentProvision;
        }

        @Override
        public String getProvision() {

            String temp = this.getAmount();
            temp = temp.replace("$", "");

            Double amountDouble = Double.parseDouble(temp);
            Double provisionDouble = (amountDouble.longValue() / 100.0) * getPercent();
            String s = provisionDouble.toString();
            return s;
        }

        @Override
        public String getProvisionWODollarSign() {
            String totalProvisionWODollar = this.getProvision().replace("$", "");
            return totalProvisionWODollar;

        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 29 * hash + this.percentProvision;
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            FlatPercentProvisionTransaction tmp = (FlatPercentProvisionTransaction) obj;
            if (this.getFromAccountID() != tmp.getFromAccountID()) {
                return false;
            }
            if (this.getToAccountID() != tmp.getToAccountID()) {
                return false;
            }
            if (!this.getAmount().equals(tmp.getAmount())) {
                return false;
            }
            if (this.getPercent() != tmp.getPercent()) {
                return false;
            }
            return true;
        }

    }

    private static class Bank {

        String name;
        Account accounts[];
        String totalTransfers = "0.00$";
        String totalProvision = "0.00$";

        Account fromAccountTemp;
        Account toAccountTemp;

        public Bank(String name, Account[] accounts) {
            this.name = name;
            this.accounts = accounts;
        }

        public boolean checkAccounts(Transaction t) {

            boolean testFromID = false;
            boolean testToId = false;
            for (Account account : accounts) {
                if (t.fromID == account.ID) {
                    testFromID = true;
                    fromAccountTemp = account;

                }
                if (t.toID == account.ID) {
                    testToId = true;
                    toAccountTemp = account;

                }
            }

            return testToId && testFromID;
        }

        public boolean makeTransaction(Transaction t) {

            if (this.checkAccounts(t)) {
                if (this.checkBalanceFromID(t)) {
                    updateBalance(t);
                    return true;
                }
            }

            return false;

        }

        private boolean checkBalanceFromID(Transaction t) {

            String fromBalance = fromAccountTemp.getBalance();
            fromBalance = fromBalance.replace("$", "");
            BigDecimal fromBalanceBDeci = new BigDecimal(fromBalance);

            String pro = t.getProvisionWODollarSign();
            BigDecimal provisionBDeci = new BigDecimal(pro);

            String amountString = t.amount;
            amountString = amountString.replace("$", "");
            BigDecimal amountBDeci = new BigDecimal(amountString);

            BigDecimal total = amountBDeci.add(provisionBDeci);

            if (fromBalanceBDeci.compareTo(total) >= 0) {
                return true;
            }
            return false;
        }

        private void updateBalance(Transaction t) {
            String fromBalanceString = fromAccountTemp.Balance.replace("$", "");

            String toBalanceString = toAccountTemp.Balance.replace("$", "");
            String totalAmountWODollarSign = t.amount.replace("$", "");

            BigDecimal fromBalanceBInt = new BigDecimal(fromBalanceString);
            BigDecimal toBalanceBInt = new BigDecimal(toBalanceString);

            String totalTransfersT = totalTransfers.replace("$", "");
            String totalProvisionT = totalProvision.replace("$", "");

            BigDecimal totalTransfersBInt = new BigDecimal(totalTransfersT);
            BigDecimal totalProvisionBInt = new BigDecimal(totalProvisionT);

            BigDecimal totalAmountToTransfer = new BigDecimal(totalAmountWODollarSign);
            BigDecimal Provision = new BigDecimal(t.getProvisionWODollarSign());

            fromBalanceBInt = fromBalanceBInt.subtract(Provision);
            fromBalanceBInt = fromBalanceBInt.subtract(totalAmountToTransfer);

            toBalanceBInt = toBalanceBInt.add(totalAmountToTransfer);

            totalTransfersBInt = totalTransfersBInt.add(totalAmountToTransfer);
            totalProvisionBInt = totalProvisionBInt.add(Provision);

            DecimalFormat df = new DecimalFormat("0.00");
            Double TotalTransfersDouble = totalTransfersBInt.doubleValue();
            Double TotalProvisionDouble = totalProvisionBInt.doubleValue();

            totalTransfers = df.format(TotalTransfersDouble) + "$";
            totalProvision = df.format(TotalProvisionDouble) + "$";

            Double fromBalanceDouble = fromBalanceBInt.doubleValue();
            Double toBalanceDouble = toBalanceBInt.doubleValue();

            String setFrom = df.format(fromBalanceDouble) + "$";
            String setTo = df.format(toBalanceDouble) + "$";

            fromAccountTemp.setBalance(setFrom);
            toAccountTemp.setBalance(setTo);

        }

        public String totalTransfers() {
            return totalTransfers;
        }

        public String totalProvision() {
            return totalProvision;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder(String.format("%s\n\n", name));
            for (int i = 0; i < accounts.length; i++) {
                sb.append("Name:" + accounts[i].getUserName());
                sb.append("\nBalance:" + accounts[i].getBalance() + "\n\n");
            }

            return sb.toString();
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 97 * hash + Objects.hashCode(this.name);
            hash = 97 * hash + Arrays.deepHashCode(this.accounts);
            hash = 97 * hash + Objects.hashCode(this.totalTransfers);
            hash = 97 * hash + Objects.hashCode(this.totalProvision);
            hash = 97 * hash + Objects.hashCode(this.fromAccountTemp);
            hash = 97 * hash + Objects.hashCode(this.toAccountTemp);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            Bank tmp = (Bank) obj;
            if (!this.name.equals(tmp.name)) {
                return false;
            }
            if (this.accounts.length != tmp.accounts.length) {
                return false;
            }
            for (int i = 0; i < accounts.length; i++) {
                if (!this.accounts[i].equals(tmp.accounts[i])) {
                    return false;
                }
            }
            return true;
        }

    }

}
