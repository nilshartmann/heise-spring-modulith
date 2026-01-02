-- noinspection SqlWithoutWhereForFile

DELETE FROM care_schema.care_tasks;

INSERT INTO care_schema.care_tasks (id, plant_id, active, type, source, next_due_date, interval)
    VALUES ('20caccb2-d48e-4f59-817a-1106a02da986','db7fee3a-9668-4bab-9679-dafe21a4f7c2', true, 'WATERING', 'SYSTEM', '2026-01-31', 7),
           ('e572cc7f-6a0f-46e8-90c5-54375f33cf8b','03f5fd02-7cd5-44b6-8b88-762dffe35475', true, 'REPOTTING', 'SYSTEM', '2026-01-31', null)
;
