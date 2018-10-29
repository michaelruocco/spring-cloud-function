package uk.co.mruoc;

public interface Request<I> extends HeaderProvider, PathParameterProvider, QueryStringParameterProvider {

    I getBody();

    String getUri();

}
