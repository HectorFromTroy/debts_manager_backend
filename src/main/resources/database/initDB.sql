CREATE TABLE IF NOT EXISTS users
(
    id          BIGSERIAL PRIMARY KEY ,
    username    VARCHAR(200) ,-- may be null because of non real debtors
    name        VARCHAR(200) NOT NULL ,
    password    varchar(200) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS debtship
(
    id          BIGSERIAL PRIMARY KEY ,
    user_id     BIGSERIAL NOT NULL ,
    debtor_id   BIGSERIAL NOT NULL ,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ,
    FOREIGN KEY (debtor_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS debt
(
    id                  BIGSERIAL PRIMARY KEY ,
    debtship_id         BIGSERIAL NOT NULL ,
    sum                 INT NOT NULL ,
    description         VARCHAR(200) ,
    date                TIMESTAMP NOT NULL ,
    is_paid_off         BOOLEAN,
    repay_date          TIMESTAMP ,
    repay_description   VARCHAR(200) ,
    FOREIGN KEY (debtship_id) REFERENCES debtship (id) ON DELETE CASCADE
);