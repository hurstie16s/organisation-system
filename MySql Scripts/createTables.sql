DROP Table IF EXISTS organized.tasks;
DROP Table IF EXISTS organized.users;

CREATE TABLE organized.users
(
	username VARCHAR(255),
    firstname VARCHAR(255),
    surname VARCHAR(255),
    securePassword LONGTEXT,
    workingHours BIT(5),
    workingDays BIT(3),
	PRIMARY KEY (username)
);
CREATE TABLE organized.tasks
(
	taskOwner VARCHAR(255),
	taskName VARCHAR(255),
    taskDescription TEXT,
    startDate VARCHAR(16),
    dueDate VARCHAR(16),
    estimatedCompletitionTime SMALLINT,
    taskStatus VARCHAR(11),
    PRIMARY KEY (taskOwner, taskName),
    FOREIGN KEY (taskOwner) REFERENCES organized.users(username)
);