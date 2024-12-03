package org.tutorhub.constans.postgres_constants.postgres_prepared_constants;

import com.university.universityapplication.constans.postgres_constants.PostgreSqlSchema;
import com.university.universityapplication.constans.postgres_constants.PostgreSqlTables;
import com.university.universityapplication.constans.postgres_constants.PostgresCommonCommands;

public final class PostgresPreparedQueries {
    public final static String GET_FILTERED_TEACHERS = String.join(
            " ",
            PostgresCommonCommands.SELECT_FROM,
            String.join(
                    ".",
                    PostgreSqlSchema.UNIVERSITY,
                    PostgreSqlTables.TEACHERS
            ),
            "WHERE name = $1 OR email = $2"
    );
}
