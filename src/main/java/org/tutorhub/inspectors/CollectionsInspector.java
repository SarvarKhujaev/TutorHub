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
    protected static <T> Set<T> emptySet () {
        return UnmodifiableSet.unmodifiableSet( Collections.EMPTY_SET );
    }

    @lombok.NonNull
    public static <T> List<T> emptyList () {
        return new UnmodifiableList<T>( Collections.EMPTY_LIST );
    }

    @lombok.NonNull
    public static <T> CopyOnWriteArrayList<T> newList () {
        return new CopyOnWriteArrayList<>();
    }

    @lombok.NonNull
    protected final <T> ArrayList<T> newList ( final int listSize ) {
        return new ArrayList<>( listSize );
    }

    @SafeVarargs
    @lombok.NonNull
    @org.jetbrains.annotations.Contract( value = "_ -> !null" )
    protected final <T> List<T> newList (
            @lombok.NonNull final T ... objects
    ) {
        return UnmodifiableList.unmodifiableList( List.of( objects ) );
    }

    @lombok.NonNull
    @org.jetbrains.annotations.Contract( value = "_, _ -> !null" )
    protected final <T> List<T> newList (
            final int duplicatesNumber,
            @lombok.NonNull final T object
    ) {
        return IntStream
                .range( 0, duplicatesNumber )
                .mapToObj( value -> object )
                .collect( Collectors.toList() );
    }

    @lombok.NonNull
    protected final <T> Vector<T> newVector ( final int listSize ) {
        return new Vector<>( listSize );
    }

    @lombok.NonNull
    protected final <T, V> TreeMap<T, V> newTreeMap () {
        return new TreeMap<>( Comparator.comparing( Objects::nonNull ) );
    }

    @lombok.NonNull
    protected static <T, V> WeakHashMap<T, V> newMap () {
        return new WeakHashMap<>( 1 );
    }

    @org.jetbrains.annotations.Contract( value = "_, _ -> _" )
    public final <A, B, C> boolean checkCollectionsLengthEquality(
            @lombok.NonNull final Map<A, B> firstCollection,
            @lombok.NonNull final Collection<C> secondCollection
    ) {
        return firstCollection.size() == secondCollection.size();
    }

    @SuppressWarnings( value = "получает коллекцию и логику описывающую поведение для элементов коллекции" )
    protected final <T> void analyze (
            @lombok.NonNull final Collection<T> someList,
            @lombok.NonNull final Consumer<T> someConsumer
    ) {
        someList.forEach( someConsumer );
    }

    protected static <T> void analyze (
            @lombok.NonNull final Stream<T> someList,
            @lombok.NonNull final Consumer<T> someConsumer
    ) {
        someList.forEach( someConsumer );
    }

    protected final <T> void analyze (
            @lombok.NonNull final T[] someArray,
            @lombok.NonNull final Consumer<T> someConsumer
    ) {
        convertArrayToStream( someArray ).forEach( someConsumer );
    }

    protected final <T> void analyze (
            @lombok.NonNull final T[] someArray,
            @lombok.NonNull final Predicate<T> somePredicate,
            @lombok.NonNull final Consumer<T> someConsumer
    ) {
        convertArrayToStream( someArray ).filter( somePredicate ).forEach( someConsumer );
    }

    protected final <T, V> void analyze (
            @lombok.NonNull final Map< T, V > someMap,
            @lombok.NonNull final BiConsumer<T, V> someConsumer
    ) {
        someMap.forEach( someConsumer );
    }

    protected final <T> void analyze (
            @lombok.NonNull final Iterator<T> iterator,
            @lombok.NonNull final Consumer<T> someConsumer,
            @lombok.NonNull final Supplier< Boolean > someSupplier
    ) {
        while ( iterator.hasNext() && someSupplier.get() ) {
            someConsumer.accept( iterator.next() );
        }
    }

    @org.jetbrains.annotations.Contract( value = "_ -> true" )
    public static <T> boolean isCollectionNotEmpty (
            final Collection<T> collection
    ) {
        return collection != null && !collection.isEmpty();
    }

    @org.jetbrains.annotations.Contract( value = "_ -> true" )
    public static <T, U> boolean isCollectionNotEmpty (
            final Map<T, U> map
    ) {
        return map != null && !map.isEmpty();
    }

    public static <T> void checkAndClear (
            final Collection<T> collection
    ) {
        if ( isCollectionNotEmpty( collection ) ) collection.clear();
    }

    public static <T, U> void checkAndClear (
            final Map<T, U> map
    ) {
        if ( isCollectionNotEmpty( map ) ) map.clear();
    }

    @lombok.NonNull
    @org.jetbrains.annotations.Contract( value = "_ -> _" )
    protected static <T> List<T> convertArrayToList (
            @lombok.NonNull final T[] objects
    ) {
        return Arrays.asList( objects );
    }

    @lombok.NonNull
    @org.jetbrains.annotations.Contract( value = "_ -> _" )
    protected static <T> Stream<T> convertArrayToStream (
            @lombok.NonNull final T[] objects
    ) {
        return Stream.of( objects );
    }

    @lombok.NonNull
    @org.jetbrains.annotations.Contract( value = "_ -> _" )
    protected final Map< String, Object > getMap (
            @lombok.NonNull final String key
    ) {
        return UnmodifiableMap.unmodifiableMap( Map.of( "message", key ) );
    }

    @lombok.NonNull
    @org.jetbrains.annotations.Contract( value = "_, _ -> _" )
    protected final Map< String, Object > getMap (
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
    @org.jetbrains.annotations.Contract( value = "_, _, _ -> _" )
    protected final <T, V> Map< String, Object > getMap (
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
