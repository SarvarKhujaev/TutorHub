package org.tutorhub.inspectors.sqlInjection;

import org.tutorhub.inspectors.dataTypesInpectors.StringOperations;
import org.tutorhub.inspectors.enttiesInspectors.EntitiesInstances;
import org.tutorhub.interfaces.services.ServiceCommonMethods;
import org.tutorhub.inspectors.AnnotationInspector;

import org.tutorhub.annotations.LinksToDocs;
import org.tutorhub.constans.errors.Errors;

import org.apache.commons.lang3.Validate;

import java.util.concurrent.atomic.AtomicReference;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import java.nio.file.Files;
import java.nio.file.Path;

import java.io.IOException;
import java.io.File;

@LinksToDocs(
        links = {
                "https://cheatsheetseries.owasp.org/cheatsheets/SQL_Injection_Prevention_Cheat_Sheet.html",
                "https://www.crowdstrike.com/en-us/cybersecurity-101/cyberattacks/sql-injection-attack/",
                "https://www.digitalocean.com/community/tutorials/sql-injection-in-java",
                "https://www.baeldung.com/sql-injection"
        }
)
@SuppressWarnings(
        value = """
                in-band SQL injection, inferential SQL injection and out-of-band SQL injection.

                1. In-band SQL Injection

                In-band SQL injection is the most common type of attack. With this type of SQL injection attack,
                a malicious user uses the same communication channel for the attack and to gather results.
                The following techniques are the most common types of in-band SQL injection attacks:
                    1) Error-based SQL injection: With this technique, attackers gain information about the database structure
                        when they use a SQL command to generate an error message from the database server.
                        Error messages are useful when developing a web application or web page,
                        but they can be a vulnerability later because they expose information about the database.
                        To prevent this vulnerability, you can disable error messages after a website or application is live.

                    2) Union-based SQL injection: With this technique, attackers use the UNION SQL operator to combine
                    multiple select statements and return a single HTTP response. An attacker can use this technique
                    to extract information from the database. This technique is the most common type of SQL injection
                    and requires more security measures to combat than error-based SQL injection.

                2. Inferential SQL Injection

                Inferential SQL injection is also called blind SQL injection because the website database doesn’t transfer data
                to the attacker like with in-band SQL injection. Instead, a malicious user can learn about the structure of the
                server by sending data payloads and observing the response. Inferential SQL injection attacks are less common
                than in-band SQL injection attacks because they can take longer to complete. The two types of inferential
                SQL injection attacks use the following techniques:

                    1) Boolean injection: With this technique, attackers send a SQL query to the database and observe the result.
                    Attackers can infer if a result is true or false based on whether the information in the HTTP response was modified.

                    2) Time-based injection: With this technique, attackers send a SQL query to the database,
                    making the database wait a specific number of seconds before responding.
                    Attackers can determine if the result is true or false based on the number of seconds that elapses before a response.
                    For example, a hacker could use a SQL query that commands a delay if the first letter of the first database’s name is A.
                    Then, if the response is delayed, the attacker knows the query is true.

                3. Out-of-Band SQL Injection
                    Out-of-band SQL injection is the least common type of attack. With this type of SQL injection attack,
                    malicious users use a different communication channel for the attack than they use to gather results.
                    Attackers use this method if a server is too slow or unstable to use inferential SQL injection or in-band SQL injection.
                """
)
@org.tutorhub.annotations.services.ImmutableEntityAnnotation
public final class SqlInjectionInspector extends AnnotationInspector implements ServiceCommonMethods {
    private final static Set< String > sqlSqlInjectionPayloads = new HashSet<>( 0 );
    private final static AtomicReference< String > STRING_ATOMIC_REFERENCE = EntitiesInstances.generateAtomicEntity( EMPTY );

    private static volatile SqlInjectionInspector sqlInjectionInspector = new SqlInjectionInspector();

    @lombok.NonNull
    @lombok.Synchronized
    public static synchronized SqlInjectionInspector getInstance() {
        return sqlInjectionInspector != null ? sqlInjectionInspector : ( sqlInjectionInspector = new SqlInjectionInspector() );
    }

