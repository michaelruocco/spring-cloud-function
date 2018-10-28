package uk.co.mruoc.function;

import uk.co.mruoc.api.WidgetDocument;
import uk.co.mruoc.api.WidgetDocument.WidgetData;
import uk.co.mruoc.api.WidgetDocument.WidgetDocumentBuilder;
import uk.co.mruoc.api.WidgetsDocument;
import uk.co.mruoc.api.WidgetsDocument.WidgetsDocumentBuilder;
import uk.co.mruoc.model.Widget;

import java.util.ArrayList;
import java.util.List;

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

    public WidgetsDocument toDocument(Iterable<Widget> widgets) {
        List<WidgetData> data = new ArrayList<>();
        widgets.forEach(widget -> data.add(toWidgetData(widget)));
        return new WidgetsDocumentBuilder()
                .setData(data)
                .build();
    }

    private WidgetData toWidgetData(Widget widget) {
        WidgetDocument document = toDocument(widget);
        return document.getData();
    }

}
