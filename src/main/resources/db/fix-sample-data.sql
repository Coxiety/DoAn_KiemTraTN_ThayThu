-- Fix script for sample data - shortens text values to fit column constraints
USE THI_TRAC_NGHIEM;

-- Fix for "A unit of work that is performed against a database" (Option B in question ID 3)
INSERT INTO BODE (MAMH, TRINHDO, NOIDUNG, A, B, C, D, DAP_AN, MAGV)
VALUES ('CSDL', 'A', 'What is a transaction in a database?', 'A single SQL statement', 'A unit of work in a database', 'A table join operation', 'A stored procedure', 'B', 'GV01');

-- Fix for "A data structure that improves the speed of data retrieval operations" (Option B in question ID 8)
INSERT INTO BODE (MAMH, TRINHDO, NOIDUNG, A, B, C, D, DAP_AN, MAGV)
VALUES ('CSDL', 'C', 'What is a database index?', 'A constraint that enforces data integrity', 'A structure that improves data retrieval', 'A type of database join', 'A type of trigger', 'B', 'GV01');

-- Fix for "To determine which portion of an IP address identifies the network" (Option B in question ID 17)
INSERT INTO BODE (MAMH, TRINHDO, NOIDUNG, A, B, C, D, DAP_AN, MAGV)
VALUES ('MMT', 'B', 'What is the purpose of a subnet mask?', 'To hide the IP address', 'To identify network portion of IP', 'To encrypt data packets', 'To connect to the internet', 'B', 'GV02');

PRINT 'Fixed entries with shortened text have been inserted.';