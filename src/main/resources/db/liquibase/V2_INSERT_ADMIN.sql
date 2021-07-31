INSERT INTO t_user (username, "password", role_type, "name", surname, email, created_by, created_date, last_modified_by,
                    last_modified_date)
VALUES ('admin', '$2a$10$SfACIid5JhU63oJVqLfAy.0nDeWJGD0.iH71iUH9s4B21eL9caxK.', 'ADMIN', 'admin-name', 'admin-surname',
        'admin@getir.com', 'anonymousUser', current_timestamp, 'anonymousUser', current_timestamp);