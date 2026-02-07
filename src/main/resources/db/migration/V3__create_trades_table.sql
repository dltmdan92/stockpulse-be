CREATE TABLE trades (
    id VARCHAR(36) PRIMARY KEY,
    user_id VARCHAR(36) NOT NULL REFERENCES users(id),
    stock_id VARCHAR(36) REFERENCES stocks(id),
    symbol VARCHAR(20) NOT NULL,
    name VARCHAR(200) NOT NULL,
    type VARCHAR(10) NOT NULL,
    quantity INTEGER NOT NULL,
    price NUMERIC(20, 4) NOT NULL,
    date DATE NOT NULL,
    memo TEXT NOT NULL DEFAULT '',
    tags TEXT[] NOT NULL DEFAULT '{}',
    target_price NUMERIC(20, 4),
    stop_loss NUMERIC(20, 4),
    holding_period VARCHAR(50),
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_trades_user_id ON trades(user_id);
CREATE INDEX idx_trades_tags ON trades USING GIN(tags);
CREATE INDEX idx_trades_date ON trades(date);
