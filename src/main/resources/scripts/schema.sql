CREATE TABLE bank
(
    bank_code       varchar(20) PRIMARY KEY,
    bank_short_name varchar(10) NOT NULL,
    bank_long_name  varchar(25) NOT NULL
);

CREATE TABLE buying_rate
(
    id            bigint PRIMARY KEY,
    currency_code char(3),
    rate          real,
    date          DATE NOT NULL,
    created_date  timestamp without time zone NOT NULL,
    updated_date  timestamp without time zone NOT NULL,
    bank_code     varchar(20),
    FOREIGN KEY (bank_code) REFERENCES bank (bank_code)
);

CREATE TABLE selling_rate
(
    id            bigint PRIMARY KEY,
    currency_code char(3),
    rate          real,
    date          DATE NOT NULL,
    created_date  timestamp without time zone NOT NULL,
    updated_date  timestamp without time zone NOT NULL,
    bank_code     varchar(20),
    FOREIGN KEY (bank_code) REFERENCES bank (bank_code)
);