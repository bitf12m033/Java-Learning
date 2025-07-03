-- Create users table if it doesn't exist
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    image_path VARCHAR(500)
);

-- Create index on email for faster lookups (MySQL doesn't support IF NOT EXISTS for indexes)
-- This will be handled by the application or you can create it manually 