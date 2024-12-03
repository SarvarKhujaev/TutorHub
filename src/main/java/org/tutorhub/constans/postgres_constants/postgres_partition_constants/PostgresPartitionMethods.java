package org.tutorhub.constans.postgres_constants.postgres_partition_constants;

import com.university.universityapplication.constans.postgres_constants.PostgresCommonCommands;
import com.university.universityapplication.constans.postgres_constants.PostgresCreateValues;

/*
https://www.postgresql.org/docs/14/ddl-partitioning.html#DDL-PARTITIONING-DECLARATIVE-BEST-PRACTICES

https://rasiksuhail.medium.com/guide-to-postgresql-table-partitioning-c0814b0fbd9b

https://rodoq.medium.com/partition-an-existing-table-on-postgresql-480b84582e8d

Общий синтаксис для создания секционированной таблицы:
    CREATE TABLE
    ...
    PARTITION BY { RANGE | LIST | HASH } ( { имя_столбца | ( выражение ) } [
    COLLATE правило_сортировки ][, ... ][класс_операторов] )

Задаваемый в скобках список столбцов или выражений формирует ключ разбиения таблицы.
Для разбиения по диапазонам или по хэшу (очень редко используется)
ключ разбиения может включать несколько столбцов или выражений, но для
разбиения по спискам ключ должен состоять из одного столбца или выражения.
Секционируемая таблица разделяется на подтаблицы (называемые секциями),
которые создаются отдельными командами CREATE TABLE.
Сама по себе секционированная таблица не содержит данных.
Строка данных, вставляемая в эту таблицу, перенаправляется в секцию в зависимости от
значений столбцов или выражений в ключе разбиения.
Если значениям в новой строке не соответствует ни одна из существующих секций, возникает ошибка.
*/
public final class PostgresPartitionMethods {
    /*
    CREATE TABLE products (
        product_id SERIAL PRIMARY KEY,
        category TEXT,
        product_name TEXT,
        price NUMERIC
    ) partition by list(category);
    */
    public static final String PARTITION_BY = "PARTITION BY";

    /*
    CREATE TABLE electronics PARTITION OF products
    FOR VALUES IN ('Electronics');
    */
    public static final String PARTITION_OF = "PARTITION OF";

    public static final String ATTACH = "ATTACH";

    public static final String DETACH = "DETACH";

    public static final String CREATE_RANGE_PARTITION_TABLE = String.join(
            " ",
            PostgresCommonCommands.CREATE.formatted(
                    PostgresCreateValues.TABLE.name()
            ),
            "%s_%s_PARTITION",
            PARTITION_OF,
            "%s.%s",
            "FOR VALUES FROM ( '%s' ) TO ( '%s' );"
    );

    /*
    CREATE TABLE electronics PARTITION OF products
        FOR VALUES IN ('Electronics');

    CREATE TABLE clothing PARTITION OF products
        FOR VALUES IN ('Clothing');

    CREATE TABLE furniture PARTITION OF products
        FOR VALUES IN ('Furniture');
     */
    public static final String CREATE_LIST_PARTITION_TABLE = String.join(
            " ",
            PostgresCommonCommands.CREATE.formatted(
                    PostgresCreateValues.TABLE.name()
            ),
            "%s_%s_PARTITION",
            PARTITION_OF,
            "%s.%s",
            "FOR VALUES IN ( '%s' );"
    );

    /*
    CREATE TABLE orders_3 PARTITION OF orders
    FOR VALUES WITH (MODULUS 3, REMAINDER 2);

    In this example, we use the HASH() function to specify that the data should be partitioned based
    on the hash value of the customer_id column.
    We use MODULUS and REMAINDER to specify the number of partitions (3 in this case) and the remainder value for each partition.
    */
    public static final String CREATE_HASH_PARTITION_TABLE = String.join(
            " ",
            PostgresCommonCommands.CREATE.formatted(
                    PostgresCreateValues.TABLE.name()
            ),
            "%s_%s_PARTITION",
            PARTITION_OF,
            "%s.%s",
            "FOR VALUES WITH ( MODULUS %d, REMAINDER %d );"
    );
}
