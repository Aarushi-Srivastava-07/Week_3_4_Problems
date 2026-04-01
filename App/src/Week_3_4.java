import java.util.*;

class Trade {
    private String id;
    private int volume;

    public Trade(String id, int volume) {
        this.id = id;
        this.volume = volume;
    }

    public String getId() { return id; }
    public int getVolume() { return volume; }

    @Override
    public String toString() {
        return id + ":" + volume;
    }
}

class TradeVolumeAnalyzer {

    public static Trade[] mergeSort(Trade[] trades) {
        if (trades.length <= 1) {
            return trades;
        }

        int mid = trades.length / 2;
        Trade[] left = Arrays.copyOfRange(trades, 0, mid);
        Trade[] right = Arrays.copyOfRange(trades, mid, trades.length);

        left = mergeSort(left);
        right = mergeSort(right);

        return merge(left, right);
    }

    private static Trade[] merge(Trade[] left, Trade[] right) {
        Trade[] result = new Trade[left.length + right.length];
        int i = 0, j = 0, k = 0;

        while (i < left.length && j < right.length) {
            if (left[i].getVolume() <= right[j].getVolume()) {
                result[k++] = left[i++];
            } else {
                result[k++] = right[j++];
            }
        }

        while (i < left.length) {
            result[k++] = left[i++];
        }

        while (j < right.length) {
            result[k++] = right[j++];
        }

        return result;
    }

    public static void quickSortByVolumeDesc(Trade[] trades, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(trades, low, high);
            quickSortByVolumeDesc(trades, low, pivotIndex - 1);
            quickSortByVolumeDesc(trades, pivotIndex + 1, high);
        }
    }

    private static int partition(Trade[] trades, int low, int high) {
        int pivotIndex = medianOfThree(trades, low, high);
        Trade pivot = trades[pivotIndex];

        swap(trades, pivotIndex, high);

        int i = low;

        for (int j = low; j < high; j++) {
            if (trades[j].getVolume() >= pivot.getVolume()) {
                swap(trades, i, j);
                i++;
            }
        }

        swap(trades, i, high);
        return i;
    }

    private static int medianOfThree(Trade[] trades, int low, int high) {
        int mid = low + (high - low) / 2;

        if (trades[low].getVolume() > trades[mid].getVolume()) {
            swap(trades, low, mid);
        }
        if (trades[low].getVolume() > trades[high].getVolume()) {
            swap(trades, low, high);
        }
        if (trades[mid].getVolume() > trades[high].getVolume()) {
            swap(trades, mid, high);
        }

        return mid;
    }

    private static void swap(Trade[] trades, int i, int j) {
        Trade temp = trades[i];
        trades[i] = trades[j];
        trades[j] = temp;
    }

    public static Trade[] mergeTwoSortedLists(Trade[] list1, Trade[] list2) {
        return merge(list1, list2);
    }

    public static int computeTotalVolume(Trade[] trades) {
        int total = 0;
        for (Trade t : trades) {
            total += t.getVolume();
        }
        return total;
    }

    public static void displayTrades(Trade[] trades, String title) {
        System.out.println(title + ":");
        for (Trade t : trades) {
            System.out.print(t + " ");
        }
        System.out.println();
    }
}

public class Week_3_4 {

    public static void main(String[] args) {
        Trade[] trades = {
                new Trade("trade3", 500),
                new Trade("trade1", 100),
                new Trade("trade2", 300)
        };

        System.out.println(" Original Trades ");
        TradeVolumeAnalyzer.displayTrades(trades, "Original");

        Trade[] mergeSorted = TradeVolumeAnalyzer.mergeSort(trades.clone());
        System.out.println("\n Merge Sort by Volume Ascending (Stable) ");
        TradeVolumeAnalyzer.displayTrades(mergeSorted, "MergeSort Result");

        Trade[] quickSorted = trades.clone();
        TradeVolumeAnalyzer.quickSortByVolumeDesc(quickSorted, 0, quickSorted.length - 1);
        System.out.println("\n= Quick Sort by Volume Descending ");
        TradeVolumeAnalyzer.displayTrades(quickSorted, "QuickSort Result");

        Trade[] morningSession = {
                new Trade("M1", 150),
                new Trade("M2", 250),
                new Trade("M3", 350)
        };

        Trade[] afternoonSession = {
                new Trade("A1", 100),
                new Trade("A2", 200),
                new Trade("A3", 400)
        };

        System.out.println("\n Merging Morning and Afternoon Sessions ");
        TradeVolumeAnalyzer.displayTrades(morningSession, "Morning Session");
        TradeVolumeAnalyzer.displayTrades(afternoonSession, "Afternoon Session");

        Trade[] merged = TradeVolumeAnalyzer.mergeTwoSortedLists(morningSession, afternoonSession);
        TradeVolumeAnalyzer.displayTrades(merged, "Merged Sorted Trades");

        int totalVolume = TradeVolumeAnalyzer.computeTotalVolume(merged);
        System.out.println("Total Volume: " + totalVolume);

        System.out.println("\n Large Scale Test (10 Trades) ");
        Trade[] largeTrades = {
                new Trade("T1", 45), new Trade("T2", 23), new Trade("T3", 89),
                new Trade("T4", 12), new Trade("T5", 67), new Trade("T6", 34),
                new Trade("T7", 78), new Trade("T8", 56), new Trade("T9", 91),
                new Trade("T10", 5)
        };

        TradeVolumeAnalyzer.displayTrades(largeTrades, "Original");

        Trade[] largeMergeSorted = TradeVolumeAnalyzer.mergeSort(largeTrades.clone());
        TradeVolumeAnalyzer.displayTrades(largeMergeSorted, "MergeSort Ascending");

        Trade[] largeQuickSorted = largeTrades.clone();
        TradeVolumeAnalyzer.quickSortByVolumeDesc(largeQuickSorted, 0, largeQuickSorted.length - 1);
        TradeVolumeAnalyzer.displayTrades(largeQuickSorted, "QuickSort Descending");

        int total = TradeVolumeAnalyzer.computeTotalVolume(largeQuickSorted);
        System.out.println("Total Volume of All Trades: " + total);

        Trade[] testList1 = {
                new Trade("X1", 10),
                new Trade("X2", 30),
                new Trade("X3", 50)
        };

        Trade[] testList2 = {
                new Trade("Y1", 20),
                new Trade("Y2", 40),
                new Trade("Y3", 60)
        };

        System.out.println("\n Merge Test with Equal Volumes ");
        Trade[] mergedEqual = TradeVolumeAnalyzer.mergeTwoSortedLists(testList1, testList2);
        TradeVolumeAnalyzer.displayTrades(mergedEqual, "Merged Result (Stable)");

        int mergedTotal = TradeVolumeAnalyzer.computeTotalVolume(mergedEqual);
        System.out.println("Merged Total Volume: " + mergedTotal);
    }
}
