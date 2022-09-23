import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class NewMain {

    public static void main(String[] args) {
        Performance performance1 = new Performance("hamlet", 55);
        Performance performance2 = new Performance("as-like", 35);
        Performance performance3 = new Performance("othello", 40);
        List<Performance> performanceList = new ArrayList<>();
        performanceList.add(performance1);
        performanceList.add(performance2);
        performanceList.add(performance3);

        Invoice invoice = new Invoice("BigCo", performanceList);
        Play play1 = new Play("hamlet", "Hamlet", "tragedy");
        Play play2 = new Play("as-like", "As You Like It", "comedy");
        Play play3 = new Play("othello", "Othello", "tragedy");
        List<Play> playList = new ArrayList<>();
        playList.add(play1);
        playList.add(play2);
        playList.add(play3);

        String statement = statement(invoice, playList);
        System.out.println(statement);
        System.out.println();
    }

    //Refactoring Techniques
    private static Play getPlay(String id, List<Play> playList) {
        boolean canFind = false;
        Play findPlay = null;
        for (int i = 0; i < playList.size(); i++) {
            if (playList.get(i).getId().equals(id)) {
                findPlay = playList.get(i);
                canFind = true;
                break;
            }
        }
        if (!canFind) {
            throw new NullPointerException();
        }
        return findPlay;
    }

    private static String format(Integer integer) {

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        String str = currencyFormat.format(integer);

        return str;
    }

    public static Performance getPerformance(int i, Invoice invoice) {
        return invoice.getPerformanceList().get(i);
    }

    public static Integer amountOnType(String type, Performance performance) {
        Integer thisAmount = 0;
        if (type.equals("tragedy")) {
            thisAmount = 40000;
            if (performance.getAudience() > 30) {
                thisAmount += 1000 * (performance.getAudience() - 30);
            }

        }
        if (type.equals("comedy")) {
            thisAmount = 30000;
            if (performance.getAudience() > 20) {
                thisAmount += 10000 + 500 * (performance.getAudience() - 20);
            }
            thisAmount += 300 * performance.getAudience();
        }
        return thisAmount;
    }

    public static String statement(Invoice invoice, List<Play> playList) {
        int totalAmount = 0;
        int volumeCredits = 0;
        String result = "Statement for " + invoice.getCustomer() + "\n";

        for (int i = 0; i < invoice.getPerformanceList().size(); i++) {

            Play play = getPlay(invoice.getPerformanceList().get(i).getPlayID(), playList);
            Performance perf = invoice.getPerformanceList().get(i);
            int thisAmount = 0;
            switch (play.getType()) {
                case "tragedy":
                    thisAmount = amountOnType("tragedy", perf);
                    break;
                case "comedy":
                    thisAmount = amountOnType("comedy", perf);
                    break;
                default:
                    throw new Error();
            }
            volumeCredits += Math.max(perf.getAudience() - 30, 0);
            // add extra credit for every ten comedy attendees
            if (play.getType().equals("comedy")) {
                volumeCredits += Math.floor(perf.getAudience() / 5);
            }

            result += play.getName() + " " + format(thisAmount / 100) + " " + Integer.toString(invoice.getPerformanceList().get(i).getAudience()) + " seats\n";
            totalAmount += thisAmount;


        }
        result += "Amount owed is  " +
                format(totalAmount / 100) + "\n";
        result += "You earned  " + Integer.toString(volumeCredits) + "  credits\n";
        return result;
    }

    public class Performance {
        public String playID;
        public Integer audience;
    }


    public class Invoice {
        public String customer;
    }

    public class Play {
        public String name;
        public String type;
    }


}

