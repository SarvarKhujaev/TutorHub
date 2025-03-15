package org.tutorhub.inspectors.securityInspectors;

import org.tutorhub.inspectors.AnnotationInspector;
import org.tutorhub.constans.errors.Errors;

import org.passay.CharacterData;
import org.passay.*;

@org.tutorhub.annotations.services.ImmutableEntityAnnotation
@org.tutorhub.annotations.services.ServiceParametrAnnotation( propertyGroupName = "PASSAY_VARIABLES" )
public final class PassayPasswordGenerator {
    private static final int charactersLength = Integer.parseInt(
            AnnotationInspector.getVariable(
                    PassayPasswordGenerator.class,
                    "PASSWORD_LENGTH"
            )
    );

    private static final int passwordLength = charactersLength * 4;

    private static final PasswordValidator passwordValidator = new PasswordValidator(
            new LengthRule( passwordLength )
    );

    private static final PasswordGenerator passwordGenerator = new PasswordGenerator();

    private static final CharacterRule lowerCaseRule = new CharacterRule( EnglishCharacterData.LowerCase, charactersLength );
    private static final CharacterRule upperCaseRule = new CharacterRule( EnglishCharacterData.UpperCase, charactersLength );
    private static final CharacterRule splCharRule = new CharacterRule(
            new CharacterData() {
                public String getErrorCode() {
                    return Errors.SERVICE_WORK_ERROR.translate( "en" );
                }

                public String getCharacters() {
                    return AnnotationInspector.getVariable(
                            PassayPasswordGenerator.class,
                            "PASSWORD_REGULARS"
                    );
                }
            },
            charactersLength
    );

    private static final CharacterRule digitRule = new CharacterRule( EnglishCharacterData.Digit, charactersLength );

    @lombok.NonNull
    @lombok.Synchronized
    public static synchronized String generateStrongPassword () {
        return passwordGenerator.generatePassword(
                passwordLength,
                digitRule,
                splCharRule,
                lowerCaseRule,
                upperCaseRule
        );
    }

    @lombok.Synchronized
    public static synchronized boolean validatePassword (
            @lombok.NonNull final String password
    ) {
        return passwordValidator.validate( new PasswordData( password ) ).isValid();
    }
}
