package uk.co.mruoc.model;

import org.javamoney.moneta.Money;

import javax.money.MonetaryAmount;
import java.text.DecimalFormat;
import java.util.UUID;

public class FakeWidget extends Widget {

    private static final String JSON_OBJECT_FORMAT = "{\"id\":\"%s\",\"type\":\"widgets\",\"attributes\":{\"description\":\"%s\",\"cost\":{\"amount\":%s,\"currency\":\"%s\"},\"price\":{\"amount\":%s,\"currency\":\"%s\"}}}";
    private static final String JSON_DOCUMENT_FORMAT = "{\"data\":%s}";

    private static final MonetaryAmount COST = Money.of(5, "GBP");
    private static final MonetaryAmount PRICE = Money.of(10, "GBP");

    public FakeWidget( ) {
        this(UUID.randomUUID());
    }

    public FakeWidget(UUID id) {
        super(id, String.format("fake widget %s", id), COST, PRICE);
    }

    public String asJsonDocument() {
        return String.format(JSON_DOCUMENT_FORMAT, asJsonObject());
    }

    public String asJsonObject() {
        DecimalFormat df = new DecimalFormat("#.00");
        return String.format(JSON_OBJECT_FORMAT,
                getId(),
                getDescription(),
                df.format(COST.getNumber()), COST.getCurrency(),
                df.format(PRICE.getNumber()), PRICE.getCurrency()
        );
    }




}
