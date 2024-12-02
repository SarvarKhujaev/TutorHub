package org.tutorhub.constans.postgres_constants;

/*
https://www.postgresql.org/docs/13/sql-checkpoint.html
https://www.postgresql.org/docs/13/wal-configuration.html

Несмотря на то, что в названии присутствует слово “точка”, процесс
записи грязных буферов происходит не одномоментно, иначе бы это подвесило
наш серверный процесс, а продолжительно по времени. Настраивается в %
между двумя контрольными точками, то есть процесс записи размывается по
времени
*/
public final class PostgresWalOperations {
    /*
    A checkpoint is a point in the write-ahead log sequence at which all data files have been updated to reflect the information in the log.
    All data files will be flushed to disk. Refer to Section 29.4 for more details about what happens during a checkpoint.

    The CHECKPOINT command forces an immediate checkpoint when the command is issued,
    without waiting for a regular checkpoint scheduled by the system (controlled by the settings in Section 19.5.2).
    CHECKPOINT is not intended for use during normal operation.

    If executed during recovery, the CHECKPOINT command will force a restartpoint rather than writing a new checkpoint.

    Only superusers can call CHECKPOINT.
    */
    public static final String CHECKPOINT = "CHECKPOINT;";
}
