CREATE TABLE users(
	Id 	INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	Username 	VARCHAR(16) UNIQUE,
	Password	VARCHAR(64),
	Name		VARCHAR(128),
	Role		VARCHAR(16)
	);