INSERT INTO stego_acs.acs_admins (login, password, role, enabled) VALUES
    ('admin', 'ee79976c9380d5e337fc1c095ece8c8f22f91f306ceeb161fa51fecede2c4ba1', 'admin', 'true'); --11111111
INSERT INTO stego_acs.acs_auth_log_type_of_entry (name, description) VALUES
    ('success', 'Authentication successful.'),
    ('fail', 'Authentication failed.');