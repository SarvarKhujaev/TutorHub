package org.tutorhub.database.postgresConfigs;

import org.tutorhub.interfaces.postgres.PostgresCheckpointMethodsInterface;
import org.tutorhub.constans.postgres_constants.PostgresWalOperations;
import org.tutorhub.inspectors.LogInspector;

@SuppressWarnings( value = "отвечает за выполнение команды CHECKPOINT" )
public final class PostgresCheckpointRegister extends LogInspector implements PostgresCheckpointMethodsInterface {
    public PostgresCheckpointRegister () {
        this.completeCheckpoint();
    }

    @Override
    public void completeCheckpoint() {
        super.logging(
                this.getSession().createNativeQuery(
                        PostgresWalOperations.CHECKPOINT
                ).getQueryString()
        );
    }
}
