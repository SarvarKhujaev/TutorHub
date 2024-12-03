package org.tutorhub.database.postgresConfigs;

import org.tutorhub.constans.postgres_constants.postgres_index_constants.PostgresIndexesNames;
import org.tutorhub.constans.postgres_constants.postgres_index_constants.PostgresIndexParams;
import org.tutorhub.inspectors.enttiesInspectors.EntitiesInstances;

@SuppressWarnings( value = "регистрирует все индексы по всем таблицам" )
public sealed class PostgresIndexesRegister extends PostgresExtensionsRegister permits PostgresStatisticsTableRegister {
    public PostgresIndexesRegister () {
        super();

        this.createIndex();
    }

    @SuppressWarnings( value = "создает индексы" )
    private void createIndex() {
        super.analyze(
                EntitiesInstances.INDEX_CREATE_QUIRES,
                indexQuery -> super.logging(
                        this.getSession().createNativeQuery( indexQuery ).getQueryString()
                )
        );
    }

    @SuppressWarnings(
            value = """
                    применяет Reindex на все индексы в таблицах
                    для очищения индексов от пустых и не используемых значений
                    """
    )
    public void reIndex() {
        super.analyze(
                super.newList( PostgresIndexesNames.values() ),
                postgresIndexesName -> super.logging(
                        this.getSession().createNativeQuery(
                                PostgresIndexParams.REINDEX.formatted(
                                        postgresIndexesName
                                )
                        ).getQueryString()
                )
        );
    }

    public void reIndex ( @lombok.NonNull final String indexName ) {
        super.logging(
                this.getSession().createNativeQuery(
                        PostgresIndexParams.REINDEX.formatted( indexName )
                ).getQueryString()
        );
    }
}
