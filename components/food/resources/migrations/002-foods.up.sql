CREATE TABLE foods (
    id SERIAL PRIMARY KEY,
    brands TEXT NOT NULL,
    code VARCHAR(50) NOT NULL,
    provider VARCHAR(100) NOT NULL,
    member_id integer NOT NULL,
    energy100 integer NOT NULL,
    fat100 float NOT NULL,
    carb100 float NOT NULL,
    protein100 float NOT NULL,
    serving_size integer,
    coocked_ratio integer,
    is_nutrition_complete boolean,
    link text,
    deleted_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE menus (
    id SERIAL PRIMARY KEY
);


CREATE TABLE menus_courses (
    id SERIAL PRIMARY KEY
);


CREATE TABLE plannings_items (
    id SERIAL PRIMARY KEY
);


CREATE TABLE products_links (
    id SERIAL PRIMARY KEY
);


CREATE TABLE subs_recipes (
    id SERIAL PRIMARY KEY
);

CREATE TABLE recipes_steps (
    id SERIAL PRIMARY KEY
);

CREATE TABLE recipes_ingredients (
    id SERIAL PRIMARY KEY
);

