package org.tutorhub.inspectors.dataTypesInpectors;

import org.tutorhub.annotations.entity.constructor.EntityConstructorAnnotation;
import org.tutorhub.annotations.LinksToDocs;

import org.tutorhub.interfaces.database.EntityToPostgresConverter;

import org.tutorhub.inspectors.CollectionsInspector;
import org.tutorhub.inspectors.AnnotationInspector;
import org.tutorhub.inspectors.Archive;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.text.SimpleDateFormat;
import java.text.ParseException;

import java.util.concurrent.atomic.AtomicReference;
import java.nio.charset.StandardCharsets;

import java.util.Locale;
import java.util.Date;

@LinksToDocs( links = "https://www.baeldung.com/java-apache-commons-text" )
@org.tutorhub.annotations.services.ImmutableEntityAnnotation
public class StringOperations extends CollectionsInspector {
    protected StringOperations () {
        super( StringOperations.class );
    }

    @EntityConstructorAnnotation( permission = Archive.class )
    protected <T extends UuidInspector> StringOperations ( @lombok.NonNull final Class<T> instance ) {
        super( StringOperations.class );
        AnnotationInspector.checkCallerPermission( instance, StringOperations.class );
        AnnotationInspector.checkAnnotationIsImmutable( StringOperations.class );
    }

    public final static String DOT = ".";
    public final static String EMPTY = "";
    public final static String SPACE = " ";
    public final static String SINGLE_QUOTE = "'";
    protected final static String SPACE_WITH_COMMA = ", ";
    protected final static String TASK_DETAILS_MESSAGE = "Your task details";
    protected final static String SPACE_WITH_DOUBLE_DOTS = " : ";

    public final static String WRONG_UUID_FORMAT = "Recieved uuid: %s is incorrect";
    public final static String NULL_VALUE_IN_ASSERT = "NULL VALUE WAS SENT";
    public final static String EMPTY_STRING_INSERTED = "EMPTY STRING WAS INSERTED";

    public final static String UUID_PATTERN = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$";
    public final static String AVRO_DATE_PATTERN = "^[0-9]{4}-[0-9]{2}-[0-9]{2}$";

    public final static String SQL_INJECTION_FILES_PATH = "src/main/resources/sql_injections/";

    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_ -> _" )
    protected static synchronized boolean checkString (
            final String value
    ) {
        return !StringUtils.isBlank( value );
    }

    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_ -> !null" )
    protected static synchronized byte @lombok.NonNull [] convertToBytes ( @lombok.NonNull final String text ) {
        Validate.isTrue(
                checkString( text ),
                EMPTY_STRING_INSERTED
        );

        return text.getBytes( StandardCharsets.UTF_8 );
    }

    @lombok.NonNull
    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_, _ -> !null" )
    protected final synchronized String getSuccessMessage (
            @lombok.NonNull final String className,
            @lombok.NonNull final String operation
    ) {
        return String.join(
                SPACE,
                className,
                "was",
                operation,
                "successfully"
        );
    }

    @lombok.NonNull
    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_, _ -> !null" )
    protected final synchronized <T extends EntityToPostgresConverter> String getSuccessMessage (
            @lombok.NonNull final AtomicReference< T > entity,
            @lombok.NonNull final String operation
    ) {
        return String.join(
                SPACE,
                entity.get().getEntityTableName().name(),
                "was",
                operation,
                "successfully"
        );
    }

    @lombok.NonNull
    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_, _ -> !null" )
    protected final synchronized <T extends EntityToPostgresConverter> String getSuccessMessage (
            @lombok.NonNull final T entity,
            @lombok.NonNull final String operation
    ) {
        return String.join(
                SPACE,
                entity.getEntityTableName().name(),
                "was",
                operation,
                "successfully"
        );
    }

    @lombok.NonNull
    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_ -> !null" )
    protected final synchronized String getFailMessage (
            @lombok.NonNull final String className
    ) {
        return String.join(
                SPACE,
                "This",
                className,
                "is not accepted"
        );
    }

    @lombok.NonNull
    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_ -> !null" )
    protected final synchronized <T extends EntityToPostgresConverter> String getFailMessage (
            @lombok.NonNull final AtomicReference< T > entity
    ) {
        return String.join(
                SPACE,
                "This",
                entity.get().getEntityTableName().name(),
                "is not accepted"
        );
    }

    @lombok.NonNull
    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_ -> !null" )
    protected final synchronized StringBuilder newStringBuilder ( @lombok.NonNull final String ... s ) {
        if ( s.length == 1 ) {
            return new StringBuilder( s[0] );
        } else {
            final StringBuilder stringBuilder = new StringBuilder();

            analyze(
                    convertArrayToStream( s ),
                    stringBuilder::append
            );

            return stringBuilder;
        }
    }

    @lombok.NonNull
    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_ -> !null" )
    protected final synchronized String removeAllDotes ( @lombok.NonNull final String s ) {
        return s.replaceAll( SINGLE_QUOTE, EMPTY );
    }

    @lombok.NonNull
    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_ -> !null" )
    protected final synchronized String concatNames ( @lombok.NonNull final Object object ) {
        return String.join( EMPTY, String.valueOf( object ).split( "[.]" ) );
    }

    @SuppressWarnings(
            value = """
                    принимает параметр для Cassandra, который является типом TEXТ,
                    и добавляет в начало и конец апострафы
                    
                    принимает параметр для Cassandra, который является типом TIMESTAMP,
                    и добавляет в начало и конец апострафы
                    """
    )
    @lombok.NonNull
    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_ -> !null" )
    protected final synchronized String joinWithAstrix ( @lombok.NonNull final Object value ) {
        return value instanceof Date ? "'" + ( (Date) value ).toInstant() + "'" : "$$" + value + "$$";
    }

    @lombok.NonNull
    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_ -> !null" )
    protected final synchronized Date convertDate( @lombok.NonNull final String s ) {
        try {
            final String[] words = s.split( SPACE );

            return new SimpleDateFormat(
                    words[3].length() == 4
                            ? "EEE MMM dd yyyy kk:mm:ss"
                            : "EEE MMM dd kk:mm:ss",
                    Locale.US
            ).parse(
                    words[3].length() >= 4
                            ? String.join(
                            SPACE,
                            words[0],
                            words[1],
                            words[2],
                            words[3],
                            words[4]
                    )
                            : String.join( SPACE, words[0], words[1], words[2], words[3] )
            );
        } catch ( final ParseException e ) {
            throw new RuntimeException( e );
        } finally {
            System.gc();
        }
    }
}
