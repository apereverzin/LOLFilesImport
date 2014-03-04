CREATE TABLE SALE (
  id INT PRIMARY KEY,
  contextId INT,
  name VARCHAR(10),
  description VARCHAR(100),
  auctionend DATE,
  auctionstart DATE,
  status INT,
  accesscontrol INT,
  locked INT,
  created DATE,
  catalogtype INT,
  extended TEXT,
  statustext VARCHAR(10),
  reference VARCHAR(10),
  lastmodified DATE,
  ims INT,
  defaultlanguage VARCHAR(5),
  documentlanguage VARCHAR(5)
);
  