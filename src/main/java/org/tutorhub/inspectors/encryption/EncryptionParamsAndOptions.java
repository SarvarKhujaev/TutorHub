package org.tutorhub.inspectors.encryption;

import org.tutorhub.annotations.entity.constructor.EntityConstructorAnnotation;
import org.tutorhub.interfaces.encryption.EncryptionCommonMethods;
import org.tutorhub.inspectors.AnnotationInspector;

import java.security.SecureRandom;

@SuppressWarnings( value = "хранит все параметры необходимые для шифрования" )
@org.tutorhub.annotations.services.ImmutableEntityAnnotation
@org.tutorhub.annotations.services.ServiceParametrAnnotation( propertyGroupName = "ENCRYPTION_VARIABLES" )
public sealed class EncryptionParamsAndOptions
        extends AnnotationInspector
        implements EncryptionCommonMethods
        permits ArgonInspector,
        BCryptInspector,
        EncryptionInspector,
        SignatureEncryptionInspector {
    @EntityConstructorAnnotation(
            permission = {
                    ArgonInspector.class,
                    BCryptInspector.class,
                    EncryptionInspector.class,
                    SignatureEncryptionInspector.class
            },
            isSealed = true
    )
    protected <T extends EncryptionParamsAndOptions> EncryptionParamsAndOptions ( @lombok.NonNull final Class<T> instance ) {
        checkCallerPermission( instance, EncryptionParamsAndOptions.class );
        checkAnnotationIsImmutable( instance );
    }

    @SuppressWarnings( value = "размер байтов для шифрования" )
    protected final static int ENCRYPTION_BYTE_SIZE = getVariable(
            EncryptionParamsAndOptions.class,
            1,
            128
    );

    @SuppressWarnings( value = "название алгоритма шифрования" )
    protected final static String PROTOCOL_TYPE = getVariable(
            EncryptionParamsAndOptions.class,
            EncryptionParamsAndOptions.class.getDeclaredFields()[2].getName()
    );

    protected final static String KEY_PAIR_GENERATOR_PROTOCOL = getVariable(
            EncryptionParamsAndOptions.class,
            EncryptionParamsAndOptions.class.getDeclaredFields()[3].getName()
    );

    protected final static String SECRET_KEY_FACTORY_PROTOCOL = getVariable(
            EncryptionParamsAndOptions.class,
            EncryptionParamsAndOptions.class.getDeclaredFields()[4].getName()
    );

    protected final static String CIPHER_PROTOCOL_TYPE_SYMMETRIC = getVariable(
            EncryptionParamsAndOptions.class,
            EncryptionParamsAndOptions.class.getDeclaredFields()[5].getName()
    );

    protected final static String SIGNATURE_PROTOCOL = getVariable(
            EncryptionParamsAndOptions.class,
            EncryptionParamsAndOptions.class.getDeclaredFields()[6].getName()
    );

    protected final static String KEY_PAIR_SIGNATURE_PROTOCOL = getVariable(
            EncryptionParamsAndOptions.class,
            EncryptionParamsAndOptions.class.getDeclaredFields()[7].getName()
    );

    protected final static int ITERATION_COUNT = getVariable(
            EncryptionParamsAndOptions.class,
            65536,
            8
    );

    protected final static String SECRET_KEY = getVariable(
            EncryptionParamsAndOptions.class,
            EncryptionParamsAndOptions.class.getDeclaredFields()[9].getName()
    );

    protected final static String SECRET_DATE = getVariable(
            EncryptionParamsAndOptions.class,
            EncryptionParamsAndOptions.class.getDeclaredFields()[10].getName()
    );

    protected final static String MESSAGE_DIGEST_ALGORITHM = getVariable(
            EncryptionParamsAndOptions.class,
            EncryptionParamsAndOptions.class.getDeclaredFields()[11].getName()
    );

    @SuppressWarnings( value = "размер байтов для шифрования" )
    protected final static int ENCRYPTION_PARALLEL_NUMBER = getVariable(
            EncryptionParamsAndOptions.class,
            1,
            12
    );

    protected final static SecureRandom SECURE_RANDOM = new SecureRandom( new byte[ ENCRYPTION_BYTE_SIZE ] );

    protected final static String SECRET_KEY_FOR_IV_SPEC = getVariable(
            EncryptionParamsAndOptions.class,
            EncryptionParamsAndOptions.class.getDeclaredFields()[12].getName()
    );

    @Override
    public void close () {
        super.clearReference( SignatureEncryptionInspector.getInstance() );
        super.clearReference( EncryptionInspector.getInstance() );
        super.clearReference( BCryptInspector.getInstance() );
        super.clearReference( ArgonInspector.getInstance() );
        super.logging( this );
        this.clean();
    }
}
