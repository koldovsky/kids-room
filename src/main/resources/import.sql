INSERT INTO users (id_user, email, first_name, last_name, password, phone_number, role) VALUES (1, 'user@gmail.com', 'vasil','kobin', '12dea96fec20593566ab75692c9949596833adc9', '0987654321', 0);
INSERT INTO users (id_user, email, first_name, last_name, password, phone_number, role) VALUES (2, 'manager@gmail.com', 'vasil','kobin', '1a8565a9dc72048ba03b4156be3e569f22771f23', '0987654321', 1);
INSERT INTO users (id_user, email, first_name, last_name, password, phone_number, role) VALUES (3, 'admin@gmail.com', 'vasil','kobin', 'd033e22ae348aeb5660fc2140aec35850c4da997', '0987654321', 2);

INSERT INTO children (id_child, comment, dateOfBirth, firstName, lastName, id_user) VALUES (1, "no comments", "12.02.2011", "Adam", "First", 1);
INSERT INTO children (id_child, comment, dateOfBirth, firstName, lastName, id_user) VALUES (2, "no comments", "12.02.2011", "Eva", "First", 1);