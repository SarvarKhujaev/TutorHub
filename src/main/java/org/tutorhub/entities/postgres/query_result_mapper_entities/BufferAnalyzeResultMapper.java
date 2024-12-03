package org.tutorhub.entities.postgres.query_result_mapper_entities;

public final class BufferAnalyzeResultMapper {
    public long getBufferid() {
        return this.bufferid;
    }

    public void setBufferid ( final long bufferid ) {
        this.bufferid = bufferid;
    }

    public long getUsagecount() {
        return this.usagecount;
    }

    public void setUsagecount ( final long usagecount ) {
        this.usagecount = usagecount;
    }

    public long getReldatabase() {
        return this.reldatabase;
    }

    public void setReldatabase ( final long reldatabase ) {
        this.reldatabase = reldatabase;
    }

    public long getRelfilenode() {
        return this.relfilenode;
    }

    public void setRelfilenode ( final long relfilenode ) {
        this.relfilenode = relfilenode;
    }

    public long getReltablespace() {
        return this.reltablespace;
    }

    public void setReltablespace ( final long reltablespace ) {
        this.reltablespace = reltablespace;
    }

    public long getRelforknumber() {
        return this.relforknumber;
    }

    public void setRelforknumber ( final long relforknumber ) {
        this.relforknumber = relforknumber;
    }

    public long getRelblocknumber() {
        return this.relblocknumber;
    }

    public void setRelblocknumber ( final long relblocknumber ) {
        this.relblocknumber = relblocknumber;
    }

    public long getPinning_backends() {
        return this.pinning_backends;
    }

    public void setPinning_backends ( final long pinning_backends ) {
        this.pinning_backends = pinning_backends;
    }

    public char getIsdirty() {
        return this.isdirty;
    }

    public void setIsdirty ( final char isdirty ) {
        this.isdirty = isdirty;
    }

    private long bufferid;
    private long usagecount;
    private long reldatabase;
    private long relfilenode;
    private long reltablespace;
    private long relforknumber;
    private long relblocknumber;
    private long pinning_backends;

    private char isdirty;

    public BufferAnalyzeResultMapper(
            final long bufferid,
            final long usagecount,
            final long reldatabase,
            final long relfilenode,
            final long reltablespace,
            final long relforknumber,
            final long relblocknumber,
            final long pinning_backends,
            final char isdirty
    ) {
        this.setIsdirty( isdirty );
        this.setBufferid( bufferid );
        this.setUsagecount( usagecount );
        this.setReldatabase( reldatabase );
        this.setRelfilenode( relfilenode );
        this.setReltablespace( reltablespace );
        this.setRelforknumber( relforknumber );
        this.setRelblocknumber( relblocknumber );
        this.setPinning_backends( pinning_backends );
    }

    @Override
    public String toString () {
        return String.join(
                " : ",
                String.valueOf( this.getIsdirty() ),
                String.valueOf( this.getBufferid() ),
                String.valueOf( this.getUsagecount() ),
                String.valueOf( this.getReldatabase() ),
                String.valueOf( this.getRelfilenode() ),
                String.valueOf( this.getReltablespace() ),
                String.valueOf( this.getRelforknumber() ),
                String.valueOf( this.getRelblocknumber() ),
                String.valueOf( this.getPinning_backends() )
        );
    }
}
