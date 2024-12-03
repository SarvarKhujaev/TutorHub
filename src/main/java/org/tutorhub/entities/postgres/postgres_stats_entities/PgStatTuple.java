package org.tutorhub.entities.postgres.postgres_stats_entities;

public final class PgStatTuple {
    public long getTuple_len() {
        return this.tuple_len;
    }

    public void setTuple_len ( final long tuple_len ) {
        this.tuple_len = tuple_len;
    }

    public long getTable_len() {
        return this.table_len;
    }

    public void setTable_len ( final long table_len ) {
        this.table_len = table_len;
    }

    public long getFree_space() {
        return this.free_space;
    }

    public void setFree_space ( final long free_space ) {
        this.free_space = free_space;
    }

    public long getTuple_count() {
        return this.tuple_count;
    }

    public void setTuple_count ( final long tuple_count ) {
        this.tuple_count = tuple_count;
    }

    public long getDead_tuple_len() {
        return this.dead_tuple_len;
    }

    public void setDead_tuple_len ( final long dead_tuple_len ) {
        this.dead_tuple_len = dead_tuple_len;
    }

    public long getDead_tuple_count() {
        return this.dead_tuple_count;
    }

    public void setDead_tuple_count ( final long dead_tuple_count ) {
        this.dead_tuple_count = dead_tuple_count;
    }

    public long getDead_tuple_percent() {
        return this.dead_tuple_percent;
    }

    public void setDead_tuple_percent ( final long dead_tuple_percent ) {
        this.dead_tuple_percent = dead_tuple_percent;
    }

    public double getFree_percent() {
        return this.free_percent;
    }

    public void setFree_percent ( final double free_percent ) {
        this.free_percent = free_percent;
    }

    public double getTuple_percent() {
        return this.tuple_percent;
    }

    public void setTuple_percent ( final double tuple_percent ) {
        this.tuple_percent = tuple_percent;
    }

    private long tuple_len;
    private long table_len;
    private long free_space;
    private long tuple_count;
    private long dead_tuple_len;
    private long dead_tuple_count;
    private long dead_tuple_percent;

    private double free_percent;
    private double tuple_percent;

    public PgStatTuple () {}

    public PgStatTuple(
            final long tuple_len,
            final long table_len,
            final long free_space,
            final long tuple_count,
            final long dead_tuple_len,
            final long dead_tuple_count,
            final long dead_tuple_percent,

            final double free_percent,
            final double tuple_percent
    ) {
        this.tuple_len = tuple_len;
        this.table_len = table_len;
        this.free_space = free_space;
        this.tuple_count = tuple_count;
        this.dead_tuple_len = dead_tuple_len;
        this.dead_tuple_count = dead_tuple_count;
        this.dead_tuple_percent = dead_tuple_percent;

        this.free_percent = free_percent;
        this.tuple_percent = tuple_percent;
    }
}
