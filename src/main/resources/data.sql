-- ================================
-- TARIFAS BASE
-- ================================
INSERT INTO tarifas (id, nombre, descripcion, fecha_inicio, fecha_fin, activa)
VALUES 
    (1, 'Tarifa Basica', 'Tarifa inicial para pruebas', '2025-01-01', '2025-12-31', true),
    (2, 'Tarifa Premium', 'Incluye servicios adicionales', '2025-01-01', '2025-12-31', true);

-- ================================
-- DETALLES DE CADA TARIFA
-- ================================
INSERT INTO detalles_tarifa (id, concepto, unidad, valor, tarifa_id)
VALUES
    (1, 'Km recorrido', 'km', 50.00, 1),
    (2, 'Peaje', 'unidad', 150.00, 1),

    (3, 'Carga especial', 'kg', 20.00, 2),
    (4, 'Uso de autopista', 'unidad', 300.00, 2);

-- ================================
-- Arreglar secuencias (IMPORTANTE)
-- ================================
SELECT setval('tarifas_id_seq', (SELECT MAX(id) FROM tarifas)+1);
SELECT setval('detalles_tarifa_id_seq', (SELECT MAX(id) FROM detalles_tarifa)+1);
