package org.tutorhub.constans.postgres_constants.postgres_prepared_constants;

import org.tutorhub.annotations.LinksToDocs;

@LinksToDocs( links = "https://habr.com/ru/companies/postgrespro/articles/574702/" )
public final class PostgresPreparedQueryParams {
    @SuppressWarnings(
            value = """
                    При подготовке запроса его можно параметризовать.
                    Вот простой пример на уровне SQL-команд (повторюсь, что это не совсем то же самое,
                    что подготовка на уровне команд протокола, но в конечном счете эффект тот же):

                        PREPARE plane(text) AS
                        SELECT * FROM aircrafts WHERE aircraft_code = $1;

                        -- create
                        CREATE TABLE EMPLOYEE (
                          empId INTEGER PRIMARY KEY,
                          name TEXT NOT NULL,
                          dept TEXT NOT NULL
                        );

                        -- insert
                        INSERT INTO EMPLOYEE VALUES (0003, 'Ava', 'Sales');
                        INSERT INTO EMPLOYEE VALUES (0001, 'Clark', 'Sales');
                        INSERT INTO EMPLOYEE VALUES (0002, 'Dave', 'Accounting');

                        -- fetch
                        PREPARE empId( INTEGER ) AS
                        SELECT * FROM EMPLOYEE WHERE empId = $1;

                        PREPARE name( TEXT ) AS
                        SELECT * FROM EMPLOYEE WHERE name = $1;

                        PREPARE test( TEXT, TEXT, INTEGER ) AS
                        SELECT * FROM EMPLOYEE WHERE dept = $1 AND name = $2 OR empId = $3;

                        SELECT name, statement, parameter_types
                        FROM pg_prepared_statements;

                        EXECUTE empId( 0001 );
                        EXECUTE name( 'Clark' );
                        EXECUTE test( 'Sales', 'Clark', 0002 );
                    """
    )
    public static final String PREPARE = "PREPARE %s( %s ) AS %s;";

    @SuppressWarnings(
            value = """
                    Перед выполнением подготовленного запроса выполняется привязка фактических значений параметров.
                    EXECUTE plane('733');

                    Преимущество параметров подготовленных операторов перед конкатенацией литералов со строкой запроса
                    — принципиальная невозможность внедрения SQL-кода, поскольку любое значение параметра не сможет изменить уже построенное дерево разбора.
                    Чтобы достичь того же уровня безопасности без подготовленных операторов,
                    требуется аккуратно экранировать каждое значение, полученное из ненадежного источника.
                    """
    )
    public static final String EXECUTE = "EXECUTE %s( %s );";

    @SuppressWarnings(
            value = """
                    Несложно представить ситуацию, в которой по неудачному стечению обстоятельств первые несколько частных планов будут более дорогими, чем общий план,
                    а последующие оказались бы эффективнее общего — но планировщик уже не будет их рассматривать. Кроме того, планировщик сравнивает оценки стоимостей,
                    а не фактические ресурсы, и это также может служить источником ошибок.

                    Поэтому (начиная с версии 12) при неправильном автоматическом решении можно принудиительно выбрать общий либо частный план,
                    установив значение параметра plan_cache_mode:

                    SET plan_cache_mode = 'force_custom_plan';
                    """
    )
    public static final String PLAN_CACHE_MODE = "plan_cache_mode";
}
