CREATE TABLE IF NOT EXISTS users
(
    id          BIGSERIAL PRIMARY KEY ,
    username    VARCHAR(200) UNIQUE ,
    name        VARCHAR(200) NOT NULL ,
    password    varchar(20) DEFAULT NULL
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
--     user_id             BIGSERIAL NOT NULL ,
--     debtor_id           BIGSERIAL NOT NULL ,
    sum                 INT NOT NULL ,
    description         VARCHAR(200) ,
    date                DATE NOT NULL ,
    repay_sum           INT ,
    repay_date          DATE ,
    repay_description   VARCHAR(200) ,
    FOREIGN KEY (debtship_id) REFERENCES debtship (id) ON DELETE CASCADE
--     FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ,
--     FOREIGN KEY (debtor_id) REFERENCES users (id) ON DELETE CASCADE
);