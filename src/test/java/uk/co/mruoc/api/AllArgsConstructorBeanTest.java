package uk.co.mruoc.api;

import org.force66.beantester.tests.BeanTest;

import java.util.Arrays;

public class AllArgsConstructorBeanTest implements BeanTest {

    @Override
    public boolean testBeanClass(Class<?> aClass, Object[] objects) {

        System.out.println("class " + aClass.getName());
        System.out.println("args " + Arrays.toString(objects));
        return false;
    }

    @Override
    public String getFailureReason() {
        return "test";
    }

}
