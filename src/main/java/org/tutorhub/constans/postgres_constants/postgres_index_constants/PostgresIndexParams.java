package org.tutorhub.constans.postgres_constants.postgres_index_constants;

import org.tutorhub.constans.postgres_constants.PostgresCommonCommands;
import org.tutorhub.constans.postgres_constants.PostgresCreateValues;
import org.tutorhub.inspectors.dataTypesInpectors.StringOperations;

@SuppressWarnings( value = "хранит все кинфигурации для индексов PostgreSQL" )
public final class PostgresIndexParams {
    @SuppressWarnings(
            value = """
                    225
                    Рассмотрим основные параметры:
                    UNIQUE - указывает, что при создании индекса (если в таблице уже есть
                    данные) и при каждом добавлении или изменении данных, при которых будет
                    нарушена уникальность индекса, будет выдана ошибка.
                    """
    )
    public static final String UNIQUE = "UNIQUE";

    @SuppressWarnings(
            value = """
                    CONCURRENTLY - конкурентное построение индекса, без блокировок,
                    которые бы предотвращали добавление, изменение или удаление записей в
                    таблице. Стандартная операция построения индекса блокирует запись (но не
                    чтение) данных в таблице до своего завершения
                    """
    )
    public static final String CONCURRENTLY = "CONCURRENTLY";

    @SuppressWarnings(
            value = """
                    INCLUDE - позволяет указать список столбцов, которые войдут в индекс
                    как неключевые столбцы. Эти столбцы не будут использоваться в условии
                    поиска для сканирования по индексу.
                                    
                    !!! Преимущество данной опции - при сканировании только индекса,
                        содержимое неключевых столбцов может быть получено без обращения к
                        целевой таблице, так как оно находится непосредственно в элементе
                        индекса. Например, таким образом запрос из предыдущей главы мы бы
                        ускорили в десятки раз!!!
                    """
    )
    public static final String INCLUDE = "INCLUDE ( %s )";

    @SuppressWarnings(
            value = """
                    name - имя создаваемого индекса. Указание схемы при этом не
                    допускается: индекс всегда относится к той же схеме, что и родительская
                    таблица. Если имя не указано, Постгрес сам формирует составное имя,
                    состоящее из имени таблицы и имён индексируемых столбцов
                    """
    )
    public static final String NAME = "NAME";

    @SuppressWarnings(
            value = """
                    ONLY - указывает, что индексы не должны рекурсивно создаваться в
                    секциях секционированной таблицы. Default - выполняется рекурсивно.
                    """
    )
    public static final String ONLY = "ONLY";

    @SuppressWarnings( value = "table_name - имя индексируемой таблицы" )
    public static final String TABLE_NAME = "TABLE_NAME";

    @SuppressWarnings( value = "column_name - имя столбца таблицы." )
    public static final String COLUMN_NAME = "COLUMN_NAME";

    @SuppressWarnings(
            value = """
                    tablespace_name - табличное пространство, в котором будет создан индекс.
                    Default - выбирается табличное пространство заданное по умолчанию для данной БД.
                    """
    )
    public static final String TABLESPACE_NAME = "TABLESPACE_NAME";

    @SuppressWarnings(
            value = """
                    method - имя применяемого метода индекса.
                    Возможные варианты: btree, hash, gist, spgist, gin и brin (подробнее рассмотрим дальше).
                    Default btree
                    """
    )
    public static final String METHOD = "METHOD";

    @SuppressWarnings(
            value = """
                    expression - выражение с одним или несколькими столбцами таблицы.
                    Обычно выражение должно записываться в скобках, как показано в синтаксисе команды.
                    Однако скобки можно опустить, если выражение записано в виде вызова функции.
                    """
    )
    public static final String EXPRESSION = "EXPRESSION";

    @SuppressWarnings( value = "ASC / DESC - указывает порядок сортировки. Default - по возрастанию." )
    public static final String ASC = "ASC";

    public static final String DESC = "DESC";

    @SuppressWarnings( value = "predicate - выражение ограничения для частичного индекса." )
    public static final String PREDICATE = "PREDICATE";

