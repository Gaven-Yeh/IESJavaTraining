CREATE TABLE CONTACT (
    id int IDENTITY PRIMARY KEY,
    customer_id int NOT NULL,
     name varchar(255) NOT NULL,
     email varchar(255) NOT NULL,
     phone varchar(255) NOT NULL,
     CONSTRAINT contact_customer_FK
        FOREIGN KEY (customer_id)
        REFERENCES customer(id)
);

INSERT INTO contact (customer_id, name, email, phone)
    SELECT id, contact_name, email, phone FROM customer;

ALTER TABLE customer DROP COLUMN contact_name;
ALTER TABLE customer DROP COLUMN email;
ALTER TABLE customer DROP COLUMN phone;