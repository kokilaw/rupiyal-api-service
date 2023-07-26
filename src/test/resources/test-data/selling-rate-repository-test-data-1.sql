INSERT INTO selling_rate (id, currency_code, rate, date, created_at, updated_at, bank_code)
VALUES (nextval('selling_rate_seq'), 'USD', 304.5410, '2023-07-20', '2023-07-20 00:55:42.511836',
        '2023-07-20 00:56:42.511836', 'BOC'),
       (nextval('selling_rate_seq'), 'USD', 306.5410, '2023-07-20', '2023-07-20 02:55:42.511836',
        '2023-07-20 02:56:42.511836', 'BOC'),
       (nextval('selling_rate_seq'), 'GBP', 406.5410, '2023-07-20', '2023-07-20 02:55:42.511836',
        '2023-07-20 05:56:42.511836', 'BOC'),
       (nextval('selling_rate_seq'), 'GBP', 406.5410, '2023-07-20', '2023-07-20 02:55:42.511836',
        '2023-07-20 05:56:42.511836', 'NSB');