--
CREATE TABLE `core_user`
(
    `id`                      bigint       NOT NULL AUTO_INCREMENT,
    `created_by`              varchar(100) DEFAULT NULL,
    `created_date`            datetime(6)  DEFAULT NULL,
    `last_modified_by`        varchar(100) DEFAULT NULL,
    `last_modified_date`      datetime(6)  DEFAULT NULL,
    `account_non_expired`     bit(1)       DEFAULT NULL,
    `account_non_locked`      bit(1)       DEFAULT NULL,
    `active`                  bit(1)       DEFAULT NULL,
    `credentials_non_expired` bit(1)       DEFAULT NULL,
    `password`                varchar(255) NOT NULL,
    `username`                varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
);


CREATE TABLE `role`
(
    `id`   bigint       NOT NULL AUTO_INCREMENT,
    `name` varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
);


CREATE TABLE `user_role`
(
    `user_id` bigint NOT NULL,
    `role_id` bigint NOT NULL
);

