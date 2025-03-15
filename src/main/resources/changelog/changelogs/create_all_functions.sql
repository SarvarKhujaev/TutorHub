CREATE OR REPLACE FUNCTION SHOW_ALL_INDEXES_IN_TABLE( schema_name text, table_name text )
returns SETOF text as $$
    SELECT indexname FROM pg_indexes WHERE schemaname = schema_name AND tablename = table_name;
    $$
    language sql;

CREATE OR REPLACE FUNCTION SHOW_ALL_TABLES_IN_SCHEMA()
returns SETOF text as $$
    SELECT
        table_schema || '.' || table_name as show_tables
    FROM
        information_schema.tables
    WHERE
        table_type = 'BASE TABLE'
        AND
        table_schema = 'university';
    $$
    language sql;



CREATE OR REPLACE FUNCTION generate_phone_number()
    RETURNS TEXT AS $$
DECLARE
    phone_number TEXT;
BEGIN
    LOOP
        phone_number := '+99897' || LPAD(FLOOR(RANDOM() * 10000000)::TEXT, 7, '0');
        RETURN phone_number;
    END LOOP;
END;
$$ LANGUAGE plpgsql;
