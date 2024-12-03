package org.tutorhub.inspectors;

import org.tutorhub.annotations.entity.constructor.EntityConstructorAnnotation;
import org.tutorhub.response.Data;

import org.tutorhub.inspectors.dataTypesInpectors.StringOperations;
import org.tutorhub.inspectors.dataTypesInpectors.UuidInspector;

import org.apache.commons.collections4.list.UnmodifiableList;
import org.apache.commons.collections4.map.UnmodifiableMap;
import org.apache.commons.collections4.set.UnmodifiableSet;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.*;

import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.function.Consumer;
import java.util.function.Supplier;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@org.tutorhub.annotations.services.ImmutableEntityAnnotation
public class CollectionsInspector extends UuidInspector {
    protected CollectionsInspector () {
        super( CollectionsInspector.class );
    }

    @EntityConstructorAnnotation( permission = StringOperations.class )
    protected <T extends UuidInspector> CollectionsInspector ( @lombok.NonNull final Class<T> instance ) {
        super( CollectionsInspector.class );

        AnnotationInspector.checkCallerPermission( instance, CollectionsInspector.class );
        AnnotationInspector.checkAnnotationIsImmutable( CollectionsInspector.class );
    }

    @lombok.NonNull
    @lombok.Synchronized
    protected static synchronized <T> Set<T> emptySet () {
        return UnmodifiableSet.unmodifiableSet( Collections.EMPTY_SET );
    }

    @lombok.NonNull
    @lombok.Synchronized
    public static synchronized <T> List<T> emptyList () {
        return new UnmodifiableList<T>( Collections.EMPTY_LIST );
    }

    @lombok.NonNull
    @lombok.Synchronized
    public static synchronized <T> CopyOnWriteArrayList<T> newList () {
        return new CopyOnWriteArrayList<>();
    }

    @lombok.NonNull
    @lombok.Synchronized
    protected final synchronized <T> ArrayList<T> newList ( final int listSize ) {
        return new ArrayList<>( listSize );
    }

    @SafeVarargs
    @lombok.NonNull
    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_ -> !null" )
    protected final synchronized <T> List<T> newList (
            @lombok.NonNull final T ... objects
    ) {
        return UnmodifiableList.unmodifiableList( List.of( objects ) );
    }

    @lombok.NonNull
    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_, _ -> !null" )
    protected final synchronized <T> List<T> newList (
            final int duplicatesNumber,
            @lombok.NonNull final T object
    ) {
        return IntStream
                .range( 0, duplicatesNumber )
                .mapToObj( value -> object )
                .collect( Collectors.toList() );
    }

    @lombok.NonNull
    @lombok.Synchronized
    protected final synchronized <T> Vector<T> newVector ( final int listSize ) {
        return new Vector<>( listSize );
    }

    @lombok.NonNull
    @lombok.Synchronized
    protected final synchronized <T, V> TreeMap<T, V> newTreeMap () {
        return new TreeMap<>( Comparator.comparing( Objects::nonNull ) );
    }

    @lombok.NonNull
    @lombok.Synchronized
    protected static synchronized <T, V> WeakHashMap<T, V> newMap () {
        return new WeakHashMap<>( 1 );
    }

    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_, _ -> _" )
    public final synchronized <A, B, C> boolean checkCollectionsLengthEquality(
            @lombok.NonNull final Map<A, B> firstCollection,
            @lombok.NonNull final Collection<C> secondCollection
    ) {
        return firstCollection.size() == secondCollection.size();
    }

    @SuppressWarnings( value = "получает коллекцию и логику описывающую поведение для элементов коллекции" )
    @lombok.Synchronized
    protected final synchronized <T> void analyze (
            @lombok.NonNull final Collection<T> someList,
            @lombok.NonNull final Consumer<T> someConsumer
    ) {
        someList.forEach( someConsumer );
    }

    @lombok.Synchronized
    protected synchronized static <T> void analyze (
            @lombok.NonNull final Stream<T> someList,
            @lombok.NonNull final Consumer<T> someConsumer
    ) {
        someList.forEach( someConsumer );
    }

    @lombok.Synchronized
    protected final synchronized <T> void analyze (
            @lombok.NonNull final T[] someArray,
            @lombok.NonNull final Consumer<T> someConsumer
    ) {
        convertArrayToStream( someArray ).forEach( someConsumer );
    }

    @lombok.Synchronized
    protected final synchronized <T> void analyze (
            @lombok.NonNull final T[] someArray,
            @lombok.NonNull final Predicate<T> somePredicate,
            @lombok.NonNull final Consumer<T> someConsumer
    ) {
        convertArrayToStream( someArray ).filter( somePredicate ).forEach( someConsumer );
    }

    @lombok.Synchronized
    protected final synchronized <T, V> void analyze (
            @lombok.NonNull final Map< T, V > someMap,
            @lombok.NonNull final BiConsumer<T, V> someConsumer
    ) {
        someMap.forEach( someConsumer );
    }

    @lombok.Synchronized
    protected final synchronized <T> void analyze (
            @lombok.NonNull final Iterator<T> iterator,
            @lombok.NonNull final Consumer<T> someConsumer,
            @lombok.NonNull final Supplier< Boolean > someSupplier
    ) {
        while ( iterator.hasNext() && someSupplier.get() ) {
            someConsumer.accept( iterator.next() );
        }
    }

    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_ -> true" )
    public static synchronized <T> boolean isCollectionNotEmpty (
            final Collection<T> collection
    ) {
        return collection != null && !collection.isEmpty();
    }

    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_ -> true" )
    public static synchronized <T, U> boolean isCollectionNotEmpty (
            final Map<T, U> map
    ) {
        return map != null && !map.isEmpty();
    }

    @lombok.Synchronized
    public static synchronized <T> void checkAndClear (
            final Collection<T> collection
    ) {
        if ( isCollectionNotEmpty( collection ) ) collection.clear();
    }

    @lombok.Synchronized
    public static synchronized <T, U> void checkAndClear (
            final Map<T, U> map
    ) {
        if ( isCollectionNotEmpty( map ) ) map.clear();
    }

    @lombok.NonNull
    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_ -> _" )
    protected static synchronized <T> List<T> convertArrayToList (
            @lombok.NonNull final T[] objects
    ) {
        return Arrays.asList( objects );
    }

    @lombok.NonNull
    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_ -> _" )
    protected static synchronized <T> Stream<T> convertArrayToStream (
            @lombok.NonNull final T[] objects
    ) {
        return Stream.of( objects );
    }

    @lombok.NonNull
    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_ -> _" )
    protected final synchronized Map< String, Object > getMap (
            @lombok.NonNull final String key
    ) {
        return UnmodifiableMap.unmodifiableMap( Map.of( "message", key ) );
    }

    @lombok.NonNull
    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_, _ -> _" )
    protected final synchronized Map< String, Object > getMap (
            @lombok.NonNull final String key,
            final boolean value
    ) {
        return UnmodifiableMap.unmodifiableMap(
                Map.of(
                        "message", key,
                        "success", value
                )
        );
    }

    @lombok.NonNull
    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_, _, _ -> _" )
    protected final synchronized <T, V> Map< String, Object > getMap (
            @lombok.NonNull final String key,
            final boolean value,
            @lombok.NonNull final Data<T, V> data
    ) {
        return UnmodifiableMap.unmodifiableMap(
                Map.of(
                        "success", value,
                        "message", key,
                        "data", data
                )
        );
    }
}
