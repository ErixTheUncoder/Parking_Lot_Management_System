# Database Schema Documentation

## Overview
SQLite database with 9 core tables supporting full parking management operations.

## Entity Relationship

```
floors (1) ──── (M) spots
                     │
                     │ spot_id
                     ↓
vehicles (1) ──── (M) tickets
    │ license_plate      │
    │                    │ ticket_id
    ↓                    ↓
fines (M)           invoices (M)
                         │
                         ↓
                    payments (M)

price_registry ← (lookup) → spot pricing
fine_schemes ← (lookup) → fine calculation
compatibility_registry ← (lookup) → vehicle-spot rules
```

## Table Definitions

### 1. `floors`
Stores floor information and premium pricing.

| Column | Type | Key | Description |
|--------|------|-----|-------------|
| floor_id | INTEGER | PK | Auto-increment ID |
| floor_number | INTEGER | UNIQUE | Floor number (1, 2, 3...) |
| extra_charge | REAL | | Premium charge for this floor |
| total_spots | INTEGER | | Total spots on floor |

**Sample Data:**
```sql
(1, 1, 0.0, 20)  -- Floor 1, no premium, 20 spots
(2, 2, 1.0, 20)  -- Floor 2, RM 1 premium
(3, 3, 2.0, 20)  -- Floor 3, RM 2 premium
```

---

### 2. `spots`
Individual parking spots with type and status.

| Column | Type | Key | Description |
|--------|------|-----|-------------|
| spot_id | INTEGER | PK | Auto-increment ID |
| spot_name | TEXT | UNIQUE | Human-readable name (F1-R1-S1) |
| spot_type | TEXT | | COMPACT, REGULAR, LARGE, etc. |
| floor_id | INTEGER | FK | References floors(floor_id) |
| is_occupied | INTEGER | | 0=free, 1=occupied |
| is_closed | INTEGER | | 0=open, 1=closed for maintenance |
| current_vehicle | TEXT | | License plate of occupying vehicle |
| row_num | INTEGER | | Row number for UI display |
| spot_num | INTEGER | | Spot number within row |

**Indexes:**
- `idx_spots_type` on spot_type
- `idx_spots_occupied` on is_occupied

**Sample Data:**
```sql
(1, 'F1-R1-S1', 'COMPACT', 1, 0, 0, NULL, 1, 1)
(2, 'F1-R1-S2', 'COMPACT', 1, 1, 0, 'abc123', 1, 2)
```

---

### 3. `vehicles`
Vehicle registration and entry/exit tracking.

| Column | Type | Key | Description |
|--------|------|-----|-------------|
| license_plate | TEXT | PK | Vehicle identifier (lowercase) |
| vehicle_type | TEXT | | MOTORCYCLE, CAR, SUV, etc. |
| entry_time | TEXT | | ISO timestamp of entry |
| exit_time | TEXT | | ISO timestamp of exit |

**Sample Data:**
```sql
('abc123', 'CAR', '2026-02-16T10:30:00', NULL)
('xyz789', 'TRUCK', '2026-02-15T14:20:00', '2026-02-16T08:15:00')
```

---

### 4. `tickets`
Parking tickets linking vehicles to spots.

| Column | Type | Key | Description |
|--------|------|-----|-------------|
| ticket_id | TEXT | PK | Format: T-{plate}-{timestamp} |
| license_plate | TEXT | FK | References vehicles(license_plate) |
| spot_id | INTEGER | FK | References spots(spot_id) |
| entry_timestamp | TEXT | | ISO timestamp of parking start |
| is_paid | INTEGER | | 0=unpaid, 1=paid |
| is_active | INTEGER | | 0=closed, 1=active |

**Indexes:**
- `idx_tickets_license` on license_plate
- `idx_tickets_active` on is_active

**Sample Data:**
```sql
('T-abc123-1708082400000', 'abc123', 15, '2026-02-16T10:30:00', 0, 1)
```

---

### 5. `invoices`
Payment invoices with detailed breakdown.