    @SuppressWarnings(
            value = """
                    fillfactor - фактор заполнения для индекса определяется в процентном
                    отношении, насколько плотно метод индекса будет заполнять страницы индекса.
                    Для Btree концевые страницы заполняются до этого процента при начальном
                    построении индекса и позже, при расширении индекса вправо (добавлении
                    новых наибольших значений ключа). Меняйте только в том случае, если чётко
                    понимаете, что делаете.
                    """
    )
    public static final String FILL_FACTOR = "FILLFACTOR";

    @SuppressWarnings(
            value = """
                    REINDEX INDEX CONCURRENTLY table_name

                        INDEX
                            Recreate the specified index. This form of REINDEX cannot be executed inside a transaction block when used with a partitioned index.

                        TABLE
                            Recreate all indexes of the specified table. If the table has a secondary “TOAST” table, that is reindexed as well.
                            This form of REINDEX cannot be executed inside a transaction block when used with a partitioned table.

                        SCHEMA
                            Recreate all indexes of the specified schema. If a table of this schema has a secondary “TOAST” table, that is reindexed as well.
                            Indexes on shared system catalogs are also processed. This form of REINDEX cannot be executed inside a transaction block.

                        DATABASE
                            Recreate all indexes within the current database. Indexes on shared system catalogs are also processed.
                            This form of REINDEX cannot be executed inside a transaction block.

                        SYSTEM
                            Recreate all indexes on system catalogs within the current database.
                            Indexes on shared system catalogs are included. Indexes on user tables are not processed.
                            This form of REINDEX cannot be executed inside a transaction block.

                        name
                            The name of the specific index, table, or database to be reindexed.
                            Index and table names can be schema-qualified.
                            Presently, REINDEX DATABASE and REINDEX SYSTEM can only reindex the current database, so their parameter must match the current database's name.

                        CONCURRENTLY
                            When this option is used, PostgreSQL will rebuild the index without taking any locks that prevent concurrent inserts,
                            updates, or deletes on the table; whereas a standard index rebuild locks out writes (but not reads) on the table until it's done.
                            There are several caveats to be aware of when using this option — see Rebuilding Indexes Concurrently below.
                            For temporary tables, REINDEX is always non-concurrent, as no other session can access them, and non-concurrent reindex is cheaper.

                        TABLESPACE
                            Specifies that indexes will be rebuilt on a new tablespace.

                        VERBOSE
                            Prints a progress report as each index is reindexed.

                        boolean
                            Specifies whether the selected option should be turned on or off.
                            You can write TRUE, ON, or 1 to enable the option, and FALSE, OFF, or 0 to disable it.
                            The boolean value can also be omitted, in which case TRUE is assumed.

                        new_tablespace
                            The tablespace where indexes will be rebuilt.

                        Команда REINDEX перестраивает отдельный индекс на таблице. Фактически, VACUUM FULL и CLUSTER используют эту команду для того, чтобы перестроить индексы
                    """
    )
    public static final String REINDEX = "REINDEX CONCURRENTLY INDEX %s;";

    @SuppressWarnings(
            value = """
                    CREATE [ UNIQUE ] INDEX [ CONCURRENTLY ] [ [ IF NOT EXISTS ] name ]
                        ON [ ONLY ] table_name [ USING method ]
                        (
                        { column_name | ( expression ) } [ COLLATE collation ]
                        [ opclass [ ( opclass_parameter = value [, ... ] ) ] ] [ ASC | DESC ]
                        [ NULLS { FIRST | LAST } ] [, ...] )
                        [ INCLUDE ( column_name [, ...] ) ]
                        [ WITH ( storage_parameter [= value] [, ... ] ) ]
                        [ TABLESPACE tablespace_name ]
                        [ WHERE predicate ]
                    """
    )
    public static final String CREATE_INDEX = String.join(
            StringOperations.SPACE,
            PostgresCommonCommands.CREATE.formatted(
                    PostgresCreateValues.INDEX
            ),
            "%s ON %s.%s USING %s( %s )"
    );

    public static final String CREATE_UNIQUE_INDEX = String.join(
            StringOperations.SPACE,
            PostgresCommonCommands.CREATE.formatted(
                    String.join(
                            StringOperations.SPACE,
                            UNIQUE,
                            PostgresCreateValues.INDEX.name()
                    )
            ),
            "%s ON %s.%s USING %s( %s )"
    );
}
