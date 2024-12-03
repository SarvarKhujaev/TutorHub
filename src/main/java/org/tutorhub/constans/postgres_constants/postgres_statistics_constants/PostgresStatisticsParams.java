package org.tutorhub.constans.postgres_constants.postgres_statistics_constants;

import org.tutorhub.constans.postgres_constants.PostgresCommonCommands;
import org.tutorhub.constans.postgres_constants.PostgresCreateValues;

import org.tutorhub.inspectors.dataTypesInpectors.StringOperations;
import org.tutorhub.annotations.LinksToDocs;

@LinksToDocs(
        links = {
                "https://habr.com/ru/company/postgrespro/blog/576100/",
                "https://habr.com/ru/companies/postgrespro/articles/576100/",
                "https://www.postgresql.org/docs/14/multivariate-statistics-examples.html"
        }
)
public final class PostgresStatisticsParams {
    @SuppressWarnings(
            value = """
                    CREATE STATISTICS stts (dependencies, ndistinct) ON a, b FROM t;

                    CREATE STATISTICS [ IF NOT EXISTS ] statistics_name
                    [ ( statistics_kind [, ... ] ) ]
                    ON column_name, column_name [, ...]
                    FROM table_name
                    """
    )
    public static final String CREATE_STATISTICS = String.join(
            StringOperations.SPACE,
            PostgresCommonCommands.CREATE.formatted(
                    PostgresCreateValues.STATISTICS
            ),
            """
            %s ( %s ) ON %s FROM %s.%s;
            """
    );

    @SuppressWarnings(
            value = """
                    track_activities включает мониторинг текущих команд,
                    выполняемых любым серверным процессом
                    """
    )
    public static final String TRACK_ACTIVITIES = "track_activities";

    @SuppressWarnings(
            value = """
                    track_functions включает отслеживание использования
                    пользовательских функций
                    """
    )
    public static final String TRACK_FUNCTIONS = "track_functions";

    @SuppressWarnings( value = "track_io_timing включает мониторинг времени чтения и записи блоков" )
    public static final String TRACK_IO_TIMING = "track_io_timing";

    @SuppressWarnings(
            value = """
                    track_counts определяет необходимость сбора статистики по
                    обращениям к таблицам и индексам
                    """
    )
    public static final String TRACK_COUNTS = "track_counts";

    @SuppressWarnings(
            value = """
                    pg_stat_database

                        Отсюда можем получить следующую информацию:
                            ● как много информации получаем из кэша
                            ● как часто бывают проблемы с транзакциями
                            Основные колонки:
                            ● blks_hit - количество блоков, полученных из кэша Постгреса
                            ● blks_read - количество блоков, прочитанных с диска
                            ● xact_commit - количество закоммиченных транзакций
                            ● xact_rollback - количество транзакций, где был выполнен откат транзакции

                        SELECT *
                        FROM pg_stat_database
                        WHERE datname = 'test'
                        limit 1;

                         datid                    | oid
                         datname                  | name
                         numbackends              | integer

                         blks_hit                 | bigint
                         blks_read                | bigint

                         xact_commit              | bigint
                         xact_rollback            | bigint

                         tup_fetched              | bigint
                         tup_updated              | bigint
                         tup_deleted              | bigint
                         tup_returned             | bigint
                         tup_inserted             | bigint

                         deadlocks                | bigint
                         conflicts                | bigint

                         temp_files               | bigint
                         temp_bytes               | bigint
                         checksum_failures        | bigint

                         sessions                 | bigint
                         sessions_fatal           | bigint
                         sessions_killed          | bigint
                         sessions_abandoned       | bigint

                         active_time              | double precision
                         session_time             | double precision
                         blk_read_time            | double precision
                         blk_write_time           | double precision
                         idle_in_transaction_time | double precision

                         stats_reset              | timestamp with time zone
                         checksum_last_failure    | timestamp with time zone
                    """
    )
    public static final String PG_STAT_DATABASE = "pg_stat_database";

