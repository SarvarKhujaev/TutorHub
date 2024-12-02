package org.tutorhub.inspectors.encryption;

import org.apache.commons.lang3.Validate;
import java.text.MessageFormat;

import com.password4j.types.Bcrypt;
import com.password4j.*;

@SuppressWarnings(
        value = """
                https://github.com/Password4j/password4j/wiki/Scrypt <- ScryptFunction
                https://github.com/Password4j/password4j/wiki/BCrypt <- BcryptFunction
                """
)
@org.tutorhub.annotations.services.ImmutableEntityAnnotation
public final class BCryptInspector extends EncryptionParamsAndOptions {
    @SuppressWarnings(
            value = """
                    Name 	        Default value 	Properties 	        Description
                    Version 	    b 	            hash.bcrypt.minor 	Defines the minor version of bcrypt
                    Cost factor 	10 	            hash.bcrypt.rounds 	Defines the number of rounds expressed as exponent of base 2

                    ADD SALT
                    bcrypt calculates the salt by its own depending on the algorithm's parameters and it is not possible to specify a custom salt in order to avoid inconsistencies.
                    """
    )
    private final static BcryptFunction BCRYPT_FUNCTION = BcryptFunction.getInstance(
            /*
            Version a mis-handles characters with the 8th bit set, x was used by system administrators
            to mark those bad hashes and y is used for the fixed version of the algorithm.
            b solves issues with password with more than 255 characters.
            */
            Bcrypt.B,
            /*
            Defines the number of rounds expressed as exponent of base 2

            We always recommend at least 10 rounds.

            In this case you have created a singleton instance which uses version B of bcrypt and has a cost factor of 2 ** 12 = 4096.
            */
            Math.round( (float) ENCRYPTION_BYTE_SIZE / 11 )
    );

    @SuppressWarnings(
            value = """
                    Name 	                Default value 	Properties 	                    Description
                    Work factor (N) 	    65536 	        hash.scrypt.workfactor 	        Defines the CPU/memory cost. Must be a power of 2.
                    Resources (r) 	        8 	            hash.scrypt.resources 	        Defines the size of memory blocks.
                    Parallelisation (p)     1 	            hash.scrypt.parallelization 	Defines the cost of parallelisation for an attacker.
                    Output length 	        64 	            hash.scrypt.derivedKeyLength 	Defines the desired length of the final derived key


                    SCRYPT

                    Use it if you are sure that side-channel attacks are not possible in your target system.
                    Work Factor

                    Always use a work factor of 214 or greater.
                    Resources

                    8 should be optimal since cache line sizes haven't changed in the last years.

                    Parallelisation

                    1 should be optimal for most cases.

                    Responsiveness
                    If your application requires n milliseconds in order to hash a password and you don't know what work factor to use,
                    you can use the SystemChecker tool.
                    """
    )
    private final static ScryptFunction SCRYPT_FUNCTION = ScryptFunction.getInstance(
            // Defines the CPU/memory cost. Must be a power of 2.
            (int) Math.pow( 2, (double) ENCRYPTION_BYTE_SIZE / 8 ),
            // Defines the size of memory blocks.
            ENCRYPTION_BYTE_SIZE / 16,
            // Defines the cost of parallelisation for an attacker.
            ENCRYPTION_PARALLEL_NUMBER,
            // Defines the desired length of the final derived key
            ENCRYPTION_BYTE_SIZE / 2
    );

    private static BCryptInspector bCryptInspector = new BCryptInspector();

    @lombok.NonNull
    @lombok.Synchronized
    public static synchronized BCryptInspector getInstance () {
        return bCryptInspector != null ? bCryptInspector : ( bCryptInspector = new BCryptInspector() ) ;
    }

    private BCryptInspector () {
        super( BCryptInspector.class );
    }

