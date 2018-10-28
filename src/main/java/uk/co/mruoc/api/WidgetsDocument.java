package uk.co.mruoc.api;

import lombok.Data;
import uk.co.mruoc.api.WidgetDocument.WidgetData;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class WidgetsDocument {

    @NotNull
    @Valid
    private List<WidgetData> data;

    private WidgetsDocument(WidgetsDocumentBuilder builder) {
        data = builder.data;
    }

    public List<WidgetData> getData() {
        return data;
    }

    public static class WidgetsDocumentBuilder {

        private List<WidgetData> data;

        public WidgetsDocumentBuilder setData(List<WidgetData> data) {
            this.data = data;
            return this;
        }

        public WidgetsDocument build() {
            return new WidgetsDocument(this);
        }

    }

}
