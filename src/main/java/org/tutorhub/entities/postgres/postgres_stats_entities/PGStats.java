package org.tutorhub.entities.postgres.postgres_stats_entities;

import java.util.Objects;

public final class PGStats {
    public int getAvg_width() {
        return this.avg_width;
    }

    public void setAvg_width ( final int avg_width ) {
        this.avg_width = avg_width;
    }

    public boolean isInherited() {
        return this.inherited;
    }

    public void setInherited ( final boolean inherited ) {
        this.inherited = inherited;
    }

    public String getAttname() {
        return this.attname;
    }

    public void setAttname ( final String attname ) {
        this.attname = attname;
    }

    public String getTablename() {
        return this.tablename;
    }

    public void setTablename ( final String tablename ) {
        this.tablename = tablename;
    }

    public String getSchemaname() {
        return this.schemaname;
    }

    public void setSchemaname ( final String schemaname ) {
        this.schemaname = schemaname;
    }

    public double getNull_frac() {
        return this.null_frac;
    }

    public void setNull_frac ( final double null_frac ) {
        this.null_frac = null_frac;
    }

    public double getN_distinct() {
        return this.n_distinct;
    }

    public void setN_distinct ( final double n_distinct ) {
        this.n_distinct = n_distinct;
    }

    public double getCorrelation() {
        return this.correlation;
    }

    public void setCorrelation ( final double correlation ) {
        this.correlation = correlation;
    }

    public double[] getMost_common_freqs() {
        return this.most_common_freqs;
    }

    public void setMost_common_freqs ( final double[] most_common_freqs) {
        this.most_common_freqs = most_common_freqs;
    }

    public double[] getElem_count_histogram() {
        return this.elem_count_histogram;
    }

    public void setElem_count_histogram ( final double[] elem_count_histogram) {
        this.elem_count_histogram = elem_count_histogram;
    }

    public double[] getMost_common_elem_freqs() {
        return this.most_common_elem_freqs;
    }

    public void setMost_common_elem_freqs ( final double[] most_common_elem_freqs) {
        this.most_common_elem_freqs = most_common_elem_freqs;
    }

    public Objects[] getMost_common_vals() {
        return this.most_common_vals;
    }

    public void setMost_common_vals ( final Objects[] most_common_vals) {
        this.most_common_vals = most_common_vals;
    }

    public Objects[] getHistogram_bounds() {
        return this.histogram_bounds;
    }

    public void setHistogram_bounds ( final Objects[] histogram_bounds) {
        this.histogram_bounds = histogram_bounds;
    }

    public Objects[] getMost_common_elems() {
        return this.most_common_elems;
    }

    public void setMost_common_elems ( final Objects[] most_common_elems) {
        this.most_common_elems = most_common_elems;
    }

    private int avg_width;
    private boolean inherited;

    private String attname;
    private String tablename;

    private String schemaname;

    private double null_frac;
    private double n_distinct;
    private double correlation;

    private double[] most_common_freqs;
    private double[] elem_count_histogram;
    private double[] most_common_elem_freqs;

    private Objects[] most_common_vals;
    private Objects[] histogram_bounds;
    private Objects[] most_common_elems;

    public PGStats () {}

    public PGStats(
            final int avg_width,
            final boolean inherited,

            final String attname,
            final String tablename,
            final String schemaname,

            final double null_frac,
            final double n_distinct,
            final double correlation,

            final double[] most_common_freqs,
            final double[] elem_count_histogram,
            final double[] most_common_elem_freqs,

            final Objects[] most_common_vals,
            final Objects[] histogram_bounds,
            final Objects[] most_common_elems
    ) {
        this.avg_width = avg_width;
        this.inherited = inherited;

        this.attname = attname;
        this.tablename = tablename;
        this.schemaname = schemaname;

        this.null_frac = null_frac;
        this.n_distinct = n_distinct;
        this.correlation = correlation;

        this.most_common_freqs = most_common_freqs;
        this.elem_count_histogram = elem_count_histogram;
        this.most_common_elem_freqs = most_common_elem_freqs;

        this.most_common_vals = most_common_vals;
        this.histogram_bounds = histogram_bounds;
        this.most_common_elems = most_common_elems;
    }
}
