package org.tutorhub.interfaces.postgres;

import org.tutorhub.interfaces.database.DatabaseCommonMethods;

public interface PostgresStatisticsQueryInterface extends DatabaseCommonMethods {
    void get_pg_stats();

    void readPgStatTuple ();

    void readPgStatIndex ();

    void readCacheStatistics ();

    void get_pg_stat_activity ();

    void get_pg_stat_database ();

    void get_pg_statistics_ext ();

    void get_pg_stat_statements ();

    void get_pg_stat_user_tables ();

    void get_pg_stat_user_indexes ();

    void get_pg_prepared_statements();
}
