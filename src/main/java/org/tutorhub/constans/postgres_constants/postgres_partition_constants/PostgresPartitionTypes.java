package org.tutorhub.constans.postgres_constants.postgres_partition_constants;

public enum PostgresPartitionTypes {
    /*
    Range partitioning is a type of table partitioning where data is divided into partitions based on a specified range of values in a column.
    This is particularly useful when dealing with time-series data or any data that has a natural order.
    Each partition represents a distinct range of values, and data falling within that range is stored in that partition.
    Range partitioning allows for efficient retrieval of data within specific ranges, leading to improved query performance.

    Example:

    CREATE TABLE measurement (
        city_id         serial not null,
        logdate         date not null default now(),
        peaktemp        int,
        unitsales       int
    ) PARTITION BY RANGE ( logdate );

    CREATE TABLE IF NOT EXISTS TEST_2024_APRIL PARTITION OF measurement FOR VALUES FROM ( 'Mon Apr 01 16:50:33 UZT 2024' ) TO ( 'Wed May 01 16:50:33 UZT 2024' );

    ALTER TABLE TEST_2024_APRIL ADD CONSTRAINT TEST_2024_APRIL_CONSTRAINT
    CHECK ( logdate >= 'Mon Apr 01 16:50:33 UZT 2024' AND logdate <= 'Wed May 01 16:50:33 UZT 2024' )

    CREATE INDEX ON measurement USING BTree( logdate );
    */
    RANGE,

    /*
    List partitioning is another type of table partitioning in PostgreSQL,
    where data is divided into partitions based on specific values in a column.
    Unlike range partitioning, which uses a range of values, list partitioning allows you to define specific values for each partition.
    This partitioning technique is useful when data can be categorized into distinct, non-overlapping sets.

    List partitioning in PostgreSQL is a valuable technique for managing and querying data based on specific values in a column.
    By dividing data into partitions based on categories or other distinct sets,
    list partitioning allows for faster data retrieval and improved data management

    Example:

    CREATE TABLE products (
        product_id SERIAL PRIMARY KEY,
        category TEXT,
        product_name TEXT,
        price NUMERIC
    ) partition by list( category );

    CREATE TABLE electronics PARTITION OF products
        FOR VALUES IN ('Electronics');

    CREATE TABLE clothing PARTITION OF products
        FOR VALUES IN ('Clothing');

    CREATE TABLE furniture PARTITION OF products
        FOR VALUES IN ('Furniture');
    */
    LIST,

    /*
    Hash partitioning is a type of table partitioning in PostgreSQL, where data is divided into partitions based on the hash value of a specified column.
    Unlike range or list partitioning, which uses specific values or ranges,
    hash partitioning uses a hash function to distribute data uniformly across partitions.
    This partitioning technique is useful when you want to evenly distribute data across partitions to achieve load balancing.

    Example:

    CREATE TABLE orders (
        order_id SERIAL PRIMARY KEY,
        order_date DATE,
        customer_id INT,
        total_amount NUMERIC
    ) partition by hash( customer_id );

    CREATE TABLE orders_1 PARTITION OF orders
    FOR VALUES WITH (MODULUS 3, REMAINDER 0);

    CREATE TABLE orders_2 PARTITION OF orders
        FOR VALUES WITH (MODULUS 3, REMAINDER 1);

    CREATE TABLE orders_3 PARTITION OF orders
        FOR VALUES WITH (MODULUS 3, REMAINDER 2);

    In this example, we use the HASH() function to specify that the data should be partitioned based on the hash value of the customer_id column.
    We use MODULUS and REMAINDER to specify the number of partitions (3 in this case) and the remainder value for each partition.

    Hash partitioning in PostgreSQL is a useful technique for distributing data evenly across partitions based on the hash value of a specified column.
    By leveraging hash functions to uniformly distribute data, hash partitioning achieves load balancing and improves query performance.
    */
    HASH,
}
