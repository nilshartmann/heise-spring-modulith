SET SEARCH_PATH TO 'storage_schema';

CREATE TABLE plant_storages (
    id         UUID PRIMARY KEY,
    plant_id   UUID           NOT NULL,
    start_date DATE           NOT NULL,
    end_date   DATE,
    daily_rate DECIMAL(10, 2) NOT NULL
);