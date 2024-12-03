package org.tutorhub.constans.postgres_constants.postgres_statistics_constants;

/*
https://habr.com/ru/companies/postgrespro/articles/576100/
*/
public enum PostgresStatisticsTypes {
    /*
    Функциональные зависимости между столбцами

    Если значения в одном столбце определяются (полностью или частично) значениями другого столбца,
    и в запросе указаны условия на оба таких столбца, оценка кардинальности окажется заниженной.

    Это известная проблема коррелированных предикатов.
    Планировщик полагается на то, что предикаты независимы и вычисляет общую селективность как произведение селективностей условий,
    объединенных логическим «и». Это хорошо видно в приведенном плане: оценка в узле Bitmap Index Scan,
    полученная по условию на столбец flight_no,
    существенно уменьшается после фильтрации по условию на столбец departure_airport в узле Bitmap Heap Scan.

    Однако мы понимаем, что номер рейса однозначно определяет аэропорты:
    фактически, второе условие избыточно (конечно, если аэропорт указан правильно).
    В таких случаях расширенная статистика по функциональным зависимостям может улучшить оценку.
     */
    DEPENDENCIES,

    /*
    Информация о количестве уникальных комбинаций значений из нескольких столбцов позволяет улучшить оценку кардинальности группировки по нескольким столбцам.

    Например, количество возможных пар аэропортов отправления и прибытия оценивается планировщиком как квадрат количества аэропортов,
    но реальное значение сильно меньше, поскольку далеко не каждая пара аэропортов соединена прямым рейсом:
    */
    NDISTINCT,

    /*
    https://www.postgresql.org/docs/14/multivariate-statistics-examples.html
    https://www.postgresql.org/docs/14/monitoring-stats.html#WAIT-EVENT-TABLE
    https://www.postgresql.org/docs/14/monitoring-stats.html#WAIT-EVENT-ACTIVITY-TABLE

    Необходимо заметить, что можно создавать мультивариативную
    коррелированную статистику (MCV), но естественно за поддержку такой
    статистики нужно платить производительностью при перестроении статистики.
    Более подробно можно почитать по ссылке

    Многовариантные списки частых значений

    При неравномерном распределении значений одного только знания функциональной зависимости может быть недостаточно,
    поскольку оценка существенно зависит от конкретной пары значений
    */
    MCV,
}