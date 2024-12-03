package org.tutorhub.interfaces.postgres;

import org.tutorhub.interfaces.database.DatabaseCommonMethods;

public interface PostgresCheckpointMethodsInterface extends DatabaseCommonMethods {
    void completeCheckpoint();
}
