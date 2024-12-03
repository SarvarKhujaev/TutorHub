package org.tutorhub.constans.postgres_constants.postgres_transactions_constants;

/*
https://www.postgresql.org/docs/14/transaction-iso.html

https://en.wikipedia.org/wiki/Two-phase_commit_protocol

https://www.postgresql.org/docs/14/transaction-iso.html#XACT-SERIALIZABLE

https://habr.com/ru/companies/postgrespro/articles/442804/ < - docs

В стандарте SQL есть четыре уровня изоляции транзакций75, которые
определяются в терминах аномалий76, которые допускаются при конкурентном
выполнении транзакций на этом уровне

Начнем с потерянного обновления.
Такая аномалия возникает, когда две транзакции читают одну и ту же строку таблицы, затем одна транзакция обновляет эту строку,
а после этого вторая транзакция тоже обновляет ту же строку, не учитывая изменений, сделанных первой транзакцией.

Например, две транзакции собираются увеличить сумму на одном и том же счете на 100 ₽.
Первая транзакция читает текущее значение (1000 ₽), затем вторая транзакция читает то же значение.
Первая транзакция увеличивает сумму (получается 1100 ₽) и записывает это значение.
Вторая транзакция поступает так же — получает те же 1100 ₽ и записывает их.
В результате клиент потерял 100 ₽.

Грязное чтение и Read Uncommitted

С грязным чтением мы уже познакомились выше. Такая аномалия возникает, когда транзакция читает еще не зафиксированные изменения, сделанные другой транзакцией.

Например, первая транзакция переводит все деньги со счета клиента на другой счет, но не фиксирует изменение.
Другая транзакция читает состояние счета, получает 0 ₽ и отказывает клиенту в выдаче наличных — несмотря на то,
что первая транзакция прерывается и отменяет свои изменения, так что значения 0 никогда не существовало в базе данных.

Грязное чтение допускается стандартом на уровне Read Uncommitted

Неповторяющееся чтение и Read Committed

Аномалия неповторяющегося чтения возникает, когда транзакция читает одну и ту же строку два раза,
и в промежутке между чтениями вторая транзакция изменяет (или удаляет) эту строку и фиксирует изменения.
Тогда первая транзакция получит разные результаты.

Например, пусть правило согласованности запрещает отрицательные суммы на счетах клиентов.
Первая транзакция собирается уменьшить сумму на счете на 100 ₽. Она проверяет текущее значение, получает 1000 ₽ и решает, что уменьшение возможно.
В это время вторая транзакция уменьшает сумму на счете до нуля и фиксирует изменения. Если бы теперь первая транзакция повторно проверила сумму,
она получила бы 0 ₽ (но она уже приняла решение уменьшить значение, и счет “уходит в минус”).

Неповторяющееся чтение допускается стандартом на уровнях Read Uncommitted и Read Committed. А вот грязное чтение Read Committed не допускает.

Фантомное чтение и Repeatable Read

Фантомное чтение возникает, когда транзакция два раза читает набор строк по одному и тому же условию,
и в промежутке между чтениями вторая транзакция добавляет строки, удовлетворяющие этому условию (и фиксирует изменения).
Тогда первая транзакция получит разные наборы строк.

Например, пусть правило согласованности запрещает клиенту иметь более 3 счетов. Первая транзакция собирается открыть новый счет,
проверяет их текущее количество (скажем, 2) и решает, что открытие возможно. В это время вторая транзакция тоже открывает клиенту
новый счет и фиксирует изменения. Если бы теперь первая транзакция перепроверила количество,
она получила бы 3 (но она уже выполняет открытие еще одного счета и у клиента их оказывается 4).

Фантомное чтение допускается стандартом на уровнях Read Uncommitted, Read Committed и Repeatable Read.
Но на уровне Repeatable Read не допускается неповторяющееся чтение.

Отсутствие аномалий и Serializable

Стандарт определяет и еще один уровень — Serializable, — на котором не допускаются никакие аномалии.
И это совсем не то же самое, что запрет на потерянное обновление и на грязное, неповторяющееся и фантомное чтения.

Дело в том, что существует значительно больше известных аномалий, чем перечислено в стандарте, и еще неизвестное число пока неизвестных.

Уровень Serializable должен предотвращать вообще все аномалии. Это означает, что на таком уровне разработчику
приложения не надо думать об одновременном выполнении. Если транзакции выполняют корректные последовательности операторов,
работая в одиночку, данные будут согласованы и при одновременной работе этих транзакций.
*/
public final class PostgresTransactionIsolationTypes {
    /*
    «Грязное» чтение (Dirty read) - транзакция T1 может читать строки,
    изменённые, но ещё не зафиксированные, транзакцией T2 (не было COMMIT).
    Отмена изменений (ROLLBACK) в T2 приведёт к тому, что T1 прочитает данные,
    которых никогда не существовало

    Простой пример: если приложение хочет получить из базы корректные данные, то оно, как минимум,
    не должно видеть изменения других незафиксированных транзакций. Иначе можно не просто получить несогласованные данные,
    но и увидеть что-то такое, чего в базе данных никогда не было (если транзакция будет отменена).
    Такая аномалия называется грязным чтением.
    */
    public static final String READ_UNCOMMITTED = "READ UNCOMMITTED";

    public static final String READ_COMMITTED = "READ COMMITTED";

    /*
    Неповторяющееся чтение (Non-repeatable read) - после того, как
    транзакция T1 прочитала строку, транзакция T2 изменила или удалила эту
    строку и зафиксировала изменения (COMMIT). При повторном чтении этой же
    строки транзакция T1 видит, что строка изменена или удалена
    */
    public static final String NON_REPEATABLE_READ = "NON REPEATABLE READ";

    /*
    В этом режиме видны только те данные, которые были зафиксированы до
    начала транзакции и предыдущие изменения в своей транзакции.
    В Постгрес этот уровень чуть усилен, не допускается даже фантомное
    чтение, за исключением аномалий сериализации.
    Другими словами, этот уровень отличается от Read Committed тем, что
    запрос в транзакции данного уровня видит снимок данных на момент начала
    первого оператора в транзакции.
    Таким образом, последовательные команды SELECT в одной транзакции
    видят одни и те же данные; они не видят изменений, внесённых и
    зафиксированных другими транзакциями после начала их текущей
    транзакции.

    Посмотрим на практике.
    Установим нужный уровень изоляции и начнём транзакции в двух сессиях.

    В первой консоли:
    SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;
    SELECT * FROM testA;

    Во второй добавим новую строку и закоммитим изменения:
    SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;
    INSERT INTO testA VALUES (777);
    COMMIT;
     */
    public static final String REPEATABLE_READ = "REPEATABLE READ";

    /*
    Фантомное чтение (Phantom read) - транзакция T1 прочитала набор
    строк по некоторому условию. Затем транзакция T2 добавила строки, также
    удовлетворяющие этому условию. Если транзакция T1 повторит запрос, она
    получит другую выборку строк
    */
    public static final String PHANTOM_READ = "PHANTOM READ";

    /*
    Аномалия сериализации (Serialization anomaly) - результат успешной
    фиксации группы транзакций оказывается несогласованным при всевозможных
    вариантах исполнения этих транзакций по очереди
    */
    public static final String SERIALIZATION_ANOMALY = "SERIALIZATION ANOMALY";
}