    @SuppressWarnings(
            value = """
                    pg_stats

                        Хранит гистограмму распределения значений в таблице и используется
                        планировщиком для построения более оптимального плана и резервирования
                        памяти исходя из предполагаемого количества возвращаемых строк.
                        Также здесь хранится частота, с которой в таблице встречаются значения NULL.

                        Основные колонки:
                            ● schemaname - имя схемы, содержащей таблицу
                            (pg_namespace.nspname)

                            ● tablename - имя таблицы (pg_class.relname)

                            ● attname - имя столбца, описываемого этой строкой (pg_attribute.attname)

                            ● null_frac - доля записей, в которых этот столбец содержит NULL

                            ● n_distinct - число больше нуля представляет примерное количество различных значений в столбце.

                            ● most_common_vals - список самых частых значений в столбце
                            (NULL, если не находятся значения, встречающиеся чаще других)

                            ● most_common_freqs - список частот самых частых значений,
                            то есть число их вхождений, разделённое на общее количество строк

                            ● histogram_bounds - список значений, разделяющих значения
                            столбца на примерно одинаковые популяции.

                            ● avg_width - Поле avg_width представления pg_stats показывает средний размер значений в данном столбце.
                            Конечно, размер значений таких типов данных, как integer или char(3),
                            всегда одинаков, но средний размер значений типов с переменной длиной, таких как text,
                            может сильно отличаться от столбца к столбцу:
                            Эта статистика используется для оценки объема памяти, необходимой для таких операций, как сортировка или хеширование.

                            ● correlation - статистическая корреляция между физическим
                            порядком строк и логическим порядком значений столбца.
                            Допустимые значения лежат в диапазоне -1 .. +1. Когда значение
                            около -1 или +1, сканирование индекса по столбцу будет считаться
                            дешевле, чем когда это значение около нуля, как результат
                            уменьшения случайного доступа к диску (этот столбец содержит
                            NULL, если для типа данных столбца не определён оператор <)

                            SELECT attname, correlation
                            FROM pg_stats WHERE tablename = 'airports_data'
                            ORDER BY abs(correlation) DESC;

                            Поле correlation представления pg_stats показывает корреляцию между физическим расположением данных на
                            диске и логическим порядком в смысле операций «больше» и «меньше». Если значения хранятся строго по возрастанию,
                            корреляция будет близка к единице; если по убыванию — к минус единице.
                            Чем более хаотично расположены данные на диске, тем ближе значение к нулю.

                            Корреляция используется для оценки стоимости индексного сканирования.

                            SELECT

                            avg_width,
                            inherited,

                            attname,
                            tablename,
                            schemaname,

                            null_frac,
                            n_distinct,
                            correlation,

                            most_common_freqs,
                            elem_count_histogram,
                            most_common_elem_freqs,

                            most_common_vals,
                            histogram_bounds,
                            most_common_elems

                            FROM pg_stats
                            WHERE tablename = 'test'
                            limit 1;

                            schemaname             | name
                            tablename              | name
                            attname                | name
                            inherited              | boolean
                            avg_width              | integer
                            most_common_vals       | anyarray
                            histogram_bounds       | anyarray
                            most_common_elems      | anyarray
                            null_frac              | real
                            n_distinct             | real
                            correlation            | real
                            most_common_freqs      | real[]
                            most_common_elem_freqs | real[]
                            elem_count_histogram   | real[]

                            schemaname | tablename | attname | null_frac | n_distinct  | most_common_vals | most_common_freqs | histogram_bounds | correlation
                            ------------+-----------+---------+-----------+-------------+------------------+-------------------+------------------+-------------
                             public     | test      | id      |         0 | -0.11111111 | {1}              | {1}               |                  |           1
                    """
    )
    public static final String PG_STATS = "pg_stats";

    @SuppressWarnings(
            value = """
                    pg_statistic_ext

                        Предназначено для хранения статистики для связанных столбцов - когда
                        мы в запросе используем несколько атрибутов и при этом их значения
                        коррелированы.

                        select * from pg_statistic_ext limit 10;

                         oid | stxrelid | stxname | stxnamespace | stxowner | stxstattarget | stxkeys | stxkind | stxexprs
                        -----+----------+---------+--------------+----------+---------------+---------+---------+----------
                    """
    )
    public static final String PG_STATISTICS_EXT = "pg_statistic_ext";

    @SuppressWarnings(
            value = """
                    SELECT dependencies
                        FROM pg_stats_ext WHERE statistics_name = 'flights_dep';

                                      dependencies
                        −−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−
                         {"2 => 5": 1.000000, "5 => 2": 0.010567}

                         Числа 2 и 5 — номера столбцов таблицы из pg_attribute.
                         Значения определяют степень функциональной зависимости:
                         от 0 (зависимости нет) до 1 (значения в первом столбце полностью определяют значения в другом).
                    """
    )
    public static final String PG_STATS_EXT = "pg_stats_ext";


