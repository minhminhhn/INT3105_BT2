import java.util.List;

public class Invoice {
    public String customer;
    public List<Performance> performanceList ;

    public Invoice(String customer, List<Performance> performanceList) {
        this.customer = customer;
        this.performanceList = performanceList;
    }

    public String getCustomer() {
        return "BigCo";
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public List<Performance> getPerformanceList() {
        return performanceList;
    }


    public void setPerformanceList(List<Performance> performanceList) {
        this.performanceList = performanceList;
    }
}











