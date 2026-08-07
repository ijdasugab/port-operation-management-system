INSERT INTO service_types (id, code, name, description, category, created_by) VALUES
(gen_random_uuid(), 'VESSEL_BERTHING', 'Vessel Berthing Service', 'Service for vessel berthing at the port', 'VESSEL', 'system'),
(gen_random_uuid(), 'PILOTAGE', 'Pilotage Service', 'Pilotage assistance for incoming vessels', 'VESSEL', 'system'),
(gen_random_uuid(), 'TOWAGE', 'Towage Service', 'Towage services for vessel positioning', 'VESSEL', 'system'),
(gen_random_uuid(), 'CARGO_LOADING', 'Cargo Loading', 'Loading cargo onto vessel', 'CARGO', 'system'),
(gen_random_uuid(), 'CARGO_UNLOADING', 'Cargo Unloading', 'Unloading cargo from vessel', 'CARGO', 'system'),
(gen_random_uuid(), 'CARGO_STORAGE', 'Cargo Storage', 'Storage of cargo in yard or warehouse', 'STORAGE', 'system'),
(gen_random_uuid(), 'CONTAINER_HANDLING', 'Container Handling', 'Handling containers via STS/RTG', 'CARGO', 'system'),
(gen_random_uuid(), 'WATER_SUPPLY', 'Fresh Water Supply', 'Supplying fresh water to vessels', 'VESSEL', 'system'),
(gen_random_uuid(), 'WASTE_DISPOSAL', 'Waste Disposal', 'Disposal of vessel waste', 'VESSEL', 'system');

INSERT INTO vessels (name, imo_number, flag, vessel_type, gross_tonnage, length_overall, draft, created_by) VALUES
('MV Nusantara Star', 'IMO9876543', 'ID', 'CONTAINER', 25000.00, 180.50, 9.50, 'system'),
('MV Samudra Jaya', 'IMO8765432', 'ID', 'BULK_CARRIER', 35000.00, 210.00, 11.20, 'system'),
('MT Pertiwi', 'IMO7654321', 'ID', 'TANKER', 45000.00, 240.00, 12.50, 'system'),
('MV Bahari Express', 'IMO6543210', 'SG', 'GENERAL_CARGO', 15000.00, 150.00, 7.80, 'system'),
('MV Pacific Voyager', 'IMO5432109', 'LR', 'CONTAINER', 55000.00, 280.00, 14.00, 'system');

INSERT INTO commodities (code, name, category, hazardous, description, created_by) VALUES
('RICE', 'Rice', 'DRY_BULK', false, 'White rice in bags or bulk', 'system'),
('COAL', 'Coal', 'DRY_BULK', false, 'Thermal coal', 'system'),
('PALM_OIL', 'Palm Oil', 'LIQUID_BULK', false, 'Crude Palm Oil (CPO)', 'system'),
('CEMENT', 'Cement', 'DRY_BULK', false, 'Portland cement', 'system'),
('FERTILIZER', 'Fertilizer', 'DRY_BULK', true, 'Chemical fertilizers', 'system'),
('STEEL_COIL', 'Steel Coil', 'GENERAL_CARGO', false, 'Rolled steel coils', 'system'),
('TIMBER', 'Timber', 'GENERAL_CARGO', false, 'Processed wood logs', 'system'),
('CHEMICALS', 'Chemicals', 'LIQUID_BULK', true, 'Industrial chemicals', 'system'),
('CONTAINERS', 'General Containers', 'CONTAINER', false, 'Standard TEU/FEU containers', 'system');

INSERT INTO terminal_areas (code, name, area_type, capacity, capacity_unit, location, created_by) VALUES
('BERTH-01', 'Berth 1', 'BERTH', 1, 'VESSEL', 'North Terminal', 'system'),
('BERTH-02', 'Berth 2', 'BERTH', 1, 'VESSEL', 'North Terminal', 'system'),
('BERTH-03', 'Berth 3', 'BERTH', 1, 'VESSEL', 'South Terminal', 'system'),
('YARD-A', 'Container Yard A', 'YARD', 5000, 'TEU', 'East Block', 'system'),
('YARD-B', 'Container Yard B', 'YARD', 3500, 'TEU', 'West Block', 'system'),
('WAREHOUSE-01', 'Main Warehouse 1', 'WAREHOUSE', 15000, 'SQM', 'Logistics Center', 'system'),
('CFS-01', 'Container Freight Station 1', 'CFS', 5000, 'SQM', 'Logistics Center', 'system'),
('LIQUID-BERTH-01', 'Liquid Bulk Berth 1', 'BERTH', 1, 'VESSEL', 'Chemical Terminal', 'system');

INSERT INTO vehicles (plate_number, vehicle_type, max_capacity_tons, owner_name, created_by) VALUES
('B 1234 CD', 'TRUCK', 15.00, 'PT Trans Logistik', 'system'),
('B 5678 EF', 'TRAILER', 30.00, 'PT Trans Logistik', 'system'),
('D 9012 GH', 'TRUCK', 10.00, 'Mandiri Transport', 'system'),
('L 3456 IJ', 'TRAILER', 40.00, 'Surabaya Freight', 'system'),
('F 7890 KL', 'FORKLIFT', 5.00, 'Port Operator', 'system');

-- Insert tariffs (lookup service type ids dynamically)
INSERT INTO tariffs (service_type_id, tariff_code, description, unit_price, unit, currency, effective_from, created_by)
SELECT id, 'TRF-VB-001', 'Basic Vessel Berthing', 15000.0000, 'PER_METER', 'IDR', '2024-01-01', 'system'
FROM service_types WHERE code = 'VESSEL_BERTHING';

INSERT INTO tariffs (service_type_id, tariff_code, description, unit_price, unit, currency, effective_from, created_by)
SELECT id, 'TRF-CL-001', 'General Cargo Loading', 25000.0000, 'PER_TON', 'IDR', '2024-01-01', 'system'
FROM service_types WHERE code = 'CARGO_LOADING';

INSERT INTO tariffs (service_type_id, tariff_code, description, unit_price, unit, currency, effective_from, created_by)
SELECT id, 'TRF-CH-001', 'Container Handling (Standard)', 850000.0000, 'PER_TEU', 'IDR', '2024-01-01', 'system'
FROM service_types WHERE code = 'CONTAINER_HANDLING';

INSERT INTO tariffs (service_type_id, tariff_code, description, unit_price, unit, currency, effective_from, created_by)
SELECT id, 'TRF-PL-001', 'Pilotage Assistance', 5000000.0000, 'FLAT', 'IDR', '2024-01-01', 'system'
FROM service_types WHERE code = 'PILOTAGE';
