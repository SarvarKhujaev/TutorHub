package org.tutorhub.entities.query_result_mapper_entities;

import com.university.universityapplication.constans.entities_constants.LessonAppearanceTypes;

public final class StudentLessonAppearanceStat {
    public long getLessonsCount() {
        return this.lessonsCount;
    }

    public LessonAppearanceTypes getLessonAppearanceTypes() {
        return this.lessonAppearanceTypes;
    }

    private final long lessonsCount;
    private final LessonAppearanceTypes lessonAppearanceTypes;

    public StudentLessonAppearanceStat (
            final long lessonsCount,
            final LessonAppearanceTypes lessonAppearanceTypes
    ) {
        this.lessonsCount = lessonsCount;
        this.lessonAppearanceTypes = lessonAppearanceTypes;
    }
}
