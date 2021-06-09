--CREATE TABLE users
--(id VARCHAR(20) PRIMARY KEY,
-- first_name VARCHAR(30),
-- last_name VARCHAR(30),
-- email VARCHAR(30),
-- admin BOOLEAN,
-- last_login TIMESTAMP,
-- is_active BOOLEAN,
-- pass VARCHAR(300));

-- 테이블 순서는 관계를 고려하여 한 번에 실행해도 에러가 발생하지 않게 정렬되었습니다.

-- DOMAINS Table Create SQL
CREATE TABLE DOMAINS
(
    Domain  VARCHAR(45) PRIMARY KEY NOT NULL
);


-- KEYWORDS Table Create SQL
CREATE TABLE KEYWORDS
(
    KeywordID  INT            NOT NULL   PRIMARY KEY  AUTO_INCREMENT,
    Domain     VARCHAR(45)    NOT NULL,
    Keyword   VARCHAR(45)    NOT NULL
);

ALTER TABLE KEYWORDS
    ADD FOREIGN KEY (Domain)
        REFERENCES DOMAINS (Domain);


-- RELATIONS Table Create SQL
CREATE TABLE KEYWORD_RELATIONS
(
    idx        INT            NOT NULL  PRIMARY KEY   AUTO_INCREMENT,
    KeywordID      INT            NOT NULL,
    RelationGroup  VARCHAR(45)    NOT NULL
);


ALTER TABLE KEYWORD_RELATIONS
    ADD FOREIGN KEY (KeywordID)
        REFERENCES KEYWORDS (KeywordID);


