package org.tutorhub.inspectors;

import org.tutorhub.annotations.entity.constructor.EntityConstructorAnnotation;
import org.tutorhub.annotations.entity.methods.MethodsAnnotations;
import org.tutorhub.annotations.entity.object.EntityAnnotations;

import org.tutorhub.annotations.entity.fields.WeakReferenceAnnotation;
import org.tutorhub.annotations.entity.fields.FieldAnnotation;

import org.tutorhub.annotations.services.ImmutableEntityAnnotation;
import org.tutorhub.annotations.services.ServiceParametrAnnotation;

import org.tutorhub.annotations.sqlInjection.SqlInjectionDanger;
import org.tutorhub.annotations.kafka.KafkaEntityAnnotation;

import org.tutorhub.inspectors.enttiesInspectors.CustomServiceCleaner;
import org.tutorhub.inspectors.securityInspectors.TokenInspector;

import org.tutorhub.inspectors.dataTypesInpectors.StringOperations;
import org.tutorhub.inspectors.dataTypesInpectors.UuidInspector;


import org.tutorhub.interfaces.database.EntityToPostgresConverter;
import org.tutorhub.interfaces.kafka.KafkaEntitiesCommonMethods;
import org.tutorhub.interfaces.services.ServiceCommonMethods;

import org.tutorhub.constans.kafka.KafkaTopics;
import org.tutorhub.constans.errors.Errors;

import org.apache.commons.lang3.Validate;
import java.lang.ref.WeakReference;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Field;

import java.util.stream.Stream;
import java.util.*;

@org.tutorhub.annotations.services.ImmutableEntityAnnotation
public class AnnotationInspector extends CustomServiceCleaner {
    protected static volatile WeakReferenceAnnotation weakReferenceAnnotation;
    protected static volatile MethodsAnnotations methodsAnnotations;
    protected static volatile FieldAnnotation fieldAnnotation;

    protected AnnotationInspector () {
        super( AnnotationInspector.class );
    }

    @EntityConstructorAnnotation(
            permission = {
                    TokenInspector.class
            }
    )
    protected <T extends UuidInspector> AnnotationInspector ( @lombok.NonNull final Class<T> instance ) {
        super( AnnotationInspector.class );

        checkCallerPermission( instance, AnnotationInspector.class );
        checkAnnotationIsImmutable( AnnotationInspector.class );
    }

    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_ -> true" )
    @SuppressWarnings(
            value = """
                    проверяет что параметр помечен как содержащий Sql инъекцию
                    """
    )
    public static synchronized boolean isFieldSqlDangerous (
            @lombok.NonNull final Field field
    ) {
        return field.isAnnotationPresent( SqlInjectionDanger.class );
    }

    @lombok.NonNull
    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_ -> !null" )
    @SuppressWarnings(
            value = """
                    проверяет сущность и возвращет название Кафка топика для него
                    """
    )
    public static synchronized <T extends KafkaEntitiesCommonMethods> KafkaTopics getKafkaTopicName (
            @lombok.NonNull final T entity
    ) {
        Validate.isTrue(
                entity.getClass().isAnnotationPresent( KafkaEntityAnnotation.class )
        );

        return entity.getClass().getAnnotation( KafkaEntityAnnotation.class ).topicName();
    }

    @SuppressWarnings(
            value = """
                    принимает экземпляр класса
                    и возвращает название таблицы или пронстранства сущности
                    """
    )
    @lombok.NonNull
    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_, _ -> !null" )
    public static synchronized <T extends EntityToPostgresConverter> String getEntityKeyspaceOrTableName (
            @lombok.NonNull final T entity,
            final boolean isKeyspaceName
    ) {
        if ( entity.getClass().isAnnotationPresent( EntityAnnotations.class ) ) {
            return isKeyspaceName
                    ? convertEntityToEntityAnnotation( entity ).keysapceName()
                    : convertEntityToEntityAnnotation( entity ).tableName();
        }

        throw new IllegalArgumentException(
                Errors.WRONG_TYPE_IN_ANNOTATION.translate( "ru", entity.getClass().getName() )
        );
    }

