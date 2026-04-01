import java.util.*;

class Client {
    private String name;
    private int riskScore;
    private double accountBalance;

    public Client(String name, int riskScore, double accountBalance) {
        this.name = name;
        this.riskScore = riskScore;
        this.accountBalance = accountBalance;
    }

    public String getName() { return name; }
    public int getRiskScore() { return riskScore; }
    public double getAccountBalance() { return accountBalance; }

    @Override
    public String toString() {
        return name + ":" + riskScore;
    }

    public String toDetailedString() {
        return name + "(" + riskScore + ", $" + accountBalance + ")";
    }
}

class ClientRiskManager {

    public static void bubbleSortByRiskAscending(Client[] clients) {
        int n = clients.length;
        int swaps = 0;
        boolean swapped;

        System.out.println("Bubble Sort Visualization:");

        for (int i = 0; i < n - 1; i++) {
            swapped = false;

            for (int j = 0; j < n - i - 1; j++) {
                if (clients[j].getRiskScore() > clients[j + 1].getRiskScore()) {
                    Client temp = clients[j];
                    clients[j] = clients[j + 1];
                    clients[j + 1] = temp;
                    swapped = true;
                    swaps++;

                    System.out.print("Swap " + swaps + ": ");
                    for (int k = 0; k < n; k++) {
                        System.out.print(clients[k] + " ");
                    }
                    System.out.println();
                }
            }

            if (!swapped) {
                break;
            }
        }

        System.out.println("Total Swaps: " + swaps);
    }

    public static void insertionSortByRiskDescAndBalance(Client[] clients) {
        int n = clients.length;
        int shifts = 0;

        for (int i = 1; i < n; i++) {
            Client key = clients[i];
            int j = i - 1;

            while (j >= 0 && compareForDescending(clients[j], key) > 0) {
                clients[j + 1] = clients[j];
                j--;
                shifts++;
            }

            clients[j + 1] = key;
        }

        System.out.println("Insertion Sort - Shifts: " + shifts);
    }

    private static int compareForDescending(Client c1, Client c2) {
        if (c1.getRiskScore() != c2.getRiskScore()) {
            return Integer.compare(c2.getRiskScore(), c1.getRiskScore());
        }
        return Double.compare(c2.getAccountBalance(), c1.getAccountBalance());
    }

    public static Client[] getTopRiskClients(Client[] clients, int topCount) {
        int n = Math.min(topCount, clients.length);
        Client[] topClients = new Client[n];
        for (int i = 0; i < n; i++) {
            topClients[i] = clients[i];
        }
        return topClients;
    }

    public static void displayClients(Client[] clients, String title) {
        System.out.println(title + ":");
        for (Client c : clients) {
            System.out.print(c + " ");
        }
        System.out.println();
    }

    public static void displayDetailedClients(Client[] clients, String title) {
        System.out.println(title + ":");
        for (Client c : clients) {
            System.out.print(c.toDetailedString() + " ");
        }
        System.out.println();
    }
}

public class Week_3_4 {

    public static void main(String[] args) {
        Client[] clients = {
                new Client("clientC", 80, 5000.0),
                new Client("clientA", 20, 10000.0),
                new Client("clientB", 50, 7500.0)
        };

        System.out.println(" Original Clients ");
        ClientRiskManager.displayClients(clients, "Original");

        Client[] bubbleClients = clients.clone();
        System.out.println("\n Bubble Sort by Risk Score Ascending ");
        ClientRiskManager.bubbleSortByRiskAscending(bubbleClients);
        ClientRiskManager.displayClients(bubbleClients, "Sorted (Ascending)");

        Client[] insertionClients = clients.clone();
        System.out.println("\n Insertion Sort by Risk Score DESC + Account Balance ");
        ClientRiskManager.insertionSortByRiskDescAndBalance(insertionClients);
        ClientRiskManager.displayDetailedClients(insertionClients, "Sorted (Descending by Risk, then Balance)");

        System.out.println("\n Top 3 Highest Risk Clients ");
        Client[] topClients = ClientRiskManager.getTopRiskClients(insertionClients, 3);
        System.out.print("Top 3 risks: ");
        for (int i = 0; i < topClients.length; i++) {
            System.out.print(topClients[i].getName() + "(" + topClients[i].getRiskScore() + ")");
            if (i < topClients.length - 1) System.out.print(", ");
        }
        System.out.println();

        System.out.println("\n Extended Test with 5 Clients ");
        Client[] moreClients = {
                new Client("Alice", 95, 15000.0),
                new Client("Bob", 45, 22000.0),
                new Client("Charlie", 95, 10000.0),
                new Client("Diana", 30, 18000.0),
                new Client("Eve", 75, 12000.0)
        };

        ClientRiskManager.displayDetailedClients(moreClients, "Original Clients");

        Client[] bubbleMore = moreClients.clone();
        System.out.println("\nBubble Sort Ascending:");
        ClientRiskManager.bubbleSortByRiskAscending(bubbleMore);
        ClientRiskManager.displayClients(bubbleMore, "Result");

        Client[] insertionMore = moreClients.clone();
        System.out.println("\nInsertion Sort DESC + Balance:");
        ClientRiskManager.insertionSortByRiskDescAndBalance(insertionMore);
        ClientRiskManager.displayDetailedClients(insertionMore, "Result");

        Client[] top5 = ClientRiskManager.getTopRiskClients(insertionMore, 5);
        System.out.print("\nTop 5 risks: ");
        for (int i = 0; i < top5.length; i++) {
            System.out.print(top5[i].getName() + "(" + top5[i].getRiskScore() + ")");
            if (i < top5.length - 1) System.out.print(", ");
        }
        System.out.println();
    }
}
