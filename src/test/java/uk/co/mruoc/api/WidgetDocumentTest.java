package uk.co.mruoc.api;

import org.force66.beantester.BeanTester;
import org.javamoney.moneta.Money;
import org.junit.Test;

import java.math.BigDecimal;

public class WidgetDocumentTest {

    private BeanTester tester = new BeanTester();

    @Test
    public void shouldTestBean() {
        tester.testBean(WidgetDocument.class);
    }

    @Test
    public void shouldTestBeanAllArgsConstructor() {
        long id = 1L;
        Money money = Money.of(BigDecimal.ONE, "GBP");
        Object[] args = new Object[ ] { id, "test description", money, money };
        tester.testBean(WidgetDocument.class, args);
    }

}
