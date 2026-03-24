INSERT INTO users (username, password) VALUES ('admin', '$2y$10$rOhEEPM7ffKXKa070wvGke9xezjdUmJbbrsv2d4HdenIEvTzdiyfS')
ON CONFLICT (username) DO NOTHING;