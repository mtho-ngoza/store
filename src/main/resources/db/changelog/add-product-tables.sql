-- Add Product table
CREATE TABLE product
(
    id          BIGSERIAL PRIMARY KEY,
    description VARCHAR(255)
);

-- Add OrderItem junction table for many-to-many relationship between Order and Product
CREATE TABLE order_item
(
    id                  BIGSERIAL PRIMARY KEY,
    order_id            BIGINT NOT NULL,
    product_id          BIGINT NOT NULL,
    quantity            INTEGER,
    price_at_order_time DECIMAL(19, 2),
    CONSTRAINT fk_order_item_order FOREIGN KEY (order_id) REFERENCES "order" (id) ON DELETE CASCADE,
    CONSTRAINT fk_order_item_product FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE CASCADE
);

-- Add indexes for better query performance
CREATE INDEX idx_order_item_order_id ON order_item (order_id);
CREATE INDEX idx_order_item_product_id ON order_item (product_id);
