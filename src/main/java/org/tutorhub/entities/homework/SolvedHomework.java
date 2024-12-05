package org.tutorhub.entities.homework;

import org.tutorhub.annotations.entity.constructor.EntityConstructorAnnotation;
import org.tutorhub.annotations.entity.fields.WeakReferenceAnnotation;
import org.tutorhub.annotations.entity.object.EntityAnnotations;

import org.tutorhub.constans.postgres_constants.PostgreSqlFunctions;
import org.tutorhub.constans.postgres_constants.PostgreSqlSchema;
import org.tutorhub.constans.postgres_constants.PostgreSqlTables;
import org.tutorhub.constans.entities_constants.ErrorMessages;

import org.tutorhub.interfaces.database.EntityToPostgresConverter;

import org.tutorhub.entities.markAndRating.HomeworkMark;
import org.tutorhub.entities.student.Student;

import org.tutorhub.inspectors.dataTypesInpectors.TimeInspector;
import org.tutorhub.inspectors.AnnotationInspector;

import jakarta.validation.constraints.NotNull;
import jakarta.persistence.*;

import org.hibernate.annotations.Immutable;
import java.util.Date;

@Entity( name = PostgreSqlTables.SOLVED_HOMEWORKS )
@Table(
        name = PostgreSqlTables.SOLVED_HOMEWORKS,
        schema = PostgreSqlSchema.ENTITIES
)
@EntityAnnotations(
        name = PostgreSqlTables.SOLVED_HOMEWORKS,
        tableName = PostgreSqlTables.SOLVED_HOMEWORKS,
        keysapceName = PostgreSqlSchema.ENTITIES,

        comment = "содержит решение дом. задания"
)
public final class SolvedHomework implements EntityToPostgresConverter {
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

    @SuppressWarnings( value = "студент который выполнил дом. задание" )
    @NotNull( message = ErrorMessages.NULL_VALUE )
    @OneToOne(
            targetEntity = Student.class,
            mappedBy = PostgreSqlTables.SOLVED_HOMEWORKS,
            cascade = CascadeType.REMOVE,
            fetch = FetchType.EAGER
    )
    @WeakReferenceAnnotation( name = PostgreSqlTables.SOLVED_HOMEWORKS + "_student", isCollection = false )
    private Student student;

    @OneToOne(
            targetEntity = HomeworkMark.class,
            mappedBy = PostgreSqlTables.SOLVED_HOMEWORKS,
            cascade = CascadeType.REMOVE,
            fetch = FetchType.EAGER
    )
    @WeakReferenceAnnotation( name = PostgreSqlTables.SOLVED_HOMEWORKS + "_homework_mark", isCollection = false )
    private HomeworkMark homeworkMark;

    public SolvedHomework () {}

    @EntityConstructorAnnotation
    public SolvedHomework ( @lombok.NonNull final Class<?> instance ) {
        AnnotationInspector.checkCallerPermission( instance, SolvedHomework.class );
    }
}
