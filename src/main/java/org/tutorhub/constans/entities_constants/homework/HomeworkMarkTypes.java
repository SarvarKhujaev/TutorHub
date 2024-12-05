package org.tutorhub.constans.entities_constants.homework;

@SuppressWarnings(
        value = """
                для более приятной, простой и эффективной
                системы оценки выолнения дом. задания
                вносим другой способ, отличный от цифровой с 2 и 5
                """
)
public enum HomeworkMarkTypes {
    @SuppressWarnings( value = "хорошо, но можно улучшить" )
    GOOD_BUT_MUST_BE_IMPROVED,
    @SuppressWarnings( value = "прекрасная работа" )
    EXCELLENT,
    @SuppressWarnings( value = "молодец, горжусь тобой" )
    AMAZING,
    @SuppressWarnings( value = "просто идеально" )
    PERFECT,
    @SuppressWarnings( value = "молодец, просто умница" )
    NICE,

    @SuppressWarnings( value = "за такое должно быть стыдно" )
    TERRIBLE,
    @SuppressWarnings( value = "крайне плохо" )
    AWFUL,
    @SuppressWarnings( value = "плохо выполнено" )
    BAD,
}
