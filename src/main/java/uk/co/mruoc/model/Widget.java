package uk.co.mruoc.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import uk.co.mruoc.repository.MonetaryAmountTypeConverter;

import javax.money.MonetaryAmount;

@DynamoDBTable(tableName = "widget")
public class Widget {

    private Long id;
    private String description;
    private MonetaryAmount cost;
    private MonetaryAmount price;

    public Widget() {
        // intentionally blank
    }

    public Widget(Long id, String description, MonetaryAmount cost, MonetaryAmount price) {
        this.id = id;
        this.description = description;
        this.cost = cost;
        this.price = price;
    }

    @DynamoDBHashKey
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
    @DynamoDBTypeConverted(converter = MonetaryAmountTypeConverter.class)
    public MonetaryAmount getCost() {
        return cost;
    }

    public void setCost(MonetaryAmount cost) {
        this.cost = cost;
    }

    @DynamoDBAttribute
    @DynamoDBTypeConverted(converter = MonetaryAmountTypeConverter.class)
    public MonetaryAmount getPrice() {
        return price;
    }

    public void setPrice(MonetaryAmount price) {
        this.price = price;
    }

}
