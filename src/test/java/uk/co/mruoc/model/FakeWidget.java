package uk.co.mruoc.model;

import org.javamoney.moneta.Money;

import javax.money.MonetaryAmount;
import java.text.DecimalFormat;
import java.util.UUID;

public class FakeWidget extends Widget {

    private static final String JSON_FORMAT = "{\"data\":{\"id\":\"%s\",\"type\":\"widgets\",\"attributes\":{\"description\":\"%s\",\"cost\":{\"amount\":%s,\"currency\":\"%s\"},\"price\":{\"amount\":%s,\"currency\":\"%s\"}}}}";

    private static final UUID ID = UUID.randomUUID();
    private static final String DESCRIPTION = String.format("fake widget %s", ID);
    private static final MonetaryAmount COST = Money.of(5, "GBP");
    private static final MonetaryAmount PRICE = Money.of(10, "GBP");

    public FakeWidget() {
        super(ID, DESCRIPTION, COST, PRICE);
    }

    public String asJson() {
        DecimalFormat df = new DecimalFormat("#.00");
        return String.format(JSON_FORMAT,
                ID,
                DESCRIPTION,
                df.format(COST.getNumber()), COST.getCurrency(),
                df.format(PRICE.getNumber()), PRICE.getCurrency()
        );
    }




}
