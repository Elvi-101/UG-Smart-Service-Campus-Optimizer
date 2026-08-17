-- =========================================================
-- UG SMART CAMPUS SERVICE OPERATIONS OPTIMIZER
-- DATABASE SCHEMA
-- =========================================================

PRAGMA foreign_keys = ON;

-- =========================================================
-- LOCATIONS
-- =========================================================

CREATE TABLE IF NOT EXISTS locations (
    location_id INTEGER PRIMARY KEY,
    name TEXT NOT NULL,
    area TEXT NOT NULL,
    type TEXT NOT NULL,
    latitude REAL NOT NULL,
    longitude REAL NOT NULL
);


-- =========================================================
-- ROADS
-- =========================================================

CREATE TABLE IF NOT EXISTS roads (
    road_id INTEGER PRIMARY KEY AUTOINCREMENT,
    from_location_id INTEGER NOT NULL,
    to_location_id INTEGER NOT NULL,
    distance REAL NOT NULL CHECK (distance >= 0),
    travel_time REAL NOT NULL CHECK (travel_time >= 0),
    road_condition_weight REAL NOT NULL DEFAULT 1.0
        CHECK (road_condition_weight > 0),

    FOREIGN KEY (from_location_id)
        REFERENCES locations(location_id),

    FOREIGN KEY (to_location_id)
        REFERENCES locations(location_id)
);


-- =========================================================
-- SERVICE REQUESTS
-- =========================================================

CREATE TABLE IF NOT EXISTS service_requests (
    request_id INTEGER PRIMARY KEY,
    location_id INTEGER NOT NULL,
    service_type TEXT NOT NULL,
    priority TEXT NOT NULL,
    status TEXT NOT NULL,
    reported_at TEXT NOT NULL,

    FOREIGN KEY (location_id)
        REFERENCES locations(location_id)
);


-- =========================================================
-- RESOURCES
-- =========================================================

CREATE TABLE IF NOT EXISTS resources (
    resource_id INTEGER PRIMARY KEY,
    resource_name TEXT NOT NULL,
    resource_type TEXT NOT NULL,
    location_id INTEGER NOT NULL,
    availability TEXT NOT NULL,
    status TEXT NOT NULL,

    FOREIGN KEY (location_id)
        REFERENCES locations(location_id)
);


-- =========================================================
-- ALGORITHM RUNS
-- =========================================================

CREATE TABLE IF NOT EXISTS algorithm_runs (
    run_id INTEGER PRIMARY KEY AUTOINCREMENT,
    algorithm_name TEXT NOT NULL,
    input_size INTEGER NOT NULL CHECK (input_size >= 0),
    time_ns INTEGER NOT NULL CHECK (time_ns >= 0),
    memory_kb REAL,
    date_run TEXT NOT NULL
);


-- =========================================================
-- AUDIT EVENTS
-- =========================================================

CREATE TABLE IF NOT EXISTS audit_events (
    event_id INTEGER PRIMARY KEY AUTOINCREMENT,
    event_type TEXT NOT NULL,
    description TEXT NOT NULL,
    created_at TEXT NOT NULL
);