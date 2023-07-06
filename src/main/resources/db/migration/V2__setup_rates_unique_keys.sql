ALTER TABLE buying_rate
    ADD CONSTRAINT buying_currency_entry_unique_key UNIQUE (bank_code, currency_code, date, rate);
ALTER TABLE selling_rate
    ADD CONSTRAINT selling_currency_entry_unique_key UNIQUE (bank_code, currency_code, date, rate);
