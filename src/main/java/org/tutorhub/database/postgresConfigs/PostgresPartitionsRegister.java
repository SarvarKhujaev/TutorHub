package org.tutorhub.database.postgresConfigs;

import com.university.universityapplication.constans.postgres_constants.PostgreSqlSchema;
import com.university.universityapplication.constans.postgres_constants.postgres_partition_constants.PostgresPartitionMethods;
import com.university.universityapplication.inspectors.LogInspector;
import com.university.universityapplication.interfaces.PostgresPartitionsRegisterInterface;
import org.hibernate.Session;

public final class PostgresPartitionsRegister extends LogInspector implements PostgresPartitionsRegisterInterface {
    private final Session session;

    private Session getSession() {
        return this.session;
    }

    public static PostgresPartitionsRegister generate (
            final Session session
    ) {
        return new PostgresPartitionsRegister( session );
    }

    private PostgresPartitionsRegister (
            final Session session
    ) {
        this.session = session;
    }

    @Override
    public void registerPartitionsByRange () {
        /*
        определяем текущий год
        */
        final int year = super.getCurrentYear();

        super.analyze(
                super.getRangePartitionsTablesList(), // берем название нужных таблиц
                tableName -> super.analyze(
                        super.getListOfMonths(),
                        month -> {
                            final String temp = PostgresPartitionMethods.CREATE_RANGE_PARTITION_TABLE.formatted(
                                    PostgreSqlSchema.UNIVERSITY,
                                    super.getYearAndMonthConvertedValue(
                                            tableName,
                                            month,
                                            year
                                    ),

                                    PostgreSqlSchema.UNIVERSITY,
                                    tableName,

                                    super.getMonthStartOrEnd(
                                            true,
                                            month
                                    ),

                                    super.getMonthStartOrEnd(
                                            false,
                                            month
                                    )
                            );

                            System.out.println( temp );
                        }
                )
        );
    }

    @Override
    public void registerPartitionsByList () {
        super.analyze(
                super.getListPartitionsTablesList(), // берем название нужных таблиц
                tableName -> this.getSession().createNativeQuery(
                        PostgresPartitionMethods.CREATE_LIST_PARTITION_TABLE.formatted(
                                PostgreSqlSchema.UNIVERSITY,
                                tableName,

                                PostgreSqlSchema.UNIVERSITY,
                                tableName,

                                "test"
                        )
                )
        );
    }

    @Override
    public void registerPartitionsByHash () {
        super.analyze(
                super.getHashPartitionsTablesList(), // берем название нужных таблиц
                tableName -> this.getSession().createNativeQuery(
                        PostgresPartitionMethods.CREATE_HASH_PARTITION_TABLE.formatted(
                                PostgreSqlSchema.UNIVERSITY,
                                tableName,

                                PostgreSqlSchema.UNIVERSITY,
                                tableName,

                                3,
                                2
                        )
                )
        );
    }
}
