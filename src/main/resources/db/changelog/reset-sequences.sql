-- Reset database sequences to start after the last inserted ID
-- This fixes the "duplicate key" error when creating new records via POST endpoints

-- Reset customer sequence
SELECT setval('customer_id_seq', (SELECT COALESCE(MAX(id), 1) FROM customer));

-- Reset order sequence
SELECT setval('order_id_seq', (SELECT COALESCE(MAX(id), 1) FROM "order"));

-- Reset product sequence
SELECT setval('product_id_seq', (SELECT COALESCE(MAX(id), 1) FROM product));

-- Reset order_item sequence
SELECT setval('order_item_id_seq', (SELECT COALESCE(MAX(id), 1) FROM order_item));
