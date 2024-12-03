package org.tutorhub.constans.postgres_constants.postgres_cursor_constants;

/*
Получение результатов

Протокол расширенных запросов позволяет клиенту получать не все результирующие строки сразу, а выбирать данные по несколько строк за раз.
Почти тот же эффект дает использование SQL-курсоров
(за исключением лишней работы для сервера и того факта, что планировщик оптимизирует получение не всей выборки, а первых cursor_tuple_fraction строк):

https://habr.com/ru/companies/postgrespro/articles/574702/

BEGIN;
DECLARE cur CURSOR FOR
  SELECT * FROM EMPLOYEE ORDER BY empId;
FETCH 3 FROM cur;
FETCH 2 FROM cur;
COMMIT;
*/
public final class PostgresCursorMethods {
    public static final String DECLARE_CURSOR = """
            BEGIN;
            DECLARE cur CURSOR FOR
              %s -- QUERY
            FETCH %d -- pagination size
            FROM cur;
            COMMIT;
            """;
}
