DROP Table IF EXISTS organized.users;

CREATE TABLE organized.users
(
	username VARCHAR(255),
    firstname VARCHAR(255),
    surname VARCHAR(255),
    securePassword LONGTEXT,
	PRIMARY KEY (username)
);