package org.tutorhub.response;

@lombok.Data
@lombok.Builder
@org.tutorhub.annotations.services.ImmutableEntityAnnotation
public final class ApiResponseModel {
    private Data data;
    private Status status;
    private boolean success;
}
