package org.tutorhub.constans.postgres_constants;

import org.tutorhub.inspectors.dataTypesInpectors.StringOperations;

public final class PostgreSqlTables {
    @SuppressWarnings( value = "хранит данные о том, какие занятия посещал студент" )
    public final static String STUDENT_APPEARANCE_IN_LESSONS = "STUDENT_APPEARANCE_IN_LESSONS";
    public final static String STUDENT_STUDY_INTERVAL = "STUDENT_STUDY_INTERVAL";
    @SuppressWarnings( value = "расписание студента" )
    public final static String STUDENT_SCHEDULE = "STUDENT_SCHEDULE";
    @SuppressWarnings( value = "оценки студента от учителя за каждый урок" )
    public final static String STUDENT_MARKS = "STUDENT_MARKS";
    public final static String STUDENTS = "STUDENTS";

    @SuppressWarnings( value = "расписание учителя" )
    public final static String TEACHER_SCHEDULE = "TEACHER_SCHEDULE";
    @SuppressWarnings( value = "оценки учителя от студента за каждый урок" )
    public final static String TEACHER_MARKS = "TEACHER_MARKS";
    public final static String TEACHERS = "TEACHERS";

    @SuppressWarnings( value = "учебный центр" )
    public final static String STUDY_CENTER = "STUDY_CENTER";
    @SuppressWarnings( value = "данные о курсах которые проводят в учебном центре" )
    public final static String COURSES = "COURSES";

    public final static String EDUCATION_DIRECTIONS = "EDUCATION_DIRECTIONS";
    public final static String EDUCATION_TYPES = "EDUCATION_TYPES";

    public final static String SOLVED_HOMEWORKS = "SOLVED_HOMEWORKS";
    public final static String HOMEWORK_MARKS = "HOMEWORK_MARKS";
    public final static String HOMEWORK = "HOMEWORK";

    public final static String LESSON_NOTES = "LESSON_NOTES";
    public final static String LESSONS = "LESSONS";

    public final static String FUTURE_PLANS_AND_WISHES = "FUTURE_PLANS_AND_WISHES";
    public final static String COMMENTS = "COMMENTS";
    public final static String ADDRESS = "ADDRESS";
    public final static String SUBJECT = "SUBJECT";
    public final static String GROUPS = "GROUPS";

    @SuppressWarnings( value = "названия таблиц которые будут отвечать за соединений разных таблиц" )
    public final static String STUDY_CENTER_AND_COURSE_JOIN_TABLE = StringOperations.generateJoinTable(
            STUDY_CENTER,
            COURSES
    );
}
