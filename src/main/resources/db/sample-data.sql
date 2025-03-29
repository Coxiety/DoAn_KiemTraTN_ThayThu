-- Sample test data for Multiple Choice Exam System
USE THI_TRAC_NGHIEM; -- Update to match your actual database name

-- ========================================
-- Insert classes (must be inserted first)
-- ========================================
INSERT INTO LOP (MALOP, TENLOP) VALUES ('D18CN01', 'Computer Science 2018 Group 1');
INSERT INTO LOP (MALOP, TENLOP) VALUES ('D18CN02', 'Computer Science 2018 Group 2');
INSERT INTO LOP (MALOP, TENLOP) VALUES ('D19AT01', 'Information Security 2019');
INSERT INTO LOP (MALOP, TENLOP) VALUES ('D19VT01', 'Telecommunications 2019');
PRINT 'Added class data';

-- ========================================
-- Insert subjects
-- ========================================
INSERT INTO MONHOC (MAMH, TENMH) VALUES ('CSDL', 'Database Systems');
INSERT INTO MONHOC (MAMH, TENMH) VALUES ('CTDL', 'Data Structures and Algorithms');
INSERT INTO MONHOC (MAMH, TENMH) VALUES ('MMT', 'Computer Networks');
INSERT INTO MONHOC (MAMH, TENMH) VALUES ('LTW', 'Web Development');
PRINT 'Added subject data';

-- ========================================
-- Insert teachers
-- ========================================
INSERT INTO GIAOVIEN (MAGV, HO, TEN, SODTLL, DIACHI) 
VALUES ('GV01', 'Nguyen', 'Van A', '0912345678', '123 Teacher St.');
INSERT INTO GIAOVIEN (MAGV, HO, TEN, SODTLL, DIACHI) 
VALUES ('GV02', 'Tran', 'Thi B', '0923456789', '456 Faculty Ave.');
INSERT INTO GIAOVIEN (MAGV, HO, TEN, SODTLL, DIACHI) 
VALUES ('GV03', 'Le', 'Van C', '0934567890', '789 Professor Rd.');
INSERT INTO GIAOVIEN (MAGV, HO, TEN, SODTLL, DIACHI) 
VALUES ('GV04', 'Pham', 'Minh D', '0945678901', '101 Lecturer Ln.');
PRINT 'Added teacher data';

-- ========================================
-- Insert students
-- ========================================
INSERT INTO SINHVIEN (MASV, HO, TEN, NGAYSINH, DIACHI, MALOP) 
VALUES ('SV001', 'Hoang', 'Tuan', '2000-05-15', '123 Student Dorm', 'D18CN01');
INSERT INTO SINHVIEN (MASV, HO, TEN, NGAYSINH, DIACHI, MALOP) 
VALUES ('SV002', 'Nguyen', 'Minh', '2000-08-22', '456 College Ave.', 'D18CN01');
INSERT INTO SINHVIEN (MASV, HO, TEN, NGAYSINH, DIACHI, MALOP) 
VALUES ('SV003', 'Tran', 'Hieu', '2000-11-30', '789 University St.', 'D18CN01');
INSERT INTO SINHVIEN (MASV, HO, TEN, NGAYSINH, DIACHI, MALOP) 
VALUES ('SV004', 'Le', 'Linh', '2001-02-10', '101 Campus Rd.', 'D18CN02');
INSERT INTO SINHVIEN (MASV, HO, TEN, NGAYSINH, DIACHI, MALOP) 
VALUES ('SV005', 'Vu', 'Huong', '2001-06-18', '202 Education Ln.', 'D18CN02');
INSERT INTO SINHVIEN (MASV, HO, TEN, NGAYSINH, DIACHI, MALOP) 
VALUES ('SV006', 'Do', 'Thanh', '2001-09-05', '303 Scholar Ave.', 'D19AT01');
INSERT INTO SINHVIEN (MASV, HO, TEN, NGAYSINH, DIACHI, MALOP) 
VALUES ('SV007', 'Phan', 'Trang', '2001-12-25', '404 Learning St.', 'D19AT01');
INSERT INTO SINHVIEN (MASV, HO, TEN, NGAYSINH, DIACHI, MALOP) 
VALUES ('SV008', 'Ngo', 'Duy', '2001-03-14', '505 Knowledge Rd.', 'D19VT01');
PRINT 'Added student data';

