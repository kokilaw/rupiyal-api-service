ALTER TABLE bank ADD COLUMN theme_config jsonb DEFAULT '{"accentColor": ""}';

UPDATE bank SET theme_config = '{"accentColor": "#006D77"}' WHERE bank_code = 'NTB';
UPDATE bank SET theme_config = '{"accentColor": "#83C5BE"}' WHERE bank_code = 'COMBANK';
UPDATE bank SET theme_config = '{"accentColor": "#FFDDD2"}' WHERE bank_code = 'HNB';
UPDATE bank SET theme_config = '{"accentColor": "#E29578"}' WHERE bank_code = 'BOC';
UPDATE bank SET theme_config = '{"accentColor": "#A7CECB"}' WHERE bank_code = 'SAMPATH';
UPDATE bank SET theme_config = '{"accentColor": "#8BA6A9"}' WHERE bank_code = 'SEYLAN';
UPDATE bank SET theme_config = '{"accentColor": "#75704E"}' WHERE bank_code = 'NDB';
UPDATE bank SET theme_config = '{"accentColor": "#CACC90"}' WHERE bank_code = 'NSB';
UPDATE bank SET theme_config = '{"accentColor": "#F4EBBE"}' WHERE bank_code = 'DFCC';
UPDATE bank SET theme_config = '{"accentColor": "#EFAAC4"}' WHERE bank_code = 'PEOPLES';
UPDATE bank SET theme_config = '{"accentColor": "#FFC4D1"}' WHERE bank_code = 'UNION';
UPDATE bank SET theme_config = '{"accentColor": "#FFE8E1"}' WHERE bank_code = 'HSBC';
UPDATE bank SET theme_config = '{"accentColor": "#D4DCCD"}' WHERE bank_code = 'STANCHART';