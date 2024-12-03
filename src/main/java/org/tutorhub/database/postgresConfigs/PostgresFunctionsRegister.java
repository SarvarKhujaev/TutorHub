package org.tutorhub.database.postgresConfigs;

import org.tutorhub.constans.postgres_constants.postgres_functions_constants.PostgresFunctionsQuery;
import org.tutorhub.constans.postgres_constants.postgres_functions_constants.PostgresFunctionsNames;
import org.tutorhub.constans.postgres_constants.PostgresCommonCommands;

import java.util.List;

public sealed class PostgresFunctionsRegister extends PostgresStatisticsTableRegister permits PostgresBufferRegister {
    public PostgresFunctionsRegister() {
        super();
    }

    public void createAllFunctions () {
        super.analyze(
                PostgresFunctionsQuery.values(),
                postgresFunctionsQuery -> super.logging(
                        this.getSession().createNativeQuery(
                                postgresFunctionsQuery.getQuery()
                        ).getQueryString()
                )
        );
    }

    public List< String > getListOfDbTables () {
        return this.getSession().createNativeQuery(
                String.join(
                        SPACE,
                        PostgresCommonCommands.SELECT_FROM,
                        PostgresFunctionsNames.SHOW_ALL_TABLES_IN_SCHEMA.name(),
                        "();"
                ),
                String.class
        ).getResultList();
    }

    public List< String > getListOfIndexesInTable (
            final String schemaName,
            final String tableName
    ) {
        return this.getSession().createNativeQuery(
                String.join(
                        SPACE,
                        PostgresCommonCommands.SELECT_FROM,
                        PostgresFunctionsNames.SHOW_ALL_INDEXES_IN_TABLE.name(),
                        "( schemaname => %s, tablename => %s );".formatted(
                                schemaName,
                                tableName
                        )
                ),
                String.class
        ).getResultList();
    }
}
