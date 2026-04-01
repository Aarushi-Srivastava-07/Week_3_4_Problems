import java.util.*;

class Transaction {
    private String id;
    private double fee;
    private String timestamp;

    public Transaction(String id, double fee, String timestamp) {
        this.id = id;
        this.fee = fee;
        this.timestamp = timestamp;
    }

    public String getId() { return id; }
    public double getFee() { return fee; }
    public String getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return id + ":" + fee;
    }

    public String toDetailedString() {
        return id + ":" + fee + "@" + timestamp;
    }
}

class TransactionSorter {

    public static List<Transaction> bubbleSortByFee(List<Transaction> transactions) {
        List<Transaction> sorted = new ArrayList<>(transactions);
        int n = sorted.size();
        int passes = 0;
        int swaps = 0;
        boolean swapped;

        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            passes++;

            for (int j = 0; j < n - i - 1; j++) {
                if (sorted.get(j).getFee() > sorted.get(j + 1).getFee()) {
                    Transaction temp = sorted.get(j);
                    sorted.set(j, sorted.get(j + 1));
                    sorted.set(j + 1, temp);
                    swapped = true;
                    swaps++;
                }
            }

            if (!swapped) {
                break;
            }
        }

        System.out.println("Bubble Sort - Passes: " + passes + ", Swaps: " + swaps);
        return sorted;
    }

    public static List<Transaction> insertionSortByFeeAndTimestamp(List<Transaction> transactions) {
        List<Transaction> sorted = new ArrayList<>(transactions);
        int n = sorted.size();
        int shifts = 0;
        int comparisons = 0;

        for (int i = 1; i < n; i++) {
            Transaction key = sorted.get(i);
            int j = i - 1;

            while (j >= 0 && compareTransactions(sorted.get(j), key) > 0) {
                sorted.set(j + 1, sorted.get(j));
                j--;
                shifts++;
                comparisons++;
            }

            sorted.set(j + 1, key);
            comparisons++;
        }

        System.out.println("Insertion Sort - Comparisons: " + comparisons + ", Shifts: " + shifts);
        return sorted;
    }

    private static int compareTransactions(Transaction t1, Transaction t2) {
        if (t1.getFee() != t2.getFee()) {
            return Double.compare(t1.getFee(), t2.getFee());
        }
        return t1.getTimestamp().compareTo(t2.getTimestamp());
    }

    public static List<Transaction> flagHighFeeOutliers(List<Transaction> transactions, double threshold) {
        List<Transaction> outliers = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t.getFee() > threshold) {
                outliers.add(t);
            }
        }
        return outliers;
    }

    public static void displayTransactions(List<Transaction> transactions, String title) {
        System.out.println(title + ":");
        for (Transaction t : transactions) {
            System.out.print(t + " ");
        }
        System.out.println();
    }

    public static void displayDetailedTransactions(List<Transaction> transactions, String title) {
        System.out.println(title + ":");
        for (Transaction t : transactions) {
            System.out.print(t.toDetailedString() + " ");
        }
        System.out.println();
    }
}

public class Week_3_4 {

    public static void main(String[] args) {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction("id1", 10.5, "10:00"));
        transactions.add(new Transaction("id2", 25.0, "09:30"));
        transactions.add(new Transaction("id3", 5.0, "10:15"));

        System.out.println(" Original Transactions");
        TransactionSorter.displayDetailedTransactions(transactions, "Original");

        System.out.println("\n Bubble Sort by Fee (Ascending) ");
        List<Transaction> bubbleSorted = TransactionSorter.bubbleSortByFee(transactions);
        TransactionSorter.displayTransactions(bubbleSorted, "Bubble Sort Result");

        System.out.println("\n Insertion Sort by Fee + Timestamp ");
        List<Transaction> insertionSorted = TransactionSorter.insertionSortByFeeAndTimestamp(transactions);
        TransactionSorter.displayDetailedTransactions(insertionSorted, "Insertion Sort Result");

        System.out.println("\n High-Fee Outlier Detection ");
        double threshold = 50.0;
        List<Transaction> outliers = TransactionSorter.flagHighFeeOutliers(transactions, threshold);
        if (outliers.isEmpty()) {
            System.out.println("No high-fee outliers found (threshold: $" + threshold + ")");
        } else {
            System.out.println("High-fee outliers (> $" + threshold + "):");
            TransactionSorter.displayDetailedTransactions(outliers, "Outliers");
        }

        System.out.println("\n Testing with Outliers ");
        List<Transaction> testWithOutliers = new ArrayList<>();
        testWithOutliers.add(new Transaction("tx1", 10.5, "10:00"));
        testWithOutliers.add(new Transaction("tx2", 75.0, "11:30"));
        testWithOutliers.add(new Transaction("tx3", 100.0, "09:15"));
        testWithOutliers.add(new Transaction("tx4", 25.5, "14:45"));

        TransactionSorter.displayDetailedTransactions(testWithOutliers, "Test Transactions");
        outliers = TransactionSorter.flagHighFeeOutliers(testWithOutliers, 50.0);
        TransactionSorter.displayDetailedTransactions(outliers, "Detected Outliers");

        System.out.println("\n Testing Stability ");
        List<Transaction> stabilityTest = new ArrayList<>();
        stabilityTest.add(new Transaction("A", 10.0, "09:00"));
        stabilityTest.add(new Transaction("B", 10.0, "08:00"));
        stabilityTest.add(new Transaction("C", 10.0, "10:00"));

        System.out.println("Original (all fees equal):");
        TransactionSorter.displayDetailedTransactions(stabilityTest, "");

        List<Transaction> stableSorted = TransactionSorter.insertionSortByFeeAndTimestamp(stabilityTest);
        System.out.println("Stable sorted (by timestamp for equal fees):");
        TransactionSorter.displayDetailedTransactions(stableSorted, "");
    }
}