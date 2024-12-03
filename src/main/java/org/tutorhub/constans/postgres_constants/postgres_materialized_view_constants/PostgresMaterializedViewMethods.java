package org.tutorhub.constans.postgres_constants.postgres_materialized_view_constants;

import com.university.universityapplication.constans.postgres_constants.PostgresCommonCommands;
import com.university.universityapplication.constans.postgres_constants.PostgresCreateValues;

/*
What is a materialized view in PostgreSQL?

A materialized view is a cached result of a complicated query.
We can save this data and work with it as with a traditional table.
You can even add primary keys and indexes to this object.

When needed, we can refresh it by executing the query on the underlying level.
Otherwise, that query is not executed when we refer to this materialized view, and only the cache is available to us.
A materialized View is not virtual.
In PostgreSQL, this data is stored physically on a disk.

Database specialists turn to materialized views quite frequently.
It is a common method of ensuring quick data access, especially for BI applications and data warehouses.
The primary reason why a Postgres materialized view is so helpful is the speed of work it ensures.

CREATE MATERIALIZED VIEW sales_summary AS
    SELECT
      seller_no,
      invoice_date,
      sum(invoice_amt)::numeric(13,2) as sales_amt
    FROM invoice
    WHERE invoice_date < CURRENT_DATE
    GROUP BY
      seller_no,
      invoice_date;

CREATE UNIQUE INDEX sales_summary_seller
  ON sales_summary (seller_no, invoice_date);

CREATE MATERIALIZED VIEW view_name
AS
query
WITH [NO] DATA;

view_name is the name of your materialized view in Postgres
query is that complex query that supplies the data for our materialized view
WITH DATA/ WITH NO DATA is the parameter that specifies if the query has to load the data (the query results) at once during the object creation.
If we need it while creating a Postgres materialized view, we specify the WITH DATA parameter. If not – use the WITH NO DATA one.

ALTER [ COLUMN ] column_name SET STATISTICS integer
ALTER [ COLUMN ] column_name SET ( attribute_option = value [, ... ] )
ALTER [ COLUMN ] column_name RESET ( attribute_option [, ... ] )
ALTER [ COLUMN ] column_name SET STORAGE { PLAIN | EXTERNAL | EXTENDED | MAIN }
ALTER [ COLUMN ] column_name SET COMPRESSION compression_method
CLUSTER ON index_name
SET WITHOUT CLUSTER
SET TABLESPACE new_tablespace
SET ( storage_parameter [= value] [, ... ] )
RESET ( storage_parameter [, ... ] )
OWNER TO { new_owner | CURRENT_ROLE | CURRENT_USER | SESSION_USER }

name is a name of a materialized view that already exists
column_name is a name of a column
extension_name is the name of the extension that will be used by the materialized view. When an extension is dropped, a materialized view that is tagged as dependent is immediately discarded
new_column_name is a new name for a column that already exists
new_owner is the name of the new materialized view owner
new_name is a placeholder for a new name for the materialized view
new_schema is a placeholder for a new schema for the materialized view
*/
public final class PostgresMaterializedViewMethods {
    public static final String CREATE_MATERIALIZED_VIEW = String.join(
            " ",
            PostgresCommonCommands.CREATE.formatted(
                    PostgresCreateValues.MATERIALIZED_VIEW.getOriginalValue()
            ),
            """
            %s AS %s
            """
    );

    /*
    https://stackoverflow.com/questions/63158446/execute-result-of-dynamic-postgres-query

    Refreshing materialized views

    As we already know, our materialized view does not keep the data up-to-date all the time.
    We can’t insert the data into that “table” either.
    Thus, to populate a materialized view with data from the query results after creating or updating that data later, we need to refresh the object.
    It forces the query in the core of the materialized view to re-execute.
    This way, the object gets updated and provides the latest, fresh results.

    There is one essential issue to note.
    PostgreSQL will lock the materialized view table while refreshing.
    You won’t be able to execute any queries until it gets all the data updated, and that takes time.
    However, there is a way out – the CONCURRENTLY option for the REFRESH command.

    The command is simple:

    REFRESH MATERIALIZED VIEW view_name;
    */
    public static final String REFRESH = String.join(
            " ",
            "REFRESH",
            PostgresCreateValues.MATERIALIZED_VIEW.getOriginalValue(),
            "%s;"
    );

    public static final String REFRESH_ALL_MATERIALIZED_VIEWS = """
            SELECT CONCAT( 'REFRESH', ' %s ', p.matviewname, ';' ) AS refresh
            FROM pg_matviews p
            WHERE schemaname = '%s';
            """;
}
