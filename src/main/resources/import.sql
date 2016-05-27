INSERT INTO users (id_user, email, first_name, last_name, password, phone_number, role, confirmed, active) VALUES (1, 'user@softserveinc.com', 'Alan','Bom', '$2a$08$6fjMaYthaRD9XpOQ7V652.N/pRpmOqdrRMU5b1otTRveK0T3pYa02', '0987654321', 0, 1, 1);
INSERT INTO users (id_user, email, first_name, last_name, password, phone_number, role, confirmed, active) VALUES (2, 'manager@softserveinc.com', 'Jackson','Bim', '$2a$08$B.5vcvm4BiBF9DdPgyKH4.1Z9wOGL9Rv9Iy1sDsniHMOOGXCZ11Je', '0987654321', 1, 1,1);
INSERT INTO users (id_user, email, first_name, last_name, password, phone_number, role, confirmed, active) VALUES (3, 'admin@softserveinc.com', 'Tony','West', '$2a$08$FSd5VkorwWVS7V8XQFl1y..ULY5fcUAASc/.6mH5wFj/ppopK0Cl2', '0987654321', 2, 1,1);
INSERT INTO users (id_user, email, first_name, last_name, password, phone_number, role, confirmed, active) VALUES (6, 'manager1@softserveinc.com', 'Alan','Bom', '$2a$08$6fjMaYthaRD9XpOQ7V652.N/pRpmOqdrRMU5b1otTRveK0T3pYa02', '0987654321', 1, 0,1);
INSERT INTO users (id_user, email, first_name, last_name, password, phone_number, role, confirmed, active) VALUES (4, 'parent1@softserveinc.com', 'Mario','Kara', '$2a$08$6fjMaYthaRD9XpOQ7V652.N/pRpmOqdrRMU5b1otTRveK0T3pYa02', '0987654321', 0, 1,1);
INSERT INTO users (id_user, email, first_name, last_name, password, phone_number, role, confirmed, active) VALUES (5, 'parent2@softserveinc.com', 'Miron','Tereh', '$2a$08$6fjMaYthaRD9XpOQ7V652.N/pRpmOqdrRMU5b1otTRveK0T3pYa02', '0987654321', 0, 0,1);
INSERT INTO users (id_user, email, first_name, last_name, password, phone_number, role, confirmed, active) VALUES (7, 'blocked@softserveinc.com', 'Miron','Tereh', '$2a$08$6fjMaYthaRD9XpOQ7V652.N/pRpmOqdrRMU5b1otTRveK0T3pYa02', '0987654321', 0, 1,0);



INSERT INTO children(id_child, comment, date_of_birth_child, first_name_child, last_name_child, id_user, gender)  VALUES (1, "he likes to play poker", "12.02.2011", "Adam", "First", 1, 0);
INSERT INTO children(id_child, comment, date_of_birth_child, first_name_child, last_name_child, id_user, gender)  VALUES (2, "likes music", "12.02.2011", "Eva", "First", 1, 1);
INSERT INTO children(id_child, comment, date_of_birth_child, first_name_child, last_name_child, id_user, gender)  VALUES (4, "drink only whiskey", "12.02.2011", "Sem", "Sec", 4, 0);
INSERT INTO children(id_child, comment, date_of_birth_child, first_name_child, last_name_child, id_user, gender)  VALUES (5, "drink only whiskey", "2014-02-13", "Jimbo", "Third", 5, 0);
INSERT INTO children(id_child, comment, date_of_birth_child, first_name_child, last_name_child, id_user, gender)  VALUES (6, "drink only whiskey", "2014-02-12", "Jimro", "Fours", 5, 0);


insert into rooms (id_room, address_room, capacity_room, name_room, phone_room, city_room, id_user) values (1, "Pasternaka 8", 40, "Roomy", "+380974074537", "Lviv", 2);
insert into rooms (id_room, address_room, capacity_room, name_room, phone_room, city_room, id_user) values (2, "Pasternaka 5", 40, "LV-5", "+380974074537", "Lviv", 2);
insert into rooms (id_room, address_room, capacity_room, name_room, phone_room, city_room, id_user) values (3, "Pasternaka 15", 40, "LV-4", "+380974074537", "Kyiv", 6);

INSERT INTO rates(id_rate, hour_rate, price_rate, id_room)  VALUES (1, 2, 300, 1);
INSERT INTO rates(id_rate, hour_rate, price_rate, id_room)  VALUES (2, 4, 500, 1);
INSERT INTO rates(id_rate, hour_rate, price_rate, id_room)  VALUES (3, 6, 600, 1);
INSERT INTO rates(id_rate, hour_rate, price_rate, id_room)  VALUES (4, 2, 80, 2);
INSERT INTO rates(id_rate, hour_rate, price_rate, id_room)  VALUES (5, 4, 90, 2);
INSERT INTO rates(id_rate, hour_rate, price_rate, id_room)  VALUES (6, 6, 100, 2);

insert into events VALUES (1, 3, 5, 'NO', '2016-05-10 16:00:00', 'EVENT1', '2016-05-10 15:00:00', 1);
insert into events VALUES (2, 3, 5, 'NO', '2016-05-15 16:00:00', 'EVENT2', '2016-05-15 15:00:00', 2);
insert into events VALUES (3, 3, 5, 'NO', '2016-05-16 16:00:00', 'EVENT3', '2016-05-16 15:00:00', 2);
insert into events VALUES (4, 3, 5, 'NO', '2016-05-26 14:00:00', 'EVENT4', '2016-05-26 10:00:00', 2);

