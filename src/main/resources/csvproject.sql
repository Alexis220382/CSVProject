DROP TABLE IF EXISTS csv;

CREATE TABLE IF NOT EXISTS csv(
  id INT(11) NOT NULL AUTO_INCREMENT,
  name VARCHAR(55),
  surname VARCHAR(55),
  login VARCHAR(55),
  email VARCHAR(55),
  phoneNumber VARCHAR(55),
  PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;