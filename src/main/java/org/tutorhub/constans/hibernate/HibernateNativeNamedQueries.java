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
}