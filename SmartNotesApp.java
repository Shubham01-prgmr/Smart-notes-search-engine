import java.util.*;

class Note {
    String category;
    String text;
    String time;

    Note(String category, String text, String time) {
        this.category = category;
        this.text = text;
        this.time = time;
    }

    public String toString() {
        return "[" + category + "] [" + time + "] " + text;
    }
}

public class SmartNotesApp {

    static ArrayList<Note> notes = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    // ================= TIME =================
    static String getTime() {
        return new Date().toString();
    }

    // ================= ADD =================
    static void addNote() {
        System.out.print("Enter category (Work/Personal/Study): ");
        String category = sc.next();

        sc.nextLine();
        System.out.print("Enter note: ");
        String text = sc.nextLine();

        notes.add(new Note(category, text, getTime()));
        System.out.println("Note added!\n");
    }

    // ================= VIEW =================
    static void viewNotes() {
        if (notes.isEmpty()) {
            System.out.println("No notes available.\n");
            return;
        }

        int i = 1;
        for (Note n : notes) {
            System.out.println(i++ + ". " + n);
        }
        System.out.println();
    }

    // ================= LINEAR SEARCH =================
    static void linearSearch(String key) {
        boolean found = false;

        for (int i = 0; i < notes.size(); i++) {
            if (notes.get(i).text.toLowerCase().contains(key.toLowerCase())) {
                System.out.println("Found: " + notes.get(i));
                found = true;
            }
        }

        if (!found) System.out.println("Not found.");
    }

    // ================= BINARY SEARCH =================
    static void binarySearch(String key) {
        ArrayList<Note> sorted = new ArrayList<>(notes);

        // sort by text
        Collections.sort(sorted, (a, b) -> a.text.compareToIgnoreCase(b.text));

        int low = 0, high = sorted.size() - 1;
        boolean found = false;

        while (low <= high) {
            int mid = (low + high) / 2;
            String midText = sorted.get(mid).text.toLowerCase();

            if (midText.contains(key.toLowerCase())) {
                System.out.println("Found: " + sorted.get(mid));
                found = true;
                break;
            } else if (midText.compareTo(key.toLowerCase()) < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        if (!found) System.out.println("Not found.");
    }

    // ================= KMP SEARCH =================
    static void KMPSearch(String pattern) {
        boolean found = false;

        for (Note note : notes) {
            if (kmp(note.text.toLowerCase(), pattern.toLowerCase())) {
                System.out.println("Found: " + note);
                found = true;
            }
        }

        if (!found) System.out.println("Not found.");
    }

    static boolean kmp(String text, String pattern) {
        int[] lps = computeLPS(pattern);

        int i = 0, j = 0;

        while (i < text.length()) {
            if (text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
            }

            if (j == pattern.length()) {
                return true;
            } else if (i < text.length() && text.charAt(i) != pattern.charAt(j)) {
                if (j != 0)
                    j = lps[j - 1];
                else
                    i++;
            }
        }
        return false;
    }

    static int[] computeLPS(String pattern) {
        int[] lps = new int[pattern.length()];
        int len = 0;
        int i = 1;

        while (i < pattern.length()) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0)
                    len = lps[len - 1];
                else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
        return lps;
    }

    // ================= SEARCH MENU =================
    static void searchNote() {
        sc.nextLine();
        System.out.print("Enter keyword: ");
        String key = sc.nextLine();

        // Linear
        long start = System.nanoTime();
        System.out.println("\n--- Linear Search ---");
        linearSearch(key);
        long end = System.nanoTime();
        System.out.println("Time: " + (end - start) + " ns\n");

        // Binary
        start = System.nanoTime();
        System.out.println("--- Binary Search ---");
        binarySearch(key);
        end = System.nanoTime();
        System.out.println("Time: " + (end - start) + " ns\n");

        // KMP
        start = System.nanoTime();
        System.out.println("--- KMP Search ---");
        KMPSearch(key);
        end = System.nanoTime();
        System.out.println("Time: " + (end - start) + " ns\n");
    }

    // ================= DELETE =================
    static void deleteNote() {
        viewNotes();
        if (notes.isEmpty()) return;

        System.out.print("Enter note number to delete: ");
        int index = sc.nextInt();

        if (index < 1 || index > notes.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        notes.remove(index - 1);
        System.out.println("Note deleted!\n");
    }

    // ================= MENU =================
    static void menu() {
        while (true) {
            System.out.println("===== SMART NOTES SEARCH ENGINE =====");
            System.out.println("1. Add Note");
            System.out.println("2. View Notes");
            System.out.println("3. Search Note (DAA)");
            System.out.println("4. Delete Note");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1: addNote(); break;
                case 2: viewNotes(); break;
                case 3: searchNote(); break;
                case 4: deleteNote(); break;
                case 5: return;
                default: System.out.println("Invalid choice\n");
            }
        }
    }

    public static void main(String[] args) {
        menu();
    }
}