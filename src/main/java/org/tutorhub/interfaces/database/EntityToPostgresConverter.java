package org.tutorhub.interfaces.database;

import org.tutorhub.interfaces.services.ServiceCommonMethods;
import org.tutorhub.inspectors.AnnotationInspector;

public interface EntityToPostgresConverter extends ServiceCommonMethods {
    @lombok.NonNull
    default String getEntityTableName () {
        return AnnotationInspector.getEntityKeyspaceOrTableName(
                this,
                false
        );
    }

    @lombok.NonNull
    default String getEntityKeyspaceName () {
        return AnnotationInspector.getEntityKeyspaceOrTableName(
                this,
                true
        );
    }
}
