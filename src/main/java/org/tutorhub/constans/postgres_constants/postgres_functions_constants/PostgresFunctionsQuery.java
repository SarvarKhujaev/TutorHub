package org.tutorhub.constans.postgres_constants.postgres_functions_constants;

import com.university.universityapplication.constans.postgres_constants.PostgreSqlSchema;
import com.university.universityapplication.constans.postgres_constants.PostgresCommonCommands;
import com.university.universityapplication.constans.postgres_constants.PostgresCreateValues;

/*
https://www.postgresqltutorial.com/postgresql-plpgsql/postgresql-create-function/
*/
public enum PostgresFunctionsQuery {
    /*
    возвращает имена названия всех индесков для конкретной таблицы
    принимает название схемы и таблицы как параметры
    */
    SELECT_ALL_INDEXES_IN_TABLE {
        @Override
        public String getQuery () {
            return """
                    %s %s( schema_name text, table_name text )
                    returns SETOF text as $$
                    SELECT indexname FROM pg_indexes WHERE schemaname = schema_name AND tablename = table_name;
                    $$
                    language sql;
                """.formatted(
                        PostgresCommonCommands.CREATE_OR_REPLACE.formatted(
                                PostgresCreateValues.FUNCTION
                        ),
                        PostgresFunctionsNames.SHOW_ALL_INDEXES_IN_TABLE
            );
        }
    },

    /*
    возвращает список с названиями таблицы внутри схемы
    */
    SELECT_ALL_TABLES_IN_SCHEMA {
        @Override
        public String getQuery () {
            return """
            %s %s() returns SETOF text as $$
            SELECT
                table_schema || '.' || table_name as show_tables
            FROM
                information_schema.tables
            WHERE
                table_type = 'BASE TABLE'
            AND
                table_schema = '%s';
            $$
            language sql;
            """.formatted(
                    PostgresCommonCommands.CREATE_OR_REPLACE.formatted(
                            PostgresCreateValues.FUNCTION
                    ),
                    PostgresFunctionsNames.SHOW_ALL_TABLES_IN_SCHEMA,
                    PostgreSqlSchema.UNIVERSITY
            );
        }
    };

    public String getQuery () {
        return "";
    }
}
