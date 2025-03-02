CREATE TABLE IF NOT EXISTS allocations (
    id INT  PRIMARY KEY,
    department VARCHAR(50) NOT null,
    session VARCHAR(50),
    students VARCHAR(50),
    start_date VARCHAR(20),
    end_date VARCHAR(20),
    room VARCHAR(100)
);