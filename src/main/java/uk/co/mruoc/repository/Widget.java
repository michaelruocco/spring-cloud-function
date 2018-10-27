package uk.co.mruoc.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import org.javamoney.moneta.Money;

@DynamoDBTable(tableName = "widget")
public class Widget {

    private long id;
    private String description;
    private Money cost;
    private Money price;

    public Widget() {
        // intentionally blank
    }

    public Widget(long id, String description, Money cost, Money price) {
        this.id = id;
        this.description = description;
        this.cost = cost;
        this.price = price;
    }

    @DynamoDBHashKey
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @DynamoDBAttribute
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @DynamoDBAttribute
    @DynamoDBTypeConverted(converter = MoneyTypeConverter.class)
    public Money getCost() {
        return cost;
    }

    public void setCost(Money cost) {
        this.cost = cost;
    }

    @DynamoDBAttribute
    @DynamoDBTypeConverted(converter = MoneyTypeConverter.class)
    public Money getPrice() {
        return price;
    }

    public void setPrice(Money price) {
        this.price = price;
    }

}
