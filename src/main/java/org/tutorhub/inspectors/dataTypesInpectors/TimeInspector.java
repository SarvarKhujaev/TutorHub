package org.tutorhub.inspectors.dataTypesInpectors;

import org.tutorhub.annotations.entity.constructor.EntityConstructorAnnotation;

import org.tutorhub.inspectors.DataValidateInspector;
import org.tutorhub.inspectors.AnnotationInspector;
import org.tutorhub.inspectors.Archive;

import java.time.format.DateTimeFormatter;
import java.time.Duration;
import java.time.Instant;
import java.time.Month;
import java.time.Year;

import java.util.Calendar;
import java.util.Locale;
import java.util.Date;

@org.tutorhub.annotations.services.ImmutableEntityAnnotation
public class TimeInspector extends Archive {
    protected TimeInspector () {
        super( TimeInspector.class );
    }

    @EntityConstructorAnnotation(
            permission = DataValidateInspector.class
    )
    protected <T extends UuidInspector> TimeInspector ( @lombok.NonNull final Class<T> instance ) {
        super( TimeInspector.class );

        AnnotationInspector.checkCallerPermission( instance, TimeInspector.class );
        AnnotationInspector.checkAnnotationIsImmutable( TimeInspector.class );
    }

    @SuppressWarnings( value = "хранит длительность суток в секундах" )
    public final static int DAY_IN_SECOND = 86400;
    @SuppressWarnings( value = "хранит длительность валидности токена" )
    protected final static long EXPIRATION_DATE = 86400 * 3 * 1000;

    @SuppressWarnings(
            value = """
                    хранит длительность в милисекундах
                    используется для проведения юнит тестов
                    чтобы устанавливать максимальное время за которое
                    метод или функция должна обработать запрос
                    """
    )
    public final static Duration DURATION = Duration.ofMillis( 100 );
    private final static Calendar calendar = Calendar.getInstance();

    private final static DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern( "EEE MMM d H:mm:ss zzz yyyy", Locale.ENGLISH );

    @lombok.NonNull
    @lombok.Synchronized
    public static synchronized Date newDate () {
        return new Date();
    }

    @lombok.NonNull
    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_ -> !null" )
    public static synchronized Date newDate ( final long interval ) {
        return new Date( interval );
    }

    @SuppressWarnings( value = "возвращает данные о дате о начале года или конце" )
    @lombok.NonNull
    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_ -> !null" )
    protected final synchronized Date getYearStartOrEnd ( final boolean flag ) {
        calendar.set( flag ? Calendar.YEAR : Calendar.MONTH, flag ? Year.now().getValue() : 11 );
        calendar.set( flag ? Calendar.DAY_OF_YEAR : Calendar.DAY_OF_MONTH, flag ? 1 : 31 );

        return calendar.getTime();
    }

    @lombok.NonNull
    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_ -> !null" )
    protected final synchronized Month getMonthName ( @lombok.NonNull final Date date ) {
        return Month.of( date.getMonth() + 1 );
    }

    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_, _ -> _" )
    protected final synchronized long getTimeDifference (
            @lombok.NonNull final Instant instant,
            final int integer
    ) {
        final Duration duration = Duration.between( Instant.now(), instant );
        return Math.abs(
                switch ( integer ) {
                    case 1 -> duration.toHours();
                    case 2 -> duration.toMinutes();
                    default -> duration.toSeconds();
                }
        );
    }

    @lombok.NonNull
    @lombok.Synchronized
    protected final synchronized Date getExpirationDate () {
        return new Date( newDate().getTime() + EXPIRATION_DATE );
    }
}
