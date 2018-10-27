package uk.co.mruoc.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.money.MonetaryAmount;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WidgetDocument {

    private long id;
    private String description;
    private MonetaryAmount cost;
    private MonetaryAmount price;

}
