ALTER TABLE bank
    ALTER COLUMN short_name TYPE varchar(25);

INSERT INTO bank (bank_code, short_name, long_name)
VALUES ('NTB', 'NTB', 'Nations Truest Bank'),
       ('COMBANK', 'COMBANK', 'Commercial Bank'),
       ('HNB', 'HNB', 'Hatton National Bank'),
       ('BOC', 'BOC', 'Bank Of Ceylon'),
       ('SAMPATH', 'Sampath Bank', 'Sampath Bank'),
       ('SEYLAN', 'Seylan Bank', 'Seylan Bank'),
       ('NDB', 'NDB', 'National Development Bank'),
       ('NSB', 'NSB', 'National Savings Bank'),
       ('DFCC', 'DFCC Bank', 'DFCC Bank'),
       ('PEOPLES', 'Peoples Bank', 'Peoples Bank'),
       ('UNION', 'Union Bank', 'Union Bank'),
       ('HSBC', 'HSBC', 'HSBC Bank');