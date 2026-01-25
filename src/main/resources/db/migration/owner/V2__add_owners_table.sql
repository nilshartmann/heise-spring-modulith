SET SEARCH_PATH TO 'owner_schema';

CREATE TABLE owners (
    id   UUID PRIMARY KEY,
    name TEXT NOT NULL
);