| Column | Type | Key | Description |
|--------|------|-----|-------------|
| invoice_id | TEXT | PK | Format: INV-{timestamp} |
| ticket_id | TEXT | FK | References tickets(ticket_id) |
| license_plate | TEXT | FK | References vehicles(license_plate) |
| base_price | REAL | | Duration × base rate |
| floor_premium | REAL | | Extra charge from floor |
| discount_amount | REAL | | Discount applied to subtotal |
| fines | REAL | | Overstay + historical fines |
| total_amount | REAL | | Final amount: (base+premium-discount)+fines |
| invoice_timestamp | TEXT | | ISO timestamp of invoice |

**Formula:**
```
total_amount = (base_price + floor_premium - discount_amount) + fines
```

**Sample Data:**
```sql
('INV-1708086000000', 'T-abc123-1708082400000', 'abc123', 
 25.0, 1.0, 0.0, 50.0, 76.0, '2026-02-16T11:30:00')
```

---

### 6. `fines`
Fine tracking per vehicle (historical and current).

| Column | Type | Key | Description |
|--------|------|-----|-------------|
| fine_id | INTEGER | PK | Auto-increment ID |
| license_plate | TEXT | FK | References vehicles(license_plate) |
| fine_amount | REAL | | Fine amount in RM |
| reason | TEXT | | Description (e.g., "Overstay: 30h") |
| is_paid | INTEGER | | 0=unpaid, 1=paid |
| created_at | TEXT | | ISO timestamp created |
| paid_at | TEXT | | ISO timestamp paid (NULL if unpaid) |

**Indexes:**
- `idx_fines_license` on license_plate
- `idx_fines_unpaid` on is_paid

**Sample Data:**
```sql
(1, 'abc123', 50.0, 'Overstay fine: 30 hours', 0, '2026-02-16T11:30:00', NULL)
```

---

### 7. `payments`
Payment transaction records.

| Column | Type | Key | Description |
|--------|------|-----|-------------|
| payment_id | INTEGER | PK | Auto-increment ID |
| invoice_id | TEXT | FK | References invoices(invoice_id) |
| payment_method | TEXT | | CASH or CARD |
| amount_paid | REAL | | Amount paid |
| payment_timestamp | TEXT | | ISO timestamp |
| card_last_four | TEXT | | Last 4 digits of card (if CARD) |

**Sample Data:**
```sql
(1, 'INV-1708086000000', 'CASH', 76.0, '2026-02-16T11:30:15', NULL)
(2, 'INV-1708086100000', 'CARD', 150.0, '2026-02-16T12:00:00', '1234')
```

---

### 8. `price_registry`
Base hourly rates per spot type.

| Column | Type | Key | Description |
|--------|------|-----|-------------|
| spot_type | TEXT | PK | COMPACT, REGULAR, etc. |
| base_rate | REAL | | Hourly rate in RM |

**Sample Data:**
```sql
('COMPACT', 2.0)
('REGULAR', 5.0)
('HANDICAPPED', 2.0)
('RESERVED', 10.0)
('LARGE', 7.0)
```

---

### 9. `fine_schemes`
Available fine calculation methods.

| Column | Type | Key | Description |
|--------|------|-----|-------------|
| scheme_id | INTEGER | PK | Auto-increment ID |
| scheme_name | TEXT | UNIQUE | Display name |
| scheme_type | TEXT | | FIXED, PROGRESSIVE, HOURLY |
| is_active | INTEGER | | 0=inactive, 1=active (only one active) |

**Sample Data:**
```sql
(1, 'Fixed Fine', 'FIXED', 1)
(2, 'Progressive Fine', 'PROGRESSIVE', 0)
(3, 'Hourly Fine', 'HOURLY', 0)
```

---

### 10. `compatibility_registry`
Vehicle-to-spot type compatibility rules.

| Column | Type | Key | Description |
|--------|------|-----|-------------|
| vehicle_type | TEXT | PK (composite) | MOTORCYCLE, CAR, etc. |
| spot_type | TEXT | PK (composite) | COMPACT, REGULAR, etc. |

