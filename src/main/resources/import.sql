INSERT INTO users (id_user, email, first_name, last_name, password, phone_number, role) VALUES (1, 'user@gmail.com', 'Vasyl','Kobin', '$2a$08$6fjMaYthaRD9XpOQ7V652.N/pRpmOqdrRMU5b1otTRveK0T3pYa02', '0987654321', 0);
INSERT INTO users (id_user, email, first_name, last_name, password, phone_number, role) VALUES (2, 'manager@gmail.com', 'Vasyl','Kobin', '$2a$08$B.5vcvm4BiBF9DdPgyKH4.1Z9wOGL9Rv9Iy1sDsniHMOOGXCZ11Je', '0987654321', 1);
INSERT INTO users (id_user, email, first_name, last_name, password, phone_number, role) VALUES (3, 'admin@gmail.com', 'Vasyl','Kobin', '$2a$08$FSd5VkorwWVS7V8XQFl1y..ULY5fcUAASc/.6mH5wFj/ppopK0Cl2', '0987654321', 2);

INSERT INTO users (id_user, email, first_name, last_name, password, phone_number, role) VALUES (4, 'parent1@gmail.com', 'Parent','One', '$2a$08$6fjMaYthaRD9XpOQ7V652.N/pRpmOqdrRMU5b1otTRveK0T3pYa02', '0987654321', 0);
INSERT INTO users (id_user, email, first_name, last_name, password, phone_number, role) VALUES (5, 'parent2@gmail.com', 'Parent','Two', '$2a$08$6fjMaYthaRD9XpOQ7V652.N/pRpmOqdrRMU5b1otTRveK0T3pYa02', '0987654321', 0);

INSERT INTO children(id_child, comment, date_of_birth_child, first_name_child, last_name_child, id_user)  VALUES (1, "no comments", "12.02.2011", "Adam", "First", 1);
INSERT INTO children(id_child, comment, date_of_birth_child, first_name_child, last_name_child, id_user)  VALUES (2, "no comments", "12.02.2011", "Eva", "First", 1);
INSERT INTO children(id_child, comment, date_of_birth_child, first_name_child, last_name_child, id_user)  VALUES (4, "no comments", "12.02.2011", "Sem", "Sec", 4);


insert into cities values (1, "Lviv");
insert into rooms (id_room, address_room, capacity_room, name_room, phone_room, id_city, id_user) values (1, "Pasternaka 8", 40, "Roomy", "+380974074537", 1, 2);
insert into rooms (id_room, address_room, capacity_room, name_room, phone_room, id_city, id_user) values (2, "Pasternaka 5", 40, "Roomy", "+380974074537", 1, 2);

insert into bookings values (1, '2016-05-02 16:56', '2016-05-02 14:57', "The whole world of comments", false, 2, 1, 1);
insert into bookings values (2, '2016-04-30 17:58', '2016-04-30 14:03', "The whole world of comments", false, 2, 1, 1);
insert into bookings values (3, '2016-04-30 19:32', '2016-04-30 17:37', "The whole world of comments", false, 2, 1, 1);
insert into bookings values (5, '2016-05-04 16:58', '2016-05-04 14:57', "The whole world of comments", false, 2, 1, 1);
insert into bookings values (7, '2016-05-05 18:36', '2016-05-05 17:25', "The whole world of comments", false, 1, 1, 1);
insert into bookings values (10, '2016-05-06 15:20', '2016-05-06 15:05', "The whole world of comments", false, 1, 1, 1);
insert into bookings values (12, '2016-05-07 19:38', '2016-05-07 14:37', "The whole world of comments", false, 2, 1, 1);
insert into bookings values (13, '2016-05-01 15:47', '2016-05-01 12:30', "The whole world of comments", false, 1, 1, 1);
insert into bookings values (15, '2016-05-10 16:39', '2016-05-10 15:47', "The whole world of comments", false, 4, 1, 4);
insert into bookings values (16, '2016-05-10 16:39', '2015-04-04', "The whole world of comments", false, 1, 1, 1);
insert into bookings values (17, '2016-05-10 16:39', '2015-04-04', "The whole world of comments", false, 1, 1, 1);
insert into bookings values (18, '2016-05-10 16:39', '2015-04-04', "The whole world of comments", false, 4, 1, 1);


insert into events VALUES (1, 3, 5, 'NO', '2016-05-10 16:00:00', 'EVENT', '2016-05-10 15:00:00', 1);
insert into events VALUES (2, 3, 5, 'NO', '2016-05-15 16:00:00', 'EVENT', '2016-05-15 15:00:00', 2);
insert into events VALUES (3, 3, 5, 'NO', '2016-05-16 16:00:00', 'EVENT', '2016-05-16 15:00:00', 2);
insert into events VALUES (4, 3, 5, 'NO', '2016-05-26 14:00:00', 'EVENT', '2016-05-26 10:00:00', 2);