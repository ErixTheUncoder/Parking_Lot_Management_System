-- Parking Management System Database Schema

-- Drop existing tables if they exist
DROP TABLE IF EXISTS payments;
DROP TABLE IF EXISTS invoices;
DROP TABLE IF EXISTS fines;
DROP TABLE IF EXISTS tickets;
DROP TABLE IF EXISTS vehicles;
DROP TABLE IF EXISTS spots;
DROP TABLE IF EXISTS floors;
DROP TABLE IF EXISTS price_registry;
DROP TABLE IF EXISTS fine_schemes;
DROP TABLE IF EXISTS compatibility_registry;

-- Floors Table
CREATE TABLE floors (
    floor_id INTEGER PRIMARY KEY AUTOINCREMENT,
    floor_number INTEGER NOT NULL UNIQUE,
    extra_charge REAL DEFAULT 0.0,
    total_spots INTEGER DEFAULT 0
);

-- Spots Table
CREATE TABLE spots (
    spot_id INTEGER PRIMARY KEY AUTOINCREMENT,
    spot_name TEXT NOT NULL UNIQUE,
    spot_type TEXT NOT NULL,
    floor_id INTEGER NOT NULL,
    is_occupied INTEGER DEFAULT 0,
    is_closed INTEGER DEFAULT 0,
    current_vehicle TEXT,
    row_num INTEGER NOT NULL,
    spot_num INTEGER NOT NULL,
    FOREIGN KEY (floor_id) REFERENCES floors(floor_id)
);

-- Vehicles Table
CREATE TABLE vehicles (
    license_plate TEXT PRIMARY KEY,
    vehicle_type TEXT NOT NULL,
    entry_time TEXT,
    exit_time TEXT
);

-- Tickets Table
CREATE TABLE tickets (
    ticket_id TEXT PRIMARY KEY,
    license_plate TEXT NOT NULL,
    spot_id INTEGER NOT NULL,
    entry_timestamp TEXT NOT NULL,
    is_paid INTEGER DEFAULT 0,
    is_active INTEGER DEFAULT 1,
    FOREIGN KEY (license_plate) REFERENCES vehicles(license_plate),
    FOREIGN KEY (spot_id) REFERENCES spots(spot_id)
);

-- Invoices Table
CREATE TABLE invoices (
    invoice_id TEXT PRIMARY KEY,
    ticket_id TEXT NOT NULL,
    license_plate TEXT NOT NULL,
    base_price REAL NOT NULL,
    floor_premium REAL DEFAULT 0.0,
    discount_amount REAL DEFAULT 0.0,
    fines REAL DEFAULT 0.0,
    total_amount REAL NOT NULL,
    invoice_timestamp TEXT NOT NULL,
    FOREIGN KEY (ticket_id) REFERENCES tickets(ticket_id),
    FOREIGN KEY (license_plate) REFERENCES vehicles(license_plate)
);

-- Fines Table
CREATE TABLE fines (
    fine_id INTEGER PRIMARY KEY AUTOINCREMENT,
    license_plate TEXT NOT NULL,
    fine_amount REAL NOT NULL,
    reason TEXT,
    is_paid INTEGER DEFAULT 0,
    created_at TEXT NOT NULL,
    paid_at TEXT,
    FOREIGN KEY (license_plate) REFERENCES vehicles(license_plate)
);

-- Payments Table
CREATE TABLE payments (
    payment_id INTEGER PRIMARY KEY AUTOINCREMENT,
    invoice_id TEXT NOT NULL,
    payment_method TEXT NOT NULL,
    amount_paid REAL NOT NULL,
    payment_timestamp TEXT NOT NULL,
    card_last_four TEXT,
    FOREIGN KEY (invoice_id) REFERENCES invoices(invoice_id)
);

-- Price Registry Table
CREATE TABLE price_registry (
    spot_type TEXT PRIMARY KEY,
    base_rate REAL NOT NULL
);

-- Fine Schemes Table
CREATE TABLE fine_schemes (
    scheme_id INTEGER PRIMARY KEY AUTOINCREMENT,
    scheme_name TEXT NOT NULL UNIQUE,
    scheme_type TEXT NOT NULL,
    is_active INTEGER DEFAULT 0
);

-- Compatibility Registry Table
CREATE TABLE compatibility_registry (
    vehicle_type TEXT NOT NULL,
    spot_type TEXT NOT NULL,
    PRIMARY KEY (vehicle_type, spot_type)
);