    private SqlInjectionInspector () {
        super.analyze(
                new File( SQL_INJECTION_FILES_PATH ).listFiles(),
                file -> file.exists() && file.isFile() && file.canRead(),
                file -> {
                    try {
                        super.analyze(
                                Files.readString( Path.of( file.getAbsolutePath() ) ).split( "\n" ),
                                StringOperations::checkString,
                                s -> sqlSqlInjectionPayloads.add( s.trim().replaceAll( SPACE, EMPTY ).toUpperCase() )
                        );
                    } catch ( final IOException e ) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }

    @lombok.Synchronized
    @SuppressWarnings(
            value = """
                    в случае если запрос содержит знак '
                    то метод вызывается для проверки обшего количества таких знаков
                    если общее количество не четное, то в запросе определенно находиться ошибка
                    """
    )
    private synchronized static void checkQuoteQuantity () {
        byte count = 0;
        int pos = STRING_ATOMIC_REFERENCE.get().indexOf( SINGLE_QUOTE );

        while ( pos != -1 ) {
            count++;
            pos = STRING_ATOMIC_REFERENCE.get().indexOf( SINGLE_QUOTE, pos + 1 );
        }

        Validate.isTrue(
                count % 2 == 0,
                Errors.SQL_INJECTION_WAS_INSERTED.translate(
                        STRING_ATOMIC_REFERENCE.get(),
                        SINGLE_QUOTE
                )
        );
    }

    @lombok.Synchronized
    @SuppressWarnings(
            value = """
                    принимает значение или запрос и проверяет что в нем не содержаться не желательные значения
                    в случае если метод найдет что-нибудь
                    то вызывается исключение с описанием ошибки и с каким параметром из списка
                    Sql инъенкий было найдено совпадение

                    После чего выполнение останавливается
                    """
    )
    public synchronized void checkInjection (
            // значение введенное пользователем
            @lombok.NonNull String inputParam
    ) {
        Validate.isTrue(
                checkString( inputParam ),
                NULL_VALUE_IN_ASSERT
        );

        STRING_ATOMIC_REFERENCE.getAndSet( inputParam.replaceAll( SPACE, EMPTY ).toUpperCase( Locale.ROOT ) );

        super.analyze(
                sqlSqlInjectionPayloads,
                sqlInjection -> {
                    if ( STRING_ATOMIC_REFERENCE.get().contains( SINGLE_QUOTE ) ) checkQuoteQuantity();

                    Validate.isTrue(
                            !STRING_ATOMIC_REFERENCE.get().contains( sqlInjection ),
                            Errors.SQL_INJECTION_WAS_INSERTED.translate(
                                    inputParam,
                                    sqlInjection
                            )
                    );
                }
        );
    }

    @lombok.Synchronized
    @SuppressWarnings(
            value = """
                    принимает значение или запрос и проверяет что в нем не содержаться не желательные значения
                    в случае если метод найдет что-нибудь
                    то вызывается исключение с описанием ошибки и с каким параметром из списка
                    Sql инъенкий было найдено совпадение

                    После чего выполнение останавливается
                    """
    )
    public synchronized <T> void checkInjection (
            // значение введенное пользователем
            @lombok.NonNull T entity
    ) {
        Validate.isTrue( super.objectIsNotNull( entity ) );

        analyze(
                getFields( entity.getClass() ),
                field -> {
                    try {
                        field.setAccessible( true );
                        org.springframework.util.ReflectionUtils.makeAccessible( field );

                        if ( field.get( entity ) instanceof String ) {
                            this.checkInjection( String.valueOf( field.get( entity ) ) );
                        } else if (
                                super.objectIsNotNull( field.get( entity ) )
                                        && AnnotationInspector.isFieldSqlDangerous( field )
                        ) {
                            this.checkInjection( (T) field.get( entity ) );
                        }
                    } catch ( final IllegalAccessException e ) {
                        super.logging( e );
                    }
                }
        );
    }

    public synchronized void checkInjection (
            // значение введенное пользователем
            @lombok.NonNull Object ... inputParam
    ) {
        super.analyze(
                inputParam,
                o -> o instanceof String,
                o -> this.checkInjection( String.valueOf( o ) )
        );
    }

    @Override
    public void close () {
        checkAndClear( sqlSqlInjectionPayloads );
        sqlInjectionInspector = null;
    }
}
