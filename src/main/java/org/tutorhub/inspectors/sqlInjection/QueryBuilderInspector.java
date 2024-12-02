package org.tutorhub.inspectors.sqlInjection;

import org.tutorhub.inspectors.LogInspector;

@SuppressWarnings( value = "отвечает за обработку запросов" )
@org.tutorhub.annotations.services.ImmutableEntityAnnotation
public final class QueryBuilderInspector extends LogInspector {
    private static final QueryBuilderInspector QUERY_BUILDER_INSPECTOR = new QueryBuilderInspector();

    @lombok.NonNull
    @lombok.Synchronized
    public static synchronized QueryBuilderInspector getInstance() {
        return QUERY_BUILDER_INSPECTOR;
    }

    private QueryBuilderInspector () {
        super( QueryBuilderInspector.class );
    }
}
