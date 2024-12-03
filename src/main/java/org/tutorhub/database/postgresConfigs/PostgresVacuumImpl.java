package org.tutorhub.database.postgresConfigs;

import org.tutorhub.interfaces.postgres.PostgresVacuumMethodsInterface;
import org.tutorhub.constans.postgres_constants.PostgresVacuumMethods;
import org.tutorhub.inspectors.LogInspector;

import org.hibernate.Transaction;
import java.text.MessageFormat;

@SuppressWarnings( value = "работает с инструментом VACUUM PostgreSQL" )
public final class PostgresVacuumImpl extends LogInspector implements PostgresVacuumMethodsInterface {
    public PostgresVacuumImpl () {}

    @SuppressWarnings( value = "проводит очистку VACUUM всех таблиц" )
    public void vacuumTable () {
        final Transaction transaction = this.getSession().beginTransaction();

        super.analyze(
                new PostgresFunctionsRegister().getListOfDbTables(),
                schemaAndTableName -> super.logging(
                        schemaAndTableName
                        + " was cleaned: "
                        + this.getSession().createNativeQuery(
                                MessageFormat.format(
                                        """
                                                {0}( {1}, {2} ) {3}
                                        """,
                                        PostgresVacuumMethods.VACUUM,

                                        PostgresVacuumMethods.ANALYZE,
                                        PostgresVacuumMethods.VERBOSE,

                                        schemaAndTableName
                                )
                        ).getQueryString()
                )
        );

        new PostgresMaterializedViewRegister().refreshAllViews();
        new PostgresIndexesRegister().reIndex();
        new PostgresCheckpointRegister();

        transaction.commit();
        super.logging( transaction );
    }

    @SuppressWarnings(
            value = """
                    провеодит очистку конкретной таблицы
                        нужно выбрать тип очистки ( Full, Verbose ) и название таблицы
                    """
    )
    @Override
    public void vacuumTable (
            final String tableName,
            final PostgresVacuumMethods vacuumMethod
    ) {
        super.logging(
                this.getSession().createNativeQuery(
                        MessageFormat.format(
                                "{0} {1} {2}",
                                PostgresVacuumMethods.VACUUM,
                                vacuumMethod,
                                tableName
                        )
                ).getQueryString()
        );
    }
}