    @SuppressWarnings(
            value = """
                    Принимает класс и возвращает его экземпляры классов,
                    у которых есть доступ к конструктору вызванного объекта

                    Проверяет что у метода есть нужная аннотация
                    В случае ошибки вызывает Exception с подходящим сообщением
                    """
    )
    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_, _ -> fail" )
    public static synchronized <T, U> void checkCallerPermission (
            // класс который обращается
            @lombok.NonNull final Class<T> callerInstance,
            // класс к которому обращаются
            @lombok.NonNull final Class<U> calledInstance
    ) {
        Validate.notNull( callerInstance, StringOperations.NULL_VALUE_IN_ASSERT );
        Validate.notNull( calledInstance, StringOperations.NULL_VALUE_IN_ASSERT );

        try {
            final Constructor<U> declaredConstructor = calledInstance.getDeclaredConstructor( Class.class );
            org.springframework.util.ReflectionUtils.makeAccessible( declaredConstructor );
            declaredConstructor.setAccessible( true );

            Validate.isTrue(
                    (
                        declaredConstructor.isAnnotationPresent( EntityConstructorAnnotation.class )
                        && declaredConstructor.getParameters().length == 1
                        && Collections.frequency(
                                convertArrayToList(
                                        declaredConstructor
                                                .getAnnotation( EntityConstructorAnnotation.class )
                                                .permission()
                                ),
                                callerInstance
                        ) > 0
                    ),
                    Errors.OBJECT_IS_OUT_OF_INSTANCE_PERMISSION.translate(
                            callerInstance.getName(),
                            calledInstance.getName()
                    )
            );
        } catch ( final NoSuchMethodException e ) {
            throw new RuntimeException(e);
        }
    }

    @lombok.NonNull
    @lombok.Synchronized
    @SuppressWarnings(
            value = """
                    1) проверяет что у класса есть аннотация ServiceParametrAnnotation
                    2) получает название под группы из application.yml
                    3) проверяет что в application.yml есть такая переменная
                    4) если переменная есть, то возвращает его
                    5) иначе возвращается ошибка
                    """
    )
    @org.jetbrains.annotations.Contract( value = "_, _ -> !null" )
    public static synchronized String getVariable (
            @lombok.NonNull final Class< ? > object,
            @lombok.NonNull final String paramName
    ) {
        Validate.isTrue(
                checkString( paramName ),
                NULL_VALUE_IN_ASSERT
        );

        Validate.isTrue(
                object.isAnnotationPresent( ServiceParametrAnnotation.class ),
                Errors.WRONG_TYPE_IN_ANNOTATION.translate( "ru", object.getName() )
        );

        final ServiceParametrAnnotation serviceParametrAnnotation = object.getAnnotation( ServiceParametrAnnotation.class );

        return checkContextOrReturnDefaultValue(
                String.join(
                        DOT,
                        serviceParametrAnnotation.mainGroupName(),
                        serviceParametrAnnotation.propertyGroupName(),
                        paramName
                ),
                Errors.DATA_NOT_FOUND.translate(
                        "ru",
                        String.join(
                                DOT,
                                serviceParametrAnnotation.mainGroupName(),
                                serviceParametrAnnotation.propertyGroupName(),
                                paramName
                        )
                )
        );
    }

    @lombok.NonNull
    @lombok.Synchronized
    @SuppressWarnings(
            value = """
                    1) проверяет что у класса есть аннотация ServiceParametrAnnotation
                    2) получает название под группы из application.yml
                    3) проверяет что в application.yml есть такая переменная
                    4) если переменная есть, то возвращает его
                    5) иначе возвращается ошибка
                    """
    )
    @org.jetbrains.annotations.Contract( value = "_, _, _ -> !null" )
    public static synchronized String getVariable (
            @lombok.NonNull final Class< ? > object,
            @lombok.NonNull final String defaultValue,
            final int index
    ) {
        Validate.isTrue(
                checkString( defaultValue ),
                NULL_VALUE_IN_ASSERT
        );

        Validate.isTrue( index >= 0 && object.getDeclaredFields().length > index );

        Validate.isTrue(
                object.isAnnotationPresent( ServiceParametrAnnotation.class ),
                Errors.WRONG_TYPE_IN_ANNOTATION.translate( "ru", object.getName() )
        );

        final ServiceParametrAnnotation serviceParametrAnnotation = object.getAnnotation( ServiceParametrAnnotation.class );

        return checkContextOrReturnDefaultValue(
                String.join(
                        DOT,
                        serviceParametrAnnotation.mainGroupName(),
                        serviceParametrAnnotation.propertyGroupName(),
                        object.getDeclaredFields()[ index ].getName()
                ),
                defaultValue
        );
    }