-- ========================================
-- Insert teacher registrations
-- ========================================
INSERT INTO GIAOVIEN_DANGKY (MAGV, MAMH, MALOP, TRINHDO, NGAYTHI, LAN, SOCAUTHI, THOIGIAN)
VALUES ('GV01', 'CSDL', 'D18CN01', 'A', '2025-05-10', 1, 30, 60);
INSERT INTO GIAOVIEN_DANGKY (MAGV, MAMH, MALOP, TRINHDO, NGAYTHI, LAN, SOCAUTHI, THOIGIAN)
VALUES ('GV01', 'CTDL', 'D18CN01', 'B', '2025-05-15', 1, 25, 45);
INSERT INTO GIAOVIEN_DANGKY (MAGV, MAMH, MALOP, TRINHDO, NGAYTHI, LAN, SOCAUTHI, THOIGIAN)
VALUES ('GV02', 'MMT', 'D18CN02', 'B', '2025-05-20', 1, 30, 60);
INSERT INTO GIAOVIEN_DANGKY (MAGV, MAMH, MALOP, TRINHDO, NGAYTHI, LAN, SOCAUTHI, THOIGIAN)
VALUES ('GV03', 'LTW', 'D19AT01', 'C', '2025-05-25', 1, 20, 30);
-- Add second attempt for CSDL
INSERT INTO GIAOVIEN_DANGKY (MAGV, MAMH, MALOP, TRINHDO, NGAYTHI, LAN, SOCAUTHI, THOIGIAN)
VALUES ('GV01', 'CSDL', 'D18CN01', 'A', '2025-06-10', 2, 30, 60);
PRINT 'Added teacher registration data';

-- ========================================
-- Insert questions for Database Systems (CSDL)
-- ========================================
-- Level A questions
INSERT INTO BODE (MAMH, TRINHDO, NOIDUNG, A, B, C, D, DAP_AN, MAGV)
VALUES ('CSDL', 'A', 'What is a primary key?', 'A unique identifier for a record', 'A foreign key reference', 'A composite key', 'A candidate key', 'A', 'GV01');
INSERT INTO BODE (MAMH, TRINHDO, NOIDUNG, A, B, C, D, DAP_AN, MAGV)
VALUES ('CSDL', 'A', 'Which normal form eliminates transitive dependencies?', '1NF', '2NF', '3NF', 'BCNF', 'C', 'GV01');
INSERT INTO BODE (MAMH, TRINHDO, NOIDUNG, A, B, C, D, DAP_AN, MAGV)
VALUES ('CSDL', 'A', 'What is a transaction in a database?', 'A single SQL statement', 'A unit of work that is performed against a database', 'A table join operation', 'A stored procedure', 'B', 'GV01');
INSERT INTO BODE (MAMH, TRINHDO, NOIDUNG, A, B, C, D, DAP_AN, MAGV)
VALUES ('CSDL', 'A', 'Which SQL statement is used to extract data from a database?', 'SELECT', 'UPDATE', 'DELETE', 'INSERT', 'A', 'GV01');

-- Level B questions
INSERT INTO BODE (MAMH, TRINHDO, NOIDUNG, A, B, C, D, DAP_AN, MAGV)
VALUES ('CSDL', 'B', 'What does ACID stand for in database transactions?', 'Atomicity, Consistency, Isolation, Durability', 'Availability, Consistency, Integration, Durability', 'Atomicity, Correctness, Isolation, Durability', 'Authentication, Consistency, Integrity, Detection', 'A', 'GV01');
INSERT INTO BODE (MAMH, TRINHDO, NOIDUNG, A, B, C, D, DAP_AN, MAGV)
VALUES ('CSDL', 'B', 'Which of the following is not a type of database join?', 'Inner join', 'Outer join', 'Complex join', 'Cross join', 'C', 'GV01');
INSERT INTO BODE (MAMH, TRINHDO, NOIDUNG, A, B, C, D, DAP_AN, MAGV)
VALUES ('CSDL', 'B', 'What is the difference between TRUNCATE and DELETE commands?', 'No difference', 'TRUNCATE is faster but cannot be rolled back', 'DELETE is faster but cannot be rolled back', 'TRUNCATE can use WHERE clause', 'B', 'GV01');

-- Level C questions
INSERT INTO BODE (MAMH, TRINHDO, NOIDUNG, A, B, C, D, DAP_AN, MAGV)
VALUES ('CSDL', 'C', 'What is a database index?', 'A constraint that enforces data integrity', 'A data structure that improves the speed of data retrieval operations', 'A type of database join', 'A type of trigger', 'B', 'GV01');
INSERT INTO BODE (MAMH, TRINHDO, NOIDUNG, A, B, C, D, DAP_AN, MAGV)
VALUES ('CSDL', 'C', 'Which statement creates a view in SQL?', 'CREATE VIEW', 'MAKE VIEW', 'DEFINE VIEW', 'ADD VIEW', 'A', 'GV01');

-- ========================================
-- Insert questions for Data Structures (CTDL)
-- ========================================
-- Level A questions
INSERT INTO BODE (MAMH, TRINHDO, NOIDUNG, A, B, C, D, DAP_AN, MAGV)
VALUES ('CTDL', 'A', 'What is a stack?', 'FIFO data structure', 'LIFO data structure', 'Random access data structure', 'Tree-based data structure', 'B', 'GV01');
INSERT INTO BODE (MAMH, TRINHDO, NOIDUNG, A, B, C, D, DAP_AN, MAGV)
VALUES ('CTDL', 'A', 'What is the time complexity of binary search?', 'O(1)', 'O(n)', 'O(log n)', 'O(n log n)', 'C', 'GV01');

