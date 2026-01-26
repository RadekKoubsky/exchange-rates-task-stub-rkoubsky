CREATE TABLE exchange_rates (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    base_currency VARCHAR(3) NOT NULL,
    date DATE NOT NULL,
    rates JSONB NOT NULL
);

CREATE INDEX idx_exchange_rates_currency_date ON exchange_rates(base_currency, date);
