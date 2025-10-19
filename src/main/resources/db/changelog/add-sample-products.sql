-- Add sample products
INSERT INTO product (id, description)
VALUES (1, 'Football');
INSERT INTO product (id, description)
VALUES (2, 'Basketball');
INSERT INTO product (id, description)
VALUES (3, 'Tennis Racket');
INSERT INTO product (id, description)
VALUES (4, 'Running Shoes');
INSERT INTO product (id, description)
VALUES (5, 'Yoga Mat');
INSERT INTO product (id, description)
VALUES (6, 'Dumbbells');
INSERT INTO product (id, description)
VALUES (7, 'Bicycle Helmet');
INSERT INTO product (id, description)
VALUES (8, 'Swimming Goggles');
INSERT INTO product (id, description)
VALUES (9, 'Baseball Glove');
INSERT INTO product (id, description)
VALUES (10, 'Golf Clubs');

-- Add sample order-product relationships with quantity and price
-- Order 1 has multiple products
INSERT INTO order_item (order_id, product_id, quantity, price_at_order_time)
VALUES (1, 1, 2, 29.99);
INSERT INTO order_item (order_id, product_id, quantity, price_at_order_time)
VALUES (1, 4, 1, 89.99);

-- Order 2 has one product
INSERT INTO order_item (order_id, product_id, quantity, price_at_order_time)
VALUES (2, 2, 1, 24.99);

-- Order 3 has multiple products
INSERT INTO order_item (order_id, product_id, quantity, price_at_order_time)
VALUES (3, 3, 1, 119.99);
INSERT INTO order_item (order_id, product_id, quantity, price_at_order_time)
VALUES (3, 5, 2, 19.99);

-- Order 4
INSERT INTO order_item (order_id, product_id, quantity, price_at_order_time)
VALUES (4, 6, 2, 45.00);

-- Order 5
INSERT INTO order_item (order_id, product_id, quantity, price_at_order_time)
VALUES (5, 7, 1, 59.99);

-- Order 6 has multiple products
INSERT INTO order_item (order_id, product_id, quantity, price_at_order_time)
VALUES (6, 8, 3, 15.99);
INSERT INTO order_item (order_id, product_id, quantity, price_at_order_time)
VALUES (6, 9, 1, 49.99);

-- Order 7
INSERT INTO order_item (order_id, product_id, quantity, price_at_order_time)
VALUES (7, 10, 1, 299.99);

-- Order 8 has multiple products
INSERT INTO order_item (order_id, product_id, quantity, price_at_order_time)
VALUES (8, 1, 1, 29.99);
INSERT INTO order_item (order_id, product_id, quantity, price_at_order_time)
VALUES (8, 2, 1, 24.99);

-- Order 9
INSERT INTO order_item (order_id, product_id, quantity, price_at_order_time)
VALUES (9, 4, 2, 89.99);

-- Order 10
INSERT INTO order_item (order_id, product_id, quantity, price_at_order_time)
VALUES (10, 5, 1, 19.99);

-- Some products (1, 4, 5) appear in multiple orders - good for testing
-- Some orders have multiple products - good for testing
-- Some products (e.g., 3, 6, 7) appear only once - variation
