package org.tutorhub.constans.postgres_constants;

import java.text.MessageFormat;

public final class PostgresBufferMethods {
    public static final String PG_PREWARM = "pg_prewarm";

    /*
    Список страниц сбрасывается в файл autoprewarm.blocks.
    Чтобы его увидеть, можно просто подождать,
    пока процесс autoprewarm master отработает в первый раз, но мы инициируем это вручную:
    */
    public static final String PREWARM_TABLE = "SELECT autoprewarm_dump_now()";

    /*
    Если мы предполагаем, что все ее содержимое очень важно, мы можем прочитать ее в буферный кеш с помощью вызова следующей функции:
    */
    public static final String INSERT_TABLE_CONTENT_INTO_BUFFER = MessageFormat.format(
            "SELECT {0}( '%s' )",
            PG_PREWARM
    );

    /*
    После перезапуска сервера должно пройти некоторое время, чтобы кеш «прогрелся» — набрал актуальные активно использующиеся данные.
    Иногда может оказаться полезным сразу прочитать в кеш данные определенных таблиц, и для этого предназначено специальное расширение:

    Раньше расширение могло только читать определенные таблицы в буферный кеш (или только в кеш ОС).
    Но в версии PostgreSQL 11 оно получило возможность сохранять актуальное состояние кеша на диск
    и восстанавливать его же после перезагрузки сервера. Чтобы этим воспользоваться,
    надо добавить библиотеку в shared_preload_libraries и перезагрузить сервер.

    Поле рестарта, если не менялось значение параметра pg_prewarm.autoprewarm, будет автоматически запущен фоновый процесс autoprewarm master,
    который раз в pg_prewarm.autoprewarm_interval будет сбрасывать на диск список страниц,
    находящихся в кеше (не забудьте учесть новый процесс при установке max_parallel_processes).

    тобы изменить текущие значения с сохранением значения нам
    необходимо внести изменения в файл с параметрами postgresql.conf или
    выполнить SQL команду alter system set <name>=’<value>’, которая записывает
    параметры в postgresql.auto.conf, но не применяет их.
    Параметры из обоих вариантов применятся после вызова:
        SELECT pg_reload_conf();
    */
    public static final String SOFT_RELOAD_OF_CONFIGURATIONS = """
            SELECT pg_reload_conf(); -- вариант мягкой перезагрузки конфигурации
            """;

    /*
    возвращает данные о потреблении буфера для конкретной таблицы
    */
    public static final String SELECT_BUFFER_ANALYZE_FOR_TABLE = """
            SELECT *
            FROM pg_buffercache
            WHERE relfilenode = pg_relation_filenode('%s');
            """;
}
