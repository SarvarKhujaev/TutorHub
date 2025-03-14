package org.tutorhub.entities.studyCenter.statistics;

import org.tutorhub.annotations.entity.constructor.EntityConstructorAnnotation;
import org.tutorhub.annotations.entity.fields.WeakReferenceAnnotation;
import org.tutorhub.annotations.entity.object.EntityAnnotations;

import org.tutorhub.constans.postgres_constants.PostgreSqlFunctions;
import org.tutorhub.constans.postgres_constants.PostgreSqlSchema;
import org.tutorhub.constans.postgres_constants.PostgreSqlTables;
import org.tutorhub.constans.entities_constants.ErrorMessages;

import org.tutorhub.interfaces.database.EntityToPostgresConverter;

import org.tutorhub.inspectors.dataTypesInpectors.TimeInspector;
import org.tutorhub.inspectors.AnnotationInspector;

import org.tutorhub.entities.studyCenter.StudyCenter;
import org.tutorhub.entities.student.Student;

import org.hibernate.annotations.PartitionKey;
import org.hibernate.annotations.Immutable;

import jakarta.validation.constraints.*;
import jakarta.persistence.*;

import java.util.Date;

@Entity( name = PostgreSqlTables.STUDENT_STUDY_INTERVAL )
@Table(
        name = PostgreSqlTables.STUDENT_STUDY_INTERVAL,
        schema = PostgreSqlSchema.ENTITIES
)
@EntityAnnotations(
        name = PostgreSqlTables.STUDENT_STUDY_INTERVAL,
        tableName = PostgreSqlTables.STUDENT_STUDY_INTERVAL,
        keysapceName = PostgreSqlSchema.ENTITIES,

        comment = """
                хранит данные о начале и окончании обучения студента в УЦ
                """
)
public final class StudentStudyInterval implements EntityToPostgresConverter {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private long id;

    @NotNull( message = ErrorMessages.NULL_VALUE )
    @Column(
            name = "student_study_ended_date",
            columnDefinition = "TIMESTAMP"
    )
    @SuppressWarnings( value = "дата окончания учебы студента в центре" )
    private Date studentStudyEndedInCenter;

    @Immutable
    @PartitionKey
    @NotNull( message = ErrorMessages.NULL_VALUE )
    @Column(
            name = "student_registration_date",
            nullable = false,
            updatable = false,
            columnDefinition = PostgreSqlFunctions.NOW
    )
    @SuppressWarnings( value = "дата регистрации студента в центре" )
    private final Date studentRegisteredInCenter = TimeInspector.newDate();

    @NotNull( message = ErrorMessages.NULL_VALUE )
    @OneToOne(
            targetEntity = Student.class,
            cascade = CascadeType.PERSIST,
            fetch = FetchType.EAGER
    )
    @WeakReferenceAnnotation( name = PostgreSqlTables.STUDENT_STUDY_INTERVAL + "_student", isCollection = false )
    private Student student;

    @SuppressWarnings( value = "УЦ в котором учился студент" )
    @NotNull( message = ErrorMessages.NULL_VALUE )
    @OneToOne(
            targetEntity = StudyCenter.class,
            cascade = CascadeType.PERSIST,
            fetch = FetchType.EAGER
    )
    @WeakReferenceAnnotation( name = PostgreSqlTables.STUDENT_STUDY_INTERVAL + "_student", isCollection = false )
    private StudyCenter studyCenter;

    public StudentStudyInterval () {}

    @EntityConstructorAnnotation
    public StudentStudyInterval ( @lombok.NonNull final Class<?> instance ) {
        AnnotationInspector.checkCallerPermission( instance, StudentStudyInterval.class );
    }
}
