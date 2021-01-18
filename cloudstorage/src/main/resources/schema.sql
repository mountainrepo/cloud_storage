CREATE TABLE IF NOT EXISTS USERTABLE (
  id INT PRIMARY KEY auto_increment,
  firstname VARCHAR(20),
  lastname VARCHAR(20),
  salt VARCHAR,
  username VARCHAR(20),
  password VARCHAR
);

CREATE TABLE IF NOT EXISTS FILE (
    id INT PRIMARY KEY auto_increment,
    name VARCHAR,
    contenttype VARCHAR,
    filesize VARCHAR,
    userid INT,
    data BLOB,
    foreign key (userid) references USERTABLE(id)
);

CREATE TABLE IF NOT EXISTS NOTE (
    id INT PRIMARY KEY auto_increment,
    title VARCHAR(20),
    description VARCHAR (1000),
    userid INT,
    foreign key (userid) references USERTABLE(id)
);

CREATE TABLE IF NOT EXISTS CREDENTIAL (
    id INT PRIMARY KEY auto_increment,
    url VARCHAR(100),
    username VARCHAR (30),
    passwordKey VARCHAR,
    password VARCHAR,
    userid INT,
    foreign key (userid) references USERTABLE(id)
);