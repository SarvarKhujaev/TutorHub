package org.tutorhub.constans.postgres_constants.postgres_statistics_constants;

import org.tutorhub.constans.postgres_constants.PostgresCommonCommands;
import org.tutorhub.constans.postgres_constants.PostgreSqlSchema;
import org.tutorhub.constans.postgres_constants.PostgreSqlTables;

import org.tutorhub.inspectors.dataTypesInpectors.StringOperations;

@SuppressWarnings(
        value = """
                хранит все запросы связанные со статистикой

                CREATE STATISTICS flights_expr ON
                (
                    extract(
                        month FROM scheduled_departure AT TIME ZONE 'Europe/Moscow'
                    )
                )
                FROM flights;
                """
)
public final class PostgresStatisticsQueries {
    @SuppressWarnings(
            value = """
                    SELECT * FROM pg_stat_database WHERE datname = 'demo';

                    Здесь видим, сколько данных было выбрано из БД, сколько было вставок,
                    обновлений, были ли дедлоки и многое другое.
                    Необходимо заметить, что информация в статистику попадает с
                    минимальным лагом, и если мы запускаем выборку из этого представления в
                    рамках транзакции, то данные будут действительны на момент начала
                    транзакции, так как уровень изоляции транзакций у нас read committed
                    """
    )
    public static final String PG_STAT_DATABASE_QUERY = String.join(
            StringOperations.SPACE,
            PostgresCommonCommands.SELECT_FROM,
            PostgresStatisticsParams.PG_STAT_DATABASE,
            "WHERE datname = '%s'"
    );

    @SuppressWarnings( value = "SELECT * FROM pg_stats WHERE tablename = 'flights'" )
    public static final String PG_STATS_QUERY = String.join(
            StringOperations.SPACE,
            PostgresCommonCommands.SELECT_FROM,
            PostgresStatisticsParams.PG_STATS,
            "WHERE tablename = '%s'"
    );

    @SuppressWarnings(
            value = """
                    SELECT most_common_vals AS mcv, left(most_common_freqs::text,60) || '...' AS mcf
                    FROM pg_stats
                    WHERE tablename = 'flights' AND attname = 'aircraft_code'

                    возвращает статистику за конкретную колонку в таблице

                    −[ RECORD 1 ]−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−
                    mcv | {CN1,CR2,SU9,321,763,733,319,773}
                    mcf | {0.2783,0.27473333,0.25816667,0.059233334,0.038533334,0.0370...
                    """
    )
    public static final String GET_PG_STATS_MOST_COMMON_VALUES_FOR_CURRENT_COLUMN_QUERY = """
            SELECT most_common_vals AS mcv,
              left(most_common_freqs::text,60) || '...' AS mcf
            FROM %s
            WHERE tablename = '%s' AND attname = '%s';
            """;

    public static final String PG_STATISTICS_EXT_QUERY = String.join(
            StringOperations.SPACE,
            PostgresCommonCommands.SELECT_FROM,
            PostgresStatisticsParams.PG_STATISTICS_EXT
    );

    public static final String PG_STAT_ACTIVITY_QUERY = String.join(
            StringOperations.SPACE,
            PostgresCommonCommands.SELECT_FROM,
            PostgresStatisticsParams.PG_STAT_ACTIVITY
    );

    public static final String PG_STAT_USER_TABLES_QUERY = String.join(
            StringOperations.SPACE,
            PostgresCommonCommands.SELECT_FROM,
            PostgresStatisticsParams.PG_STAT_USER_TABLES,
            "WHERE relname = '%s'"
    );

    public static final String PG_STAT_USER_INDEXES_QUERY = String.join(
            StringOperations.SPACE,
            PostgresCommonCommands.SELECT_FROM,
            PostgresStatisticsParams.PG_STAT_USER_INDEXES
    );

    public static final String PG_STAT_STATEMENTS_QUERY = String.join(
            StringOperations.SPACE,
            PostgresCommonCommands.SELECT_FROM,
            PostgresStatisticsParams.PG_STAT_STATEMENTS
    );

    public static final String PG_PREPARED_STATEMENTS_QUERY = """
            SELECT name, generic_plans, custom_plans
            FROM %s
            """.formatted(
                    PostgresStatisticsParams.PG_PREPARED_STATEMENTS
            );

    public static final String PG_STAT_TUPLE_QUERY = String.join(
            StringOperations.SPACE,
            PostgresCommonCommands.SELECT_FROM,
            PostgresStatisticsParams.PG_STAT_TUPLE,
            "( '%s' )"
    );

    public static final String PG_STAT_INDEX_QUERY = String.join(
            StringOperations.SPACE,
            PostgresCommonCommands.SELECT_FROM,
            PostgresStatisticsParams.PG_STAT_INDEX,
            "( '%s' )"
    );
}
