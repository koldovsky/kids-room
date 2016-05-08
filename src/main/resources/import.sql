INSERT INTO users (id_user, email, first_name, last_name, password, phone_number, role) VALUES (1, 'user@gmail.com', 'vasil','kobin', '$2a$08$6fjMaYthaRD9XpOQ7V652.N/pRpmOqdrRMU5b1otTRveK0T3pYa02', '0987654321', 0);
INSERT INTO users (id_user, email, first_name, last_name, password, phone_number, role) VALUES (2, 'manager@gmail.com', 'vasil','kobin', '$2a$08$B.5vcvm4BiBF9DdPgyKH4.1Z9wOGL9Rv9Iy1sDsniHMOOGXCZ11Je', '0987654321', 1);
INSERT INTO users (id_user, email, first_name, last_name, password, phone_number, role) VALUES (3, 'admin@gmail.com', 'vasil','kobin', '$2a$08$FSd5VkorwWVS7V8XQFl1y..ULY5fcUAASc/.6mH5wFj/ppopK0Cl2', '0987654321', 2);

INSERT INTO users (id_user, email, first_name, last_name, password, phone_number, role) VALUES (4, 'parent1@gmail.com', 'parent','One', '$2a$08$6fjMaYthaRD9XpOQ7V652.N/pRpmOqdrRMU5b1otTRveK0T3pYa02', '0987654321', 0);
INSERT INTO users (id_user, email, first_name, last_name, password, phone_number, role) VALUES (5, 'parent2@gmail.com', 'parent','Two', '$2a$08$6fjMaYthaRD9XpOQ7V652.N/pRpmOqdrRMU5b1otTRveK0T3pYa02', '0987654321', 0);

INSERT INTO children  VALUES (1, "no comments", "12.02.2011", "Adam", "First", 1);
INSERT INTO children  VALUES (2, "no comments", "12.02.2011", "Eva", "First", 1);

insert into cities values (1, "Lviv");
insert into rooms values (1, "Pasternaka 8", 40, "Roomy", "+380974074537", 1, 2);
insert into booking values (13, '2015-04-04', '2015-04-04', "The whole world of comments", false, 1, 1, 1);