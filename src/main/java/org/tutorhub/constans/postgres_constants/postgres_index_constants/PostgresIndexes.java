package org.tutorhub.constans.postgres_constants.postgres_index_constants;

/*
Для других операций с индексом используются команды:
● ALTER INDEX143 - изменить индекс
● DROP INDEX144 - удалить индекс
● REINDEX145 - перестроить индекс заново, используется, когда было
много вставок, обновлений и удалений, и информация на страницах
индекса уже расположена не оптимальным образом, записывая
новую версию индекса без мёртвых страниц146. Не забываем
указывать CONCURRENTLY!

Плюсы индексов:
● Ускоряют выборку в операциях SELECT, UPDATE, DELETE, INSERT
● При выборке данных только индексного поля, данные из таблицы
не выбираются
● Увеличение скорости сортировки по индексному полю
● Обеспечение уникальности

Минусы индексов:
● Индексы требуют дополнительного места
● Необходимо перестраивать индексы при операциях UPDATE,
DELETE, INSERT
● При большом количестве индексов оптимизатору сложно выбрать
какой использовать

Составной индекс.
    Это когда используется два и более полей для построения индекса. Здесь
    есть своя особенность: сначала строится дерево по первому индексу, затем уже
    внутри данные отсортированы по второму и последующим индексам. Исходя из
    концепции - при поиске только по второму индексу - составной индекс
    задействован не будет.

Также индексы используются и при сортировке.
Причём и при обратной сортировке тоже!
Естественно, если будет задействовано только вложенное поле в составном индексе
или будет разнонаправленная сортировка - индекс использован не будет

Частичные индексы.
    Частичный индекс - индекс, который строится по подмножеству строк
    таблицы, определяемому условным выражением:
    CREATE INDEX name ON table_name(column) WHERE condition;

    Плюсы:
        ● позволяют избежать индексирования распространенных значений
        ● позволяют исключить из индекса значения, которые обычно не
        представляют интереса

Покрывающие индексы.
    Предназначены для включения столбцов, которые требуются в определенных часто выполняемых запросах.

    CREATE INDEX name ON table_name(column1) INCLUDE (column2);

    Создается индекс, в котором столбец column2 будет «дополнительной нагрузкой», но не войдет в поисковый ключ.
    ● Плюс: для запросов, включающих вывод column2, не нужно обращаться к основной таблице - больше скорость
    ● Минус: требуется дополнительное место, дублирование информации

Хэш-индекс (hash).
    Работает только с условием равенства (=).
    В условиях IS NULL и IS NOT NULL так же не используется.
    Создаём корзины, куда складываем значения по хэшу поля,
    соответственно, всегда знаем, в какой корзине находятся строки. При
    совпадении хэша у разных строк идём по пути полного перебора строк в корзине.
    Сложность алгоритма при этом О(1) - поиск за константное время - быстрее О(log n).
*/
public final class PostgresIndexes {
    /*
    Btree (btree) - сбалансированное дерево (по умолчанию).
    Используется в 99% индексов.

    Применим для любого типа, который можно отсортировать в чётко
    определённом линейном порядке. Поддерживает:
    ● операторы сравнения >, <, =, >=, <=, BETWEEN и IN
    ● условия пустоты IS NULL и IS NOT NULL
    ● операторы поиска подстроки LIKE и ~, если искомая строка
    закреплена в начале шаблона (например str LIKE 'search%')
    ● регистронезависимые операторы поиска подстроки ILIKE и ~* (но
    только в том случае, если искомая строка начинается с символа,
    который одинаков и в верхнем, и в нижнем регистре, например,
    числа)

    Индексы Btree дополнительно принимают эти параметры:

    deduplicate_items - управляет механизмом исключения дубликатов. Это
    механизм формирования одной строки со списком идентификаторов для каждой
    группы - значения ключевых столбцов хранятся в единственном экземпляре, а
    за ними идёт отсортированный массив идентификаторов TID, указывающих на
    строки в таблице.

    Это существенно уменьшает размер хранимых индексов, в которых
    каждое значение (или каждое уникальное сочетание значений столбцов)
    появляется в среднем несколько раз. В результате может значительно
    увеличиться скорость выполнения запросов, а также могут сократиться
    издержки, связанные с регулярной очисткой индексов. Default - ON.
    Необходимо учитывать, что после выключения параметра
    deduplicate_items командой ALTER INDEX при добавлении в будущем новых
    элементов дубликаты исключаться не будут, но представление существующих
    строк не поменяется на стандартное.

    vacuum_cleanup_index_scale_factor - определяет процент от общего
        числа строк, подсчитанных при последнем сборе статистики, который может
        быть вставлен без необходимости сканирования индекса на стадии очистки при
        выполнении VACUUM.

    Индексы в B будут в любом случае сканироваться на стадии очистки
    VACUUM, когда выполняется хотя бы одно из следующих условий:
        ● статистика индексов устарела
        ● индекс содержит удалённые страницы, которые могут быть переработаны при очистке

    Статистика индекса считается устаревшей, если число недавно
    вставленных строк превышает процент vacuum_cleanup_index_scale_factor от
    общего числа строк, полученных при предыдущем сборе статистики.
    Когда vacuum_cleanup_index_scale_factor равен 0, сканирование
    индекса на этапе очистки VACUUM не пропускается никогда. Default - 0.1.
    */
    public static final String BTREE = "BTREE";

