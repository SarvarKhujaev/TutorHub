package org.tutorhub.constans.postgres_constants.postgres_backup_constants;

/*
https://habr.com/ru/articles/267881/

https://www.postgresql.org/docs/14/sql-copy.html

https://www.postgresql.org/docs/14/app-pgdump.html

https://www.postgresql.org/docs/14/app-pgrestore.html

https://www.postgresql.org/docs/14/app-pg-dumpall.html

https://www.postgresql.org/docs/14/app-pgbasebackup.html
*/
public final class PostgresBackupMethods {
    /*
    Синтаксис:
    COPY table_name [ ( column_name [, ...] ) | query]
    FROM/TO { 'filename' | PROGRAM 'command' | STDIN }
    [ [ WITH ] ( option [, ...] ) ]
    [ WHERE condition ]

    Посмотрим на основные параметры:
    table_name - имя существующей таблицы (возможно, дополненное схемой).
    column_name - необязательный список столбцов, данные которых будут
    копироваться. Если этот список отсутствует, копируются все столбцы таблицы, за исключением генерируемых.
    query - команда SELECT, INSERT, UPDATE или DELETE, результаты которой будут скопированы.
    filename - путь входного или выходного файла.
    PROGRAM - выполняемая команда.

    !!! Так как команда запускается через командную оболочку, все
    аргументы, поступающие из недоверенного источника, необходимо
    проверить и избавиться от всех спецсимволов, имеющих особое значение в
    оболочке, либо экранировать их !!!
    FORMAT format_name - выбирает формат чтения или записи данных: text
    (текстовый), csv (значения, разделённые запятыми) или binary (двоичный).
    Подробно можно прочитать по ссылке выше.

    COPY test TO '/home/1/test.csv' CSV HEADER;
    */
    public static final String COPY = "COPY";
}
