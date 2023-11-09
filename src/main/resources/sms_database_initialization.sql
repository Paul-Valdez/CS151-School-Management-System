-- PostgreSQL version

BEGIN;

-- Table structure for table 'addteacher'

CREATE TABLE addteacher (
  id INTEGER NOT NULL,
  name VARCHAR(255) NOT NULL,
  spec VARCHAR(255) NOT NULL,
  subject VARCHAR(255) NOT NULL
);


-- Table structure for table 'feesubmit'

CREATE TABLE feesubmit (
  id INTEGER NOT NULL,
  name VARCHAR(255) NOT NULL,
  monthname VARCHAR(255) NOT NULL,
  annual INTEGER NOT NULL,
  monthly INTEGER NOT NULL,
  sport INTEGER NOT NULL,
  library INTEGER NOT NULL,
  "Status" VARCHAR(255) NOT NULL
);


-- Table structure for table 'reportcard'

CREATE TABLE reportcard (
  id INTEGER NOT NULL,
  name VARCHAR(255) NOT NULL,
  class VARCHAR(255) NOT NULL,
  phy INTEGER NOT NULL,
  chem INTEGER NOT NULL,
  math INTEGER NOT NULL,
  rollnumber VARCHAR(255) NOT NULL,
  grade VARCHAR(255) NOT NULL
);


-- Table structure for table 'stureg'

CREATE TABLE stureg (
  id INTEGER NOT NULL,
  name VARCHAR(255) NOT NULL,
  fname VARCHAR(255) NOT NULL,
  phone INTEGER NOT NULL,
  fatherphone INTEGER NOT NULL,
  class VARCHAR(255) NOT NULL,
  roll VARCHAR(255) NOT NULL,
  address VARCHAR(255) NOT NULL
);


-- Table structure for table 'user_login'

CREATE TABLE user_login (
  id INTEGER NOT NULL,
  username VARCHAR(200) NOT NULL,
  password VARCHAR(200) NOT NULL
);


INSERT INTO user_login (id, username, password) VALUES (1, 'admin', 'admin');

COMMIT;