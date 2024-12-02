package org.tutorhub.inspectors.encryption;

import org.tutorhub.inspectors.securityInspectors.PassayPasswordGenerator;
import org.tutorhub.interfaces.database.EntityToCassandraConverter;
import org.tutorhub.inspectors.SerDes;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.Validate;

import java.security.spec.InvalidKeySpecException;
import java.security.*;

import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.KeyAgreement;
import javax.crypto.Cipher;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.PBEKeySpec;

@SuppressWarnings( value = "хранит весь функционал для симметричного шифрования" )
@org.tutorhub.annotations.services.ImmutableEntityAnnotation
public final class EncryptionInspector extends EncryptionParamsAndOptions {
    private final static Cipher CIPHER;
    private final static KeyPair KEY_PAIR;
    private final static PBEKeySpec PBE_KEY_SPEC;
    private final static SecretKeySpec SECRET_KEY_SPEC;
    private final static IvParameterSpec ivParameterSpec = new IvParameterSpec( convertToBytes(SECRET_KEY_FOR_IV_SPEC) );

    private static EncryptionInspector ENCRYPTION_INSPECTOR = new EncryptionInspector();

    static {
        try {
            final KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance( KEY_PAIR_GENERATOR_PROTOCOL );
            keyPairGenerator.initialize( ENCRYPTION_BYTE_SIZE * 8, SECURE_RANDOM );

            KEY_PAIR = keyPairGenerator.generateKeyPair();

            CIPHER = Cipher.getInstance( CIPHER_PROTOCOL_TYPE_SYMMETRIC );

            PBE_KEY_SPEC = new PBEKeySpec(
                    generateSafePassword().toCharArray(),
                    convertToBytes( SECRET_KEY ),
                    ITERATION_COUNT,
                    ENCRYPTION_BYTE_SIZE
            );

            SECRET_KEY_SPEC = new SecretKeySpec(
                    SecretKeyFactory
                            .getInstance( SECRET_KEY_FACTORY_PROTOCOL )
                            .generateSecret( PBE_KEY_SPEC )
                            .getEncoded(),
                    PROTOCOL_TYPE
            );
        } catch ( final NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeySpecException e ) {
            throw new RuntimeException( e );
        }
    }

    @lombok.NonNull
    @lombok.Synchronized
    public static synchronized EncryptionInspector getInstance () {
        return ENCRYPTION_INSPECTOR != null ? ENCRYPTION_INSPECTOR : ( ENCRYPTION_INSPECTOR = new EncryptionInspector() ) ;
    }

    private EncryptionInspector () {
        super( EncryptionInspector.class );
    }

    @lombok.NonNull
    @lombok.Synchronized
    @SuppressWarnings( value = "генерирует рандомный набор байтов" )
    private static synchronized String generateSafePassword() {
        try {
            final KeyAgreement agreement = KeyAgreement.getInstance( KEY_PAIR_GENERATOR_PROTOCOL );
            agreement.init( KEY_PAIR.getPrivate(), SECURE_RANDOM );
            agreement.doPhase( KEY_PAIR.getPublic(), true );

            return org.apache.commons.codec.binary.Base64.encodeBase64String(
                    MessageDigest
                            .getInstance( MESSAGE_DIGEST_ALGORITHM )
                            .digest(
                                    SECRET_KEY.isBlank()
                                        ? convertToBytes(
                                                String.join(
                                                        EMPTY,
                                                        Hex.encodeHexString( agreement.generateSecret() ),
                                                        PassayPasswordGenerator.generateStrongPassword()
                                                )
                                        )
                                        : convertToBytes( SECRET_KEY )
                            )
            );
        } catch ( final NoSuchAlgorithmException | InvalidKeyException e ) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @lombok.NonNull
    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_ -> !null" )
    public synchronized String encryptMessage( @lombok.NonNull final String message ) {
        Validate.isTrue( checkString( message ), this.getClass().getName() );

        try {
            CIPHER.init( Cipher.ENCRYPT_MODE, SECRET_KEY_SPEC, ivParameterSpec, SECURE_RANDOM );
            return convertBytesToString( CIPHER.doFinal( convertToBytes( message ) ) );
        } catch ( final Exception e ) {
            super.logging( e );
            return EMPTY;
        }
    }

    @lombok.NonNull
    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_ -> !null" )
    public synchronized <T extends EntityToCassandraConverter> String encryptMessage (
            @lombok.NonNull final T entity
    ) {
        Validate.isTrue( super.objectIsNotNull( entity ), this.getClass().getName() );

        try {
            CIPHER.init( Cipher.ENCRYPT_MODE, SECRET_KEY_SPEC, ivParameterSpec, SECURE_RANDOM );
            return convertBytesToString(
                    CIPHER.doFinal(
                            convertToBytes( SerDes.serialize( entity ) )
                    )
            );
        } catch ( final Exception e ) {
            super.logging( e );
            return EMPTY;
        }
    }

    @Override
    @lombok.NonNull
    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_ -> !null" )
    public synchronized String decryptMessage( @lombok.NonNull final String encryptedMessage ) {
        Validate.isTrue( checkString( encryptedMessage ), this.getClass().getName() );

        try {
            CIPHER.init( Cipher.DECRYPT_MODE, SECRET_KEY_SPEC, ivParameterSpec, SECURE_RANDOM );
            return new String( CIPHER.doFinal( convertStringToBytes( encryptedMessage ) ) );
        } catch ( final Exception e ) {
            super.logging( e );
            return EMPTY;
        }
    }

    @Override
    public void close () {
        PBE_KEY_SPEC.clearPassword();
        ENCRYPTION_INSPECTOR = null;
        super.logging( this );
    }
}
