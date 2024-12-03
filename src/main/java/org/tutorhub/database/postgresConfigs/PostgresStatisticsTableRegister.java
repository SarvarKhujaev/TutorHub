package org.tutorhub.database.postgresConfigs;

import org.tutorhub.inspectors.enttiesInspectors.EntitiesInstances;

@SuppressWarnings( value = "создает все таблицы для сбора статистики по всем основным таблицам" )
public sealed class PostgresStatisticsTableRegister extends PostgresIndexesRegister permits PostgresFunctionsRegister {
    public PostgresStatisticsTableRegister () {
        super();
        this.createAllTablesForStats();
    }

    private void createAllTablesForStats() {
        super.analyze(
                EntitiesInstances.TABLES_FOR_STATE_NAMES,
                tableName -> super.logging(
                        this.getSession().createNativeQuery(
                                tableName
                        ).getQueryString()
                )
        );
    }
}