    @Override
    @lombok.NonNull
    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_ -> !null" )
    @SuppressWarnings(
            value = """
                    Bcrypt uses 2 parameters: rounds and minor version.

                    More rounds you use and more secure against brute-force attacks;
                    this number should also depends on the desired responsiveness of your system.
                    The minor version should always be the latest one (2b), unless you need backward-compatibility.

                    In this example we will hash the password using 12 rounds and version 2b.
                    Note that bcrypt randomly generates by itself the salt: this means the hash will be always different for the same input.
                    """
    )
    public synchronized String encryptMessage( @lombok.NonNull final String password ) {
        Validate.isTrue( checkString( password ), this.getClass().getName() );

        return Password.hash( password )
                .addPepper( SECRET_KEY )
                .addRandomSalt( ENCRYPTION_BYTE_SIZE / 4 )
                .with( SCRYPT_FUNCTION )
                .getResult();
    }

    @Override
    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_, _ -> true" )
    public synchronized boolean verify(
            // пароль введенный пользователем
            @lombok.NonNull final String rawPassword,
            // хэшированный пароль
            @lombok.NonNull final String encryptedPassword
    ) {
        Validate.isTrue( checkString( encryptedPassword ), this.getClass().getName() );

        /*
        Alternatively, you may not specify the configuration used and let Password4j
        retrieve the data from the hash with Password.getInstanceFromHash
        */
        return Password.check( rawPassword, encryptedPassword )
                .addPepper( SECRET_KEY )
                .with( ScryptFunction.getInstanceFromHash( encryptedPassword ) );
    }

    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_, _ -> !null" )
    public synchronized String updatePassword (
            @lombok.NonNull final String rawPassword,
            // хэшированный пароль
            @lombok.NonNull final String encryptedPassword
    ) {
        Validate.isTrue( checkString( encryptedPassword ), this.getClass().getName() );

        final HashUpdate hashUpdate = Password.check( rawPassword, encryptedPassword )
                .addPepper( SECRET_KEY )
                .andUpdate()
                .addNewRandomSalt()
                .with( BCRYPT_FUNCTION, SCRYPT_FUNCTION );

        return hashUpdate.isVerified()
                ? hashUpdate.getHash().getResult()
                : EMPTY;
    }

    @lombok.Synchronized
    @SuppressWarnings(
            value = """
                    проводит рассчеты и находит самые оптимальные настройки
                    для алгоритма шифрования
                    """
    )
    public synchronized void calculateOptimalSettings (
            // максимальное время за которое алгоритм должен провести шифрование
            final long requiredTimeInMillis
    ) {
        final BenchmarkResult< BcryptFunction > benchmarkResult = SystemChecker.benchmarkBcrypt( requiredTimeInMillis );
        super.logging(
                MessageFormat.format(
                        """
                        Rounds quantity: {0}
                        BCryption code type: {1}
                        Elsapsed time: {2}
                        """,
                        benchmarkResult.getPrototype().getLogarithmicRounds(),
                        benchmarkResult.getPrototype().getType(),
                        benchmarkResult.getElapsed()
                )
        );

        final BenchmarkResult< ScryptFunction > benchmarkResultForScrypt = SystemChecker.findWorkFactorForScrypt(
                requiredTimeInMillis,
                SCRYPT_FUNCTION.getResources(),
                SCRYPT_FUNCTION.getParallelization()
        );

        super.logging(
                MessageFormat.format(
                        """
                        DerivedKeyLength: {0}
                        Parallelization: {1}
                        RequiredMemory: {2}
                        RequiredBytes: {3}
                        Elsapsed Time: {4}
                        """,
                        benchmarkResultForScrypt.getPrototype().getDerivedKeyLength(),
                        benchmarkResultForScrypt.getPrototype().getParallelization(),
                        benchmarkResultForScrypt.getPrototype().getRequiredMemory(),
                        benchmarkResultForScrypt.getPrototype().getRequiredBytes(),
                        benchmarkResultForScrypt.getElapsed()
                )
        );
    }

    @Override
    public void close () {
        bCryptInspector = null;
        super.logging( this );
    }
}
