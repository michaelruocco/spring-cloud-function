package uk.co.mruoc.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.javamoney.moneta.Money;
import uk.co.mruoc.JacksonConfiguration;
import uk.co.mruoc.function.UnexpectedErrorException;

import javax.money.MonetaryAmount;
import java.io.IOException;

public class MonetaryAmountTypeConverter implements DynamoDBTypeConverter<String, MonetaryAmount> {

    private ObjectMapper mapper = JacksonConfiguration.getMapper();

    @Override
    public String convert(MonetaryAmount money) {
        try {
            return mapper.writeValueAsString(money);
        } catch (IOException e) {
            throw new UnexpectedErrorException(e);
        }
    }

    @Override
    public MonetaryAmount unconvert(String json) {
        try {
            return mapper.readValue(json, Money.class);
        } catch (IOException e) {
            throw new UnexpectedErrorException(e);
        }
    }

}
