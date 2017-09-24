package notes;

import java.util.Date;

public class ProductTemp {

    String name;
    String category;
    Integer price;
    Date startDate;
    Date endDate;

    public ProductTemp() {
    }

    public ProductTemp(String name, String category, Integer price, Date startDate, Date endDate) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public Integer getPrice() {
        return price;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    @Override
    public String toString() {
        return "ProductTemp{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
