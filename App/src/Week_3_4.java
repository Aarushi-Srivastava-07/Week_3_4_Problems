import java.util.*;

public class Week_3_4 {
    public static void main(String[] args) {
        String[] logs = {"accB", "accA", "accB", "accC"};
        String target = "accB";

        linearSearch(logs, target);
        Arrays.sort(logs);
        binarySearch(logs, target);
    }

    static void linearSearch(String[] arr, String target) {
        int comparisons = 0;
        int first = -1, last = -1;
        for (int i = 0; i < arr.length; i++) {
            comparisons++;
            if (arr[i].equals(target)) {
                if (first == -1) first = i;
                last = i;
            }
        }
        System.out.println("Linear Search:");
        System.out.println("First occurrence: " + first);
        System.out.println("Last occurrence: " + last);
        System.out.println("Comparisons: " + comparisons);
        System.out.println("Time Complexity: O(n)");
    }

    static void binarySearch(String[] arr, String target) {
        int comparisons = 0;
        int low = 0, high = arr.length - 1;
        int index = -1;
        while (low <= high) {
            int mid = (low + high) / 2;
            comparisons++;
            if (arr[mid].equals(target)) {
                index = mid;
                break;
            } else if (arr[mid].compareTo(target) < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        int count = 0;
        if (index != -1) {
            int left = index;
            while (left >= 0 && arr[left].equals(target)) {
                count++;
                left--;
            }
            int right = index + 1;
            while (right < arr.length && arr[right].equals(target)) {
                count++;
                right++;
            }
        }
        System.out.println("Binary Search:");
        System.out.println("Exact match index: " + index);
        System.out.println("Count occurrences: " + count);
        System.out.println("Comparisons: " + comparisons);
        System.out.println("Time Complexity: O(log n)");
    }
}
