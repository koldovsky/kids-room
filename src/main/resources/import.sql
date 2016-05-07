INSERT INTO users (id_user, email, first_name, last_name, password, phone_number, role) VALUES (1, 'user@gmail.com', 'vasil','kobin', '$2a$08$6fjMaYthaRD9XpOQ7V652.N/pRpmOqdrRMU5b1otTRveK0T3pYa02', '0987654321', 0);
INSERT INTO users (id_user, email, first_name, last_name, password, phone_number, role) VALUES (2, 'manager@gmail.com', 'vasil','kobin', '$2a$08$B.5vcvm4BiBF9DdPgyKH4.1Z9wOGL9Rv9Iy1sDsniHMOOGXCZ11Je', '0987654321', 1);
INSERT INTO users (id_user, email, first_name, last_name, password, phone_number, role) VALUES (3, 'admin@gmail.com', 'vasil','kobin', '$2a$08$FSd5VkorwWVS7V8XQFl1y..ULY5fcUAASc/.6mH5wFj/ppopK0Cl2', '0987654321', 2);

INSERT INTO children (id_child, comment, dateOfBirth, firstName, lastName, id_user) VALUES (1, "no comments", "12.02.2011", "Adam", "First", 1);
INSERT INTO children (id_child, comment, dateOfBirth, firstName, lastName, id_user) VALUES (2, "no comments", "12.02.2011", "Eva", "First", 1);

