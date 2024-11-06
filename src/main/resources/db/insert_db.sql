INSERT INTO `users` (`user_name`, `first_name`, `last_name`, `password`, `account_non_expired`, `account_non_locked`, `credentials_non_expired`, `enabled`) VALUES
	('juan', 'Juan', 'Test', '1120962f58b29bcd74b19a8de89338db2e5cd7addb53f84c6c3b4fe9cc868608ef77e2b27dcb70e6', true, true, true, true),
	('gaby', 'Gaby', 'Test', '965f0a4aa397f9e2b51cf9c7fd206e3ae53035dc1ec1dfb9062b5ef4f2e434c8f6fe58b544f72621', true, true, true, true);

INSERT INTO `permission` (`description`) VALUES
	('ADMIN'),
	('MANAGER'),
	('COMMON_USER');

INSERT INTO `user_permission` (`id_user`, `id_permission`) VALUES
	(1, 1),
	(1, 2),
	(2, 1);