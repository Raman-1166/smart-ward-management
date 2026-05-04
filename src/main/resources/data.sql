-- -- Ensure DB ENUM supports new Categories
-- ALTER TABLE services MODIFY COLUMN category ENUM('HOSPITAL', 'GYM', 'MEDICAL_STORE', 'BANK', 'ELECTRICITY', 'WARD_OFFICE', 'SCHOOL', 'GARBAGE', 'DRAINAGE', 'SANITATION', 'OTHER') NOT NULL;
-- 
-- -- Ensure clean table recreation if re-init
-- SET FOREIGN_KEY_CHECKS = 0;
-- TRUNCATE TABLE feedback;
-- TRUNCATE TABLE complaints;
-- TRUNCATE TABLE services;
-- TRUNCATE TABLE users;
-- TRUNCATE TABLE wards;
-- SET FOREIGN_KEY_CHECKS = 1;

-- Wards
INSERT INTO wards (id, name, area, population, officer_name, cleanliness_score) VALUES
(1, 'Vijay Nagar', 'North', 25000, 'Rajesh Sharma', 92.5),
(2, 'Rajwada', 'Central', 30000, 'Amit Patel', 85.0),
(3, 'Annapurna', 'South', 18000, 'Sneha Desai', 95.2);

-- Users
-- Password for all users is 'password' (hash: $2a$10$36X03z3.XND1FrJqZGA51eiEsrz4d4.zBm2NxbQOiWof6b9msx.vC)
INSERT INTO users (id, name, email, password, role, ward_id, created_at) VALUES
(1, 'System Admin', 'admin@smartward.com', '$2a$10$36X03z3.XND1FrJqZGA51eiEsrz4d4.zBm2NxbQOiWof6b9msx.vC', 'ADMIN', NULL, NOW()),
(2, 'Raman Chourasiya', 'raman@example.com', '$2a$10$36X03z3.XND1FrJqZGA51eiEsrz4d4.zBm2NxbQOiWof6b9msx.vC', 'CITIZEN', 1, NOW()),
(3, 'Indore Resident', 'resident@indore.in', '$2a$10$36X03z3.XND1FrJqZGA51eiEsrz4d4.zBm2NxbQOiWof6b9msx.vC', 'CITIZEN', 2, NOW());

-- Services (10 samples across 3 wards)
INSERT INTO services (id, ward_id, name, category, address, phone, opening_time, closing_time) VALUES
(1, 1, 'Apollo Hospital', 'HOSPITAL', 'Sector A, Vijay Nagar', '0731-240001', '00:00', '23:59'),
(2, 1, 'Gold Gym', 'GYM', 'Scheme No 54', '0731-250002', '06:00', '22:00'),
(3, 1, 'Vijay Nagar Branch', 'BANK', 'Satya Sai Square', '0731-270004', '10:00', '16:00'),
(4, 1, 'Sanchi Medical Store', 'MEDICAL_STORE', 'Opp. Apollo', '0731-260003', '09:00', '23:00'),
(5, 2, 'M.Y. Hospital', 'HOSPITAL', 'Residency Area', '0731-290006', '00:00', '23:59'),
(6, 2, 'Rajwada Ward Office', 'WARD_OFFICE', 'Rajwada Chowk', '0731-280005', '10:00', '18:00'),
(7, 2, 'HDFC Bank', 'BANK', 'MG Road', '0731-300007', '10:00', '17:00'),
(8, 2, 'Electricity Board Central', 'ELECTRICITY', 'MTH Compound', '1912', '00:00', '23:59'),
(9, 3, 'Annapurna Medicals', 'MEDICAL_STORE', 'Annapurna Road', '0731-112233', '08:00', '23:00'),
(10, 3, 'Fitness Pro', 'GYM', 'Sudama Nagar', '0731-445566', '05:00', '21:00');

-- Complaints (5 complaints)
INSERT INTO complaints (id, user_id, ward_id, category, description, status, photo_url, created_at, updated_at) VALUES
(1, 2, 1, 'GARBAGE', 'Garbage bin overflowing near Sector B park.', 'PENDING', NULL, DATE_SUB(NOW(), INTERVAL 2 DAY), DATE_SUB(NOW(), INTERVAL 2 DAY)),
(2, 3, 2, 'ELECTRICITY', 'Streetlight pole 42 is not working.', 'IN_PROGRESS', NULL, DATE_SUB(NOW(), INTERVAL 1 DAY), NOW()),
(3, 2, 1, 'DRAINAGE', 'Water logging after recent rain at junction.', 'RESOLVED', NULL, DATE_SUB(NOW(), INTERVAL 5 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY)),
(4, 3, 2, 'SANITATION', 'Public toilet at MG road needs cleaning.', 'PENDING', NULL, NOW(), NOW()),
(5, 2, 3, 'OTHER', 'Pothole needs fixing on main Annapurna road.', 'PENDING', NULL, NOW(), NOW());

-- Feedback (5 feedbacks)
INSERT INTO feedback (id, user_id, ward_id, rating, comment, created_at) VALUES
(1, 2, 1, 5, 'Cleanliness drive was very effective.', DATE_SUB(NOW(), INTERVAL 10 DAY)),
(2, 3, 2, 4, 'Good work but traffic management needed.', DATE_SUB(NOW(), INTERVAL 8 DAY)),
(3, 2, 1, 3, 'Average services, hospital wait time is high.', DATE_SUB(NOW(), INTERVAL 5 DAY)),
(4, 3, 2, 5, 'Electricity board responded quickly.', DATE_SUB(NOW(), INTERVAL 2 DAY)),
(5, 2, 3, 5, 'South zone is perfectly well maintained.', NOW());
