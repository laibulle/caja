-- Drop the 'recipes_ingredients' table
DROP TABLE IF EXISTS recipes_ingredients;

-- Drop the 'recipes_steps' table
DROP TABLE IF EXISTS recipes_steps;

-- Drop the 'subs_recipes' table
DROP TABLE IF EXISTS subs_recipes;

-- Drop the 'products_links' table
DROP TABLE IF EXISTS products_links;

-- Drop the 'plannings_items' table
DROP TABLE IF EXISTS plannings_items;

-- Drop the 'menus_courses' table
DROP TABLE IF EXISTS menus_courses;

-- Drop the 'menus' table
DROP TABLE IF EXISTS menus;

-- Drop the GIN index on 'brands_ts' in the 'foods' table
DROP INDEX IF EXISTS brands_ts_idx;

-- Drop the GIN index on 'name_ts' in the 'foods' table
DROP INDEX IF EXISTS name_ts_idx;

-- Remove the 'brands_ts' column from the 'foods' table
ALTER TABLE foods DROP COLUMN IF EXISTS brands_ts;

-- Remove the 'name_ts' column from the 'foods' table
ALTER TABLE foods DROP COLUMN IF EXISTS name_ts;

-- Drop the index on the 'member_id' column in the 'foods' table
DROP INDEX IF EXISTS foods_member_id_index;

-- Drop the unique index on the 'code' column in the 'foods' table
DROP INDEX IF EXISTS foods_code_index;

-- Drop the 'foods' table
DROP TABLE IF EXISTS foods;
