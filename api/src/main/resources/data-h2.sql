INSERT INTO user(id, display_name, email, password) 
	VALUES (1, 'user_1', 'user1@test.com', 'test');
INSERT INTO user(id, display_name, email, password) 
	VALUES (2, 'user_2', 'user2@test.com', 'test');
INSERT INTO user(id, display_name, email, password) 
	VALUES (3, 'user_3', 'user3@test.com', 'test');
INSERT INTO tag(id, display_name, user_id) 
	VALUES (1, 'holidays', '1');
INSERT INTO tag(id, display_name, user_id) 
	VALUES (2, 'work', '1');
INSERT INTO tag(id, display_name, user_id) 
	VALUES (3, 'work', '2');