package mka.coffeshopmanagementsystem.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class to format and print ASCII tables in the console.
 * Supports headers, rows, and section titles.
 * 
 * @author Anthony Aimacaña, MKA programer, @ESPE
 */
public class ConsoleTable {
    private String title;
    private String[] headers;
    private final List<String[]> rows = new ArrayList<>();

    public ConsoleTable() {
    }

    public ConsoleTable(String... headers) {
        this.headers = headers;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setHeaders(String... headers) {
        this.headers = headers;
    }

    public void addRow(String... row) {
        rows.add(row);
    }

    public void print() {
        if (headers == null || headers.length == 0) {
            if (title != null) {
                printTitleOnly();
            }
            return;
        }

        int[] columnWidths = new int[headers.length];
        for (int i = 0; i < headers.length; i++) {
            columnWidths[i] = headers[i].length();
        }

        for (String[] row : rows) {
            for (int i = 0; i < Math.min(row.length, columnWidths.length); i++) {
                if (row[i] != null && row[i].length() > columnWidths[i]) {
                    columnWidths[i] = row[i].length();
                }
            }
        }

        int totalWidth = 1;
        for (int width : columnWidths) totalWidth += width + 3;

        if (title != null) {
            printCenteredTitle(totalWidth);
        }

        printSeparator(columnWidths);
        printRow(headers, columnWidths);
        printSeparator(columnWidths);
        for (String[] row : rows) {
            printRow(row, columnWidths);
        }
        printSeparator(columnWidths);
    }

    private void printTitleOnly() {
        System.out.println("\n=== " + title.toUpperCase() + " ===");
    }

    private void printCenteredTitle(int totalWidth) {
        System.out.println();
        int padding = (totalWidth - title.length()) / 2;
        for (int i = 0; i < padding; i++) System.out.print(" ");
        System.out.println(title.toUpperCase());
    }

    private void printSeparator(int[] columnWidths) {
        System.out.print("+");
        for (int width : columnWidths) {
            for (int i = 0; i < width + 2; i++) System.out.print("-");
            System.out.print("+");
        }
        System.out.println();
    }

    private void printRow(String[] row, int[] columnWidths) {
        System.out.print("|");
        for (int i = 0; i < columnWidths.length; i++) {
            String val = (i < row.length && row[i] != null) ? row[i] : "";
            System.out.print(" " + val);
            for (int j = 0; j < columnWidths[i] - val.length() + 1; j++) System.out.print(" ");
            System.out.print("|");
        }
        System.out.println();
    }
}