    @SuppressWarnings(
            value = """
                    Информация о расширенной статистике сохраняется в таблице системного каталога pg_statistic_ext.
                        Собранная статистика (начиная с версии PostgreSQL 12) сохраняется в отдельной таблице pg_statistic_ext_data.
                        Смысл такого разделения — в возможности ограничения доступа пользователей к чувствительной информации.

                        Несколько представлений показывают собранную информацию, доступную для пользователя, в более удобном виде.
                        Расширенную статистику по выражению можно посмотреть следующим образом:

                        SELECT left(expr,50) || '...' AS expr,
                          null_frac, avg_width, n_distinct,
                          most_common_vals AS mcv,
                          left(most_common_freqs::text,50) || '...' AS mcf,
                          correlation
                        FROM pg_stats_ext_exprs WHERE statistics_name = 'flights_expr'

                        expr        | EXTRACT(month FROM (scheduled_departure AT TIME ZO...
                        null_frac   | 0
                        avg_width   | 8
                        n_distinct  | 12
                        mcv         | {8,9,3,5,12,4,10,7,11,1,6,2}
                        mcf         | {0.12526667,0.11016667,0.07903333,0.07903333,0.078..
                        correlation | 0.095407926
                    """
    )
    public static final String PG_STATIS_EXT_EXPRS = "pg_stats_ext_exprs";

    @LinksToDocs(
            links = {
                    "https://www.postgresql.org/docs/14/multivariate-statistics-examples.html",
                    "https://www.postgresql.org/docs/14/monitoring-stats.html#WAIT-EVENT-TABLE"
            }
    )
    @SuppressWarnings(
            value = """
                    pg_stat_activity

                        Содержит в себе по одной строке на каждый процесс сервера. Напомню,
                        что любое подключение к серверу порождает форк основного процесса и также
                        отображается в этом представлении.

                        Основные параметры:
                            ● datid - OID базы данных, к которой подключён этот серверный процесс
                            ● datname - имя базы данных, к которой подключён этот серверный процесс
                            ● pid - идентификатор процесса этого серверного процесса

                            ● backend_start - время запуска процесса.
                            Для процессов, обслуживающих клиентов, это время подключения клиента к серверу

                            ● client_addr - IP-адрес клиента, подключённого к этому серверному
                            процессу. Значение null в этом поле означает, что клиент
                            подключён через сокет Unix на стороне сервера или что это
                            внутренний процесс, например, автоочистка

                            ● client_hostname - имя компьютера для подключённого клиента.
                            Это поле будет отлично от null только в случае соединений по IP и
                            только при включённом режиме log_hostname

                            ● wait_event_type - тип события169, которого ждёт процесс

                            ● wait_event - имя ожидаемого события170, если процесс находится в состоянии ожидания

                            ● state - общее текущее состояние этого серверного процесса.

                            Возможные значения:
                                ○ active - серверный процесс выполняет запрос
                                ○ idle - серверный процесс ожидает новой команды от клиента
                                ○ idle in transaction - серверный процесс находится внутри
                                транзакции, но в настоящее время не выполняет ничего

                                ○ idle in transaction (aborted) - состояние подобно idle in transaction,
                                    за исключением того, что один из операторов в
                                    транзакции вызывал ошибку

                                ○ fastpath function call - серверный процесс выполняет fast-path функцию

                                ○ disabled - отображается для серверных процессов, у которых параметр track_activities отключён

                                ● query - текст последнего запроса этого серверного процесса.
                                По умолчанию текст запроса обрезается до 1024 байт (параметр track_activity_query_size)

                                ● backend_type - тип текущего серверного процесса

                           SELECT
                           pid,
                           state,
                           datid,
                           datname,
                           wait_event,
                           client_addr,
                           backend_start,
                           client_hostname,
                           wait_event_type
                           FROM pg_stat_activity limit 1;

                           datid | datname |  pid  |         backend_start         | client_addr | client_hostname | wait_event_type |   wait_event   | state
                           -------+---------+-------+-------------------------------+-------------+-----------------+-----------------+----------------+-------
                                 |         | 10150 | 2024-03-30 21:50:25.778232+05 |             |                 | Activity        | AutoVacuumMain |
                    """
    )
    public static final String PG_STAT_ACTIVITY = "pg_stat_activity";

