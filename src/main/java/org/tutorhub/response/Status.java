package org.tutorhub.response;

@lombok.Data
@lombok.Builder
@org.tutorhub.annotations.services.ImmutableEntityAnnotation
public final class Status {
    private long code;
    private String message;
}
