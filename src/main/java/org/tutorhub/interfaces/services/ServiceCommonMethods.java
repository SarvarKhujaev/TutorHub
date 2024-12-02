package org.tutorhub.interfaces.services;

@SuppressWarnings(
        value = """
                содердит методы используемые для очистки классов или сервисов
                """
)
public interface ServiceCommonMethods {
    @SuppressWarnings(
            value = """
                    используется как универсальный метод
                    для очистки всех методов или instance-ов сервисов
                    """
    )
    default void close() {}

    @SuppressWarnings(
            value = """
                    используется для вызова GarbageCollector-а
                    и финальной очистки памяти
                    """
    )
    default void clean() {
        System.gc();
    }

    @SuppressWarnings(
            value = """
                    используется как универсальный метод
                    для очистки всех методов или instance-ов сервисов
                    в случае возникноввения ошибки или исключения
                    """
    )
    default void close( @lombok.NonNull final Throwable throwable ) {}
}
