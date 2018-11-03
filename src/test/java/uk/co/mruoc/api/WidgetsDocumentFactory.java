package uk.co.mruoc.api;

import uk.co.mruoc.api.WidgetDocument.WidgetData;
import uk.co.mruoc.api.WidgetsDocument.WidgetsDocumentBuilder;

import java.util.Arrays;
import java.util.List;

public class WidgetsDocumentFactory {

    private WidgetsDocumentFactory() {
        // utility class
    }

    public static WidgetsDocument build() {
        List<WidgetData> data = Arrays.asList(
                WidgetDocumentFactory.build(1).getData(),
                WidgetDocumentFactory.build(2).getData()
        );
        return new WidgetsDocumentBuilder()
                .setData(data)
                .build();
    }

}
