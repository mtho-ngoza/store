-- Add index on order.customer_id foreign key for better JOIN performance
-- This FK is used heavily when fetching orders with customer details
CREATE INDEX idx_order_customer_id ON "order" (customer_id);

-- Add trigram index on customer.name for efficient LIKE '%search%' queries
-- Requires pg_trgm extension for substring search performance
-- Note: This is optional but significantly improves name search performance
CREATE EXTENSION IF NOT EXISTS pg_trgm;
CREATE INDEX idx_customer_name_trgm ON customer USING gin (name gin_trgm_ops);
