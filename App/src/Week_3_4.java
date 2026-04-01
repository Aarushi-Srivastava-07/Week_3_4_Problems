import java.util.*;

class RiskBand {
    private int threshold;
    private String riskLevel;
    private double interestRate;

    public RiskBand(int threshold, String riskLevel, double interestRate) {
        this.threshold = threshold;
        this.riskLevel = riskLevel;
        this.interestRate = interestRate;
    }

    public int getThreshold() { return threshold; }
    public String getRiskLevel() { return riskLevel; }
    public double getInterestRate() { return interestRate; }

    @Override
    public String toString() {
        return String.valueOf(threshold);
    }

    public String toDetailedString() {
        return threshold + "(" + riskLevel + "," + interestRate + "%)";
    }
}

class RiskThresholdAnalyzer {
    private static int linearComparisons = 0;
    private static int binaryComparisons = 0;

    public static boolean linearSearchThreshold(RiskBand[] bands, int target) {
        linearComparisons = 0;
        for (int i = 0; i < bands.length; i++) {
            linearComparisons++;
            if (bands[i].getThreshold() == target) {
                return true;
            }
        }
        return false;
    }

    public static int binarySearchInsertionPoint(RiskBand[] bands, int target) {
        binaryComparisons = 0;
        int low = 0;
        int high = bands.length - 1;
        int insertionPoint = bands.length;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            binaryComparisons++;

            if (bands[mid].getThreshold() >= target) {
                insertionPoint = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return insertionPoint;
    }

    public static int findFloor(RiskBand[] bands, int target) {
        binaryComparisons = 0;
        int low = 0;
        int high = bands.length - 1;
        int floor = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            binaryComparisons++;

            if (bands[mid].getThreshold() <= target) {
                floor = bands[mid].getThreshold();
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return floor;
    }

    public static int findCeiling(RiskBand[] bands, int target) {
        binaryComparisons = 0;
        int low = 0;
        int high = bands.length - 1;
        int ceiling = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            binaryComparisons++;

            if (bands[mid].getThreshold() >= target) {
                ceiling = bands[mid].getThreshold();
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return ceiling;
    }

    public static RiskBand assignRiskBand(RiskBand[] bands, int clientScore) {
        int insertionPoint = binarySearchInsertionPoint(bands, clientScore);

        if (insertionPoint < bands.length && bands[insertionPoint].getThreshold() == clientScore) {
            return bands[insertionPoint];
        } else if (insertionPoint > 0) {
            return bands[insertionPoint - 1];
        } else {
            return null;
        }
    }

    public static void sortRiskBands(RiskBand[] bands) {
        Arrays.sort(bands, (a, b) -> Integer.compare(a.getThreshold(), b.getThreshold()));
    }

    public static int getLinearComparisons() { return linearComparisons; }
    public static int getBinaryComparisons() { return binaryComparisons; }

    public static void displayBands(RiskBand[] bands, String title) {
        System.out.println(title + ":");
        for (RiskBand band : bands) {
            System.out.print(band + " ");
        }
        System.out.println();
    }

    public static void displayDetailedBands(RiskBand[] bands, String title) {
        System.out.println(title + ":");
        for (RiskBand band : bands) {
            System.out.print(band.toDetailedString() + " ");
        }
        System.out.println();
    }
}

public class Week_3_4 {

    public static void main(String[] args) {
        RiskBand[] riskBands = {
                new RiskBand(50, "High", 8.5),
                new RiskBand(10, "Low", 4.0),
                new RiskBand(100, "Very High", 12.0),
                new RiskBand(25, "Medium", 6.5)
        };

        System.out.println(" Original Risk Bands (Unsorted) ");
        RiskThresholdAnalyzer.displayDetailedBands(riskBands, "Original");

        System.out.println("\n Linear Search on Unsorted Bands ");
        int targetThreshold = 30;
        boolean found = RiskThresholdAnalyzer.linearSearchThreshold(riskBands, targetThreshold);
        System.out.println("Threshold " + targetThreshold + " found: " + found);
        System.out.println("Linear comparisons: " + RiskThresholdAnalyzer.getLinearComparisons());

        System.out.println("\n Sorting Risk Bands ");
        RiskThresholdAnalyzer.sortRiskBands(riskBands);
        RiskThresholdAnalyzer.displayBands(riskBands, "Sorted Risk Bands");

        System.out.println("\n Binary Search Operations on Sorted Bands ");
        int[] testValues = {30, 25, 10, 75, 100, 5, 110};

        for (int value : testValues) {
            System.out.println("\n--- Target: " + value + " ---");

            int insertionPoint = RiskThresholdAnalyzer.binarySearchInsertionPoint(riskBands, value);
            System.out.println("Insertion point index: " + insertionPoint);
            System.out.println("Binary comparisons: " + RiskThresholdAnalyzer.getBinaryComparisons());

            int floor = RiskThresholdAnalyzer.findFloor(riskBands, value);
            System.out.println("Floor (largest ≤ " + value + "): " + (floor == -1 ? "none" : floor));
            System.out.println("Binary comparisons: " + RiskThresholdAnalyzer.getBinaryComparisons());

            int ceiling = RiskThresholdAnalyzer.findCeiling(riskBands, value);
            System.out.println("Ceiling (smallest ≥ " + value + "): " + (ceiling == -1 ? "none" : ceiling));
            System.out.println("Binary comparisons: " + RiskThresholdAnalyzer.getBinaryComparisons());

            RiskBand assignedBand = RiskThresholdAnalyzer.assignRiskBand(riskBands, value);
            if (assignedBand != null) {
                System.out.println("Assigned risk band: " + assignedBand.toDetailedString());
            } else {
                System.out.println("No suitable risk band found");
            }
        }

        System.out.println("\n Extended Test with More Risk Bands ");
        RiskBand[] extendedBands = {
                new RiskBand(0, "Minimal", 2.0),
                new RiskBand(200, "Extreme", 15.0),
                new RiskBand(50, "Moderate", 6.0),
                new RiskBand(150, "Severe", 12.5),
                new RiskBand(25, "Low", 4.5),
                new RiskBand(75, "High", 8.0),
                new RiskBand(100, "Very High", 10.0),
                new RiskBand(125, "Critical", 11.0)
        };

        RiskThresholdAnalyzer.displayDetailedBands(extendedBands, "Extended Risk Bands (Unsorted)");
        RiskThresholdAnalyzer.sortRiskBands(extendedBands);
        RiskThresholdAnalyzer.displayBands(extendedBands, "Sorted Extended Bands");

        System.out.println("\n Client Risk Score Assignment ");
        int[] clientScores = {12, 38, 62, 88, 135, 175, 220};

        for (int score : clientScores) {
            System.out.println("\nClient Score: " + score);

            int floor = RiskThresholdAnalyzer.findFloor(extendedBands, score);
            int ceiling = RiskThresholdAnalyzer.findCeiling(extendedBands, score);

            System.out.println("Floor: " + (floor == -1 ? "none" : floor));
            System.out.println("Ceiling: " + (ceiling == -1 ? "none" : ceiling));

            RiskBand assigned = RiskThresholdAnalyzer.assignRiskBand(extendedBands, score);
            if (assigned != null) {
                System.out.println("Assigned: " + assigned.toDetailedString());
            } else {
                System.out.println("No band assigned (below minimum threshold)");
            }

            System.out.println("Binary comparisons: " + RiskThresholdAnalyzer.getBinaryComparisons());
        }

        System.out.println("\n Performance Comparison ");
        System.out.println("For " + extendedBands.length + " risk bands:");
        System.out.println("Linear Search worst-case comparisons: " + extendedBands.length);
        System.out.println("Binary Search maximum comparisons: " +
                (int)(Math.log(extendedBands.length) / Math.log(2) + 1));

        System.out.println("\n Edge Cases ");
        RiskBand[] singleBand = {new RiskBand(50, "Medium", 6.0)};

        System.out.println("Single band [50]:");
        System.out.println("Floor for 30: " + RiskThresholdAnalyzer.findFloor(singleBand, 30));
        System.out.println("Ceiling for 30: " + RiskThresholdAnalyzer.findCeiling(singleBand, 30));
        System.out.println("Floor for 50: " + RiskThresholdAnalyzer.findFloor(singleBand, 50));
        System.out.println("Ceiling for 50: " + RiskThresholdAnalyzer.findCeiling(singleBand, 50));
        System.out.println("Floor for 70: " + RiskThresholdAnalyzer.findFloor(singleBand, 70));
        System.out.println("Ceiling for 70: " + RiskThresholdAnalyzer.findCeiling(singleBand, 70));

        System.out.println("\n Duplicate Threshold Handling ");
        RiskBand[] duplicateBands = {
                new RiskBand(25, "Low", 4.0),
                new RiskBand(50, "Medium", 6.0),
                new RiskBand(50, "Medium-High", 7.0),
                new RiskBand(75, "High", 8.0),
                new RiskBand(100, "Very High", 10.0)
        };

        RiskThresholdAnalyzer.sortRiskBands(duplicateBands);
        RiskThresholdAnalyzer.displayBands(duplicateBands, "Bands with Duplicates");

        int duplicateTarget = 50;
        int insertionIdx = RiskThresholdAnalyzer.binarySearchInsertionPoint(duplicateBands, duplicateTarget);
        System.out.println("Insertion point for " + duplicateTarget + ": index " + insertionIdx);

        RiskBand assigned = RiskThresholdAnalyzer.assignRiskBand(duplicateBands, duplicateTarget);
        System.out.println("Assigned band: " + assigned.toDetailedString());
    }
}