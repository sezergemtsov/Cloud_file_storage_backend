create table file_bearer
(id serial, size bigint  NOT NULL,file_name text NOT NULL UNIQUE , file_holder bytea, PRIMARY KEY (id));

