package org.tutorhub.interfaces.encryption;

import org.tutorhub.inspectors.dataTypesInpectors.StringOperations;
import org.tutorhub.interfaces.services.ServiceCommonMethods;

public interface EncryptionCommonMethods extends ServiceCommonMethods {
    @org.jetbrains.annotations.Contract( value = "_, _ -> false" )
    @SuppressWarnings(
            value = "принимает зашифрованный пароль и возвращает результат проверки на валидность"
    )
    default boolean verify (
            // пароль введенный пользователем
            @lombok.NonNull final String rawPassword,
            // хэшированный пароль из БД
            @lombok.NonNull final String encryptedPassword
    ) {
        return false;
    }

    @SuppressWarnings(
            value = """
                    принимает сообщение для шифрования и возвращает зашифрованную версию

                    You can use a hash function to map an arbitrary sized set of bytes into a finite size of a relatively unique set of bytes.
                    A well-engineered cryptographic hash function should use salt,
                    a string of random (or pseudo-random) bits concatenated with a key or password.
                    You can increase security by introducing an additional cryptographic variance using an initialization vector (IV)
                    for encryption of plaintext BLOCK sequence.
                    """
    )
    @lombok.NonNull
    @org.jetbrains.annotations.Contract( value = "_ -> !null" )
    default String convertBytesToString( final byte @lombok.NonNull [] bytes ) {
        return org.apache.commons.codec.binary.Base64.encodeBase64String( bytes );
    }

    @org.jetbrains.annotations.Contract( value = "_ -> !null" )
    default byte @lombok.NonNull [] convertStringToBytes( final @lombok.NonNull String encryptedMessage ) {
        return org.apache.commons.codec.binary.Base64.decodeBase64( encryptedMessage );
    }

    @lombok.NonNull
    @org.jetbrains.annotations.Contract( value = "_ -> !null" )
    @SuppressWarnings(
            value = """
                    принимает сообщение для шифрования и возвращает зашифрованную версию

                    You can use a hash function to map an arbitrary sized set of bytes into a finite size of a relatively unique set of bytes.
                    A well-engineered cryptographic hash function should use salt,
                    a string of random (or pseudo-random) bits concatenated with a key or password.
                    You can increase security by introducing an additional cryptographic variance using an initialization vector (IV)
                    for encryption of plaintext BLOCK sequence.
                    """
    )
    default String encryptMessage( @lombok.NonNull final String message ) {
        return StringOperations.EMPTY;
    }

    @lombok.NonNull
    @org.jetbrains.annotations.Contract( value = "_ -> !null" )
    @SuppressWarnings(
            value = """
                    принимает зашифрованное сообщение для расшифрования и возвращает расшифрованную версию
                    """
    )
    default String decryptMessage( @lombok.NonNull final String encryptedMessage ) {
        return StringOperations.EMPTY;
    }
}
