-- CREATE t_user table
CREATE sequence if not exists user_id_sequence increment 100;
CREATE TABLE if not exists t_user
(
    id                 int8         NOT NULL default nextval('user_id_sequence'),
    username           varchar(100) NOT NULL,
    "password"         varchar(255) NOT NULL,
    "name"             varchar(255) NOT NULL,
    surname            varchar(255) NOT NULL,
    email              varchar(255) NOT NULL,
    role_type          varchar(50)  NOT NULL,
    created_by         varchar(255) NULL,
    created_date       timestamp    NULL,
    last_modified_by   varchar(255) NULL,
    last_modified_date timestamp    NULL,
    CONSTRAINT t_user_pkey PRIMARY KEY (id),
    CONSTRAINT uk_i6qjjoe560mee5ajdg7v1o6mi UNIQUE (email),
    CONSTRAINT uk_jhib4legehrm4yscx9t3lirqi UNIQUE (username)
);

-- CREATE t_book table
CREATE sequence if not exists book_id_sequence increment 100;
CREATE TABLE if not exists t_book (
  id                 int8         NOT NULL default nextval('book_id_sequence'),
  "name"             varchar(255) NOT NULL,
  author             varchar(255) NOT NULL,
  page               int8         NOT NULL,
  stock              int8         NOT NULL,
  price              float8       NOT NULL,
  created_by         varchar(255) NULL,
  created_date       timestamp    NULL,
  last_modified_by   varchar(255) NULL,
  last_modified_date timestamp    NULL,
  CONSTRAINT t_book_page_check CHECK ((page >= 1)),
  CONSTRAINT t_book_price_check CHECK ((price >= (0)::double precision)),
  CONSTRAINT t_book_stock_check CHECK ((stock >= 0)),
  CONSTRAINT t_book_pkey PRIMARY KEY (id)
);


