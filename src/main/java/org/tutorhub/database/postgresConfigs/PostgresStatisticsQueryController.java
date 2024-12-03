package org.tutorhub.database.postgresConfigs;

import org.tutorhub.constans.postgres_constants.postgres_statistics_constants.PostgresStatisticsQueries;
import org.tutorhub.constans.postgres_constants.PostgresVacuumMethods;

import org.tutorhub.interfaces.postgres.PostgresStatisticsQueryInterface;

import org.tutorhub.inspectors.enttiesInspectors.EntitiesInstances;
import org.tutorhub.inspectors.AnnotationInspector;

import org.tutorhub.entities.postgres_stats_entities.PgStatIndex;
import org.tutorhub.entities.postgres_stats_entities.PgStatTuple;
import org.tutorhub.entities.postgres_stats_entities.PGStats;

import org.hibernate.stat.CacheRegionStatistics;
import org.hibernate.Transaction;

@SuppressWarnings( value = "выолняет все запросы связанные со статистикой" )
public class PostgresStatisticsQueryController extends AnnotationInspector implements PostgresStatisticsQueryInterface {
    public PostgresStatisticsQueryController () {}

    @Override
    public void get_pg_stats() {
        super.analyze(
                new PostgresFunctionsRegister().getListOfDbTables(),
                tableName -> super.analyze(
                        this.getSession().createNativeQuery(
                                PostgresStatisticsQueries.PG_STATS_QUERY.formatted(
                                        tableName
                                ),
                                PGStats.class
                        ).getResultList(),
                        pgStats -> super.logging(
                                pgStats.getSchemaname()
                        )
                )
        );
    }

    @Override
    public void get_pg_stat_activity() {
        this.getSession().createNativeQuery(
                PostgresStatisticsQueries.PG_STAT_ACTIVITY_QUERY
        );
    }

    @Override
    public void get_pg_stat_database() {
        this.getSession().createNativeQuery(
                PostgresStatisticsQueries.PG_STAT_DATABASE_QUERY
        );
    }

    @Override
    public void get_pg_statistics_ext() {
        this.getSession().createNativeQuery(
                PostgresStatisticsQueries.PG_STATISTICS_EXT_QUERY
        );
    }

    @Override
    public void get_pg_stat_statements() {
        this.getSession().createNativeQuery(
                PostgresStatisticsQueries.PG_STAT_STATEMENTS_QUERY
        );
    }

    @Override
    public void get_pg_stat_user_tables() {
        this.getSession().createNativeQuery(
                PostgresStatisticsQueries.PG_STAT_USER_TABLES_QUERY
        );
    }

    @Override
    public void get_pg_stat_user_indexes() {
        this.getSession().createNativeQuery(
                PostgresStatisticsQueries.PG_STAT_USER_INDEXES_QUERY
        );
    }

    @Override
    public void get_pg_prepared_statements() {
        this.getSession().createNativeQuery(
                PostgresStatisticsQueries.PG_PREPARED_STATEMENTS_QUERY
        );
    }

    @SuppressWarnings(
            value = """
                    If you enable the hibernate.generate_statistics configuration property,
                        Hibernate will expose a number of metrics via SessionFactory.getStatistics().
                        Hibernate can even be configured to expose these statistics via JMX.
                                        
                        This way, you can get access to the Statistics class which comprises all sort of second-level cache metrics.
                    """
    )
    @Override
    public void readCacheStatistics () {
        super.analyze(
                EntitiesInstances.CACHE_REGIONS_NAMES,
                regionName -> {
                    final CacheRegionStatistics regionStatistics = this.getSession()
                            .getSessionFactory()
                            .getStatistics()
                            .getDomainDataRegionStatistics( regionName );

                    super.logging( regionStatistics.getRegionName() );

                    super.logging( regionStatistics.getHitCount() );

                    super.logging( regionStatistics.getMissCount() );

                    super.logging( regionStatistics.getSizeInMemory() );
                }
        );
    }

    @Override
    public void readPgStatTuple () {
        final Transaction transaction = this.getSession().beginTransaction();

        super.analyze(
                new PostgresFunctionsRegister().getListOfDbTables(),
                schemaAndTableName -> super.analyze(
                        this.getSession().createNativeQuery(
                                PostgresStatisticsQueries.PG_STAT_TUPLE_QUERY.formatted(
                                        schemaAndTableName
                                ),
                                PgStatTuple.class
                        ).getResultList(),
                        pgStatTuple -> {
                            /*
                            если процент соотношений живых записей и мертвых больше 70 %
                            то применяем VACUUM FULL на этой таблице
                            чтобы очистить таблицу и индексы
                            */
                            if ( pgStatTuple.getFree_percent() < 70 ) {
                                new PostgresVacuumImpl().vacuumTable(
                                        schemaAndTableName,
                                        PostgresVacuumMethods.FULL
                                );
                            }
                        }
                )
        );

        transaction.commit();
        super.logging( transaction );
    }

    @Override
    public void readPgStatIndex () {
        final Transaction transaction = this.getSession().beginTransaction();

        super.analyze(
                new PostgresFunctionsRegister().getListOfDbTables(),
                schemaAndTableName -> super.analyze(
                        this.getSession().createNativeQuery(
                                PostgresStatisticsQueries.PG_STAT_INDEX_QUERY.formatted(
                                        String.join(
                                                EMPTY,
                                                schemaAndTableName,
                                                "_s"
                                        )
                                ),
                                PgStatIndex.class
                        ).getResultList(),
                        pgStatIndex -> {
                            if ( pgStatIndex.getAvg_leaf_density() < 70 ) {
                                super.analyze(
                                        new PostgresFunctionsRegister().getListOfIndexesInTable(
                                                schemaAndTableName.split( "[.]" )[0],
                                                schemaAndTableName.split( "[.]" )[1]
                                        ),
                                        indexName -> new PostgresIndexesRegister().reIndex( indexName )
                                );
                            }
                        }
                )
        );

        transaction.commit();
        super.logging( transaction );
    }
}
