package org.tutorhub.constans.hibernate;

public final class HibernateNativeNamedQueries {
    public static final String GET_ALL_GROUPS_FOR_CURRENT_USER_BY_USER_ID = "GET_ALL_GROUPS_FOR_CURRENT_USER_BY_USER_ID";

    public static final String GET_ALL_GROUPS_FOR_CURRENT_USER_BY_USER_ID_QUERY = """
            SELECT *
            FROM university.groups g
            WHERE id IN (
                    SELECT s.group_id
                    FROM university.STUDENTS_WITH_GROUPS_JOIN_TABLE s
                    WHERE s.student_id = :student_id
                )
            ORDER BY g.id;
            """;

    public static final String GET_TEACHER_AVERAGE_MARKS = "GET_TEACHER_AVERAGE_MARKS";

    public static final String GET_TEACHER_AVERAGE_MARKS_SETTER = "GET_TEACHER_AVERAGE_MARKS_SETTER";

    public static final String GET_TEACHER_AVERAGE_MARKS_QUERY = """
            SELECT COALESCE( averageMark, 0 ) AS averageMark,
            lessonCount,
            CASE
                WHEN averageMark BETWEEN 0 AND 2 THEN 'horrible'
                WHEN averageMark BETWEEN 2 AND 3 THEN 'normal'
                WHEN averageMark BETWEEN 3 AND 4 THEN 'medium'
            ELSE 'perfect'
            END AS averageMarkNaming
            FROM (
                SELECT avg( c.mark ) AS averageMark, -- средняя оценка занятия
                COUNT( l.id ) AS lessonCount -- количество занятий которые провел препод

                FROM university.groups g

                INNER JOIN ( university.lessons l INNER JOIN university.comments c ON c.lesson_id = l.id ) -- находим всe комметарии к этим занятиям
                ON l.group_id = g.id -- находим все занятия в этих группах

                WHERE g.teacher_id = :teacher_id -- находим все группы за которые он отвечает
            );
            """;

    public static final String GET_GROUPED_STUDENTS_STATS_FOR_LESSON_APPEARANCE = "GET_GROUPED_STUDENTS_STATS_FOR_LESSON_APPEARANCE";

    public static final String GET_GROUPED_STUDENTS_STATS_FOR_LESSON_APPEARANCE_SETTER = "GET_GROUPED_STUDENTS_STATS_FOR_LESSON_APPEARANCE_SETTER";

    public static final String GET_GROUPED_STUDENTS_STATS_FOR_LESSON_APPEARANCE_QUERY = """
            --сначала вычисляем одним запросом общее количество занятий студента
            WITH studentLessonsCount AS (
                SELECT COUNT(*) AS totalLessonsCount
                FROM university.STUDENT_APPEARANCE_IN_LESSONS u
                WHERE u.student_id = :student_id
            )
            SELECT s.lesson_appearance_types AS lessonAppearanceTypes,
            COUNT( s.lesson_appearance_types ) AS lessonsCount, -- общее количество занятий студента сгрупораванные по lesson_appearance_types
            ( SELECT totalLessonsCount FROM studentLessonsCount ),
            CASE
                WHEN s.lesson_appearance_types = 'IN_TIME' THEN 'пришел вовремя'
                WHEN s.lesson_appearance_types = 'ABSENT' THEN 'отсутствовал'
            ELSE 'опоздал'
            END AS studentAppearanceDesc
            FROM university.STUDENT_APPEARANCE_IN_LESSONS s
            WHERE s.student_id = :student_id AND ( SELECT totalLessonsCount FROM studentLessonsCount ) > 0
            GROUP BY s.lesson_appearance_types;
            """;

    public static final String GET_STUDENTS_QUERY_TEST =
            """
            SELECT *
            FROM %s.%s
            WHERE name = %s AND surname = %s AND father_name = %s;
            """;
}