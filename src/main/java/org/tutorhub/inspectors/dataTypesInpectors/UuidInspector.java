package org.tutorhub.inspectors.dataTypesInpectors;

import org.tutorhub.annotations.entity.constructor.EntityConstructorAnnotation;
import org.tutorhub.constans.errors.Errors;

import org.tutorhub.inspectors.AnnotationInspector;
import org.tutorhub.inspectors.CollectionsInspector;

import org.apache.commons.lang3.Validate;

import java.lang.ref.WeakReference;
import java.util.UUID;

@org.tutorhub.annotations.services.ImmutableEntityAnnotation
public class UuidInspector {
    protected final static WeakReference< UUID > uuid = new WeakReference<>( generateTimeBased() );

    protected UuidInspector () {}

    @EntityConstructorAnnotation( permission = CollectionsInspector.class )
    protected <T extends UuidInspector> UuidInspector ( @lombok.NonNull final Class<T> instance ) {
        AnnotationInspector.checkCallerPermission( instance, UuidInspector.class );
        AnnotationInspector.checkAnnotationIsImmutable( UuidInspector.class );
    }

    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_ -> _" )
    public static synchronized boolean isUUIDValid ( @lombok.NonNull final String inputValue ) {
        Validate.isTrue( StringOperations.checkString( inputValue ) );

        return inputValue.matches( StringOperations.UUID_PATTERN );
    }

    @lombok.NonNull
    @lombok.Synchronized
    public static synchronized UUID generateTimeBased () {
        return UUID.randomUUID();
    }

    @lombok.NonNull
    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_ -> _" )
    protected static synchronized UUID convertStringToUuid( @lombok.NonNull final String value ) {
        Validate.isTrue( isUUIDValid( value ), StringOperations.WRONG_UUID_FORMAT );
        return UUID.fromString( value );
    }

    @SuppressWarnings( value = "Prevent modification of the object's state" )
    @Override
    public UuidInspector clone() {
        throw new UnsupportedOperationException( Errors.OBJECT_IS_IMMUTABLE.translate( this.getClass().getName() ) );
    }
}
