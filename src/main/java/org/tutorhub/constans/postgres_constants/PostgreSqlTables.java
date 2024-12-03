package org.tutorhub.constans.postgres_constants;

public final class PostgreSqlTables {
    @SuppressWarnings( value = "данные о посещениях урока" )
    public final static String STUDENT_APPEARANCE_IN_LESSONS = "STUDENT_APPEARANCE_IN_LESSONS";
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

    public final static String EDUCATION_DIRECTIONS = "EDUCATION_DIRECTIONS";

    public final static String COMMENTS = "COMMENTS";
    public final static String LESSONS = "LESSONS";
    public final static String GROUPS = "GROUPS";
}
