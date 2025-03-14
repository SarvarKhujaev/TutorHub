package org.tutorhub.entities.homework;

import org.tutorhub.annotations.entity.constructor.EntityConstructorAnnotation;
import org.tutorhub.annotations.entity.fields.WeakReferenceAnnotation;
import org.tutorhub.annotations.entity.object.EntityAnnotations;

import org.tutorhub.constans.postgres_constants.PostgreSqlFunctions;
import org.tutorhub.constans.postgres_constants.PostgreSqlSchema;
import org.tutorhub.constans.postgres_constants.PostgreSqlTables;
import org.tutorhub.constans.entities_constants.ErrorMessages;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import org.tutorhub.inspectors.dataTypesInpectors.StringOperations;
import org.tutorhub.inspectors.dataTypesInpectors.TimeInspector;

import org.tutorhub.inspectors.CollectionsInspector;
import org.tutorhub.inspectors.AnnotationInspector;

import org.tutorhub.interfaces.database.EntityToPostgresConverter;
import org.tutorhub.entities.lesson.Lesson;

import jakarta.validation.constraints.NotNull;
import jakarta.persistence.*;

import org.hibernate.annotations.Immutable;
import java.util.Date;
import java.util.List;

@Entity( name = PostgreSqlTables.HOMEWORK )
@Table(
        name = PostgreSqlTables.HOMEWORK,
        schema = PostgreSqlSchema.ENTITIES
)
@EntityAnnotations(
        name = PostgreSqlTables.HOMEWORK,
        tableName = PostgreSqlTables.HOMEWORK,
        keysapceName = PostgreSqlSchema.ENTITIES,

        comment = "дом. задание которое прикрепляется к каждому уроку"
)
public final class Homework implements EntityToPostgresConverter {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private long id;

    @Immutable
    @NotNull( message = ErrorMessages.NULL_VALUE )
    @Column(
            name = "created_date",
            nullable = false,
            updatable = false,
            columnDefinition = PostgreSqlFunctions.NOW
    )
    private final Date createdDate = TimeInspector.newDate();

    @SuppressWarnings( value = "урок к которому прикреплено дом. задание" )
    @Immutable
    @NotNull( message = ErrorMessages.NULL_VALUE )
    @OneToOne(
            targetEntity = Lesson.class,
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY
    )
    @WeakReferenceAnnotation( name = PostgreSqlTables.HOMEWORK + "_lesson", isCollection = false )
    private Lesson lesson;

    @NotNull( message = ErrorMessages.NULL_VALUE )
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.REFRESH,
            targetEntity = SolvedHomework.class,
            orphanRemoval = true
    )
    @JoinTable(
            name = PostgreSqlTables.HOMEWORK + PostgreSqlTables.SOLVED_HOMEWORKS,
            schema = PostgreSqlSchema.ENTITIES,
            joinColumns = @JoinColumn(
                    name = PostgreSqlTables.HOMEWORK + StringOperations.ENTITY_ID,
                    table = PostgreSqlTables.HOMEWORK,
                    nullable = false
            ),
            inverseJoinColumns = @JoinColumn(
                    name = PostgreSqlTables.SOLVED_HOMEWORKS + StringOperations.ENTITY_ID,
                    table = PostgreSqlTables.SOLVED_HOMEWORKS,
                    nullable = false
            )
    )
    @OrderBy( value = "id ASC, createdDate DESC" )
    @org.hibernate.annotations.Cache( usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )
    @WeakReferenceAnnotation( name = PostgreSqlTables.HOMEWORK + "_solvedHomeworkList" )
    private final List< SolvedHomework > solvedHomeworkList = CollectionsInspector.emptyList();

    public Homework () {}

    @EntityConstructorAnnotation
    public Homework ( @lombok.NonNull final Class<?> instance ) {
        AnnotationInspector.checkCallerPermission( instance, Homework.class );
    }
}
