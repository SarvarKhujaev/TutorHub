package org.tutorhub.constans.postgres_constants;

public final class PostgreSqlFunctions {
    public static final String NOW = "TIMESTAMP DEFAULT now()";

    /*
    In PostgreSQL, you can use the character_length function to get the length of a text field. The syntax for this function is:
    */
    public static final String TEXT_LENGTH = """
            character_length( %s ) %s
            """;

    public static final String SUB_STR = "substr( %s, %d, %d ) = '%s'";
}
