CREATE TABLE journal_entries (
    id VARCHAR(36) PRIMARY KEY,
    user_id VARCHAR(36) NOT NULL REFERENCES users(id),
    trade_id VARCHAR(36) REFERENCES trades(id),
    date DATE NOT NULL,
    content TEXT NOT NULL,
    sentiment VARCHAR(20) NOT NULL DEFAULT 'neutral',
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_journal_entries_user_id ON journal_entries(user_id);
