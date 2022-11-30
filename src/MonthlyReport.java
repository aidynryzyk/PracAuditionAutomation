import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MonthlyReport {
    HashMap<Integer, ArrayList<HashMap<String, String>>> reportByMonth = new HashMap<>();
    ArrayList<String> columns = new ArrayList<>();

    public void addReport(int month, String content) {
        if (!reportByMonth.containsKey(month)) {
            reportByMonth.put(month, new ArrayList<>());
        }
        String[] lines = content.split("\\n");
        for (String line:
             lines) {
            String[] headers = line.split(",");
            if (columns.size() == 0) {
                columns.addAll(List.of(headers));
                continue;
            }
            if (headers[0].equals("item_name")) {
                continue;
            }
            HashMap<String, String> map = new HashMap<>();
            for (int i = 0; i < headers.length; i++) {
                map.put(columns.get(i), headers[i]);
            }
            reportByMonth.get(month).add(map);
        }
    }

    public long getTotalIncome(int month) {
        long totalIncome = 0;
        ArrayList<HashMap<String, String>> map = reportByMonth.get(month);
        for (HashMap<String, String> record:
             map) {
            if (record.get("is_expense").equals("FALSE")) {
                totalIncome += Long.parseLong(record.get("quantity")) * Long.parseLong(record.get("sum_of_one"));
            }
        }
        return totalIncome;
    }

    public long getTotalExpense(int month) {
        long totalExpense = 0;
        ArrayList<HashMap<String, String>> map = reportByMonth.get(month);
        for (HashMap<String, String> record:
                map) {
            if (record.get("is_expense").equals("TRUE")) {
                totalExpense += Long.parseLong(record.get("quantity")) * Long.parseLong(record.get("sum_of_one"));
            }
        }
        return totalExpense;
    }

    public ArrayList<Integer> getTotalMonthsList() {
        return new ArrayList<>(reportByMonth.keySet());
    }

    public String getMonthName(int month) {
        switch (month) {
            case 1:
                return "January";
            case 2:
                return "February";
            case 3:
                return "March";
            case 4:
                return "April";
            case 5:
                return "May";
            case 6:
                return "June";
            case 7:
                return "July";
            case 8:
                return "August";
            case 9:
                return "September";
            case 10:
                return "October";
            case 11:
                return "November";
            case 12:
                return "December";
        }
        return null;
    }

    public HashMap<String, ArrayList<String>> getMostProfitableAndMostExpensiveGoods(int month) {
        HashMap<String, ArrayList<String>> maxProfitAndMaxExpense = new HashMap<>();
        ArrayList<String> maxProfitGood = new ArrayList<>();
        ArrayList<String> maxExpensiveGood = new ArrayList<>();
        String maxProfitName = "";
        String maxExpensiveName = "";
        long maxProfit = 0;
        long maxExpense = 0;
        ArrayList<HashMap<String, String>> listOfRecords = reportByMonth.get(month);
        for (HashMap<String, String> record:
             listOfRecords) {
            if (record.get("is_expense").equals("FALSE")) {
                long profit = Long.parseLong(record.get("quantity")) * Long.parseLong(record.get("sum_of_one"));
                if (profit > maxProfit) {
                    maxProfit = profit;
                    maxProfitName = record.get("item_name");
                }
            } else {
                long expense = Long.parseLong(record.get("quantity")) * Long.parseLong(record.get("sum_of_one"));
                if (expense > maxExpense) {
                    maxExpense = expense;
                    maxExpensiveName = record.get("item_name");
                }
            }
        }
        maxProfitGood.add(maxProfitName);
        maxProfitGood.add(String.valueOf(maxProfit));
        maxExpensiveGood.add(maxExpensiveName);
        maxExpensiveGood.add(String.valueOf(maxExpense));
        maxProfitAndMaxExpense.put("most_profitable", maxProfitGood);
        maxProfitAndMaxExpense.put("most_expensive", maxExpensiveGood);
        return maxProfitAndMaxExpense;
    }
}
