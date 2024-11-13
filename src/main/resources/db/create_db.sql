CREATE TABLE IF NOT EXISTS `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `account_non_expired` bit(1) DEFAULT NULL,
  `account_non_locked` bit(1) DEFAULT NULL,
  `credentials_non_expired` bit(1) DEFAULT NULL,
  `enabled` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_name` (`user_name`)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `user_permission` (
  `id_user` bigint(20) NOT NULL,
  `id_permission` bigint(20) NOT NULL,
  PRIMARY KEY (`id_user`,`id_permission`),
  KEY `fk_user_permission_permission` (`id_permission`),
  CONSTRAINT `fk_user_permission` FOREIGN KEY (`id_user`) REFERENCES `users` (`id`),
  CONSTRAINT `fk_user_permission_permission` FOREIGN KEY (`id_permission`) REFERENCES `permission` (`id`)
) ENGINE=InnoDB;

create table accounts (id bigint not null auto_increment, balance decimal(38,2), name varchar(255), type enum ('CHECKING','CREDIT','SAVINGS'), user_id bigint not null, primary key (id)) engine=InnoDB;
alter table accounts add constraint fk_accounts_user_id foreign key (user_id) references users (id)