    @SuppressWarnings(
            value = """
                    pg_stat_user_tables

                        Здесь собрана статистика, что вообще у нас происходит с таблицей.
                        Сколько строк, мёртвых строк и т.д.

                        Основные параметры:
                            ● relid - OID таблицы
                            ● schemaname - имя схемы, в которой расположена эта таблица
                            ● relname - имя таблицы
                            ● seq_scan - количество последовательных чтений, произведённых в этой таблице
                            ● seq_tup_read - количество "живых" строк, прочитанных при последовательных чтениях
                            ● n_tup_upd - количество изменённых строк
                            ● n_live_tup - оценочное количество "живых" строк
                            ● n_dead_tup - оценочное количество "мертвых" строк
                            ● last_autovacuum - время последней очистки таблицы фоновым процессом автоочистки

                        SELECT
                        relid,
                        relname,
                        seq_scan,
                        n_tup_upd,
                        n_live_tup,
                        n_dead_tup,
                        schemaname,
                        seq_tup_read,
                        last_autovacuum
                        FROM pg_stat_user_tables
                        WHERE relname = 'test' limit 1;

                         relid | schemaname | relname | seq_scan | seq_tup_read | n_tup_upd | n_live_tup | n_dead_tup | last_autovacuum
                        -------+------------+---------+----------+--------------+-----------+------------+------------+-----------------
                         21334 | public     | test    |        3 |           27 |         0 |          9 |          0 |
                    """
    )
    public static final String PG_STAT_USER_TABLES = "pg_stat_user_tables";

    @SuppressWarnings(
            value = """
                    pg_stat_user_indexes

                        Здесь собирается статистика по используемым индексам.

                        Основные параметры:
                            ● relid - OID таблицы для индекса
                            ● relname - имя таблицы для индекса
                            ● idx_scan - количество произведённых сканирований по этому индексу
                            ● indexrelid - OID индекса
                            ● indexrelname - имя индекса
                            ● idx_tup_read - количество элементов индекса, возвращённых при сканированиях по этому индексу
                            ● idx_tup_fetch - количество живых строк таблицы, отобранных при простых сканированиях по этому индексу

                        select
                        relid,
                        relname,
                        idx_scan,
                        indexrelid,
                        indexrelname,
                        idx_tup_read,
                        idx_tup_fetch
                        from pg_stat_user_indexes limit 1;

                         relid |       relname        | idx_scan | indexrelid |       indexrelname        | idx_tup_read | idx_tup_fetch
                        -------+----------------------+----------+------------+---------------------------+--------------+---------------
                         21185 | education_directions |        0 |      21189 | education_directions_pkey |            0 |             0
                    """
    )
    public static final String PG_STAT_USER_INDEXES = "pg_stat_user_tables";

    @SuppressWarnings(
            value = """
                    pg_stat_statements

                        Модуль pg_stat_statements предоставляет возможность отслеживать
                        статистику планирования и выполнения сервером всех операторов SQL.
                        Этот модуль нужно загружать, добавив pg_stat_statements в
                        shared_preload_libraries в файле postgresql.conf, так как ему требуется
                        дополнительная разделяемая память.
                        Это значит, что для загрузки или выгрузки модуля необходимо перезапустить сервер.

                        Когда модуль pg_stat_statements загружается, он отслеживает
                        статистику по всем базам данных на сервере.

                        Для получения и обработки этой статистики этот модуль предоставляет представление pg_stat_statements и
                        вспомогательные функции pg_stat_statements_reset и pg_stat_statements

                        Эти объекты не доступны глобально, но их можно установить в определённой базе
                        данных, выполнив команду CREATE EXTENSION pg_stat_statements.

                        Основные параметры:
                            ● rows - суммарное количество возвращённых строк

                            ● shared_blks_hit - количество страниц, которые были в кэше БД

                            ● shared_blks_read - количество страниц, которые были прочитаны с
                            диска, чтобы выполнить запросы такого типа

                            ● shared_blks_dirtied - количество страниц, которые были изменены

                            ● shared_blks_written - количество страниц, которые были записаны
                            на диск

                            ● local_blks_hit, local_blks_read, local_blks_dirtied,
                            local_blks_written - то же самое, что предыдущие четыре, только
                            для временных таблиц и индексов

                            ● temp_blks_read - сколько страниц временных данных было
                            прочитано

                            ● temp_blks_written - сколько страниц временных данных было
                            записано (используется при сортировке на диске, когда она не
                            помещается в память и других временных операциях)

                            ● blk_read_time - сколько времени суммарно заняло чтение с диска

                            ● blk_write_time - сколько времени суммарно заняла запись на диск

                        select
                        rows,
                        blk_read_time,
                        blk_write_time,
                        local_blks_hit,
                        temp_blks_read,
                        shared_blks_hit,
                        shared_blks_read,
                        temp_blks_written,
                        shared_blks_dirtied,
                        shared_blks_written
                        from pg_stat_statements limit 1;
                    """
    )
    public static final String PG_STAT_STATEMENTS = "pg_stat_statements";

