ALTER TABLE user_friends
    ADD COLUMN is_blocked BOOLEAN DEFAULT FALSE,
    ADD COLUMN is_accepted BOOLEAN DEFAULT FALSE;
