CREATE TABLE SALE (
  id INT PRIMARY KEY AUTO_INCREMENT,
  context_id INT NOT NULL,
  name VARCHAR(10) NOT NULL,
  description VARCHAR(100) NOT NULL,
  auction_end DATE NOT NULL,
  auction_start DATE NOT NULL,
  status INT NOT NULL,
  access_control INT NOT NULL,
  locked INT NOT NULL,
  created DATE NOT NULL,
  catalog_type INT NOT NULL,
  extended TEXT NOT NULL,
  status_text VARCHAR(10) NOT NULL,
  reference VARCHAR(10) NOT NULL,
  last_modified DATE NOT NULL,
  ims INT NOT NULL,
  default_language VARCHAR(5) NOT NULL,
  document_language VARCHAR(5) NOT NULL
);
