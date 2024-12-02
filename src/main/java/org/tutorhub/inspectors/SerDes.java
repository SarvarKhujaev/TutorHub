package org.tutorhub.inspectors;

import org.tutorhub.annotations.entity.constructor.EntityConstructorAnnotation;
import org.tutorhub.inspectors.dataTypesInpectors.UuidInspector;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Objects;

import com.google.gson.GsonBuilder;
import com.google.gson.Gson;

@org.tutorhub.annotations.services.ImmutableEntityAnnotation
public class SerDes extends UuidInspector {
    public final static ObjectMapper objectMapper = new ObjectMapper();
    private final static Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create();

    protected SerDes () {
        super( SerDes.class );
    }

    @EntityConstructorAnnotation
    protected <T extends UuidInspector> SerDes( @lombok.NonNull final Class<T> instance ) {
        super( SerDes.class );

        AnnotationInspector.checkCallerPermission( instance, SerDes.class );
        AnnotationInspector.checkAnnotationIsImmutable( SerDes.class );
    }

    @lombok.NonNull
    @lombok.Synchronized
    protected static synchronized Gson getGson () {
        return gson;
    }

    @lombok.NonNull
    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_ -> !null" )
    public static synchronized <T> String serialize ( @lombok.NonNull final T object ) {
        return getGson().toJson( Objects.requireNonNull( object ) );
    }

    @lombok.NonNull
    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_, _ -> !null" )
    protected final synchronized <T> T deserialize (
            @lombok.NonNull final String value,
            @lombok.NonNull final Class<T> clazz
    ) {
        return getGson().fromJson( value, clazz );
    }
}
