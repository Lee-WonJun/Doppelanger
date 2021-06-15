-- :name get-domain :? :*
-- :doc select all domains
SELECT * FROM DOMAINS

-- :name get-keyword-id :? :*
-- :doc select all domains
SELECT KeywordID FROM KEYWORDS
WHERE Keyword = :keyword

-- :name create-domain! :! :n
-- :doc creates a new user record
INSERT INTO DOMAINS
(Domain)
VALUES (:domain)


-- :name create-keyword! :! :n
-- :doc creates a new user record
INSERT INTO KEYWORDS
(Domain, Keyword)
VALUES (:domain, :keyword)

-- :name create-relation! :! :n
-- :doc creates a new user record
INSERT INTO KEYWORD_RELATIONS
(KeywordID, RelationGroup)
VALUES (:keyword_id, :relation_group)

-- :name get-relation-groups :! :*
-- :doc select relation-groups about keyword id
SELECT DISTINCT r.RelationGroup
FROM KEYWORD_RELATIONS AS r
    INNER JOIN KEYWORDS AS k ON r.KeywordID = k.KeywordID
WHERE r.KeywordID = :keyword_id

-- :name get-dopple-keyword :! :1
-- :doc select keyword of same mean
SELECT DISTINCT k.Keyword
FROM KEYWORD_RELATIONS AS r
    INNER JOIN KEYWORDS AS k ON r.KeywordID = k.KeywordID
WHERE r.RelationGroup = :relation_group AND k.Domain = :domain


-- :name get-relations :? :*
-- :doc select all relations
SELECT *
FROM KEYWORD_RELATIONS AS r
    INNER JOIN KEYWORDS As k ON r.KeywordID = k.KeywordID