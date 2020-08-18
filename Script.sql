
CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    user_first_name VARCHAR(20) UNIQUE NOT NULL,
    user_last_name VARCHAR(20) UNIQUE NOT NULL,
    user_email VARCHAR(30) UNIQUE NOT NULL,
    user_password VARCHAR(30) NOT NULL,
    user_type VARCHAR (9) NOT NULL
);


CREATE TABLE accounts(
	account_number SERIAL PRIMARY KEY,
	status_of_account INTEGER,
	account_type VARCHAR (10),
	account_balance NUMERIC(10, 2) DEFAULT 0,
	user_id_fk INTEGER REFERENCES users(user_id)
);
 

