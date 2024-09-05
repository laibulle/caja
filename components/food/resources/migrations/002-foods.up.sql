-- Create the 'foods' table
CREATE TABLE foods (
    id SERIAL PRIMARY KEY,
    name VARCHAR,
    brands VARCHAR,
    code VARCHAR,
    provider VARCHAR,
    description TEXT,
    member_id INTEGER,
    energy100 INTEGER NOT NULL,
    fat100 FLOAT NOT NULL,
    carb100 FLOAT NOT NULL,
    protein100 FLOAT NOT NULL,
    serving_size INTEGER,
    coocked_ratio FLOAT,
    is_nutrition_complete BOOLEAN,
    link VARCHAR,
    deleted_at TIMESTAMP,
    inserted_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now()
);

-- Unique index on the 'code' column of 'foods'
CREATE UNIQUE INDEX foods_code_index ON foods (code);

-- Index on the 'member_id' column of 'foods'
CREATE INDEX foods_member_id_index ON foods (member_id);

-- Add 'name_ts' tsvector column to 'foods'
ALTER TABLE foods ADD COLUMN name_ts tsvector GENERATED ALWAYS AS (to_tsvector('english', name)) STORED;

-- Add 'brands_ts' tsvector column to 'foods'
ALTER TABLE foods ADD COLUMN brands_ts tsvector GENERATED ALWAYS AS (to_tsvector('english', brands)) STORED;

-- Create GIN index on 'name_ts'
CREATE INDEX name_ts_idx ON foods USING GIN (name_ts);

-- Create GIN index on 'brands_ts'
CREATE INDEX brands_ts_idx ON foods USING GIN (brands_ts);

-- Create the 'menus' table
CREATE TABLE menus (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    description TEXT,
    member_id INTEGER NOT NULL,
    deleted_at TIMESTAMP,
    inserted_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now()
);

-- Create the 'menus_courses' table
CREATE TABLE menus_courses (
    id SERIAL PRIMARY KEY,
    day INTEGER NOT NULL,
    quantity INTEGER NOT NULL,
    meal VARCHAR NOT NULL,
    recipe_id INTEGER NOT NULL REFERENCES foods(id),
    menu_id INTEGER NOT NULL REFERENCES menus(id),
    inserted_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now()
);

-- Create the 'plannings_items' table
CREATE TABLE plannings_items (
    id SERIAL PRIMARY KEY,
    member_id INTEGER NOT NULL,
    meal VARCHAR NOT NULL,
    status VARCHAR NOT NULL,
    date DATE NOT NULL,
    quantity INTEGER NOT NULL,
    recipe_id INTEGER NOT NULL REFERENCES foods(id),
    inserted_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now()
);

-- Create the 'products_links' table
CREATE TABLE products_links (
    id SERIAL PRIMARY KEY,
    link TEXT NOT NULL,
    food_id INTEGER NOT NULL REFERENCES foods(id),
    inserted_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now()
);

-- Create the 'subs_recipes' table
CREATE TABLE subs_recipes (
    id SERIAL PRIMARY KEY,
    parent_recipe_id INTEGER NOT NULL REFERENCES foods(id),
    quantity INTEGER NOT NULL,
    sub_recipe_id INTEGER NOT NULL REFERENCES foods(id),
    inserted_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now()
);

-- Create the 'recipes_steps' table
CREATE TABLE recipes_steps (
    id SERIAL PRIMARY KEY,
    step INTEGER,
    duration INTEGER,
    description TEXT,
    food_id INTEGER NOT NULL REFERENCES foods(id),
    inserted_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now()
);

-- Create the 'recipes_ingredients' table
CREATE TABLE recipes_ingredients (
    id SERIAL PRIMARY KEY,
    quantity INTEGER,
    food_id INTEGER NOT NULL REFERENCES foods(id),
    ingredient_food_id INTEGER NOT NULL REFERENCES foods(id),
    inserted_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now()
);
