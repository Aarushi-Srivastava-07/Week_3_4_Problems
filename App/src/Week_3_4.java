import java.util.*;

class Asset {
    private String symbol;
    private double returnRate;
    private double volatility;

    public Asset(String symbol, double returnRate, double volatility) {
        this.symbol = symbol;
        this.returnRate = returnRate;
        this.volatility = volatility;
    }

    public String getSymbol() { return symbol; }
    public double getReturnRate() { return returnRate; }
    public double getVolatility() { return volatility; }

    @Override
    public String toString() {
        return symbol + ":" + String.format("%.0f", returnRate) + "%";
    }

    public String toDetailedString() {
        return symbol + ":" + String.format("%.0f", returnRate) + "% (vol:" + volatility + ")";
    }
}

class PortfolioSorter {
    private static final int INSERTION_THRESHOLD = 10;
    private static Random random = new Random();

    public static Asset[] mergeSortByReturnRate(Asset[] assets) {
        if (assets.length <= 1) {
            return assets;
        }

        int mid = assets.length / 2;
        Asset[] left = Arrays.copyOfRange(assets, 0, mid);
        Asset[] right = Arrays.copyOfRange(assets, mid, assets.length);

        left = mergeSortByReturnRate(left);
        right = mergeSortByReturnRate(right);

        return merge(left, right);
    }

