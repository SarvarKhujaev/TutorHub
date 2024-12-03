package org.tutorhub.interfaces.postgres;

import org.tutorhub.constans.postgres_constants.PostgresVacuumMethods;
import org.tutorhub.interfaces.database.DatabaseCommonMethods;

public interface PostgresVacuumMethodsInterface extends DatabaseCommonMethods {
    /*
    очищаем таблицу от старых и не используемых записей
    */
    void vacuumTable ();

    void vacuumTable (
            final String tableName,
            final PostgresVacuumMethods vacuumMethod
    );
}