    @lombok.Synchronized
    @SuppressWarnings(
            value = """
                    1) проверяет что у класса есть аннотация ServiceParametrAnnotation
                    2) получает название под группы из application.yml
                    3) проверяет что в application.yml есть такая переменная
                    4) если переменная есть, то возвращает его
                    5) иначе возвращается ошибка
                    """
    )
    public static synchronized int getVariable (
            @lombok.NonNull final Class< ? > object,
            final int defaultValue,
            final int index
    ) {
        Validate.isTrue( index >= 0 && object.getDeclaredFields().length > index );

        Validate.isTrue(
                object.isAnnotationPresent( ServiceParametrAnnotation.class ),
                Errors.WRONG_TYPE_IN_ANNOTATION.translate( "ru", object.getName() )
        );

        final ServiceParametrAnnotation serviceParametrAnnotation = object.getAnnotation( ServiceParametrAnnotation.class );

        return checkContextOrReturnDefaultValue(
                String.join(
                        DOT,
                        serviceParametrAnnotation.mainGroupName(),
                        serviceParametrAnnotation.propertyGroupName(),
                        object.getDeclaredFields()[ index ].getName()
                ),
                defaultValue
        );
    }

    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_ -> _" )
    protected final synchronized boolean checkAnnotations (
            @lombok.NonNull final Object object
    ) {
        return object.getClass().isAnnotationPresent( EntityAnnotations.class )
                && object.getClass().getAnnotation( EntityAnnotations.class ).canTouch()
                && object.getClass().getAnnotation( EntityAnnotations.class ).isReadable();
    }

    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_ -> fail" )
    protected static synchronized boolean checkAnnotations (
            @lombok.NonNull final Method method
    ) {
        return checkAnnotationInitialized( method )
                && method.getAnnotation( MethodsAnnotations.class ).canTouch()
                && !method.getAnnotation( MethodsAnnotations.class ).withoutParams()
                && !method.getAnnotation( MethodsAnnotations.class ).isReturnEntity();
    }

    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_ -> fail" )
    protected final synchronized boolean checkAnnotations (
            @lombok.NonNull final Field field
    ) {
        return field.isAnnotationPresent( FieldAnnotation.class )
                && field.getAnnotation( FieldAnnotation.class ).canTouch()
                && field.getAnnotation( FieldAnnotation.class ).isReadable();
    }

    @lombok.Synchronized
    protected static synchronized void set (
            @lombok.NonNull final Field field
    ) {
        org.springframework.util.ReflectionUtils.makeAccessible( field );
        fieldAnnotation = field.getAnnotation( FieldAnnotation.class );
    }

    @lombok.Synchronized
    protected static synchronized void set (
            @lombok.NonNull final Method method
    ) {
        methodsAnnotations = method.getAnnotation( MethodsAnnotations.class );
    }

    @lombok.NonNull
    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_ -> !null" )
    protected static synchronized < T extends EntityToPostgresConverter> EntityAnnotations convertEntityToEntityAnnotation (
            @lombok.NonNull final T entity
    ) {
        checkAnnotationInitialized( entity.getClass() );
        return entity.getClass().getAnnotation( EntityAnnotations.class );
    }

    @lombok.NonNull
    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_ -> fail" )
    protected static synchronized FieldAnnotation convertFieldToFieldAnnotation (
            @lombok.NonNull final Field field
    ) {
        checkAnnotationInitialized( field );
        return field.getAnnotation( FieldAnnotation.class );
    }

