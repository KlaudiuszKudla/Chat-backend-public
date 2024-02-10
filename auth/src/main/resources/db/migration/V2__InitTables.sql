create table resetoperations
(
    id         serial primary key,
    users      integer REFERENCES "users" (id),
    create_date timestamp DEFAULT current_timestamp,
    uid        varchar
)