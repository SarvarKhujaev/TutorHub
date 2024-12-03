package org.tutorhub.entities.postgres_stats_entities;

import java.util.Date;

public final class PGStatDatabase {
    public long getDatid() {
        return this.datid;
    }

    public void setDatid ( final long datid ) {
        this.datid = datid;
    }

    public String getDatname() {
        return this.datname;
    }

    public void setDatname ( final String datname ) {
        this.datname = datname;
    }

    public int getNumbackends() {
        return this.numbackends;
    }

    public void setNumbackends ( final int numbackends ) {
        this.numbackends = numbackends;
    }

    public long getBlks_hit() {
        return this.blks_hit;
    }

    public void setBlks_hit ( final long blks_hit ) {
        this.blks_hit = blks_hit;
    }

    public long getBlks_read() {
        return this.blks_read;
    }

    public void setBlks_read ( final long blks_read ) {
        this.blks_read = blks_read;
    }

    public long getXact_commit() {
        return this.xact_commit;
    }

    public void setXact_commit ( final long xact_commit ) {
        this.xact_commit = xact_commit;
    }

    public long getXact_rollback() {
        return this.xact_rollback;
    }

    public void setXact_rollback ( final long xact_rollback ) {
        this.xact_rollback = xact_rollback;
    }

    public long getTup_fetched() {
        return this.tup_fetched;
    }

    public void setTup_fetched ( final long tup_fetched ) {
        this.tup_fetched = tup_fetched;
    }

    public long getTup_updated() {
        return this.tup_updated;
    }

    public void setTup_updated ( final long tup_updated ) {
        this.tup_updated = tup_updated;
    }

    public long getTup_deleted() {
        return this.tup_deleted;
    }

    public void setTup_deleted ( final long tup_deleted ) {
        this.tup_deleted = tup_deleted;
    }

    public long getTup_returned() {
        return this.tup_returned;
    }

    public void setTup_returned ( final long tup_returned ) {
        this.tup_returned = tup_returned;
    }

    public long getTup_inserted() {
        return this.tup_inserted;
    }

    public void setTup_inserted ( final long tup_inserted ) {
        this.tup_inserted = tup_inserted;
    }

    public long getDeadlocks() {
        return this.deadlocks;
    }

    public void setDeadlocks ( final long deadlocks ) {
        this.deadlocks = deadlocks;
    }

    public long getConflicts() {
        return this.conflicts;
    }

    public void setConflicts ( final long conflicts ) {
        this.conflicts = conflicts;
    }

    public long getTemp_files() {
        return this.temp_files;
    }

    public void setTemp_files ( final long temp_files ) {
        this.temp_files = temp_files;
    }

    public long getTemp_bytes() {
        return this.temp_bytes;
    }

    public void setTemp_bytes ( final long temp_bytes ) {
        this.temp_bytes = temp_bytes;
    }

    public long getChecksum_failures() {
        return this.checksum_failures;
    }

    public void setChecksum_failures ( final long checksum_failures ) {
        this.checksum_failures = checksum_failures;
    }

    public long getSessions() {
        return this.sessions;
    }

    public void setSessions ( final long sessions ) {
        this.sessions = sessions;
    }

    public long getSessions_fatal() {
        return this.sessions_fatal;
    }

    public void setSessions_fatal ( final long sessions_fatal ) {
        this.sessions_fatal = sessions_fatal;
    }

    public long getSessions_killed() {
        return this.sessions_killed;
    }

    public void setSessions_killed ( final long sessions_killed ) {
        this.sessions_killed = sessions_killed;
    }

    public long getSessions_abandoned() {
        return this.sessions_abandoned;
    }

    public void setSessions_abandoned ( final long sessions_abandoned ) {
        this.sessions_abandoned = sessions_abandoned;
    }

    public double getActive_time() {
        return this.active_time;
    }

    public void setActive_time ( final double active_time ) {
        this.active_time = active_time;
    }

