package org.tutorhub.interfaces.postgres;

import org.tutorhub.interfaces.database.DatabaseCommonMethods;

@SuppressWarnings( value = "хранит методы для создания расширений в PostgreSql" )
public interface PostgresExtensionsRegisterInterface extends DatabaseCommonMethods {
    @SuppressWarnings( value = "создает расширение в БД" )
    void createExtension();
}
