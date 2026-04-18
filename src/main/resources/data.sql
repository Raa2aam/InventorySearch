-- Seed data for Inventory Search API
-- This file runs automatically on app startup

INSERT IGNORE INTO inventory (name, category, subcategory, model, seller, location, brand, color, warranty, specification, price, stock, manufacturing_date, expiry_date, is_active, created_at, updated_at)
VALUES
-- Electronics
('Samsung 55" 4K Smart TV', 'Electronics', 'Television', 'UA55AU7700', 'Samsung India', 'Mumbai', 'Samsung', 'Black', '1 Year', '4K UHD, HDR, Smart TV with WiFi', 49999.00, 50, '2023-01-15', '2028-01-15', true, NOW(), NOW()),
('Apple iPhone 15', 'Electronics', 'Smartphone', 'iPhone15', 'Apple India', 'Delhi', 'Apple', 'Black', '1 Year', '6.1 inch, A16 Bionic, 128GB', 79999.00, 30, '2023-09-20', '2028-09-20', true, NOW(), NOW()),
('Sony WH-1000XM5', 'Electronics', 'Headphones', 'WH1000XM5', 'Sony India', 'Bangalore', 'Sony', 'Silver', '1 Year', 'Noise Cancelling, 30hr battery, Bluetooth 5.2', 29999.00, 75, '2023-03-10', '2028-03-10', true, NOW(), NOW()),
('Dell XPS 15 Laptop', 'Electronics', 'Laptop', 'XPS9530', 'Dell India', 'Hyderabad', 'Dell', 'Silver', '2 Years', 'Intel i7, 16GB RAM, 512GB SSD, 15.6 inch', 129999.00, 20, '2023-06-01', '2028-06-01', true, NOW(), NOW()),
('OnePlus Nord CE3', 'Electronics', 'Smartphone', 'NordCE3', 'OnePlus India', 'Chennai', 'OnePlus', 'Grey', '1 Year', '6.7 inch, Snapdragon 782G, 256GB', 24999.00, 100, '2023-08-15', '2028-08-15', true, NOW(), NOW()),

-- Furniture
('Wooden Study Table', 'Furniture', 'Table', 'WST-001', 'WoodCraft', 'Mumbai', 'WoodCraft', 'Brown', '2 Years', 'Solid wood, 4ft x 2ft, with drawer', 8999.00, 40, '2023-02-20', '2033-02-20', true, NOW(), NOW()),
('Ergonomic Office Chair', 'Furniture', 'Chair', 'EOC-200', 'ErgoSeat', 'Delhi', 'ErgoSeat', 'Black', '3 Years', 'Lumbar support, adjustable height, mesh back', 12999.00, 25, '2023-04-10', '2033-04-10', true, NOW(), NOW()),
('3 Seater Sofa', 'Furniture', 'Sofa', 'SF3-400', 'HomePlus', 'Bangalore', 'HomePlus', 'Grey', '2 Years', 'Fabric upholstery, wooden frame, 7ft', 34999.00, 15, '2023-05-15', '2033-05-15', true, NOW(), NOW()),

-- Clothing
('Nike Air Max 270', 'Clothing', 'Footwear', 'AM270-BLK', 'Nike India', 'Mumbai', 'Nike', 'Black', '6 Months', 'Running shoes, Air cushion, Size 7-11', 12999.00, 200, '2023-07-01', '2025-07-01', true, NOW(), NOW()),
('Levi 511 Slim Jeans', 'Clothing', 'Bottomwear', '511-SLIM', 'Levis India', 'Delhi', 'Levis', 'Blue', 'No Warranty', 'Slim fit, stretch denim, sizes 28-36', 3999.00, 150, '2023-06-15', '2025-06-15', true, NOW(), NOW()),
('Allen Solly Formal Shirt', 'Clothing', 'Topwear', 'AS-FRML-01', 'Allen Solly', 'Chennai', 'Allen Solly', 'White', 'No Warranty', 'Regular fit, cotton blend, sizes S-XXL', 1999.00, 300, '2023-08-01', '2025-08-01', true, NOW(), NOW()),

-- Kitchen
('Prestige Pressure Cooker 5L', 'Kitchen', 'Cookware', 'PPC-5L', 'Prestige', 'Hyderabad', 'Prestige', 'Silver', '5 Years', 'Stainless steel, induction compatible, 5 litre', 2499.00, 80, '2023-01-10', '2033-01-10', true, NOW(), NOW()),
('Philips Air Fryer', 'Kitchen', 'Appliance', 'HD9200', 'Philips India', 'Mumbai', 'Philips', 'Black', '2 Years', '4.1L capacity, 1400W, digital display', 8999.00, 45, '2023-03-20', '2028-03-20', true, NOW(), NOW()),
('Bosch Microwave 25L', 'Kitchen', 'Appliance', 'HMT75G451B', 'Bosch India', 'Bangalore', 'Bosch', 'Silver', '2 Years', '25L, 900W, grill function, auto cook', 12999.00, 30, '2023-05-01', '2028-05-01', true, NOW(), NOW()),

-- Sports
('Yonex Badminton Racket', 'Sports', 'Racket', 'YNEX-ARC', 'Yonex India', 'Delhi', 'Yonex', 'Blue', '6 Months', 'Carbon graphite, 85g, string tension 24lbs', 3499.00, 120, '2023-04-15', '2026-04-15', true, NOW(), NOW()),
('Cosco Cricket Bat', 'Sports', 'Cricket', 'CC-BAT-01', 'Cosco Sports', 'Mumbai', 'Cosco', 'Brown', '6 Months', 'English willow, full size, oiled and pressed', 2999.00, 60, '2023-06-20', '2026-06-20', true, NOW(), NOW()),

-- Out of stock item (good for testing isActive and stock filters)
('LG OLED TV 65"', 'Electronics', 'Television', 'OLED65C3', 'LG India', 'Mumbai', 'LG', 'Black', '1 Year', '65 inch OLED, 4K, 120Hz, webOS', 189999.00, 0, '2023-09-01', '2028-09-01', false, NOW(), NOW());