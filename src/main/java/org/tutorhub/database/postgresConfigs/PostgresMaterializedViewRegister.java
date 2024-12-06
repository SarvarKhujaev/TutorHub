package org.tutorhub.database.postgresConfigs;

import org.tutorhub.constans.postgres_constants.postgres_materialized_view_constants.PostgresMaterializedViewMethods;
import org.tutorhub.constans.postgres_constants.PostgresCreateValues;
import org.tutorhub.constans.postgres_constants.PostgreSqlSchema;

import org.tutorhub.inspectors.enttiesInspectors.EntitiesInstances;

public sealed class PostgresMaterializedViewRegister extends PostgresBufferRegister permits PostgresPrepareStatementsRegister {
    public PostgresMaterializedViewRegister () {
        super();

        this.createAllMaterializedViews();
        this.refreshAllViews();
    }

    public void createAllMaterializedViews () {
        super.analyze(
                EntitiesInstances.MATERIALIZED_VIEWS,
                viewName -> super.logging(
                        this.getSession().createNativeQuery( viewName ).getQueryString()
                )
        );
    }

    public void refreshAllViews () {
        super.analyze(
                this.getSession().createNativeQuery(
                        PostgresMaterializedViewMethods.REFRESH_ALL_MATERIALIZED_VIEWS.formatted(
                                PostgresCreateValues.MATERIALIZED_VIEW.getOriginalValue(),
                                PostgreSqlSchema.ENTITIES
                        ),
                        String.class
                ).getResultList(),
                viewName -> this.getSession().createNativeQuery(
                        PostgresMaterializedViewMethods.REFRESH.formatted(
                                viewName
                        )
                )
        );
    }
}
