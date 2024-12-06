CREATE TYPE entities_enums.lesson_appearance_types AS ENUM (
    'ABSENT_BY_REASON',
    'WILL_BE_LATE',
    'VISITED',
    'ABSENT',
    'LATE'
);

CREATE TYPE entities_enums.lesson_status AS ENUM (
    'CANCELED',
    'FINISHED',
    'CREATED'
);

CREATE TYPE entities_enums.homework_mark_types AS ENUM (
    'GOOD_BUT_MUST_BE_IMPROVED',
    'EXCELLENT',
    'TERRIBLE',
    'PERFECT',
    'AMAZING',
    'AWFUL',
    'NICE',
    'BAD'
);

CREATE TYPE entities_enums.wish_completion_types AS ENUM (
    'NOT_COMPLETED',
    'IN_PROGRESS',
    'COMPLETED',
    'REFUSED'
);