**Sample Data:**
```sql
('MOTORCYCLE', 'COMPACT')
('CAR', 'COMPACT')
('CAR', 'REGULAR')
('SUV', 'REGULAR')
('TRUCK', 'REGULAR')
('VIP', 'RESERVED')
('HANDICAPPED', 'HANDICAPPED')
('HANDICAPPED', 'REGULAR')
```

---

## Key Queries

### 1. Find Available Spots for Vehicle Type
```sql
SELECT s.* 
FROM spots s
JOIN compatibility_registry cr ON s.spot_type = cr.spot_type
WHERE cr.vehicle_type = 'CAR'
  AND s.is_occupied = 0
  AND s.is_closed = 0
ORDER BY s.spot_id
LIMIT 5;
```

### 2. Get Active Ticket for License Plate
```sql
SELECT * FROM tickets
WHERE license_plate = 'abc123'
  AND is_active = 1;
```

### 3. Calculate Total Unpaid Fines
```sql
SELECT SUM(fine_amount) as total_fines
FROM fines
WHERE license_plate = 'abc123'
  AND is_paid = 0;
```

### 4. Get Current Occupancy Rate
```sql
SELECT 
    (SELECT COUNT(*) FROM spots WHERE is_occupied = 1) as occupied,
    (SELECT COUNT(*) FROM spots WHERE is_closed = 0) as total,
    ROUND(100.0 * occupied / total, 2) as occupancy_rate;
```

### 5. Get Today's Revenue
```sql
SELECT SUM(total_amount) as revenue
FROM invoices
WHERE DATE(invoice_timestamp) = DATE('now');
```

### 6. List Currently Parked Vehicles
```sql
SELECT 
    v.license_plate,
    v.vehicle_type,
    s.spot_name,
    t.entry_timestamp
FROM vehicles v
JOIN tickets t ON v.license_plate = t.license_plate
JOIN spots s ON t.spot_id = s.spot_id
WHERE t.is_active = 1
ORDER BY t.entry_timestamp DESC;
```

---

## Transaction Examples

### Entry Transaction (Atomic)
```sql
BEGIN TRANSACTION;

INSERT INTO tickets (ticket_id, license_plate, spot_id, entry_timestamp, is_paid, is_active)
VALUES ('T-abc123-1708082400000', 'abc123', 15, '2026-02-16T10:30:00', 0, 1);

UPDATE spots
SET is_occupied = 1, current_vehicle = 'abc123'
WHERE spot_id = 15;

COMMIT;
```

### Exit Transaction (Atomic)
```sql
BEGIN TRANSACTION;

-- Close ticket
UPDATE tickets
SET is_active = 0, is_paid = 1
WHERE ticket_id = 'T-abc123-1708082400000';

-- Free spot
UPDATE spots
SET is_occupied = 0, current_vehicle = NULL
WHERE spot_id = 15;

-- Save invoice
INSERT INTO invoices (invoice_id, ticket_id, license_plate, ...)
VALUES (...);

-- Mark fines as paid
UPDATE fines
SET is_paid = 1, paid_at = '2026-02-16T11:30:00'
WHERE license_plate = 'abc123' AND is_paid = 0;

COMMIT;
```

---

## Data Integrity Rules

1. **Atomic Entry/Exit**: All spot/ticket updates in single transaction
2. **Foreign Key Constraints**: Enforced at database level
3. **Unique Constraints**: 
   - One active ticket per vehicle at a time
   - Spot names must be unique
   - License plates normalized (lowercase, no spaces)
4. **Cascading**: Not used (explicit cleanup required)

---

## Performance Optimizations

1. **Indexes** on frequently queried columns:
   - tickets.license_plate
   - tickets.is_active
   - spots.is_occupied
   - spots.spot_type
   - fines.license_plate

2. **Denormalization**: 
   - license_plate stored in both vehicles and tickets
   - Reduces joins for common queries

3. **Prepared Statements**: All DAO methods use PreparedStatement


