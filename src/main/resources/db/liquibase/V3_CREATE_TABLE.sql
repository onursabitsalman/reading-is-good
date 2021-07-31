-- CREATE t_order table
CREATE sequence if not exists order_id_sequence increment 100;
CREATE TABLE if not exists t_order (
  id                 int8         NOT NULL,
  book_id            int8         NULL,
  customer_id        int8         NULL,
  quantity           int8         NOT NULL,
  status             varchar(255) NOT NULL,
  created_by         varchar(255) NULL,
  created_date       timestamp    NULL,
  last_modified_by   varchar(255) NULL,
  last_modified_date timestamp    NULL,
  CONSTRAINT t_order_quantity_check CHECK ((quantity >= 1)),
  CONSTRAINT t_order_pkey PRIMARY KEY (id)
);

ALTER TABLE t_order ADD CONSTRAINT fk9xrxacypr9n7pamivfvqdb7g1 FOREIGN KEY (book_id) REFERENCES t_book(id);
ALTER TABLE t_order ADD CONSTRAINT fkdtosb0vobpvemw6wi8bhixej5 FOREIGN KEY (customer_id) REFERENCES t_user(id);