    /*
    GiST индекс (Generalized Search Tree).
    Или обобщённое поисковое дерево.
    Представляет собой базовый шаблон, на основе которого могут
    реализовываться произвольные схемы индексации, например, Btree, R-деревья
    и другие схемы индексации. Для построения индексов используют один из
    нескольких алгоритмов, наиболее подходящих под тип индексируемого поля,
    поэтому набор операторов зависит от типа поля.
    Применяется для специфических типов данных: геометрии, сетевые
    адреса, диапазоны

    Индексы GiST дополнительно принимают этот параметр:
    buffering - будет ли при построении индекса использоваться буферизация.
    Default AUTO - отключена вначале, но может затем включиться на ходу,
    как только размер индекса достигнет значения effective_cache_size.
    */
    public static final String GIST = "GIST";

    /*
    Индексы GIN принимают другие параметры:

    fastupdate - управляет механизмом быстрого обновления. Default - ON.

    gin_pending_list_limit - задаёт максимальный размер очереди записей GIN,
    которая используется, когда включён режим fastupdate.

    Если размер очереди превышает заданный предел, записи из неё массово переносятся в
    основную структуру данных индекса GIN, и очередь очищается.

    Default - 4MB

    GIN индекс (Generalized Inverted Index).
    Это обобщённый инвертированный индекс.
    Применяется к составным типам, работа с которыми осуществляется с
    помощью ключей: массивы, jsonb.GIN.
    Предназначается для случаев, когда индексируемые значения являются
    составными, а запросы ищут значения элементов в этих составных объектах.
    Самый распространённый вариант использования индексов GIN & GiST -
    полнотекстовый поиск149 по аналогии с Google/Yandex.

    Для работы с полнотекстовым поиском используются две функции150:

    ● to_tsvector - преобразует строку в вектор, содержащий корни слов,
    по которым будет в дальнейшем произведён поиск

    ● to_tsquery - преобразует запрос в вектор, содержащий корни слов,
    которые и будут найдены в массиве векторов to_tsvector

    Sure, here are some tips for optimizing the performance of a PostgreSQL database using GIN indexes:

    Use GIN indexes for large datasets:
    GIN indexes are particularly useful for large datasets because they allow for efficient insertion, deletion, and search operations.
    If you have a large dataset and frequently perform insert, update, and delete operations, a GIN index can help improve performance.
    Choose the right operator class:
    The operator class used in a GIN index can have a significant impact on performance.
    PostgreSQL provides several pre-defined operator classes for different data types, such as integer, float, and text.
    Choose the operator class that best matches the data type and operation you will be performing.
    You can also create custom operator classes tailored to your specific needs.
    Define the index carefully:
    When creating a GIN index, it's essential to define the index carefully to ensure it's efficient and effective.
    Consider the data type of the columns you're indexing, the operator class you're using, and the order of the columns in the index.
    Use the "gin_trust_level" parameter:
    The "gin_trust_level" parameter allows you to control the level of trust that PostgreSQL has in the index.
    A higher trust level can lead to faster queries, but it may also increase the risk of errors.
    Experiment with different trust levels to find the right balance for your use case.
    Use the "gin_stats_target" parameter:
    The "gin_stats_target" parameter allows you to control the number of statistics collected by PostgreSQL for the index.
    Collecting more statistics can help PostgreSQL make better decisions about how to use the index, but it can also increase the amount of memory used.
    Experiment with different values for this parameter to find the right balance for your use case.
    Use the "gin_cleanup_threshold" parameter:
    The "gin_cleanup_threshold" parameter controls when PostgreSQL cleans up unused index pages.
    A lower threshold can lead to more frequent cleanups and help maintain a healthy index, but it can also increase the amount of I/O needed.
    Experiment with different values for this parameter to find the right balance for your use case.
    Monitor index usage:
    Monitor the usage of your GIN indexes to identify any performance bottlenecks or issues.
    Use tools like pg_stat_activity or pg_stat_database to monitor index usage and identify any slow queries or performance issues.
    Optimize your queries:
    Make sure your queries are optimized for the GIN index.
    Use EXPLAIN to analyze the execution plan of your queries and identify any potential issues.
    Consider rewriting your queries or adjusting your index configuration to improve performance.
    Use GIN together with other indexing techniques:
    GIN indexes are powerful, but they're not always the best solution for every use case.
    Consider using other indexing techniques, such as B-tree or hash indexes, in combination with GIN to achieve better performance.
    Test and experiment:
    Finally, remember to test and experiment with different GIN index configurations to find the best setup for your specific use case.
    Use tools like pg_test_gsm to test and compare different index configurations and identify the best approach for your workload.

    By following these tips, you can effectively use GIN indexes in PostgreSQL to improve the performance of your database and optimize your queries.
    Remember to monitor your index usage, optimize your queries, and experiment with different configurations to find the best approach for your specific use case.

    Sure, here's an example of creating a GIN index in PostgreSQL:

    Let's say we have a table called "items" with columns "id" (serial), "name" (text), "price" (numeric), and "category" (text). We want to create a GIN index on the "category" column to improve the performance of queries that filter by category.

    Here's the command to create a GIN index:

    CREATE INDEX items_category_gin ON items USING gin (category gin_trust_level = 3);

    This command creates a GIN index named "items_category_gin" on the "items" table, using the "category" column. The "gin_trust_level" parameter is set to 3, which means that PostgreSQL will use a moderate amount of memory to store the index.

    You can also specify additional parameters to customize the index, such as the operator class, storage parameters, and statistics target. Here's an example with additional parameters:

    CREATE INDEX items_category_gin ON items USING gin (category gin_trust_level = 3, gin_operator_class = 'category_ops', gin_storage_type = 'plain' , gin_stats_target = 100);

    In this example, we've added three additional parameters:

    "gin_operator_class" specifies the operator class used for the index. In this case, we've defined a custom operator class called "category_ops"
    that includes the necessary operators for the "category" column.
    "gin_storage_type" specifies the storage type for the index. In this case, we've chosen the "plain" storage type, which is the default.
    "gin_stats_target" specifies the target number of statistics that PostgreSQL should collect for the index. In this case, we've set the target to 100,
    which means that PostgreSQL will collect up to 100 statistics for the index.

    Once the GIN index is created, you can run queries that filter by the "category" column and take advantage of the optimized index. For example:

    SELECT * FROM items WHERE category = 'electronics';

    This query will use the GIN index to efficiently find all items with the category "electronics".

    Keep in mind that GIN indexes can take up more memory and disk space than other indexing techniques,
    so it's essential to monitor your server's resources and adjust your indexing strategy as needed. Additionally,
    GIN indexes may not always be the best solution for every use case, so be sure to test and experiment with different indexing techniques to find the best approach for your specific needs.

    Например, чтобы узнать, есть ли “толстая” и “кошка” в строке для поиска:
    SELECT to_tsvector('fat cats ate fat rats') @@ to_tsquery('fat & rat');
    */
    public static final String GIN = "GIN";

    /*
    Индексы BRIN142 принимают другие параметры:
    pages_per_range - сколько блоков таблицы образуют зону блоков для
    каждой записи в индексе BRIN.
    Default - 128

    BRIN индекс (Block Range Index) - индекс зон блоков.
    Предназначается для обработки очень больших таблиц, в которых
    определённые столбцы некоторым естественным образом коррелируют с их
    физическим расположением в таблице.
    Зона блоков - группа страниц, физически расположенных в таблице рядом;
    для каждой зоны в индексе сохраняется некоторая сводная информация.
    */
    public static final String BRIN = "BRIN";

    /*
    SP-GiST индекс (Space-Partitioned GiST).
    Это GiST с разбиением пространства.
    Метод поддерживает деревья поиска с разбиением, что позволяет
    работать с различными несбалансированными структурами данных (деревья
    квадрантов, k-мерные и префиксные деревья).
    Как и GiST, SP-GiST позволяет разрабатывать дополнительные типы
    данных с соответствующими методами доступа.
    */
    public static final String SP_GIST = "SP-GiST";
}
