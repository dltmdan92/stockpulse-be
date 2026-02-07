CREATE TABLE stocks (
    id VARCHAR(36) PRIMARY KEY,
    user_id VARCHAR(36) NOT NULL REFERENCES users(id),
    symbol VARCHAR(20) NOT NULL,
    name VARCHAR(200) NOT NULL,
    quantity INTEGER NOT NULL DEFAULT 0,
    avg_price NUMERIC(20, 4) NOT NULL DEFAULT 0,
    current_price NUMERIC(20, 4) NOT NULL DEFAULT 0,
    sector VARCHAR(100) NOT NULL DEFAULT '',
    country VARCHAR(10) NOT NULL DEFAULT 'US',
    added_at DATE NOT NULL DEFAULT CURRENT_DATE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_stocks_user_id ON stocks(user_id);