    @lombok.NonNull
    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_ -> !null" )
    protected static synchronized MethodsAnnotations convertMethodToMethodAnnotation(
            @lombok.NonNull final Method method
    ) {
        checkAnnotationInitialized( method );
        return method.getAnnotation( MethodsAnnotations.class );
    }

    @SuppressWarnings(
            value = """
                    Принимает любой Object и проверяет не является ли он Immutable
                    если все хорошо, то возвращает сам Object
                    """
    )
    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_ -> fail" )
    protected static synchronized < T extends EntityToPostgresConverter> T checkAnnotationIsNotImmutable (
            @lombok.NonNull final T object
    ) {
        Validate.isTrue(
                !object.getClass().isAnnotationPresent( ImmutableEntityAnnotation.class ),
                Errors.OBJECT_IS_IMMUTABLE.translate( object.getClass().getName() )
        );

        return object;
    }

    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_ -> fail" )
    public static synchronized < T extends UuidInspector > void checkAnnotationIsImmutable (
            @lombok.NonNull final Class<T> object
    ) {
        Validate.isTrue(
                object.isAnnotationPresent( ImmutableEntityAnnotation.class ),
                Errors.OBJECT_IS_IMMUTABLE.translate( object.getName() )
        );
    }

    @SuppressWarnings(
            value = """
                    Принимает любой Object и проверяет есть ли у него аннотация EntityAnnotations
                    """
    )
    @lombok.Synchronized
    protected static synchronized void checkAnnotationInitialized (
            @lombok.NonNull final Class< ? extends EntityToPostgresConverter> object
    ) {
        Validate.isTrue(
                object.isAnnotationPresent( EntityAnnotations.class ),
                Errors.WRONG_TYPE_IN_ANNOTATION.translate( "ru", object.getName() )
        );
    }

    @SuppressWarnings(
            value = """
                    Проверяет есть ли у метода класса аннотация MethodsAnnotations
                    """
    )
    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_ -> _" )
    protected static synchronized boolean checkAnnotationInitialized (
            @lombok.NonNull final Method method
    ) {
        return method.isAnnotationPresent( MethodsAnnotations.class );
    }

    @SuppressWarnings(
            value = """
                    Проверяет есть ли у параметра класса аннотация MethodsAnnotations
                    """
    )
    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_ -> fail" )
    private static synchronized void checkAnnotationInitialized (
            @lombok.NonNull final Field field
    ) {
        Validate.isTrue(
                field.isAnnotationPresent( FieldAnnotation.class ),
                Errors.WRONG_TYPE_IN_ANNOTATION.translate( field.getName() )
        );
    }

    @SuppressWarnings(
            value = """
                    Принимает метод, класс которому он принадледит и возвращает его значение
                    Проверяет что у метода есть нужная аннотация
                    В случае ошибки вызывает Exceptio с подходящим сообщением
                    """
    )
    @lombok.NonNull
    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_, _ -> !null" )
    protected static synchronized <T extends EntityToPostgresConverter> Object getMethodReturnValue(
            @lombok.NonNull final Method method,
            @lombok.NonNull final T object
    ) {
        try {
            checkAnnotationInitialized( object.getClass() );

            Validate.isTrue(
                    !checkAnnotationInitialized( method ),
                    Errors.WRONG_TYPE_IN_ANNOTATION.translate( method.getName() )
            );

            return method.invoke( object );
        } catch ( final IllegalAccessException | InvocationTargetException e ) {
            return e;
        }
    }

    @SuppressWarnings(
            value = """
                    Принимает метод, класс которому он принадледит и возвращает его значение
                    Проверяет что у метода есть нужная аннотация
                    В случае ошибки вызывает Exceptio с подходящим сообщением
                    """
    )
    @lombok.NonNull
    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_, _ -> fail" )
    protected static synchronized <T extends EntityToPostgresConverter> Object getFieldReturnValue(
            @lombok.NonNull final Field field,
            @lombok.NonNull final T object
    ) {
        try {
            org.springframework.util.ReflectionUtils.makeAccessible( field );
            checkAnnotationInitialized( object.getClass() );
            checkAnnotationInitialized( field );
            return field.get( object );
        } catch ( final IllegalAccessException e ) {
            throw new IllegalArgumentException( e );
        }
    }

