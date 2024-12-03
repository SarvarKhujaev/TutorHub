package org.tutorhub.entities.query_result_mapper_entities;

public final class TeacherAverageMark {
    private long lessonCount;
    private float averageMark;

    private String averageMarkNaming;

    public TeacherAverageMark(
            final float averageMark,
            final long lessonCount,
            final String averageMarkNaming
    ) {
        this.setAverageMark( averageMark );
        this.setLessonCount( lessonCount );
        this.setAverageMarkNaming( averageMarkNaming );
    }

    public float getAverageMark() {
        return this.averageMark;
    }

    public long getLessonCount() {
        return this.lessonCount;
    }

    public String getAverageMarkNaming() {
        return this.averageMarkNaming;
    }

    public void setAverageMark( final float averageMark ) {
        this.averageMark = averageMark;
    }

    public void setLessonCount( final long lessonCount ) {
        this.lessonCount = lessonCount;
    }

    public void setAverageMarkNaming( final String averageMarkNaming ) {
        this.averageMarkNaming = averageMarkNaming;
    }

    @Override
    public String toString () {
        return this.getAverageMarkNaming()
                + " : "
                + this.getAverageMark()
                + " : "
                + this.getLessonCount();
    }
}
