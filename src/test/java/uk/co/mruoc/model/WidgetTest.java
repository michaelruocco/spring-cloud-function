package uk.co.mruoc.model;

import org.javamoney.moneta.Money;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class WidgetTest {

    @Test
    public void shouldUpdateDescriptionIfSet() {
        Widget widget = new FakeWidget();
        Widget newWidget = new Widget();
        newWidget.setDescription("Updated description");

        Widget updatedWidget = widget.update(newWidget);

        assertThat(updatedWidget).isEqualToIgnoringGivenFields(widget, "description");
        assertThat(updatedWidget.getDescription()).isEqualTo(newWidget.getDescription());
    }

    @Test
    public void shouldUpdateCostIfSet() {
        Widget widget = new FakeWidget();
        Widget newWidget = new Widget();
        newWidget.setCost(Money.of(99.99, "GBP"));

        Widget updatedWidget = widget.update(newWidget);

        assertThat(updatedWidget).isEqualToIgnoringGivenFields(widget, "cost");
        assertThat(updatedWidget.getCost()).isEqualTo(newWidget.getCost());
    }

    @Test
    public void shouldUpdatePriceIfSet() {
        Widget widget = new FakeWidget();
        Widget newWidget = new Widget();
        newWidget.setPrice(Money.of(99.99, "GBP"));

        Widget updatedWidget = widget.update(newWidget);

        assertThat(updatedWidget).isEqualToIgnoringGivenFields(widget, "price");
        assertThat(updatedWidget.getPrice()).isEqualTo(newWidget.getPrice());
    }

}
