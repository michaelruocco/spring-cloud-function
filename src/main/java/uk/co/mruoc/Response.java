package uk.co.mruoc;

public interface Response<I> extends HeaderProvider {

    I getBody();

    int getStatusCode();

}
