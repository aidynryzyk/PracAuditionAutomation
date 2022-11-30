import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class YearlyReport {
    HashMap<Integer, HashMap<String, String>> reportByYear = new HashMap<>();
    ArrayList<String> columns = new ArrayList<>();
    int year;
    public void addReport(String content) {
        String[] lines = content.split("\\n");
        for (String line:
                lines) {
            String[] headers = line.split(",");
            if (columns.size() == 0) {
                columns.addAll(List.of(headers));
                continue;
            }
            int indexOfMonth = columns.indexOf("month");
            int indexOfIsExpense = columns.indexOf("is_expense");
            int indexOfAmount = columns.indexOf("amount");
            if (!reportByYear.containsKey(Integer.parseInt(headers[indexOfMonth]))) {
                reportByYear.put(Integer.parseInt(headers[indexOfMonth]), new HashMap<>());
            }
            if (headers[indexOfIsExpense].equals("false")) {
                reportByYear.get(Integer.parseInt(headers[indexOfMonth])).put("total_income", headers[indexOfAmount]);
            } else if (headers[indexOfIsExpense].equals("true")) {
                reportByYear.get(Integer.parseInt(headers[indexOfMonth])).put("total_expense", headers[indexOfAmount]);
            }
        }
    }

    public long getTotalIncome(int month) {
        return Long.parseLong(reportByYear.get(month).get("total_income"));
    }

    public long getTotalExpense(int month) {
        return Long.parseLong(reportByYear.get(month).get("total_expense"));
    }

    public ArrayList<Integer> getTotalMonthsList() {
        return new ArrayList<>(reportByYear.keySet());
    }

    public void setYear(int year) {
        this.year = year;
    }
}
