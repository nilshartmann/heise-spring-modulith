-- Pflanze 1: Rose
INSERT INTO plant_schema.plants (id, owner_id, name, plant_type, location)
VALUES ('d1683726-2937-a842-3c94-2db853718890',
        '85483586-044c-9778-73b1-6327133cf030',
        'Meine Rose',
        'ROSES',
        'Garten, Südseite');

-- Pflanze 2: Cannabis
INSERT INTO plant_schema.plants (id, owner_id, name, plant_type, location)
VALUES ('da25dc26-b511-145b-8053-57befe00d56c',
        '259ca287-08d0-50b0-3999-183b93e2e5bc',
        'Cannabis (nur für medizinische Zwecke)',
        'INDOOR',
        'Wohnzimmer, Fensterbank');

-- Care Tasks für Rose (2 Tasks)
INSERT INTO care_schema.care_tasks (id, plant_id, active, type, source, interval, next_due_date)
VALUES ('a031d9b8-6cb7-7496-3773-9cfa71a224c9',
        'd1683726-2937-a842-3c94-2db853718890',
        true,
        'WATERING',
        'SYSTEM',
        5,
        CURRENT_DATE + 8);

INSERT INTO care_schema.care_tasks (id, plant_id, active, type, source, interval, next_due_date)
VALUES ('90810114-dc34-8681-125b-3d5d6a642bbc',
        'd1683726-2937-a842-3c94-2db853718890',
        true,
        'PRUNING',
        'SYSTEM',
        null,
        CURRENT_DATE + 7);

-- Care Task für Canabis
INSERT INTO care_schema.care_tasks (id, plant_id, active, type, source, interval, next_due_date)
VALUES ('9c7c8843-13a8-756a-5620-f422194a5d11',
        'da25dc26-b511-145b-8053-57befe00d56c',
        true,
        'WATERING',
        'SYSTEM',
        2,
        CURRENT_DATE + 10);

INSERT INTO care_schema.care_tasks (id, plant_id, active, type, source, interval, next_due_date)
    VALUES ('0a50b222-5211-0452-06a1-545000f41906',
            'da25dc26-b511-145b-8053-57befe00d56c',
            true,
            'REPOTTING',
            'SYSTEM',
            NULL,
            CURRENT_DATE + 11);
