CREATE TABLE user_friends
(
                              user_id INT NOT NULL,
                              friend_id INT NOT NULL,
                              PRIMARY KEY (user_id, friend_id),
                              FOREIGN KEY (user_id) REFERENCES users (id),
                              FOREIGN KEY (friend_id) REFERENCES users (id)
);