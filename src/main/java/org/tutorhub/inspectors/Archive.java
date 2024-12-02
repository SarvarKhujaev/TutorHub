package org.tutorhub.inspectors;

import org.tutorhub.annotations.entity.constructor.EntityConstructorAnnotation;
import org.tutorhub.constans.errors.Errors;

import org.tutorhub.inspectors.enttiesInspectors.EntitiesInstances;

import org.tutorhub.inspectors.dataTypesInpectors.StringOperations;
import org.tutorhub.inspectors.dataTypesInpectors.TimeInspector;
import org.tutorhub.inspectors.dataTypesInpectors.UuidInspector;

import org.tutorhub.response.ApiResponseModel;
import org.tutorhub.response.Data;

import reactor.core.publisher.Mono;
import java.util.Map;

@org.tutorhub.annotations.services.ImmutableEntityAnnotation
public class Archive extends StringOperations {
    @EntityConstructorAnnotation( permission = TimeInspector.class )
    protected <T extends UuidInspector> Archive ( @lombok.NonNull final Class<T> instance ) {
        super( Archive.class );

        AnnotationInspector.checkCallerPermission( instance, Archive.class );
        AnnotationInspector.checkAnnotationIsImmutable( Archive.class );
    }

    @lombok.NonNull
    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_ -> !null" )
    protected final synchronized <T> Mono< T > convert ( final T o ) {
        return Mono.justOrEmpty( o );
    }

    @lombok.NonNull
    @lombok.Synchronized
    @SuppressWarnings( value = "com.ssd.mvd.gpstabletsservice.entity.Data in function" )
    @org.jetbrains.annotations.Contract( value = "_ -> !null" )
    protected final synchronized Mono< ApiResponseModel > function (
            @lombok.NonNull final Map< String, ? > map
    ) {
        return this.convert(
                EntitiesInstances.API_RESPONSE_MODEL_ATOMIC_REFERENCE.getAndUpdate(
                        apiResponseModel -> {
                            apiResponseModel.setSuccess( !map.containsKey( "success" ) );
                            apiResponseModel.setStatus(
                                    EntitiesInstances.STATUS_ATOMIC_REFERENCE.getAndUpdate(
                                            status -> {
                                                status.setMessage( map.get( "message" ).toString() );
                                                status.setCode(
                                                        map.containsKey( "code" )
                                                                ? (long) map.get( "code" )
                                                                : 200
                                                );

                                                return status;
                                            }
                                    )
                            );

                            apiResponseModel.setData(
                                    map.containsKey( "data" )
                                            ? ( Data<?, ?> ) map.get( "data" )
                                            : Data.builder().build()
                            );

                            return apiResponseModel;
                        }
                )
        );
    }

    @lombok.NonNull
    @lombok.Synchronized
    @SuppressWarnings(
            value = """
                    тспользуется в случае ошибки со стороны сервиса
                    """
    )
    protected final synchronized ApiResponseModel errorResponse () {
        return EntitiesInstances.API_RESPONSE_MODEL_ATOMIC_REFERENCE.getAndUpdate(
                apiResponseModel -> {
                    apiResponseModel.setSuccess( false );
                    apiResponseModel.setStatus(
                            EntitiesInstances.STATUS_ATOMIC_REFERENCE.getAndUpdate(
                                    status -> {
                                        status.setMessage( Errors.SERVICE_WORK_ERROR.translate( "ru" ) );
                                        status.setCode( 201 );

                                        return status;
                                    }
                            )
                    );

                    return apiResponseModel;
                }
        );
    }

    @lombok.NonNull
    @lombok.Synchronized
    @org.jetbrains.annotations.Contract( value = "_ -> !null" )
    protected final synchronized Mono< ApiResponseModel > errorResponse ( @lombok.NonNull final String message ) {
        return this.convert(
                EntitiesInstances.API_RESPONSE_MODEL_ATOMIC_REFERENCE.getAndUpdate(
                        apiResponseModel -> {
                            apiResponseModel.setSuccess( false );
                            apiResponseModel.setStatus(
                                    EntitiesInstances.STATUS_ATOMIC_REFERENCE.getAndUpdate(
                                            status -> {
                                                status.setMessage( message );
                                                status.setCode( 201 );

                                                return status;
                                            }
                                    )
                            );

                            return apiResponseModel;
                        }
                )
        );
    }
}