    private static Asset[] merge(Asset[] left, Asset[] right) {
        Asset[] result = new Asset[left.length + right.length];
        int i = 0, j = 0, k = 0;

        while (i < left.length && j < right.length) {
            if (left[i].getReturnRate() <= right[j].getReturnRate()) {
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

    public static void quickSortByReturnDescAndVolatilityAsc(Asset[] assets, int low, int high) {
        if (high - low + 1 <= INSERTION_THRESHOLD) {
            insertionSort(assets, low, high);
            return;
        }

        if (low < high) {
            int pivotIndex = partition(assets, low, high);
            quickSortByReturnDescAndVolatilityAsc(assets, low, pivotIndex - 1);
            quickSortByReturnDescAndVolatilityAsc(assets, pivotIndex + 1, high);
        }
    }

    private static int partition(Asset[] assets, int low, int high) {
        int pivotIndex = medianOfThree(assets, low, high);
        Asset pivot = assets[pivotIndex];

        swap(assets, pivotIndex, high);

        int i = low;

        for (int j = low; j < high; j++) {
            if (compareAssets(assets[j], pivot) < 0) {
                swap(assets, i, j);
                i++;
            }
        }

        swap(assets, i, high);
        return i;
    }

    private static int medianOfThree(Asset[] assets, int low, int high) {
        int mid = low + (high - low) / 2;

        if (compareAssets(assets[low], assets[mid]) > 0) {
            swap(assets, low, mid);
        }
        if (compareAssets(assets[low], assets[high]) > 0) {
            swap(assets, low, high);
        }
        if (compareAssets(assets[mid], assets[high]) > 0) {
            swap(assets, mid, high);
        }

        return mid;
    }

    private static int compareAssets(Asset a1, Asset a2) {
        if (a1.getReturnRate() != a2.getReturnRate()) {
            return Double.compare(a2.getReturnRate(), a1.getReturnRate());
        }
        return Double.compare(a1.getVolatility(), a2.getVolatility());
    }

    private static void insertionSort(Asset[] assets, int low, int high) {
        for (int i = low + 1; i <= high; i++) {
            Asset key = assets[i];
            int j = i - 1;

            while (j >= low && compareAssets(assets[j], key) > 0) {
                assets[j + 1] = assets[j];
                j--;
            }

            assets[j + 1] = key;
        }
    }

    private static void swap(Asset[] assets, int i, int j) {
        Asset temp = assets[i];
        assets[i] = assets[j];
        assets[j] = temp;
    }

    public static void displayAssets(Asset[] assets, String title) {
        System.out.println(title + ":");
        for (Asset a : assets) {
            System.out.print(a + " ");
        }
        System.out.println();
    }

    public static void displayDetailedAssets(Asset[] assets, String title) {
        System.out.println(title + ":");
        for (Asset a : assets) {
            System.out.print(a.toDetailedString() + " ");
        }
        System.out.println();
    }
}

public class Week_3_4 {

    public static void main(String[] args) {
        Asset[] assets = {
                new Asset("AAPL", 12, 0.25),
                new Asset("TSLA", 8, 0.45),
                new Asset("GOOG", 15, 0.18)
        };

        System.out.println(" Original Assets ");
        PortfolioSorter.displayAssets(assets, "Original");

        Asset[] mergeSorted = PortfolioSorter.mergeSortByReturnRate(assets.clone());
        System.out.println("\n Merge Sort by Return Rate Ascending (Stable) ");
        PortfolioSorter.displayAssets(mergeSorted, "MergeSort Result");

        Asset[] quickSorted = assets.clone();
        PortfolioSorter.quickSortByReturnDescAndVolatilityAsc(quickSorted, 0, quickSorted.length - 1);
        System.out.println("\n Quick Sort by Return Rate DESC + Volatility ASC ");
        PortfolioSorter.displayDetailedAssets(quickSorted, "QuickSort Result");

        System.out.println("\n Extended Test with 10 Assets ");
        Asset[] extendedAssets = {
                new Asset("AAPL", 12, 0.25),
                new Asset("MSFT", 14, 0.20),
                new Asset("TSLA", 8, 0.45),
                new Asset("GOOG", 15, 0.18),
                new Asset("AMZN", 13, 0.30),
                new Asset("META", 11, 0.22),
                new Asset("NFLX", 9, 0.35),
                new Asset("NVDA", 16, 0.28),
                new Asset("JPM", 7, 0.15),
                new Asset("V", 10, 0.12)
        };

        PortfolioSorter.displayDetailedAssets(extendedAssets, "Original Assets");

        Asset[] extendedMergeSorted = PortfolioSorter.mergeSortByReturnRate(extendedAssets.clone());
        System.out.println("\nMerge Sort Ascending by Return Rate:");
        PortfolioSorter.displayAssets(extendedMergeSorted, "Result");

        Asset[] extendedQuickSorted = extendedAssets.clone();
        PortfolioSorter.quickSortByReturnDescAndVolatilityAsc(extendedQuickSorted, 0, extendedQuickSorted.length - 1);
        System.out.println("\nQuick Sort DESC by Return + ASC by Volatility:");
        PortfolioSorter.displayDetailedAssets(extendedQuickSorted, "Result");

        System.out.println("\n Stability Test with Equal Return Rates ");
        Asset[] stabilityTest = {
                new Asset("StockA", 10, 0.30),
                new Asset("StockB", 10, 0.25),
                new Asset("StockC", 10, 0.35),
                new Asset("StockD", 10, 0.20)
        };

        System.out.println("Before Merge Sort (all 10% return):");
        for (Asset a : stabilityTest) {
            System.out.print(a.getSymbol() + " ");
        }
        System.out.println();

        Asset[] stableSorted = PortfolioSorter.mergeSortByReturnRate(stabilityTest);
        System.out.println("After Merge Sort (stable - original order preserved):");
        for (Asset a : stableSorted) {
            System.out.print(a.getSymbol() + " ");
        }
        System.out.println();

        System.out.println("\n Hybrid Quick Sort with Insertion for Small Partitions ");
        Asset[] hybridTest = {
                new Asset("X", 25, 0.22),
                new Asset("Y", 18, 0.30),
                new Asset("Z", 30, 0.18),
                new Asset("W", 22, 0.25),
                new Asset("V", 28, 0.20),
                new Asset("U", 15, 0.35)
        };

        PortfolioSorter.displayDetailedAssets(hybridTest, "Before Hybrid Quick Sort");
        PortfolioSorter.quickSortByReturnDescAndVolatilityAsc(hybridTest, 0, hybridTest.length - 1);
        PortfolioSorter.displayDetailedAssets(hybridTest, "After Hybrid Quick Sort");

        System.out.println("\n Pivot Selection Strategy (Median-of-3) ");
        Asset[] pivotTest = {
                new Asset("P1", 5, 0.10),
                new Asset("P2", 50, 0.50),
                new Asset("P3", 25, 0.30)
        };

        System.out.println("Original:");
        for (Asset a : pivotTest) {
            System.out.print(a + " ");
        }
        System.out.println();

        PortfolioSorter.quickSortByReturnDescAndVolatilityAsc(pivotTest, 0, pivotTest.length - 1);
        System.out.println("Sorted with Median-of-3 pivot:");
        for (Asset a : pivotTest) {
            System.out.print(a + " ");
        }
        System.out.println();
    }
}
