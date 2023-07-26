ALTER TABLE bank ADD COLUMN logo jsonb DEFAULT '{"defaultUrl": ""}';

UPDATE bank SET logo = '{"defaultUrl": "https://assets.lkr.exchange/images/logos/ntb-logo-min.png"}' WHERE bank_code = 'NTB';
UPDATE bank SET logo = '{"defaultUrl": "https://assets.lkr.exchange/images/logos/combank-logo-min.png"}' WHERE bank_code = 'COMBANK';
UPDATE bank SET logo = '{"defaultUrl": "https://assets.lkr.exchange/images/logos/hnb-logo-min.png"}' WHERE bank_code = 'HNB';
UPDATE bank SET logo = '{"defaultUrl": "https://assets.lkr.exchange/images/logos/boc-logo-min.png"}' WHERE bank_code = 'BOC';
UPDATE bank SET logo = '{"defaultUrl": "https://assets.lkr.exchange/images/logos/sampath-logo-min.png"}' WHERE bank_code = 'SAMPATH';
UPDATE bank SET logo = '{"defaultUrl": "https://assets.lkr.exchange/images/logos/seylan-logo-min.png"}' WHERE bank_code = 'SEYLAN';
UPDATE bank SET logo = '{"defaultUrl": "https://assets.lkr.exchange/images/logos/ndb-logo-min.png"}' WHERE bank_code = 'NDB';
UPDATE bank SET logo = '{"defaultUrl": "https://assets.lkr.exchange/images/logos/nsb-logo-min.png"}' WHERE bank_code = 'NSB';
UPDATE bank SET logo = '{"defaultUrl": "https://assets.lkr.exchange/images/logos/dfcc-logo-min.png"}' WHERE bank_code = 'DFCC';
UPDATE bank SET logo = '{"defaultUrl": "https://assets.lkr.exchange/images/logos/peoples-logo-min.png"}' WHERE bank_code = 'PEOPLES';
UPDATE bank SET logo = '{"defaultUrl": "https://assets.lkr.exchange/images/logos/union-logo-min.png"}' WHERE bank_code = 'UNION';
UPDATE bank SET logo = '{"defaultUrl": "https://assets.lkr.exchange/images/logos/hsbc-logo-min.png"}' WHERE bank_code = 'HSBC';