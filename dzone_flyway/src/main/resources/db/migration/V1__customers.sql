CREATE TABLE customer (
 id int IDENTITY PRIMARY KEY,
 name varchar(255) NOT NULL,
 contact_name varchar(255) NOT NULL,
 email varchar(255) NOT NULL,
 phone varchar(255) NOT NULL
);

INSERT INTO customer (name, contact_name, email, phone) values
    ('Coca cola', 'John Doe', 'john.doe@cocacola.com', '012-3456789'),
    ('Dell', 'Bob Frapples', 'bob.frapples@dell.com', '202-555-0180'),
    ('Apple', 'Barb Ackue', 'barb.ackue@apple.com', '202-555-0128'),
    ('Google', 'Sue Vaneer', 'sue.vaneer@google.com', '202-555-0174'),
    ('FedEx', 'Robin Banks', 'robin.banks@fedex.com', '202-555-0146'),
    ('Salesforce', 'Zack Lee', 'zack.lee@salesforce.com', '202-555-0122');