    @SuppressWarnings(
            value = """
                    Начиная с версии 14 представление pg_prepared_statements показывает в том числе и статистику выборов планов:

                        SELECT name, generic_plans, custom_plans
                        FROM pg_prepared_statements;

                        name  | generic_plans | custom_plans
                        −−−−−−−+−−−−−−−−−−−−−−−+−−−−−−−−−−−−−−
                        plane |             1 |            6
                    """
    )
    public static final String PG_PREPARED_STATEMENTS = "pg_prepared_statements";

    @LinksToDocs(
            links = {
                    "https://postgrespro.ru/docs/postgresql/11/pgstattuple",
                    "https://habr.com/ru/companies/postgrespro/articles/452320/"
            }
    )
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

                    table_len	bigint	Физическая длина отношения в байтах
                    tuple_count	bigint	Количество «живых» кортежей
                    tuple_len	bigint	Общая длина «живых» кортежей в байтах
                    tuple_percent	float8	Процент «живых» кортежей
                    dead_tuple_count	bigint	Количество «мёртвых» кортежей
                    dead_tuple_len	bigint	Общая длина «мёртвых» кортежей в байтах
                    dead_tuple_percent	float8	Процент «мёртвых» кортежей
                    free_space	bigint	Общий объём свободного пространства в байтах
                    free_percent	float8	Процент свободного пространства
                    """
    )
    public static final String PG_STAT_TUPLE = "pgstattuple";


    @LinksToDocs( links = "https://habr.com/ru/companies/postgrespro/articles/452320/" )
    @SuppressWarnings(
            value = """
                    SELECT * FROM pgstatindex('vac_s');

                    version            | 3
                    tree_level         | 3
                    index_size         | 72802304
                    root_block_no      | 2722
                    internal_pages     | 241
                    leaf_pages         | 8645
                    empty_pages        | 0
                    deleted_pages      | 0
                    avg_leaf_density   | 83.77
                    leaf_fragmentation | 64.25

                    version	integer	Номер версии B-дерева
                    tree_level	integer	Уровень корневой страницы в дереве

                    empty_pages	bigint	Количество пустых страниц
                    index_size	bigint	Общий объём индекса в байтах
                    leaf_pages	bigint	Количество страниц на уровне листьев
                    deleted_pages	bigint	Количество удалённых страниц
                    root_block_no	bigint	Расположение страницы корня (0, если её нет)
                    internal_pages	bigint	Количество «внутренних» страниц (верхнего уровня)

                    avg_leaf_density	float8	Средняя плотность страниц на уровне листьев
                    leaf_fragmentation	float8	Фрагментация на уровне листьев
                    """
    )
    public static final String PG_STAT_INDEX = "pgstatindex";


    @SuppressWarnings(
            value = """
                    SELECT * FROM pgstatginindex('test_gin_index');

                        version        | 1
                        pending_pages  | 0
                        pending_tuples | 0

                        Столбцы результата:
                        Столбец	Тип	Описание
                        version	integer	Номер версии GIN
                        pending_pages	integer	Количество страниц в списке ожидающих обработки
                        pending_tuples	bigint	Количество кортежей в списке ожидающих обработки
                    """
    )
    public static final String PG_STAT_GIN_INDEX = "pgstatginindex";

    @SuppressWarnings(
            value = """
                    Функция pgstathashindex возвращает запись с информацией о хеш-индексе.
                    Например:

                    test=> select * from pgstathashindex('con_hash_index');
                    -[ RECORD 1 ]--+-----------------
                    version        | 4
                    bucket_pages   | 33081
                    overflow_pages | 0
                    bitmap_pages   | 1
                    unused_pages   | 32455
                    live_items     | 10204006
                    dead_items     | 0
                    free_percent   | 61.8005949100872

                    Столбцы результата:
                    Столбец	Тип	Описание
                    version	integer	Номер версии HASH
                    bucket_pages	bigint	Количество страниц групп
                    overflow_pages	bigint	Количество страниц переполнения
                    bitmap_pages	bigint	Количество страниц битовой карты
                    unused_pages	bigint	Количество неиспользованных страниц
                    live_items	bigint	Количество «живых» кортежей
                    dead_tuples	bigint	Количество «мёртвых» кортежей
                    free_percent	float	Процент свободного пространства
                    """
    )
    public static final String PG_STAT_HASG_INDEX = "pgstathashindex";
}