-- Create Indexes for Performance
CREATE INDEX idx_tickets_license ON tickets(license_plate);
CREATE INDEX idx_tickets_active ON tickets(is_active);
CREATE INDEX idx_spots_type ON spots(spot_type);
CREATE INDEX idx_spots_occupied ON spots(is_occupied);
CREATE INDEX idx_fines_license ON fines(license_plate);
CREATE INDEX idx_fines_unpaid ON fines(is_paid);

-- Insert Initial Price Registry Data
INSERT INTO price_registry (spot_type, base_rate) VALUES 
    ('COMPACT', 2.0),
    ('REGULAR', 5.0),
    ('HANDICAPPED', 2.0),
    ('RESERVED', 10.0),
    ('LARGE', 7.0);

-- Insert Fine Schemes
INSERT INTO fine_schemes (scheme_name, scheme_type, is_active) VALUES 
    ('Fixed Fine', 'FIXED', 1),
    ('Progressive Fine', 'PROGRESSIVE', 0),
    ('Hourly Fine', 'HOURLY', 0);

-- Insert Compatibility Rules
INSERT INTO compatibility_registry (vehicle_type, spot_type) VALUES 
    ('MOTORCYCLE', 'COMPACT'),
    ('CAR', 'COMPACT'),
    ('CAR', 'REGULAR'),
    ('SUV', 'REGULAR'),
    ('TRUCK', 'REGULAR'),
    ('TRUCK', 'LARGE'),
    ('SUV', 'LARGE'),
    ('VIP', 'RESERVED'),
    ('HANDICAPPED', 'HANDICAPPED'),
    ('HANDICAPPED', 'REGULAR');

-- Insert Sample Floors
INSERT INTO floors (floor_number, extra_charge, total_spots) VALUES 
    (1, 0.0, 20),
    (2, 1.0, 20),
    (3, 2.0, 20);

-- Insert Sample Spots for Floor 1
INSERT INTO spots (spot_name, spot_type, floor_id, row_num, spot_num) VALUES 
    ('F1-R1-S1', 'COMPACT', 1, 1, 1),
    ('F1-R1-S2', 'COMPACT', 1, 1, 2),
    ('F1-R1-S3', 'COMPACT', 1, 1, 3),
    ('F1-R1-S4', 'REGULAR', 1, 1, 4),
    ('F1-R1-S5', 'REGULAR', 1, 1, 5),
    ('F1-R2-S1', 'REGULAR', 1, 2, 1),
    ('F1-R2-S2', 'REGULAR', 1, 2, 2),
    ('F1-R2-S3', 'HANDICAPPED', 1, 2, 3),
    ('F1-R2-S4', 'RESERVED', 1, 2, 4),
    ('F1-R2-S5', 'LARGE', 1, 2, 5);

-- Insert Sample Spots for Floor 2
INSERT INTO spots (spot_name, spot_type, floor_id, row_num, spot_num) VALUES 
    ('F2-R1-S1', 'COMPACT', 2, 1, 1),
    ('F2-R1-S2', 'COMPACT', 2, 1, 2),
    ('F2-R1-S3', 'REGULAR', 2, 1, 3),
    ('F2-R1-S4', 'REGULAR', 2, 1, 4),
    ('F2-R1-S5', 'REGULAR', 2, 1, 5),
    ('F2-R2-S1', 'REGULAR', 2, 2, 1),
    ('F2-R2-S2', 'REGULAR', 2, 2, 2),
    ('F2-R2-S3', 'HANDICAPPED', 2, 2, 3),
    ('F2-R2-S4', 'RESERVED', 2, 2, 4),
    ('F2-R2-S5', 'LARGE', 2, 2, 5);

-- Insert Sample Spots for Floor 3
INSERT INTO spots (spot_name, spot_type, floor_id, row_num, spot_num) VALUES 
    ('F3-R1-S1', 'COMPACT', 3, 1, 1),
    ('F3-R1-S2', 'COMPACT', 3, 1, 2),
    ('F3-R1-S3', 'REGULAR', 3, 1, 3),
    ('F3-R1-S4', 'REGULAR', 3, 1, 4),
    ('F3-R1-S5', 'REGULAR', 3, 1, 5),
    ('F3-R2-S1', 'REGULAR', 3, 2, 1),
    ('F3-R2-S2', 'REGULAR', 3, 2, 2),
    ('F3-R2-S3', 'HANDICAPPED', 3, 2, 3),
    ('F3-R2-S4', 'RESERVED', 3, 2, 4),
    ('F3-R2-S5', 'LARGE', 3, 2, 5);
