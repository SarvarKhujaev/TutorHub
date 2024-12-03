package org.tutorhub.database.postgresConfigs;

import org.tutorhub.inspectors.enttiesInspectors.EntitiesInstances;

public final class PostgresPrepareStatementsRegister extends PostgresMaterializedViewRegister {
    public PostgresPrepareStatementsRegister () {
        super();
        this.prepareAllStatements();
    }

    public void prepareAllStatements() {
        super.analyze(
                EntitiesInstances.PREPARED_STATEMENTS,
                query -> this.getSession().createNativeQuery( query ).getQueryString()
        );
    }
}
