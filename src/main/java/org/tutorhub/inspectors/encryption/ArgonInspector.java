package org.tutorhub.inspectors.encryption;

import org.tutorhub.annotations.LinksToDocs;
import org.apache.commons.lang3.Validate;

import de.mkammerer.argon2.Argon2Advanced;
import de.mkammerer.argon2.Argon2Factory;
import de.mkammerer.argon2.Argon2Version;

@SuppressWarnings(
        value = """
                воплощение всех базовых функций библиотеки Argon2

                Argon2 uses 5 (optionally 6) parameters: memory, number of iterations, parallelisation, length and type.

                OWASP recommends using it with a minimum memory of 15MiB, 2 iterations, 1 degree of parallelisation, 32 bytes of length and type Argon2id.
                """
)
@org.tutorhub.annotations.services.ImmutableEntityAnnotation
public final class ArgonInspector extends EncryptionParamsAndOptions {
    private final static Argon2Advanced ARGON_2_ADVANCED = Argon2Factory.createAdvanced(
            Argon2Factory.Argon2Types.ARGON2id,
            ENCRYPTION_BYTE_SIZE / 8,
            ENCRYPTION_BYTE_SIZE / 4
    );

    private final static Argon2Version ARGON_2_VERSION = Argon2Version.V13;
    private final static byte[] salt = ARGON_2_ADVANCED.generateSalt( ENCRYPTION_BYTE_SIZE / 8 );

    private static ArgonInspector argonInspector = new ArgonInspector();

    @lombok.NonNull
    @lombok.Synchronized
    public static synchronized ArgonInspector getInstance() {
        return argonInspector != null
                ? argonInspector
                : ( argonInspector = new ArgonInspector() );
    }

    private ArgonInspector () {
        super( ArgonInspector.class );
    }

    @Override
    @lombok.NonNull
    @org.jetbrains.annotations.Contract( value = "_ -> !null" )
    @LinksToDocs(
            links = "https://mkyong.com/java/java-password-hashing-with-argon2/"
    )
    @SuppressWarnings(
            value = """
                   The Argon2 algorithm has three variants:
                    Argon2d, maximizes resistance to GPU cracking attacks, suitable for Cryptocurrency.
                    Argon2i, optimized to resist side-channel attacks, suitable for password hashing.
                    Argon2id, hybrid version, if not sure, picks this.

                   Salt length — The length of the random salt, recommends 16 bytes.
                   Key length — The length of the generated hash, recommends 16 bytes, but the majority prefer 32 bytes.
                   Iterations — Number of iterations, affect the time cost.
                   Memory — The amount of memory used by the algorithm (in kibibytes, 1k = 1024 bytes), affect the memory cost.
                   Parallelism — Number of threads (or lanes) used by the algorithm, affect the degree of parallelism.
                   """
    )
    public String encryptMessage(
            // пароль введенный пользователем
            @lombok.NonNull final String rawPassword
    ) {
        Validate.isTrue( checkString( rawPassword ), this.getClass().getName() );

        return convertBytesToString(
                ARGON_2_ADVANCED.rawHashAdvanced(
                        /*
                        количество циклов для обработки
                        чем больше тем лучше качество хэширования
                        */
                        ENCRYPTION_BYTE_SIZE / 8,
                        /*
                        размер памяти выделяемый для хэширования ( в килобайтах )
                        чем больше тем надежнее результат, но больше времени на обработку
                        */
                        ITERATION_COUNT,
                        /*
                        количество параллелей для обработки
                        чем больше тем ниже качество хэширования
                        */
                        ENCRYPTION_PARALLEL_NUMBER,
                        convertToBytes( rawPassword ),
                        salt,
                        convertToBytes( SECRET_KEY ),
                        // дата создания хэша
                        convertToBytes( super.convertDate( SECRET_DATE ).toString() ),
                        // длина хэша
                        ENCRYPTION_BYTE_SIZE / 4,
                        ARGON_2_VERSION
                )
        );
    }

    @Override
    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_, _ -> true" )
    public synchronized boolean verify (
            // пароль введенный пользователем
            @lombok.NonNull final String rawPassword,
            // хэшированный пароль из БД
            @lombok.NonNull final String encryptedPassword
    ) {
        Validate.isTrue( checkString( rawPassword ), this.getClass().getName() );
        Validate.isTrue( checkString( encryptedPassword ), this.getClass().getName() );

        return ARGON_2_ADVANCED.verifyAdvanced(
                /*
                количество циклов для обработки
                чем больше тем лучше качество хэширования
                */
                ENCRYPTION_BYTE_SIZE / 8,
                /*
                размер памяти выделяемый для хэширования ( в килобайтах )
                чем больше тем надежнее результат, но больше времени на обработку
                */
                ITERATION_COUNT,
                /*
                количество параллелей для обработки
                чем больше тем ниже качество хэширования
                */
                ENCRYPTION_PARALLEL_NUMBER,
                convertToBytes( rawPassword ),
                salt,
                convertToBytes( SECRET_KEY ),
                // дата создания хэша
                convertToBytes( super.convertDate( SECRET_DATE ).toString() ),
                // длина хэша
                ENCRYPTION_BYTE_SIZE / 4,
                ARGON_2_VERSION,
                convertStringToBytes( encryptedPassword )
        );
    }

    @Override
    public void close () {
        argonInspector = null;
        super.logging( this );
    }
}
