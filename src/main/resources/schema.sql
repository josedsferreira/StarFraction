
CREATE TYPE planet_size AS ENUM ('SMALL', 'MEDIUM', 'LARGE');


CREATE TABLE IF NOT EXISTS planet (
    planet_id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    planet_name VARCHAR(255),
    planet_size planet_size
);