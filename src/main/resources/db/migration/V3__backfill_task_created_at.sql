UPDATE task
SET created_at = COALESCE(updated_at, NOW())
WHERE created_at IS NULL;