package org.tutorhub.constans.postgres_constants;

public enum PostgreDataTypes {
    INT2, // знаковое двухбайтное целое ( -32768 .. +32767 )
    INT4, // знаковое четырёхбайтное целое ( int, int4 ) ( -2147483648 .. +2147483647 )
    INT8, // знаковое целое из 8 байт ( -9223372036854775808 .. 9223372036854775807 )

    /*
    Тип numeric позволяет хранить числа с очень большим количеством цифр.
    Он особенно рекомендуется для хранения денежных сумм и других величин, где важна точность.
    Вычисления с типом numeric дают точные результаты, где это возможно, например, при сложении, вычитании и умножении.
    Однако операции со значениями numeric выполняются гораздо медленнее,
    чем с целыми числами или с типами с плавающей точкой, описанными в следующем разделе.

    Для столбца типа numeric можно настроить и максимальную точность, и максимальный масштаб. Столбец типа numeric объявляется следующим образом:

    NUMERIC(точность, масштаб)

    Точность должна быть положительной, а масштаб положительным или равным нулю. Альтернативный вариант

    NUMERIC(точность)

    устанавливает масштаб 0. Форма:

    без указания точности и масштаба создаёт столбец, в котором можно сохранять числовые значения любой точности
    и масштаба в пределах, поддерживаемых системой.
    В столбце этого типа входные значения не будут приводиться к какому-либо масштабу,
    тогда как в столбцах numeric с явно заданным масштабом значения подгоняются под этот масштаб.
    Стандарт SQL утверждает, что по умолчанию должен устанавливаться масштаб 0, т. е. значения должны приводиться к целым числам.
    Однако мы считаем это не очень полезным. Если для вас важна переносимость, всегда указывайте точность и масштаб явно.

    Максимально допустимая точность, которую можно указать в объявлении типа, равна 1000;
    если же использовать NUMERIC без указания точности, действуют ограничения

    https://postgrespro.ru/docs/postgresql/9.6/datatype-numeric <- docs
    */
    NUMERIC, // вещественное число заданной точности

    /*
    CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
    */
    UUID,
    BOOLEAN,

    TEXT, // символьная строка переменной длины
    VARCHAR, // символьная строка переменной длины
    CHARACTER, // символьная строка фиксированной длины

    CIDR, // сетевой адрес IPv4 или IPv6

    DATE, // календарная дата (год, месяц, день)
    TIME, // время суток
    TIMESTAMP, // дата и время
    TIMESTAMPZP, // дата и время

    FLOAT4, // число одинарной точности с плавающей точкой (4 байта)
    FLOAT8, // число двойной точности с плавающей точкой (8 байт)

    /*
    https://www.postgresql.org/docs/current/datatype-json.html
    https://postgrespro.ru/docs/postgresql/9.6/datatype-json <- DOCS

    текстовые данные JSON

    Существуют два типа данных JSON: json и jsonb
    Они принимают на вход почти одинаковые наборы значений, а отличаются главным образом с точки зрения эффективности
    Тип json сохраняет точную копию введённого текста,
    которую функции обработки должны разбирать заново при каждом выполнении запроса,
    тогда как данные jsonb сохраняются в разобранном двоичном формате,
    что несколько замедляет ввод из-за преобразования, но значительно ускоряет обработку, не требуя многократного разбора текста
    Кроме того, jsonb поддерживает индексацию, что тоже может быть очень полезно.

    Так как тип json сохраняет точную копию введённого текста, он сохраняет семантически незначащие пробелы между элементами,
    а также порядок ключей в JSON-объектах. И если JSON-объект внутри содержит повторяющиеся ключи,
    этот тип сохранит все пары ключ/значение. (Функции обработки будут считать действительной последнюю пару.)
    Тип jsonb, напротив, не сохраняет пробелы, порядок ключей и значения с дублирующимися ключами.
    Если во входных данных оказываются дублирующиеся ключи, сохраняется только последнее значение.
     */
    JSON,
    /*
    текстовые данные JSON

    SELECT jdoc->'id' FROM test WHERE jdoc @> '{
        "id": 3
    }';

    INSERT INTO test ( name, jdoc ) VALUES(
    	'test',
        '{
            "id": 1,
            "age": 25.23,
            "temp": "asdsda"
        }'
    )
    */
    JSONB,

    MONEY, // денежная сумма

    BOX, // прямоугольник в плоскости
    LINE, // прямая в плоскости
    PATH, // геометрический путь в плоскости
    POINT, // геометрическая точка в плоскости
    CIRCLE, // круг в плоскости
    POLYGON, // замкнутый геометрический путь в плоскости

    SERIAL, // четырёхбайтное целое с автоувеличением ( 1 .. 2147483647 )
    BIGSERIAL, // восьмибайтное целое с автоувеличением ( 1 .. 9223372036854775807 )
    SMALLSERIAL, // двухбайтное целое с автоувеличением ( 1 .. 32767 )

    /*
    https://postgrespro.ru/docs/postgresql/9.6/functions-array <- docs

    pay_by_quarter  integer ARRAY[4]

    INSERT INTO sal_emp
    VALUES ('Bill',
        '{10000, 10000, 10000, 10000}',
        '{{"meeting", "lunch"}, {"training", "presentation"}}');

    INSERT INTO sal_emp
    VALUES ('Bill',
        ARRAY[10000, 10000, 10000, 10000],
        ARRAY[['meeting', 'lunch'], ['training', 'presentation']]);
    */
    ARRAY,
}
