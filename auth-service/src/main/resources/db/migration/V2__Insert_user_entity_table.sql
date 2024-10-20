-- Insert admin user into the user_entity table
INSERT INTO public.user_entity (
    username,
    first_name,
    last_name,
    email,
    password,
    account_non_expired,
    account_non_locked,
    credentials_non_expired,
    enabled,
    version,
    created_at,
    updated_at
) VALUES (
             'admin',                       -- username
             'admin',                       -- first_name
             'admin',                       -- last_name
             'admin@admin.com',            -- email
             '$2a$10$LrIXjsRGoLaZ.nUTWRIHN.nKizN6WHwaoFwbw4X.aKu7PMCFw8KPK', -- password
             true,                         -- account_non_expired
             true,                         -- account_non_locked
             true,                         -- credentials_non_expired
             true,                         -- enabled
             0,                            -- version
             '2024-10-20 00:16:01.48518', -- created_at
             '2024-10-20 00:16:01.48518'  -- updated_at
         );
