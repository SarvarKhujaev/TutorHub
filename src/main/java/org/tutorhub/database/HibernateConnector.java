package org.tutorhub.database;

import org.tutorhub.constans.postgres_constants.postgres_prepared_constants.PostgresPreparedQueryParams;
import org.tutorhub.constans.postgres_constants.postgres_statistics_constants.PostgresStatisticsParams;
import org.tutorhub.constans.postgres_constants.PostgresBufferMethods;

import org.tutorhub.database.hibernateConfigs.HibernateConfigsAndOptions;
import org.tutorhub.database.postgresConfigs.*;

import org.tutorhub.inspectors.enttiesInspectors.EntitiesInstances;
import org.tutorhub.interfaces.database.DatabaseCommonMethods;

import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.MetadataSources;
import org.hibernate.*;

import jakarta.validation.ValidatorFactory;
import jakarta.validation.Validation;

public final class HibernateConnector extends HibernateConfigsAndOptions implements DatabaseCommonMethods {
    private final Session session;
    private final SessionFactory sessionFactory;
    private final StandardServiceRegistry registry;
    private final ValidatorFactory validatorFactory;

    private static HibernateConnector CONNECTOR = new HibernateConnector();

    @Override
    @lombok.NonNull
    @lombok.Synchronized
    public synchronized Session getSession() {
        return this.session;
    }

    @Override
    @lombok.NonNull
    @lombok.Synchronized
    public synchronized SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }

    @Override
    @lombok.NonNull
    @lombok.Synchronized
    public synchronized StandardServiceRegistry getRegistry() {
        return this.registry;
    }

    @Override
    @lombok.NonNull
    @lombok.Synchronized
    public synchronized ValidatorFactory getValidatorFactory() {
        return this.validatorFactory;
    }

    @lombok.NonNull
    @lombok.Synchronized
    public synchronized static HibernateConnector getInstance() {
        return CONNECTOR != null ? CONNECTOR : ( CONNECTOR = new HibernateConnector() );
    }

    private HibernateConnector () {
        /*
        регистрируем все настройки
        */
        this.registry = new StandardServiceRegistryBuilder()
                .applySettings( dbSettings )
                .build();

        final MetadataSources metadataSources = new MetadataSources( this.getRegistry() );

        super.analyze(
                EntitiesInstances.instancesList,
                atomicReference -> metadataSources.addAnnotatedClass( atomicReference.get().getClass() )
        );

        /*
        подключаемся к самой БД
        */
        this.sessionFactory = metadataSources
                .getMetadataBuilder()
                .build()
                .getSessionFactoryBuilder()
                .build();

        /*
        открываем сессию
        */
        this.session = this.getSessionFactory().openSession();

        /*
        создаем instance класса Validation для валидации объектов
        */
        this.validatorFactory = Validation.buildDefaultValidatorFactory();

        /*
        настраиваем Second Level Cache
        */
        super.analyze(
                EntitiesInstances.instancesList,
                atomicReference -> this.getSessionFactory()
                        .getCache()
                        .evictEntityData( atomicReference.get().getClass() )
        );

        /*
        Hibernate specific JDBC batch size configuration on a per-Session basis
        */
        this.getSession().setJdbcBatchSize( BATCH_SIZE );

        this.setSessionProperties();

        super.logging( this.getClass() );

//        new PostgresStatisticsQueryController().readPgStatTuple();
//        new PostgresStatisticsQueryController().readPgStatIndex();
    }

    private void setSessionProperties () {
        /*
        меняем настройки кластера, только в рамках сессии
        */
        this.getSession().setProperty(
                "shared_preload_libraries",
                String.join(
                        SPACE_WITH_COMMA,
                        PostgresBufferMethods.PG_PREWARM, // расширение для прогрева буфера
                        PostgresStatisticsParams.PG_STAT_STATEMENTS // расширение для работы со статистикой
                )
        );

        /*
        настраиваем работу с Prepared statements
        */
        this.getSession().setProperty(
                PostgresPreparedQueryParams.PLAN_CACHE_MODE,
                "'force_custom_plan'"
        );

        /*
        сохраняем временные настройки для сбора статистики
        */
        this.getSession().setProperty( PostgresStatisticsParams.TRACK_FUNCTIONS, "all" );
        this.getSession().setProperty( PostgresStatisticsParams.TRACK_ACTIVITIES, "on" );
    }

    @SuppressWarnings( value = "создаем и регистрируем все сервисы, параметры и расщирения" )
    public void registerAllServices () {
        final Transaction transaction = this.newTransaction();

        /*
        подгатавливаем все основные запросы для дальнейшей обработки
        */
        new PostgresPrepareStatementsRegister();

        transaction.commit();

        super.logging( transaction );
    }

    @Override
    public synchronized void close () {
        new PostgresVacuumImpl().vacuumTable();

        this.getSession().clear();
        this.getSession().close();
        this.getSessionFactory().close();
        this.getValidatorFactory().close();

        StandardServiceRegistryBuilder.destroy( this.getRegistry() );

        CONNECTOR = null;

        super.logging( this );
        this.clean();
    }
}
