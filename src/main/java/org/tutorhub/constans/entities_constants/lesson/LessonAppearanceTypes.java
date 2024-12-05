package org.tutorhub.constans.entities_constants.lesson;

@SuppressWarnings( value = "хранит типы посещений учеников на уроках" )
public enum LessonAppearanceTypes {
    @SuppressWarnings( value = "отсутствовал по причине" )
    ABSENT_BY_REASON,

    @SuppressWarnings( value = "отсутствовал без причины" )
    ABSENT,

    @SuppressWarnings(
            value = """
                    используется в случае,
                    если ученик предупредил до занятия,
                    что опоздает
                    """
    )
    WILL_BE_LATE,

    @SuppressWarnings( value = "опоздал на занятие, но не предупредил" )
    LATE,

    @SuppressWarnings( value = "пришел на занятие" )
    VISITED,
}
