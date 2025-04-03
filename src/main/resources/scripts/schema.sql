DROP USER IF EXISTS 'appuser'@'localhost';

CREATE USER IF NOT EXISTS 'appuser'@'localhost' IDENTIFIED BY 'password';

CREATE DATABASE IF NOT EXISTS localization_app CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

GRANT ALL PRIVILEGES ON localization_app.* TO 'appuser'@'localhost';
FLUSH PRIVILEGES;

USE localization_app;

CREATE TABLE IF NOT EXISTS localization (
    id INT AUTO_INCREMENT PRIMARY KEY,
    key_name VARCHAR(50) NOT NULL,
    language_code VARCHAR(10) NOT NULL,
    translation_text VARCHAR(255) NOT NULL,
    UNIQUE KEY unique_translation (key_name, language_code)
);