-- Level B questions
INSERT INTO BODE (MAMH, TRINHDO, NOIDUNG, A, B, C, D, DAP_AN, MAGV)
VALUES ('CTDL', 'B', 'Which sorting algorithm has the best average-case performance?', 'Bubble sort', 'Insertion sort', 'Quick sort', 'Selection sort', 'C', 'GV01');
INSERT INTO BODE (MAMH, TRINHDO, NOIDUNG, A, B, C, D, DAP_AN, MAGV)
VALUES ('CTDL', 'B', 'What is the worst-case time complexity of hash table lookup?', 'O(1)', 'O(log n)', 'O(n)', 'O(n²)', 'C', 'GV01');

-- Level C questions
INSERT INTO BODE (MAMH, TRINHDO, NOIDUNG, A, B, C, D, DAP_AN, MAGV)
VALUES ('CTDL', 'C', 'Which of the following is not a balanced binary search tree?', 'AVL tree', 'Red-Black tree', 'B-tree', 'Binary tree', 'D', 'GV01');

-- ========================================
-- Insert questions for Computer Networks (MMT)
-- ========================================
-- Created by GV02
INSERT INTO BODE (MAMH, TRINHDO, NOIDUNG, A, B, C, D, DAP_AN, MAGV)
VALUES ('MMT', 'A', 'Which protocol is used for email transmission?', 'HTTP', 'FTP', 'SMTP', 'DHCP', 'C', 'GV02');
INSERT INTO BODE (MAMH, TRINHDO, NOIDUNG, A, B, C, D, DAP_AN, MAGV)
VALUES ('MMT', 'B', 'What is the purpose of a subnet mask?', 'To hide the IP address', 'To determine which portion of an IP address identifies the network', 'To encrypt data packets', 'To connect to the internet', 'B', 'GV02');
INSERT INTO BODE (MAMH, TRINHDO, NOIDUNG, A, B, C, D, DAP_AN, MAGV)
VALUES ('MMT', 'C', 'Which layer of the OSI model is responsible for end-to-end communication?', 'Network layer', 'Transport layer', 'Session layer', 'Application layer', 'B', 'GV02');

-- ========================================
-- Insert questions for Web Development (LTW)
-- ========================================
-- Created by GV03
INSERT INTO BODE (MAMH, TRINHDO, NOIDUNG, A, B, C, D, DAP_AN, MAGV)
VALUES ('LTW', 'A', 'What does HTML stand for?', 'Hyper Text Markup Language', 'High Tech Multi Language', 'Hyper Transfer Markup Language', 'Home Tool Markup Language', 'A', 'GV03');
INSERT INTO BODE (MAMH, TRINHDO, NOIDUNG, A, B, C, D, DAP_AN, MAGV)
VALUES ('LTW', 'B', 'Which technology is primarily used for styling web pages?', 'HTML', 'JavaScript', 'CSS', 'PHP', 'C', 'GV03');
INSERT INTO BODE (MAMH, TRINHDO, NOIDUNG, A, B, C, D, DAP_AN, MAGV)
VALUES ('LTW', 'C', 'What is the purpose of AJAX in web development?', 'To create animations', 'To asynchronously exchange data with a server', 'To validate form input', 'To create database connections', 'B', 'GV03');
PRINT 'Added question data';

-- ========================================
-- Insert sample exam results
-- ========================================
INSERT INTO BANGDIEM (MASV, MAMH, LAN, NGAYTHI, DIEM)
VALUES ('SV001', 'CSDL', 1, '2025-05-10', 8.5);
INSERT INTO BANGDIEM (MASV, MAMH, LAN, NGAYTHI, DIEM)
VALUES ('SV002', 'CSDL', 1, '2025-05-10', 7.0);
INSERT INTO BANGDIEM (MASV, MAMH, LAN, NGAYTHI, DIEM)
VALUES ('SV003', 'CSDL', 1, '2025-05-10', 9.0);
INSERT INTO BANGDIEM (MASV, MAMH, LAN, NGAYTHI, DIEM)
VALUES ('SV001', 'CTDL', 1, '2025-05-15', 7.5);
INSERT INTO BANGDIEM (MASV, MAMH, LAN, NGAYTHI, DIEM)
VALUES ('SV002', 'CTDL', 1, '2025-05-15', 8.0);
PRINT 'Added exam result data';

-- ========================================
-- Insert user accounts
-- ========================================
-- PGV user (Coordinator)
INSERT INTO USERS (USERNAME, PASSWORD, ROLE, MAGV) 
VALUES ('admin', 'password', 'PGV', 'GV01');

-- Teacher users
INSERT INTO USERS (USERNAME, PASSWORD, ROLE, MAGV) 
VALUES ('teacher1', 'password', 'GIANGVIEN', 'GV01');
INSERT INTO USERS (USERNAME, PASSWORD, ROLE, MAGV) 
VALUES ('teacher2', 'password', 'GIANGVIEN', 'GV02');
INSERT INTO USERS (USERNAME, PASSWORD, ROLE, MAGV) 
VALUES ('teacher3', 'password', 'GIANGVIEN', 'GV03');

-- Student user (shared account)
INSERT INTO USERS (USERNAME, PASSWORD, ROLE) 
VALUES ('student', 'password', 'SINHVIEN');
PRINT 'Added user account data';