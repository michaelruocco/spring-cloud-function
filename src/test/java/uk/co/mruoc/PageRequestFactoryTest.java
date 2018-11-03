package uk.co.mruoc;

import org.junit.Test;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class PageRequestFactoryTest {

    private static final String PAGE_NUMBER_NAME = "pageNumber";
    private static final String PAGE_SIZE_NAME = "pageSize";

    private static final int DEFAULT_PAGE_NUMBER = 0;
    private static final int DEFAULT_PAGE_SIZE = 10;

    private final PageRequestFactory factory = new PageRequestFactory();

    @Test
    public void shouldBuildRequestWithDefaultParametersIfNotProvided() {
        QueryStringParameterProvider provider = mock(QueryStringParameterProvider.class);

        Pageable request = factory.build(provider);

        assertThat(request.getPageNumber()).isEqualTo(DEFAULT_PAGE_NUMBER);
        assertThat(request.getPageSize()).isEqualTo(DEFAULT_PAGE_SIZE);
    }

    @Test
    public void shouldBuildRequestFromQueryStringParameterValues() {
        QueryStringParameterProvider provider = mock(QueryStringParameterProvider.class);
        final int pageNumber = 2;
        given(provider.hasQueryStringParameter(PAGE_NUMBER_NAME)).willReturn(true);
        given(provider.getQueryStringParameter(PAGE_NUMBER_NAME)).willReturn(Integer.toString(pageNumber));
        final int pageSize = 5;
        given(provider.hasQueryStringParameter(PAGE_SIZE_NAME)).willReturn(true);
        given(provider.getQueryStringParameter(PAGE_SIZE_NAME)).willReturn(Integer.toString(pageSize));

        Pageable request = factory.build(provider);

        assertThat(request.getPageNumber()).isEqualTo(pageNumber);
        assertThat(request.getPageSize()).isEqualTo(pageSize);
    }

}
