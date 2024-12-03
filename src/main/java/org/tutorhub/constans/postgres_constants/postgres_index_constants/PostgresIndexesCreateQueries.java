package org.tutorhub.constans.postgres_constants.postgres_index_constants;

import com.university.universityapplication.constans.postgres_constants.PostgreSqlSchema;
import com.university.universityapplication.constans.postgres_constants.PostgreSqlTables;

/*
хранит все QUERY запросы для создания индексов
*/
public final class PostgresIndexesCreateQueries {
    public static final String UNIVERSITY_TEACHER_FIO_INDEX = String.join(
            " ",
            PostgresIndexParams.CREATE_INDEX.formatted(
                    PostgresIndexesNames.UNIVERSITY_TEACHERS_NAME_INDEX.name(),

                    PostgreSqlSchema.UNIVERSITY,
                    PostgreSqlTables.TEACHERS,

                    PostgresIndexes.BTREE,

                    "name %s, surname %s, father_name %s".formatted(
                            PostgresIndexParams.ASC,
                            PostgresIndexParams.ASC,
                            PostgresIndexParams.ASC
                    )
            ),
            ";"
    );

    public static final String UNIVERSITY_TEACHER_EMAIL_INDEX = String.join(
            " ",
            PostgresIndexParams.CREATE_UNIQUE_INDEX.formatted(
                    PostgresIndexesNames.UNIVERSITY_TEACHERS_EMAIL_INDEX.name(),

                    PostgreSqlSchema.UNIVERSITY,
                    PostgreSqlTables.TEACHERS,

                    PostgresIndexes.BTREE,

                    "email %s".formatted(
                            PostgresIndexParams.ASC
                    )
            ),
            ";"
    );

    public static final String UNIVERSITY_STUDENT_FIO_INDEX = String.join(
            " ",
            PostgresIndexParams.CREATE_INDEX.formatted(
                    PostgresIndexesNames.UNIVERSITY_STUDENTS_NAME_INDEX.name(),

                    PostgreSqlSchema.UNIVERSITY,
                    PostgreSqlTables.STUDENTS,

                    PostgresIndexes.BTREE,

                    "name %s".formatted(
                            PostgresIndexParams.ASC
                    )
            ),
            PostgresIndexParams.INCLUDE.formatted(
                    "surname, father_name"
            ),
            ";"
    );

    public static final String UNIVERSITY_STUDENT_EMAIL_INDEX = String.join(
            " ",
            PostgresIndexParams.CREATE_UNIQUE_INDEX.formatted(
                    PostgresIndexesNames.UNIVERSITY_STUDENTS_EMAIL_INDEX.name(),

                    PostgreSqlSchema.UNIVERSITY,
                    PostgreSqlTables.STUDENTS,

                    PostgresIndexes.BTREE,

                    "email %s".formatted(
                            PostgresIndexParams.ASC
                    )
            ),
            ";"
    );

    public static final String UNIVERSITY_GROUPS_GROUP_NAME_INDEX = String.join(
            " ",
            PostgresIndexParams.CREATE_UNIQUE_INDEX.formatted(
                    PostgresIndexesNames.UNIVERSITY_GROUPS_GROUP_NAME_INDEX.name(),

                    PostgreSqlSchema.UNIVERSITY,
                    PostgreSqlTables.GROUPS,

                    PostgresIndexes.BTREE,

                    "group_name %s".formatted(
                            PostgresIndexParams.ASC
                    )
            ),
            ";"
    );

    public static final String UNIVERSITY_COMMENT_MARK_INDEX = String.join(
            " ",
            PostgresIndexParams.CREATE_INDEX.formatted(
                    PostgresIndexesNames.UNIVERSITY_COMMENT_MARK_INDEX.name(),

                    PostgreSqlSchema.UNIVERSITY,
                    PostgreSqlTables.COMMENTS,

                    PostgresIndexes.BTREE,

                    "mark %s".formatted(
                            PostgresIndexParams.ASC
                    )
            ),
            ";"
    );

    public static final String UNIVERSITY_LESSON_LESSON_NAME_INDEX = String.join(
            " ",
            PostgresIndexParams.CREATE_INDEX.formatted(
                    PostgresIndexesNames.UNIVERSITY_LESSON_LESSON_NAME_INDEX.name(),

                    PostgreSqlSchema.UNIVERSITY,
                    PostgreSqlTables.LESSONS,

                    PostgresIndexes.BTREE,

                    "lesson_name %s".formatted(
                            PostgresIndexParams.ASC
                    )
            ),
            ";"
    );

    public static final String UNIVERSITY_LESSON_LESSON_STATUS_INDEX = String.join(
            " ",
            PostgresIndexParams.CREATE_INDEX.formatted(
                    PostgresIndexesNames.UNIVERSITY_LESSON_LESSON_STATUS_INDEX.name(),

                    PostgreSqlSchema.UNIVERSITY,
                    PostgreSqlTables.LESSONS,

                    PostgresIndexes.BTREE,

                    "lesson_status %s".formatted(
                            PostgresIndexParams.ASC
                    )
            ),
            ";"
    );

    public static final String UNIVERSITY_STUDENT_APPEARANCE_IN_LESSONS_TYPES_INDEX = String.join(
            " ",
            PostgresIndexParams.CREATE_INDEX.formatted(
                    PostgresIndexesNames.UNIVERSITY_STUDENT_APPEARANCE_IN_LESSONS_TYPES_INDEX.name(),

                    PostgreSqlSchema.UNIVERSITY,
                    PostgreSqlTables.STUDENT_APPEARANCE_IN_LESSONS,

                    PostgresIndexes.BTREE,

                    "lesson_appearance_types %s".formatted(
                            PostgresIndexParams.ASC
                    )
            ),
            ";"
    );

    public static final String UNIVERSITY_STUDENT_MARKS_INDEX = String.join(
            " ",
            PostgresIndexParams.CREATE_INDEX.formatted(
                    PostgresIndexesNames.UNIVERSITY_STUDENTS_MARKS_INDEX.name(),

                    PostgreSqlSchema.UNIVERSITY,
                    PostgreSqlTables.STUDENT_MARKS,

                    PostgresIndexes.BTREE,

                    "mark_for_homework %s".formatted(
                            PostgresIndexParams.ASC
                    )
            ),
            ";"
    );

    public static final String UNIVERSITY_STUDENT_MARKS_STUDENT_AND_TEACHER_REF_INDEX = String.join(
            " ",
            PostgresIndexParams.CREATE_INDEX.formatted(
                    PostgresIndexesNames.UNIVERSITY_STUDENT_MARKS_STUDENT_AND_TEACHER_REF_INDEX.name(),

                    PostgreSqlSchema.UNIVERSITY,
                    PostgreSqlTables.STUDENT_MARKS,

                    PostgresIndexes.BTREE,

                    "student_id %s, teacher_id %s".formatted(
                            PostgresIndexParams.ASC,
                            PostgresIndexParams.ASC
                    )
            ),
            ";"
    );
}
