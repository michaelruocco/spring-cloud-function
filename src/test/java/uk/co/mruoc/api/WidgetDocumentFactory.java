package uk.co.mruoc.api;

import org.javamoney.moneta.Money;
import uk.co.mruoc.api.WidgetDocument.WidgetDocumentBuilder;

import java.util.UUID;

public class WidgetDocumentFactory {

    private WidgetDocumentFactory() {
        // utility class
    }

    public static WidgetDocument build(int number) {
        return new WidgetDocumentBuilder()
                .setId(getId(number))
                .setDescription("widget " + number)
                .setCost(Money.of(8, "GBP"))
                .setPrice(Money.of(12.33, "GBP"))
                .build();
    }

    private static UUID getId(int number) {
        if (number == 1) {
            return UUID.fromString("a99163ea-6e5f-4da1-9e7f-7402f02a675b");
        }
        return UUID.fromString("00441a27-bb59-4bfe-b9b1-0d591d3b311f");
    }

}