    public double getSession_time() {
        return this.session_time;
    }

    public void setSession_time ( final double session_time ) {
        this.session_time = session_time;
    }

    public double getBlk_read_time() {
        return this.blk_read_time;
    }

    public void setBlk_read_time ( final double blk_read_time ) {
        this.blk_read_time = blk_read_time;
    }

    public double getBlk_write_time() {
        return this.blk_write_time;
    }

    public void setBlk_write_time ( final double blk_write_time ) {
        this.blk_write_time = blk_write_time;
    }

    public double getIdle_in_transaction_time() {
        return this.idle_in_transaction_time;
    }

    public void setIdle_in_transaction_time ( final double idle_in_transaction_time ) {
        this.idle_in_transaction_time = idle_in_transaction_time;
    }

    public Date getStats_reset() {
        return this.stats_reset;
    }

    public void setStats_reset ( final Date stats_reset ) {
        this.stats_reset = stats_reset;
    }

    public Date getChecksum_last_failure() {
        return this.checksum_last_failure;
    }

    public void setChecksum_last_failure ( final Date checksum_last_failure ) {
        this.checksum_last_failure = checksum_last_failure;
    }

    private long datid;
    private String datname;
    private int numbackends;

    private long blks_hit;
    private long blks_read;

    private long xact_commit;
    private long xact_rollback;

    private long tup_fetched;
    private long tup_updated;
    private long tup_deleted;
    private long tup_returned;
    private long tup_inserted;

    private long deadlocks;
    private long conflicts;
    private long temp_files;
    private long temp_bytes;
    private long checksum_failures;

    private long sessions;
    private long sessions_fatal;
    private long sessions_killed;
    private long sessions_abandoned;

    private double active_time;
    private double session_time;
    private double blk_read_time;
    private double blk_write_time;
    private double idle_in_transaction_time;

    private Date stats_reset;
    private Date checksum_last_failure;

    public PGStatDatabase () {}

    public PGStatDatabase(
            final long datid,
            final String datname,
            final int numbackends,

            final long blks_hit,
            final long blks_read,
            final long xact_commit,
            final long xact_rollback,

            final long tup_fetched,
            final long tup_updated,
            final long tup_deleted,
            final long tup_returned,
            final long tup_inserted,

            final long deadlocks,
            final long conflicts,
            final long temp_files,
            final long temp_bytes,
            final long checksum_failures,

            final long sessions,
            final long sessions_fatal,
            final long sessions_killed,
            final long sessions_abandoned,

            final double active_time,
            final double session_time,
            final double blk_read_time,
            final double blk_write_time,
            final double idle_in_transaction_time,

            final Date stats_reset,
            final Date checksum_last_failure
    ) {
        this.datid = datid;
        this.datname = datname;
        this.numbackends = numbackends;
        this.blks_hit = blks_hit;
        this.blks_read = blks_read;
        this.xact_commit = xact_commit;
        this.xact_rollback = xact_rollback;
        this.tup_fetched = tup_fetched;
        this.tup_updated = tup_updated;
        this.tup_deleted = tup_deleted;
        this.tup_returned = tup_returned;
        this.tup_inserted = tup_inserted;
        this.deadlocks = deadlocks;
        this.conflicts = conflicts;
        this.temp_files = temp_files;
        this.temp_bytes = temp_bytes;
        this.checksum_failures = checksum_failures;
        this.sessions = sessions;
        this.sessions_fatal = sessions_fatal;
        this.sessions_killed = sessions_killed;
        this.sessions_abandoned = sessions_abandoned;
        this.active_time = active_time;
        this.session_time = session_time;
        this.blk_read_time = blk_read_time;
        this.blk_write_time = blk_write_time;
        this.stats_reset = stats_reset;
        this.checksum_last_failure = checksum_last_failure;
        this.idle_in_transaction_time = idle_in_transaction_time;
    }
}
