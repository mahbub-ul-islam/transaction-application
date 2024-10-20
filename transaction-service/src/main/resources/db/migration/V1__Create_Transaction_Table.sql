CREATE TABLE IF NOT EXISTS transactions (
    id BIGSERIAL PRIMARY KEY,  -- Unique identifier for each transaction
    account_number VARCHAR(20) NOT NULL,
    trx_amount DECIMAL(15, 2) NOT NULL,
    description VARCHAR(255) NOT NULL,
    trx_date VARCHAR(20) NOT NULL,
    trx_time VARCHAR(20) NOT NULL,
    customer_id VARCHAR(20) NOT NULL,
    version bigint,
    created_at timestamp(6) without time zone,
    updated_at timestamp(6) without time zone  -- Update timestamp
    -- Unique constraint on a combination of fields
--     UNIQUE (account_number, trx_date, trx_time, customer_id, description)
);

-- Creating indexes for search optimization
CREATE INDEX IF NOT EXISTS idx_account_number ON transactions(account_number);
CREATE INDEX IF NOT EXISTS idx_customer_id ON transactions(customer_id);
CREATE INDEX IF NOT EXISTS idx_description ON transactions(description);