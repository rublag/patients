CREATE TABLE IF NOT EXISTS patients (
       id int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
       first_name text NOT NULL,
       last_name text,
       patronimic_name text,
       birthday date NOT NULL,
       address text,
       oms_number text NOT NULL,
       sex sex
);