    @lombok.Synchronized
    protected static synchronized <T extends EntityToPostgresConverter> void compareFieldWithParam (
            @lombok.NonNull final Field field,
            @lombok.NonNull final T entity
    ) {
        set( field );
        Validate.isTrue(
                field.getName().compareTo( fieldAnnotation.name() ) == 0,
                Errors.FIELD_AND_ANNOTATION_NAME_MISMATCH.translate(
                        entity.getClass().getName(),
                        field.getName(),
                        fieldAnnotation.name()
                )
        );
    }

    @SuppressWarnings(
            value = """
                    Принимает экземпляр класса и возвращает список всех его параметров
                    """
    )
    @lombok.NonNull
    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_ -> !null" )
    protected static synchronized Stream< Field > getFields (
            @lombok.NonNull final Class< ? > object
    ) {
        return convertArrayToStream( object.getDeclaredFields() );
    }

    @SuppressWarnings(
            value = """
                    Принимает экземпляр класса и возвращает список всех его методов
                    """
    )
    @lombok.NonNull
    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_ -> !null" )
    protected static synchronized Stream< Method > getMethods (
            @lombok.NonNull final Class< ? > object
    ) {
        return convertArrayToStream( object.getDeclaredMethods() );
    }

    @SuppressWarnings(
            value = """
                    Проверяет содержит ли параметр класса,
                    экземпляр класса имплементирующего интерфейс ServiceCommonMethods
                    """
    )
    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_ -> true" )
    private static synchronized boolean iSFieldMustBeCleaned (
            @lombok.NonNull final Field field
    ) {
        return field.getClass().getInterfaces().length == 1
                && field.getClass().getInterfaces()[0].isAssignableFrom( ServiceCommonMethods.class );
    }

    @SuppressWarnings(
            value = """
                    берет все статичные ссылки на объекты из сборника EntitiesInstances
                    и очищает каждый объект через вызов метода close из интерфейса ServiceCommonMethods
                    """
    )
    protected <T extends ServiceCommonMethods> void clearEntity ( @lombok.NonNull final T entity ) {
        super.analyze(
                getFields( entity.getClass() )
                        .filter( field -> field.isAnnotationPresent( WeakReferenceAnnotation.class ) )
                        .toList(),
                field -> {
                    try {
                        org.springframework.util.ReflectionUtils.makeAccessible( field );
                        weakReferenceAnnotation = field.getAnnotation( WeakReferenceAnnotation.class );

                        super.logging( "Clearing: " + weakReferenceAnnotation.name() );

                        Validate.isTrue(
                                field.getName().compareTo( weakReferenceAnnotation.name() ) == 0,
                                Errors.FIELD_AND_ANNOTATION_NAME_MISMATCH.translate(
                                        entity.getClass().getName(),
                                        field.getName(),
                                        weakReferenceAnnotation.name()
                                )
                        );

                        if ( weakReferenceAnnotation.isCollection() ) {
                            checkAndClear( ( (Collection<?>) field.get( entity ) ) );
                        } else if ( weakReferenceAnnotation.isWeak() ) {
                            clearReference( ( (WeakReference<?>) field.get( entity ) ) );
                        } else if ( weakReferenceAnnotation.isMap() ) {
                            checkAndClear( ( (Map<?, ?>) field.get( entity ) ) );
                        }

                        /*
                        если параметр является ссылкой на другой объект,
                        то просто стираем его через обозначение null
                        */
                        else {
                            if ( iSFieldMustBeCleaned( field ) ) {
                                super.clearReference( entity );
                            }

                            field.set( entity, null );
                        }
                    } catch ( final IllegalAccessException e ) {
                        super.logging( e );
                    }
                }
        );
    }
}
