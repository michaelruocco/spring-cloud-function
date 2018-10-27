package uk.co.mruoc.function;

import uk.co.mruoc.api.WidgetDocument;
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
        WidgetDocument document = new WidgetDocument();
        document.setId(model.getId());
        document.setDescription(model.getDescription());
        document.setCost(model.getCost());
        document.setPrice(model.getPrice());
        return document;
    }

}
