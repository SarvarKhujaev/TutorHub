package org.tutorhub.database.postgresConfigs;

import org.tutorhub.entities.query_result_mapper_entities.BufferAnalyzeResultMapper;
import org.tutorhub.constans.postgres_constants.PostgresBufferMethods;

@SuppressWarnings( value = "работает с кэщом буферизации PostgreSQL" )
public sealed class PostgresBufferRegister extends PostgresFunctionsRegister permits PostgresMaterializedViewRegister {
    public PostgresBufferRegister () {
        super();

        this.prewarmTable();
        this.insertTableContentToBuffer();
        this.calculateBufferAnalyze();
    }

    private void prewarmTable () {
        /*
        прогреваем кэш
        */
        super.logging(
                PostgresBufferMethods.PREWARM_TABLE
                        + " : "
                        + this.getSession().createQuery(
                                PostgresBufferMethods.PREWARM_TABLE
                ).getSingleResult()
        );
    }

    private void calculateBufferAnalyze () {
        super.analyze(
                new PostgresFunctionsRegister().getListOfDbTables(),
                table -> super.analyze(
                        this.getSession().createNativeQuery(
                                PostgresBufferMethods.SELECT_BUFFER_ANALYZE_FOR_TABLE.formatted( table ),
                                BufferAnalyzeResultMapper.class
                        ).addScalar( "bufferid", Long.class )
                        .addScalar( "usagecount", Long.class )
                        .addScalar( "reldatabase", Long.class )
                        .addScalar( "relfilenode", Long.class )
                        .addScalar( "reltablespace", Long.class )
                        .addScalar( "relforknumber", Long.class )
                        .addScalar( "relblocknumber", Long.class )
                        .addScalar( "pinning_backends", Long.class )
                        .addScalar( "isdirty", Character.class )
                        .getResultList(),
                        bufferAnalyzeResultMapper -> super.logging( bufferAnalyzeResultMapper.toString() )
                )
        );
    }

    private void insertTableContentToBuffer () {
        /*
        создаем расширение, меняем настройки pg_config и перезапускаем БД
        */
        super.logging(
                PostgresBufferMethods.SOFT_RELOAD_OF_CONFIGURATIONS
                        + " : "
                        + this.getSession().createNativeQuery(
                                PostgresBufferMethods.SOFT_RELOAD_OF_CONFIGURATIONS
                ).getQueryString()
        );

        /*
        загружаем список таблиц в буферы
        */
        super.analyze(
                new PostgresFunctionsRegister().getListOfDbTables(),
                table -> super.logging(
                        table
                        + " was inserted into buffer: "
                        + this.getSession().createQuery(
                                PostgresBufferMethods.INSERT_TABLE_CONTENT_INTO_BUFFER.formatted(
                                        table
                                )
                        ).getQueryString()
                )
        );
    }
}
