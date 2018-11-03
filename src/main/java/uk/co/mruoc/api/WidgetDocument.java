package uk.co.mruoc.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.money.MonetaryAmount;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.UUID;

@Data
@NoArgsConstructor
public class WidgetDocument {

    @Valid
    private WidgetData data;

    private WidgetDocument(WidgetDocumentBuilder builder) {
        data = new WidgetData();
        data.id = builder.id;

        data.attributes = new WidgetAttributes();
        data.attributes.description = builder.description;
        data.attributes.cost = builder.cost;
        data.attributes.price = builder.price;
    }

    @JsonIgnore
    public UUID getId() {
        return data.getId();
    }

    @JsonIgnore
    public String getDescription() {
        return data.getAttributes().getDescription();
    }

    @JsonIgnore
    public MonetaryAmount getCost() {
        return data.getAttributes().getCost();
    }

    @JsonIgnore
    public MonetaryAmount getPrice() {
        return data.getAttributes().getPrice();
    }

    @ToString
    public static class WidgetData {

        private UUID id;

        @Pattern(regexp = "^widgets")
        private final String type = "widgets";

        @NotNull
        @Valid
        private WidgetAttributes attributes;

        public UUID getId() {
            return id;
        }

        public String getType() {
            return type;
        }

        public WidgetAttributes getAttributes() {
            return attributes;
        }

    }

    @ToString
    public static class WidgetAttributes {

        @NotNull
        private String description;

        @NotNull
        private MonetaryAmount cost;

        @NotNull
        private MonetaryAmount price;

        public String getDescription() {
            return description;
        }

        public MonetaryAmount getCost() {
            return cost;
        }

        public MonetaryAmount getPrice() {
            return price;
        }

    }

    public static class WidgetDocumentBuilder {

        private UUID id;
        private String description;
        private MonetaryAmount cost;
        private MonetaryAmount price;

        public WidgetDocumentBuilder setId(UUID id) {
            this.id = id;
            return this;
        }

        public WidgetDocumentBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public WidgetDocumentBuilder setCost(MonetaryAmount cost) {
            this.cost = cost;
            return this;
        }

        public WidgetDocumentBuilder setPrice(MonetaryAmount price) {
            this.price = price;
            return this;
        }

        public WidgetDocument build() {
            return new WidgetDocument(this);
        }

    }

}