-- DEMIAN DEMO START
-- user@softserveinc.com bookings for month start
insert into bookings (id_book, booking_end_time, booking_start_time, comment, is_cancelled, id_child, id_room, id_user) values (1, '2016-05-01 17:58', '2016-05-01 14:03', "The whole world of comments", false, 1, 1, 1);
insert into bookings (id_book, booking_end_time, booking_start_time, comment, is_cancelled, id_child, id_room, id_user) values (2, '2016-05-02 19:32', '2016-05-02 17:37', "The whole world of comments", false, 2, 1, 1);
insert into bookings (id_book, booking_end_time, booking_start_time, comment, is_cancelled, id_child, id_room, id_user) values (3, '2016-05-03 19:38', '2016-05-03 14:37', "The whole world of comments", false, 1, 1, 1);
insert into bookings (id_book, booking_end_time, booking_start_time, comment, is_cancelled, id_child, id_room, id_user) values (4, '2016-05-04 16:56', '2016-05-04 14:57', "The whole world of comments", false, 2, 1, 1);
insert into bookings (id_book, booking_end_time, booking_start_time, comment, is_cancelled, id_child, id_room, id_user) values (5, '2016-05-05 16:58', '2016-05-05 14:57', "The whole world of comments", false, 1, 1, 1);
insert into bookings (id_book, booking_end_time, booking_start_time, comment, is_cancelled, id_child, id_room, id_user) values (6, '2016-05-06 16:57', '2016-05-06 14:57', "The whole world of comments", false, 2, 1, 1);
insert into bookings (id_book, booking_end_time, booking_start_time, comment, is_cancelled, id_child, id_room, id_user) values (7, '2016-05-07 15:20', '2016-05-07 15:05', "The whole world of comments", false, 1, 2, 1);
insert into bookings (id_book, booking_end_time, booking_start_time, comment, is_cancelled, id_child, id_room, id_user) values (8, '2016-05-08 19:36', '2016-05-08 17:25', "The whole world of comments", false, 2, 2, 1);
insert into bookings (id_book, booking_end_time, booking_start_time, comment, is_cancelled, id_child, id_room, id_user) values (9, '2016-05-09 19:47', '2016-05-09 12:30', "The whole world of comments", false, 1, 2, 1);
-- user@softserveinc.com bookings for month end
-- user@softserveinc.com bookings for other period start
insert into bookings (id_book, booking_end_time, booking_start_time, comment, is_cancelled, id_child, id_room, id_user) values (11, '2016-04-01 18:15', '2016-04-01 15:24', "The whole world of comments", false, 1, 1, 1);
insert into bookings (id_book, booking_end_time, booking_start_time, comment, is_cancelled, id_child, id_room, id_user) values (12, '2016-04-02 14:21', '2016-04-02 10:38', "The whole world of comments", false, 2, 1, 1);
insert into bookings (id_book, booking_end_time, booking_start_time, comment, is_cancelled, id_child, id_room, id_user) values (13, '2016-04-03 15:48', '2016-04-03 15:32', "The whole world of comments", false, 1, 2, 1);
insert into bookings (id_book, booking_end_time, booking_start_time, comment, is_cancelled, id_child, id_room, id_user) values (14, '2016-04-04 16:34', '2016-04-04 13:15', "The whole world of comments", false, 2, 1, 1);
-- user@softserveinc.com bookings for other period end
-- another parents start
insert into bookings (id_book, booking_end_time, booking_start_time, comment, is_cancelled, id_child, id_room, id_user) values (21, '2016-05-01 16:27', '2016-05-01 13:15', "The whole world of comments", false, 4, 1, 4);
insert into bookings (id_book, booking_end_time, booking_start_time, comment, is_cancelled, id_child, id_room, id_user) values (22, '2016-05-02 18:26', '2016-05-02 14:27', "The whole world of comments", false, 4, 2, 4);
insert into bookings (id_book, booking_end_time, booking_start_time, comment, is_cancelled, id_child, id_room, id_user) values (23, '2016-05-03 17:18', '2016-05-03 16:27', "The whole world of comments", false, 4, 1, 4);
insert into bookings (id_book, booking_end_time, booking_start_time, comment, is_cancelled, id_child, id_room, id_user) values (24, '2016-04-01 16:34', '2016-04-01 13:15', "The whole world of comments", false, 5, 1, 5);
-- another parents end
-- DEMIAN DEMO END

-- VASYL DEMO START
insert into bookings (id_book, booking_end_time, booking_start_time, comment, is_cancelled, id_child, id_room, id_user) values (31, '2016-06-20 16:39', '2016-06-20 15:00', "The whole world of comments", false, 1, 1, 1);
insert into bookings (id_book, booking_end_time, booking_start_time, comment, is_cancelled, id_child, id_room, id_user) values (32, '2016-06-20 16:55', '2016-06-20 15:30', "The whole world of comments", false, 2, 1, 1);
insert into bookings (id_book, booking_end_time, booking_start_time, comment, is_cancelled, id_child, id_room, id_user) values (33, '2016-06-20 21:00', '2016-06-20 19:30', "The whole world of comments", false, 4, 1, 4);
insert into bookings (id_book, booking_end_time, booking_start_time, comment, is_cancelled, id_child, id_room, id_user) values (34, '2016-06-20 18:50', '2016-06-20 17:30', "The whole world of comments", false, 5, 1, 5);
insert into bookings (id_book, booking_end_time, booking_start_time, comment, is_cancelled, id_child, id_room, id_user) values (35, '2016-06-20 21:50', '2016-06-20 18:30', "The whole world of comments", false, 6, 1, 5);
-- VASYL DEMO END