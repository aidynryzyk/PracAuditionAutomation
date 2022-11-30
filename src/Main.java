import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MonthlyReport monthlyReport = new MonthlyReport();
        YearlyReport yearlyReport = new YearlyReport();

        while (true) {
            System.out.println(
                    "Please choose what you want to do:\n" +
                            "1. Read monthly report;\n" +
                            "2. Read yearly report;\n" +
                            "3. Check report;\n" +
                            "4. Print monthly report information;\n" +
                            "5. Print yearly report information;\n" +
                            "6. Exit.\n"
            );
            String action = scanner.next();
            if (action.equals("6")) {
                break;
            } else if (action.equals("1")) {
                System.out.print("Please enter number of monthly reports: ");
                int numberOfReports = scanner.nextInt();
                for (int i = 1; i <= numberOfReports; i++) {
                    String dateName = "resources/m.2021";
                    if (i < 10) {
                        dateName += "0";
                    }
                    monthlyReport.addReport(i, readReportFileOrNull(dateName + i + ".csv"));
                }
            } else if (action.equals("2")) {
                System.out.print("Please enter the year: ");
                int year = scanner.nextInt();
                yearlyReport.setYear(year);
                yearlyReport.addReport(Objects.requireNonNull(readReportFileOrNull("resources/y." + year + ".csv")));
            } else if (action.equals("3")) {
                boolean isCorrect = true;
                ArrayList<Integer> months = monthlyReport.getTotalMonthsList();
                for (Integer month:
                     months) {
                    if (monthlyReport.getTotalIncome(month) != yearlyReport.getTotalIncome(month)
                    || monthlyReport.getTotalExpense(month) != yearlyReport.getTotalExpense(month)) {
                        isCorrect = false;
                        break;
                    }
                }
                if (isCorrect) {
                    System.out.println("Everything is correct");
                } else {
                    System.out.println("Something is incorrect");
                }
            } else if (action.equals("4")) {
                System.out.print("Please enter month ");
                int month = scanner.nextInt();
                System.out.println(monthlyReport.getMonthName(month));
                ArrayList<String> mostProfitableGood = monthlyReport
                        .getMostProfitableAndMostExpensiveGoods(month)
                        .get("most_profitable");
                System.out.println("Most profitable good is: ");
                System.out.println(mostProfitableGood.get(0) + " with profit of " + mostProfitableGood.get(1));
                ArrayList<String> mostExpensiveGood = monthlyReport
                        .getMostProfitableAndMostExpensiveGoods(month)
                        .get("most_expensive");
                System.out.println("Most expensive good is: ");
                System.out.println(mostExpensiveGood.get(0) + " with expense of " + mostExpensiveGood.get(1));
            } else if (action.equals("5")) {
                System.out.println(yearlyReport.year);
                ArrayList<Integer> months = yearlyReport.getTotalMonthsList();
                int totalExpense = 0;
                int totalIncome = 0;
                for (Integer month:
                     months) {
                    System.out.println(monthlyReport.getMonthName(month) + " profit: "
                            + (yearlyReport.getTotalIncome(month) - yearlyReport.getTotalExpense(month)));
                    totalIncome += yearlyReport.getTotalIncome(month);
                    totalExpense += yearlyReport.getTotalExpense(month);
                }
                System.out.printf("Average expense: %.2f\n", (double) totalExpense / months.size());
                System.out.printf("Average income: %.2f\n", (double) totalIncome / months.size());
            }
        }
    }

    public static String readReportFileOrNull(String path) {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            System.out.println("Unable to read file. Please check for its correctness.");
            return null;
        }
    }
}