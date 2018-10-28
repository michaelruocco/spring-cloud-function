package uk.co.mruoc.function;

import uk.co.mruoc.api.WidgetDocument;
import uk.co.mruoc.api.WidgetDocument.WidgetDocumentBuilder;
import uk.co.mruoc.model.Widget;

public class WidgetConverter {

    public Widget toModel(WidgetDocument document) {
        Widget model = new Widget();
        model.setId(document.getId());
        model.setDescription(document.getDescription());
        model.setCost(document.getCost());
        model.setPrice(document.getPrice());
        return model;
    }

    public WidgetDocument toDocument(Widget model) {
        return new WidgetDocumentBuilder()
                .setId(model.getId())
                .setDescription(model.getDescription())
                .setCost(model.getCost())
                .setPrice(model.getPrice())
                .build();
    }

}
