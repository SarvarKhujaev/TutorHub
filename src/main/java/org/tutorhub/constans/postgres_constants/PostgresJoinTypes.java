package org.tutorhub.constans.postgres_constants;

/*
хранит все типы JOINS в PostgreSQL
*/
public final class PostgresJoinTypes {
    public static final String FULL_JOIN = "FULL JOIN";

    public static final String LEFT_JOIN = "LEFT JOIN";

    public static final String RIGHT_JOIN = "RIGHT JOIN";

    public static final String INNER_JOIN = "INNER JOIN";

    /*
    Соединяем каждую строчку с каждой.
    */
    public static final String CROSS_JOIN = "CROSS JOIN";

    /*
    https://stackoverflow.com/questions/28550679/what-is-the-difference-between-a-lateral-join-and-a-subquery-in-postgresql
    https://stackoverflow.com/questions/25536422/optimize-group-by-query-to-retrieve-latest-row-per-user/25536748#25536748

    Аналог CROSS APPLY в других СУБД.
    Используется для джойна с подзапросом.
    При этом в самом подзапросе можно использовать поля из основного запроса - получается зависимый подзапрос

    SELECT b.city_name, foo.country
    FROM b
    JOIN LATERAL
    (SELECT a.country FROM a WHERE a.city_name = b.city_name) as foo
    on true;

    Также lateral join единственный способ соединения с результатами
    вызова функции, возвращающей не одно значение.
    */
    public static final String LATERAL_JOIN = "LATERAL JOIN";
}
