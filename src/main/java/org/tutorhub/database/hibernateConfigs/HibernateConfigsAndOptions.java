package org.tutorhub.database.hibernateConfigs;

import org.tutorhub.constans.postgres_constants.postgres_transactions_constants.PostgresTransactionIsolationTypes;
import org.tutorhub.database.postgresConfigs.PostgresStatisticsQueryController;
import org.tutorhub.annotations.LinksToDocs;

import org.hibernate.cfg.Environment;
import java.util.Map;

@LinksToDocs(
        links = {
                "https://vladmihalcea.com/how-to-batch-insert-and-update-statements-with-hibernate/",
                "https://docs.jboss.org/hibernate/orm/6.4/userguide/html_single/Hibernate_User_Guide.html#batch"
        }
)
@org.tutorhub.annotations.services.ServiceParametrAnnotation( propertyGroupName = "HIBERNATE_VALUES" )
@SuppressWarnings(
        value = """
                CacheMode.NORMAL -> CacheStoreMode.USE and CacheRetrieveMode.USE -> Default. Reads/writes data from/into the cache

                CacheMode.REFRESH -> CacheStoreMode.REFRESH and CacheRetrieveMode.BYPASS -> Doesn’t read from cache, but writes to the cache upon loading from the database

                CacheMode.PUT -> CacheStoreMode.USE and CacheRetrieveMode.BYPASS -> Doesn’t read from cache, but writes to the cache as it reads from the database

                CacheMode.GET -> CacheStoreMode.BYPASS and CacheRetrieveMode.USE -> Read from the cache, but doesn’t write to cache

                CacheMode.IGNORE -> CacheStoreMode.BYPASS and CacheRetrieveMode.BYPASS -> Doesn’t read/write data from/into the cache
                """
)
public class HibernateConfigsAndOptions extends PostgresStatisticsQueryController {
    private static int operationsCount = 0;
    protected static final int BATCH_SIZE = 30;
    protected static final Map< String, Object > dbSettings = newMap();

    static {
        dbSettings.put(
                Environment.URL,
                getVariable(
                        HibernateConfigsAndOptions.class,
                        "URL"
                )
        );

        dbSettings.put(
                Environment.USER,
                getVariable(
                        HibernateConfigsAndOptions.class,
                        "USER"
                )
        );

        dbSettings.put(
                Environment.PASS,
                getVariable(
                        HibernateConfigsAndOptions.class,
                        "PASSWORD"
                )
        );

        dbSettings.put( Environment.DRIVER, "org.postgresql.Driver" );
        dbSettings.put( Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect" );
        dbSettings.put( Environment.HBM2DDL_AUTO, "update" );

        dbSettings.put( Environment.POOL_SIZE, 50 );
        dbSettings.put( Environment.ISOLATION, PostgresTransactionIsolationTypes.READ_COMMITTED );

        dbSettings.put( Environment.SHOW_SQL, true );
        dbSettings.put( Environment.FORMAT_SQL, true );
        dbSettings.put( Environment.LOG_SLOW_QUERY, true );
        dbSettings.put( Environment.USE_SQL_COMMENTS, true );
        dbSettings.put( Environment.LOG_JDBC_WARNINGS, true );
        /*
        There’s the hibernate.jdbc.batch_versioned_data configuration property we need to set,
        in order to enable UPDATE batching

        Some JDBC drivers return incorrect row counts when a batch is executed.
        If your JDBC driver falls into this category this setting should be set to false.
        Otherwise, it is safe to enable this which will allow Hibernate to still batch the DML for versioned entities
        and still use the returned row counts for optimistic lock checks. Since 5.0, it defaults to true.
        Previously (versions 3.x and 4.x), it used to be false.
        */
        dbSettings.put( Environment.BATCH_VERSIONED_DATA, true );

        dbSettings.put( Environment.ORDER_INSERTS, true );
        dbSettings.put( Environment.ORDER_UPDATES, true );

        /*
        A non-zero value enables use of JDBC2 batch updates by Hibernate (e.g. recommended values between 5 and 30)

        Controls the maximum number of statements Hibernate will batch together before asking the driver to execute the batch.
        Zero or a negative number disables this feature.
        */
        dbSettings.put( Environment.STATEMENT_BATCH_SIZE, BATCH_SIZE );

        /*
        Optimizes second-level cache operations to minimize writes, at the cost of more frequent reads.
        Providers typically set this appropriately.
        */
        dbSettings.put( Environment.USE_MINIMAL_PUTS, true );

        /*
        Defines a name to be used as a prefix to all second-level cache region names.
        */
        dbSettings.put( Environment.CACHE_REGION_PREFIX, "hibernate" );

        /*
        Enable or disable second level caching overall.
        By default, if the currently configured RegionFactory is not the NoCachingRegionFactory,
        then the second-level cache is going to be enabled.
        Otherwise, the second-level cache is disabled.
        */
        dbSettings.put(
                Environment.USE_SECOND_LEVEL_CACHE,
                true
        );

        /*
        If you enable the hibernate.generate_statistics configuration property,
        Hibernate will expose a number of metrics via SessionFactory.getStatistics().
        Hibernate can even be configured to expose these statistics via JMX.

        This way, you can get access to the Statistics class which comprises all sort of second-level cache metrics.
        */
        dbSettings.put( Environment.GENERATE_STATISTICS, true );
    }

    protected static void increaseOperationsCount () {
        operationsCount++;
    }

    public static boolean isBatchLimitNotOvercrowded () {
        increaseOperationsCount();
        /*
        проверяем что количество операций не превысило
        макс количество Batch
        */
        return operationsCount > 0 && operationsCount % BATCH_SIZE == 0;
    }

    protected HibernateConfigsAndOptions() {}
}
