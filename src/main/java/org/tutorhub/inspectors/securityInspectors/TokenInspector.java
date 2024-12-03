package org.tutorhub.inspectors.securityInspectors;

import org.tutorhub.annotations.entity.constructor.EntityConstructorAnnotation;
import org.tutorhub.inspectors.dataTypesInpectors.UuidInspector;
import org.tutorhub.inspectors.AnnotationInspector;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Jwts;

import java.util.UUID;
import java.util.Map;

@org.tutorhub.annotations.services.ImmutableEntityAnnotation
@org.tutorhub.annotations.services.ServiceParametrAnnotation
public class TokenInspector extends AnnotationInspector {
    private final static String ISSUER = getVariable(
            TokenInspector.class,
            TokenInspector.class.getDeclaredFields()[1].getName()
    );
    private final static String SECRET = getVariable(
            TokenInspector.class,
            TokenInspector.class.getDeclaredFields()[2].getName()
    );

    public TokenInspector () {
        super( TokenInspector.class );
    }

    @EntityConstructorAnnotation
    public <T extends UuidInspector> TokenInspector (@lombok.NonNull final Class<T> instance ) {
        super( TokenInspector.class );

        AnnotationInspector.checkCallerPermission( instance, TokenInspector.class );
        AnnotationInspector.checkAnnotationIsImmutable( TokenInspector.class );
    }

    @lombok.NonNull
    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_ -> !null" )
    private synchronized Map< String, Object > generateParamsForJWTToken (
    ) {
        return Map.of();
    }

    @lombok.NonNull
    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_ -> !null" )
    @SuppressWarnings(
            value = "генерирует рандомный токен с вшитыми данными пользователя"
    )
    public final synchronized String generateToken () {
        return Jwts
                .builder()
                .issuer( ISSUER )
//                .claims( this.generateParamsForJWTToken( patrul ) )
//                .subject( patrul.getEmail() )
                .issuedAt( newDate() )
                .expiration( super.getExpirationDate() )
                .signWith(
                        SignatureAlgorithm.HS512,
                        String.join( EMPTY, SECRET, ISSUER )
                ).compact();
    }

    @SuppressWarnings(
            value = "принимает токен и возвращает JWT значение"
    )
    @lombok.NonNull
    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_ -> !null" )
    public final synchronized UUID decode (
            @lombok.NonNull final String token
    ) {
        return convertStringToUuid(
                Jwts
                        .parser()
                        .setSigningKey( String.join( EMPTY, SECRET, ISSUER ) )
                        .build()
                        .parseClaimsJws( token )
                        .getPayload()
                        .get( "id" )
                        .toString()
        );
    }
}
