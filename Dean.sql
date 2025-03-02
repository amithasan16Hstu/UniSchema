create database Dean;
use Dean;
CREATE TABLE Departments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);
INSERT INTO Departments (name) 
VALUES ('CSE'), ('EEE'), ('ECE');
CREATE TABLE Rooms (
    id INT AUTO_INCREMENT PRIMARY KEY,
    building VARCHAR(100) NOT NULL,
    floor VARCHAR(50) NOT NULL,
    room_number VARCHAR(20) NOT NULL,
    capacity INT NOT NULL,
    is_available BOOLEAN DEFAULT TRUE
);
INSERT INTO Rooms (building, floor, room_number, capacity, is_available) 
VALUES 
('Dr. Wazed Mia Building', 'First Floor', 'Room210', 60, TRUE),
('Dr. Wazed Mia Building', 'First Floor', 'Room235', 75, TRUE),
('Dr. Kudrate Khuda Building', 'Fifth Floor', 'Room601', 50, TRUE),
('Dr. Kudrate Khuda Building', 'Fifth Floor', 'Room602', 50, FALSE);
CREATE TABLE Allocations (
    id INT AUTO_INCREMENT PRIMARY KEY,
    department_id INT,
    session VARCHAR(50) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    room_id INT,
    FOREIGN KEY (department_id) REFERENCES Departments(id),
    FOREIGN KEY (room_id) REFERENCES Rooms(id)
);
CREATE VIEW AvailableRooms AS
SELECT building, floor, room_number, capacity 
FROM Rooms 
WHERE is_available = TRUE;
CREATE VIEW AllocationSummary AS
SELECT a.id, d.name AS department, a.session, a.start_date, a.end_date, 
       r.building, r.floor, r.room_number
FROM Allocations a
JOIN Departments d ON a.department_id = d.id
JOIN Rooms r ON a.room_id = r.id;



