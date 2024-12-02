package org.tutorhub.response;

@lombok.Data
@lombok.Builder
public final class Data< T, V > {
    private volatile String type;
    private volatile V total;
    private volatile T data;

    public static <T> Data from ( final T value ) {
        return Data
                .builder()
                .data( value )
                .build();
    }
}
