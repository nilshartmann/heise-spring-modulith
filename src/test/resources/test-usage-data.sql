INSERT INTO billing_schema.usage_records (id,
                                          owner_id,
                                          usage_type,
                                          recorded_at,
                                          cost_cents)
    VALUES
        -- Owner 1 not in November
        ('b2b635db-612f-46cc-96fd-778280ed6dbf', '5ec3f30d-4df6-4080-a5dc-560e8db94259', 'SETUP_FEE',
         '2025-10-27T08:15:00.00Z', 1000),
        -- Owner 1 in November
        ('6f507fe0-2144-4da1-a454-624c5b00f961', '5ec3f30d-4df6-4080-a5dc-560e8db94259', 'CARE_TASK_COMPLETED',
         '2025-11-27T08:15:00.00Z', 200),
        -- Owner 2 in November
        ('af42bea6-60b5-41be-94d8-f997b107624b', '1c7132f5-0e91-420c-9987-7f6ac7911d3c', 'SETUP_FEE',
         '2025-11-27T08:15:00.00Z', 1000),
        -- Owner 1 in November, different Usage Type
        ('4d20082d-6973-4a0a-bf3f-ceeb6a970ed2', '5ec3f30d-4df6-4080-a5dc-560e8db94259', 'CARE_TASK_COMPLETED',
         '2025-11-30T08:15:00.00Z', 200),
        -- Owner 3 only in december
        ('4bc60a95-392b-1425-8556-2496b4dc9d84', '5705a7d5-b009-7420-0103-f1b0227cae0a', 'SETUP_FEE',
         '2025-12-01T08:15:00.00Z', 1000)

;