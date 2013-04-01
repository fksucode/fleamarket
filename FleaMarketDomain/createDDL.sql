CREATE TABLE T_BID (BID_ID BIGINT NOT NULL, BID_DATE DATE, BID_PRICE FLOAT, BID_STATUS VARCHAR(255), BID_BIDDER VARCHAR(255), BID_ITEM_ID BIGINT, PRIMARY KEY (BID_ID))
CREATE TABLE T_USER (USER_ID VARCHAR(255) NOT NULL, USER_TYPE VARCHAR(1), BIRTH_DATE DATE, EMAIL VARCHAR(255) NOT NULL, FIRST_NAME VARCHAR(255) NOT NULL, LAST_NAME VARCHAR(255) NOT NULL, PASS VARCHAR(255) NOT NULL, TEL_NUMBER VARCHAR(255), CITY VARCHAR(255), COUNTRY VARCHAR(255), STREET VARCHAR(255), ZIP_CODE VARCHAR(255), BIDDER_STATUS VARCHAR(255), CREDIT_RATING BIGINT, COMM_RATE FLOAT, MAX_ITEMS BIGINT, PRIMARY KEY (USER_ID))
CREATE TABLE T_USER_PICTURE (USER_ID VARCHAR(255) NOT NULL, PICTURE BLOB(2147483647), PRIMARY KEY (USER_ID))
CREATE TABLE T_BILLING_DETAILS (BILLING_ID BIGINT NOT NULL, ACCOUNT_NO VARCHAR(255), EXPIRY_DATE DATE, SECRET_CODE VARCHAR(255), CITY VARCHAR(255), COUNTRY VARCHAR(255), STREET VARCHAR(255), ZIP_CD VARCHAR(255), PRIMARY KEY (BILLING_ID))
CREATE TABLE T_ITEM (ITEM_ID BIGINT NOT NULL, BID_END_DATE DATE, CREATED_DATE DATE, ITEM_DESC VARCHAR(500), INITIAL_PRICE FLOAT NOT NULL, ITEM_NAME VARCHAR(50) NOT NULL, SELLER_ID VARCHAR(255), PRIMARY KEY (ITEM_ID))
CREATE TABLE T_ORDER (ORDERID BIGINT NOT NULL, STATUS INTEGER, USER_USER_ID VARCHAR(255), BILLINGINFO_BILLING_ID BIGINT, ITEM_ITEM_ID BIGINT, SHIPPINGINFO_SHIPPING_ID BIGINT, PRIMARY KEY (ORDERID))
CREATE TABLE T_SHIPPING_DETAILS (SHIPPING_ID BIGINT NOT NULL, COST DECIMAL(15), CITY VARCHAR(255), COUNTRY VARCHAR(255), STREET VARCHAR(255), ZIP_CODE VARCHAR(255), PRIMARY KEY (SHIPPING_ID))
CREATE TABLE T_GROUP (GROUP_ID VARCHAR(255) NOT NULL, PRIMARY KEY (GROUP_ID))
CREATE TABLE T_USER_GROUP (USER_ID VARCHAR(255) NOT NULL, GROUP_ID VARCHAR(255) NOT NULL, PRIMARY KEY (USER_ID, GROUP_ID))
ALTER TABLE T_BID ADD CONSTRAINT T_BID_BID_ITEM_ID FOREIGN KEY (BID_ITEM_ID) REFERENCES T_ITEM (ITEM_ID)
ALTER TABLE T_BID ADD CONSTRAINT T_BID_BID_BIDDER FOREIGN KEY (BID_BIDDER) REFERENCES T_USER (USER_ID)
ALTER TABLE T_USER_PICTURE ADD CONSTRAINT TUSERPICTUREUSERID FOREIGN KEY (USER_ID) REFERENCES T_USER (USER_ID)
ALTER TABLE T_ITEM ADD CONSTRAINT T_ITEM_SELLER_ID FOREIGN KEY (SELLER_ID) REFERENCES T_USER (USER_ID)
ALTER TABLE T_ORDER ADD CONSTRAINT TORDERITEM_ITEM_ID FOREIGN KEY (ITEM_ITEM_ID) REFERENCES T_ITEM (ITEM_ID)
ALTER TABLE T_ORDER ADD CONSTRAINT TORDERUSER_USER_ID FOREIGN KEY (USER_USER_ID) REFERENCES T_USER (USER_ID)
ALTER TABLE T_ORDER ADD CONSTRAINT TRDRBLLNGNFBLLNGID FOREIGN KEY (BILLINGINFO_BILLING_ID) REFERENCES T_BILLING_DETAILS (BILLING_ID)
ALTER TABLE T_ORDER ADD CONSTRAINT TRDSHPPNGNFSHPPNGD FOREIGN KEY (SHIPPINGINFO_SHIPPING_ID) REFERENCES T_SHIPPING_DETAILS (SHIPPING_ID)
ALTER TABLE T_USER_GROUP ADD CONSTRAINT TUSER_GROUPUSER_ID FOREIGN KEY (USER_ID) REFERENCES T_USER (USER_ID)
ALTER TABLE T_USER_GROUP ADD CONSTRAINT TUSERGROUPGROUP_ID FOREIGN KEY (GROUP_ID) REFERENCES T_GROUP (GROUP_ID)
CREATE TABLE SEQUENCE (SEQ_NAME VARCHAR(50) NOT NULL, SEQ_COUNT DECIMAL(15), PRIMARY KEY (SEQ_NAME))
INSERT INTO SEQUENCE(SEQ_NAME, SEQ_COUNT) values ('SEQ_GEN', 0)
CREATE SEQUENCE BID_SEQUENCE START WITH 1
