package org.tutorhub.constans.entities_constants.wishes;

@SuppressWarnings(
        value = "хранит стадии выполнения желаний и планов у учителей и студентов"
)
public enum WishCompletionTypes {
    @SuppressWarnings( value = "планы не выполнены" )
    NOT_COMPLETED,
    @SuppressWarnings( value = "в процессе над выполнением" )
    IN_PROGRESS,
    @SuppressWarnings( value = "планы полностью выполнены" )
    COMPLETED,
    @SuppressWarnings( value = "отказался от планов" )
    REFUSED,
}
