package org.tutorhub.inspectors.encryption;

import org.apache.commons.lang3.Validate;
import java.nio.charset.StandardCharsets;
import java.security.*;

@org.tutorhub.annotations.services.ImmutableEntityAnnotation
public final class SignatureEncryptionInspector extends EncryptionParamsAndOptions {
    private static final KeyPair keyPair;
    private static final Signature signature;

    private static byte[] signatureBytes;

    static {
        try {
            // Generate a key pair for Alice
            final KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance( KEY_PAIR_SIGNATURE_PROTOCOL );
            keyPairGenerator.initialize( ENCRYPTION_BYTE_SIZE * 8, SECURE_RANDOM );

            keyPair = keyPairGenerator.generateKeyPair();

            signature = Signature.getInstance( SIGNATURE_PROTOCOL );
            signature.initSign( keyPair.getPrivate(), SECURE_RANDOM );
        } catch ( final NoSuchAlgorithmException | InvalidKeyException e ) {
            throw new RuntimeException( e );
        }
    }

    private static SignatureEncryptionInspector signatureEncryptionInspector = new SignatureEncryptionInspector();

    @lombok.NonNull
    @lombok.Synchronized
    public static synchronized SignatureEncryptionInspector getInstance() {
        return signatureEncryptionInspector != null
                ? signatureEncryptionInspector
                : ( signatureEncryptionInspector = new SignatureEncryptionInspector() );
    }

    private SignatureEncryptionInspector () {
        super( SignatureEncryptionInspector.class );
    }

    @org.jetbrains.annotations.Contract( value = "_ -> !null" )
    public synchronized byte[] generateDigitalSignature( @lombok.NonNull final String message ) {
        Validate.isTrue( checkString( message ), this.getClass().getName() );

        try {
            signature.initSign( keyPair.getPrivate() );
            signature.update( message.getBytes( StandardCharsets.UTF_8 ) );
            signatureBytes = signature.sign();
            return signatureBytes;
        } catch ( final SignatureException | InvalidKeyException e ) {
            super.logging( e );
            return new byte[] {};
        }
    }

    @org.jetbrains.annotations.Contract( value = "_ -> true" )
    public synchronized boolean verifyDigitalSignature( @lombok.NonNull final String encryptedMessage ) {
        Validate.isTrue( checkString( encryptedMessage ), this.getClass().getName() );
        Validate.isTrue( signatureBytes != null && signatureBytes.length > 0, "signatureBytes is empty" );

        try {
            signature.initVerify( keyPair.getPublic() );
            signature.update( encryptedMessage.getBytes( StandardCharsets.UTF_8 ) );
            return signature.verify( signatureBytes );
        } catch ( final InvalidKeyException | SignatureException e ) {
            super.logging( e );
            return false;
        }
    }

    @Override
    public void close () {
        signatureEncryptionInspector = null;
        super.logging( this );
    }
}
