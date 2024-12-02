package org.tutorhub.constans.postgres_constants;

/*
хранит все основные и часто используемые запрос в БД
*/
public final class PostgresCommonCommands {
    public static final String SELECT_FROM = "SELECT * FROM";

    public static final String CREATE = "CREATE %s IF NOT EXISTS";

    public static final String CREATE_OR_REPLACE = "CREATE OR REPLACE %s IF NOT EXISTS";

    public static final String SELECT_ALL_FROM = "SELECT * FROM %s.%s;";

    /*
    Команда TRUNCATE логически работает так же, как и DELETE — удаляет все табличные строки.
    Но DELETE, как уже было рассмотрено, только помечает версии строк как удаленные, что требует дальнейшей очистки.
    TRUNCATE же просто создает новый, чистый файл. Как правило, это работает быстрее, но надо учитывать,
    что TRUNCATE полностью заблокирует работу с таблицей на все время до конца транзакции.
    */
    public static final String TRUNCATE = "TRUNCATE TABLE %s.%s;";

    public static final String DELETE = "DELETE FROM %s.%s WHERE %s;";

    public static final String DROP = "DROP %s %s CASCADE;";
}
