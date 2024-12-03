package org.tutorhub.entities.postgres_stats_entities;

public final class PgStatIndex {
    public long getVersion() {
        return this.version;
    }

    public void setVersion ( final long version ) {
        this.version = version;
    }

    public long getTree_level() {
        return this.tree_level;
    }

    public void setTree_level ( final long tree_level ) {
        this.tree_level = tree_level;
    }

    public long getIndex_size() {
        return this.index_size;
    }

    public void setIndex_size ( final long index_size ) {
        this.index_size = index_size;
    }

    public long getLeaf_pages() {
        return this.leaf_pages;
    }

    public void setLeaf_pages ( final long leaf_pages ) {
        this.leaf_pages = leaf_pages;
    }

    public long getEmpty_pages() {
        return this.empty_pages;
    }

    public void setEmpty_pages ( final long empty_pages ) {
        this.empty_pages = empty_pages;
    }

    public long getDeleted_pages() {
        return this.deleted_pages;
    }

    public void setDeleted_pages ( final long deleted_pages ) {
        this.deleted_pages = deleted_pages;
    }

    public long getRoot_block_no() {
        return this.root_block_no;
    }

    public void setRoot_block_no ( final long root_block_no ) {
        this.root_block_no = root_block_no;
    }

    public long getInternal_pages() {
        return this.internal_pages;
    }

    public void setInternal_pages ( final long internal_pages ) {
        this.internal_pages = internal_pages;
    }

    public double getAvg_leaf_density() {
        return this.avg_leaf_density;
    }

    public void setAvg_leaf_density ( final double avg_leaf_density ) {
        this.avg_leaf_density = avg_leaf_density;
    }

    public double getLeaf_fragmentation() {
        return this.leaf_fragmentation;
    }

    public void setLeaf_fragmentation ( final double leaf_fragmentation ) {
        this.leaf_fragmentation = leaf_fragmentation;
    }

    private long version;
    private long tree_level;
    private long index_size;
    private long leaf_pages;
    private long empty_pages;
    private long deleted_pages;
    private long root_block_no;
    private long internal_pages;

    private double avg_leaf_density;
    private double leaf_fragmentation;

    public PgStatIndex () {}

    public PgStatIndex (
            final long version,
            final long tree_level,
            final long index_size,
            final long leaf_pages,
            final long empty_pages,
            final long deleted_pages,
            final long root_block_no,
            final long internal_pages,

            final double avg_leaf_density,
            final double leaf_fragmentation
    ) {
        this.version = version;
        this.tree_level = tree_level;
        this.index_size = index_size;
        this.leaf_pages = leaf_pages;
        this.empty_pages = empty_pages;
        this.deleted_pages = deleted_pages;
        this.root_block_no = root_block_no;
        this.internal_pages = internal_pages;

        this.avg_leaf_density = avg_leaf_density;
        this.leaf_fragmentation = leaf_fragmentation;
    }
}
