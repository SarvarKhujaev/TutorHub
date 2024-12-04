CREATE INDEX IF NOT EXISTS UNIVERSITY_TEACHERS_NAME_INDEX ON entities.teachers
    USING BTREE( name ASC, surname ASC, father_name ASC );

CREATE UNIQUE INDEX IF NOT EXISTS UNIVERSITY_TEACHERS_EMAIL_INDEX ON entities.teachers
    USING BTREE( email ASC );

CREATE INDEX IF NOT EXISTS UNIVERSITY_STUDENT_FIO_INDEX ON entities.students
    USING BTREE( name ASC )
    INCLUDE ( surname, father_name );

CREATE UNIQUE INDEX IF NOT EXISTS UNIVERSITY_STUDENT_EMAIL_INDEX ON entities.students
    USING BTREE( email ASC );

CREATE UNIQUE INDEX IF NOT EXISTS UNIVERSITY_GROUPS_GROUP_NAME_INDEX ON entities.groups
    USING BTREE( group_name ASC );

CREATE INDEX IF NOT EXISTS UNIVERSITY_COMMENT_MARK_INDEX ON entities.comments
    USING BTREE( mark ASC );

CREATE INDEX IF NOT EXISTS UNIVERSITY_LESSON_LESSON_NAME_INDEX ON entities.lessons
    USING BTREE( lesson_name ASC );

CREATE INDEX IF NOT EXISTS UNIVERSITY_LESSON_LESSON_STATUS_INDEX ON entities.lessons
    USING BTREE( lesson_status ASC );

CREATE INDEX IF NOT EXISTS UNIVERSITY_STUDENT_APPEARANCE_IN_LESSONS_TYPES_INDEX ON entities.student_appearance_in_lessons
    USING BTREE( lesson_appearance_types ASC );

CREATE INDEX IF NOT EXISTS UNIVERSITY_STUDENTS_MARKS_INDEX ON entities.student_marks
    USING BTREE( mark_for_homework ASC );

CREATE INDEX IF NOT EXISTS UNIVERSITY_STUDENT_MARKS_STUDENT_AND_TEACHER_REF_INDEX ON entities.student_marks
    USING BTREE( student_id ASC, teacher_id ASC );

CREATE INDEX IF NOT EXISTS UNIVERSITY_LESSONS_CREATED_DATE_INDEX ON entities.lessons USING BTREE( created_date );
CREATE INDEX IF NOT EXISTS UNIVERSITY_COMMENTS_CREATED_DATE_INDEX ON entities.comments USING BTREE( created_date );
CREATE INDEX IF NOT EXISTS UNIVERSITY_HOMEWORK_CREATED_DATE_INDEX ON entities.homework USING BTREE( created_date );
CREATE INDEX IF NOT EXISTS UNIVERSITY_STUDENT_MARKS_CREATED_DATE_INDEX ON entities.student_marks USING BTREE( created_date );


-- CREATE INDEX IF NOT EXISTS entities_patruls_index ON entities.patruls
--     ( passport_number NULLS LAST );
--
-- CREATE INDEX IF NOT EXISTS entities_patrul_cars_index ON entities.patrul_cars
--     ( tracker_id NULLS LAST, car_number NULLS LAST, patrul_id NULLS LAST );
--
-- CREATE INDEX IF NOT EXISTS entities_lustra_index ON entities.lustra
--     ( car_gos_number NULLS LAST );
--
--
-- CREATE INDEX IF NOT EXISTS tracker_trackers_info_index ON tracker.trackers_info
--     ( car_id NULLS LAST );
--
--
-- CREATE INDEX IF NOT EXISTS escort_countries_index ON escort.countries
--     ( country_name_uz NULLS LAST, country_name_ru NULLS LAST, country_name_en NULLS LAST );
--
-- CREATE INDEX IF NOT EXISTS escort_tuple_of_escort_index ON escort.tuple_of_escort
--     ( country_id NULLS LAST, uuid_of_polygon NULLS LAST );
--
-- CREATE INDEX IF NOT EXISTS sos_patrul_sos_table_index ON sos.patrul_sos_table
--     ( status NULLS LAST );
