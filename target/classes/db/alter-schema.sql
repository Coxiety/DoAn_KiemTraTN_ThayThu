-- Alter script for database schema - increases column sizes to accommodate longer text
USE THI_TRAC_NGHIEM;

-- Alter BODE table columns A, B, C, D to accommodate longer text
ALTER TABLE BODE
ALTER COLUMN A NVARCHAR(200);

ALTER TABLE BODE
ALTER COLUMN B NVARCHAR(200);

ALTER TABLE BODE
ALTER COLUMN C NVARCHAR(200);

ALTER TABLE BODE
ALTER COLUMN D NVARCHAR(200);

PRINT 'BODE table columns A, B, C, D have been increased to NVARCHAR(200).';

-- Now you can run the original sample-data.sql script again
-- or you can run the following inserts that failed previously:

-- Re-insert "What is a transaction in a database?" question (Option B was truncated)
INSERT INTO BODE (MAMH, TRINHDO, NOIDUNG, A, B, C, D, DAP_AN, MAGV)
VALUES ('CSDL', 'A', 'What is a transaction in a database?', 'A single SQL statement', 'A unit of work that is performed against a database', 'A table join operation', 'A stored procedure', 'B', 'GV01');

-- Re-insert "What is a database index?" question (Option B was truncated)
INSERT INTO BODE (MAMH, TRINHDO, NOIDUNG, A, B, C, D, DAP_AN, MAGV)
VALUES ('CSDL', 'C', 'What is a database index?', 'A constraint that enforces data integrity', 'A data structure that improves the speed of data retrieval operations', 'A type of database join', 'A type of trigger', 'B', 'GV01');

-- Re-insert "What is the purpose of a subnet mask?" question (Option B was truncated)
INSERT INTO BODE (MAMH, TRINHDO, NOIDUNG, A, B, C, D, DAP_AN, MAGV)
VALUES ('MMT', 'B', 'What is the purpose of a subnet mask?', 'To hide the IP address', 'To determine which portion of an IP address identifies the network', 'To encrypt data packets', 'To connect to the internet', 'B', 'GV02');

PRINT 'Re-inserted questions with full-length text.';