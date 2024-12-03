package org.tutorhub.constans.postgres_constants.postgres_extensions;

import org.tutorhub.constans.postgres_constants.PostgresCommonCommands;
import org.tutorhub.constans.postgres_constants.PostgresCreateValues;

import org.tutorhub.inspectors.dataTypesInpectors.StringOperations;
import org.tutorhub.annotations.LinksToDocs;

public final class PostgresExtensions {
    public static final String CREATE_EXTENSION_PG_PREWARM = String.join(
            StringOperations.SPACE,
            PostgresCommonCommands.CREATE.formatted(
                    PostgresCreateValues.EXTENSION
            ),
            "pg_prewarm;"
    );

    @SuppressWarnings( value = "создает расширение для работы и аналитики буфера кэширования" )
    public static final String CREATE_EXTENSION_FOR_BUFFER_READ = String.join(
            StringOperations.SPACE,
            PostgresCommonCommands.CREATE.formatted(
                    PostgresCreateValues.EXTENSION
            ),
            "pg_buffercache;"
    );

    public static final String CREATE_EXTENSION_PG_STAT_STATEMENTS = String.join(
            StringOperations.SPACE,
            PostgresCommonCommands.CREATE.formatted(
                    PostgresCreateValues.EXTENSION
            ),
            "pg_stat_statements;"
    );

    @SuppressWarnings(
            value = """
                    Создаёт секционированную по времени таблицу, если нужно писать очень
                    много данных во времени (системы мониторинга, биржевые системы)
                    """
    )
    @LinksToDocs(
            links = "https://medium.com/@mudasirhaji/step-by-step-process-of-how-to-install-timescaledb-with-postgresql-on-aws-ubuntu-ec2-ddc939dd819c"
    )
    public static final String CREATE_EXTENSION_TIMESCALE_DB = String.join(
            StringOperations.SPACE,
            PostgresCommonCommands.CREATE.formatted(
                    PostgresCreateValues.EXTENSION
            ),
            "timescaledb;"
    );

    @SuppressWarnings(
            value = """
                    SELECT * FROM pgstattuple( 'tablename' );

                        -[ RECORD 1 ]------+---------
                        table_len          | 68272128
                        tuple_count        | 500000
                        tuple_len          | 64500000
                        tuple_percent      | 94.47
                        dead_tuple_count   | 0
                        dead_tuple_len     | 0
                        dead_tuple_percent | 0
                        free_space         | 38776
                        free_percent       | 0.06
                    """
    )
    public static final String CREATE_EXTENSION_PG_STAT_TUPLE = String.join(
            StringOperations.SPACE,
            PostgresCommonCommands.CREATE.formatted(
                    PostgresCreateValues.EXTENSION
            ),
            "pgstattuple;"
    );
}
