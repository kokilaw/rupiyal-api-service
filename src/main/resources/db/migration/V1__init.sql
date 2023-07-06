CREATE TABLE bank
(
    bank_code  varchar(20) PRIMARY KEY,
    short_name varchar(10) NOT NULL,
    long_name  varchar(25) NOT NULL
);

CREATE TABLE buying_rate
(
    id            bigint PRIMARY KEY,
    currency_code varchar(3),
    rate          numeric(19, 4),
    date          DATE NOT NULL,
    created_at    timestamp without time zone NOT NULL,
    updated_at    timestamp without time zone NOT NULL,
    bank_code     varchar(20),
    FOREIGN KEY (bank_code) REFERENCES bank (bank_code)
);

create sequence buying_rate_seq increment by 50;

CREATE TABLE selling_rate
(
    id            bigint PRIMARY KEY,
    currency_code varchar(3),
    rate          numeric(19, 4),
    date          DATE NOT NULL,
    created_at    timestamp without time zone NOT NULL,
    updated_at    timestamp without time zone NOT NULL,
    bank_code     varchar(20),
    FOREIGN KEY (bank_code) REFERENCES bank (bank_code)
);

create sequence selling_rate_seq increment by 50;