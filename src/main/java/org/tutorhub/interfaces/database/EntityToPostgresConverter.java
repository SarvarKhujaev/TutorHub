package org.tutorhub.interfaces.database;

import org.tutorhub.constans.postgres_constants.PostgreSqlTables;
import org.tutorhub.interfaces.services.ServiceCommonMethods;
import org.tutorhub.inspectors.AnnotationInspector;

public interface EntityToPostgresConverter extends ServiceCommonMethods {
    @lombok.NonNull
    default PostgreSqlTables getEntityTableName () {
        return AnnotationInspector.getEntityKeyspaceOrTableName(
                this,
                false
        );
    }

    @lombok.NonNull
    default PostgreSqlTables getEntityKeyspaceName () {
        return AnnotationInspector.getEntityKeyspaceOrTableName(
                this,
                true
        );
    }
}
