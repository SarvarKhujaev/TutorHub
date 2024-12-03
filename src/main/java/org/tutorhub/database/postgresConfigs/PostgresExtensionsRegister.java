package org.tutorhub.database.postgresConfigs;

import org.tutorhub.inspectors.LogInspector;
import org.tutorhub.inspectors.enttiesInspectors.EntitiesInstances;
import org.tutorhub.interfaces.postgres.PostgresExtensionsRegisterInterface;

public sealed class PostgresExtensionsRegister
        extends LogInspector
        implements PostgresExtensionsRegisterInterface
        permits PostgresIndexesRegister {
    public PostgresExtensionsRegister () {
        this.createExtension();
    }

    @Override
    public void createExtension() {
        super.analyze(
                EntitiesInstances.POSTGRES_EXTENSIONS,
                extension -> super.logging(
                        this.getSession().createNativeQuery( extension ).getQueryString()
                )
        );
    }
}
