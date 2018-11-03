package uk.co.mruoc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Slf4j
public class PageRequestFactory {

    private static final String PAGE_NUMBER_NAME = "pageNumber";
    private static final String PAGE_SIZE_NAME = "pageSize";

    private static final int DEFAULT_PAGE_NUMBER = 0;
    private static final int DEFAULT_PAGE_SIZE = 10;

    public Pageable build(QueryStringParameterProvider provider) {
        int pageNumber = extractPageNumber(provider);
        int pageSize = extractPageSize(provider);
        return PageRequest.of(pageNumber, pageSize);
    }

    private static int extractPageNumber(QueryStringParameterProvider provider) {
        if (provider.hasQueryStringParameter(PAGE_NUMBER_NAME)) {
            return Integer.parseInt(provider.getQueryStringParameter(PAGE_NUMBER_NAME));
        }
        log.info("page number not specified, using default page number {}", DEFAULT_PAGE_NUMBER);
        return DEFAULT_PAGE_NUMBER;
    }

    private static int extractPageSize(QueryStringParameterProvider provider) {
        if (provider.hasQueryStringParameter(PAGE_SIZE_NAME)) {
            return Integer.parseInt(provider.getQueryStringParameter(PAGE_SIZE_NAME));
        }
        log.info("page size not specified, using default page size {}", DEFAULT_PAGE_SIZE);
        return DEFAULT_PAGE_SIZE;
    }

}
