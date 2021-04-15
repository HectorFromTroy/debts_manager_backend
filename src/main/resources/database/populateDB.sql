INSERT INTO users
VALUES
( 100 , 'NIYAZ', 'NIYAZ', 'niyaz' ),
( 200 , 'DURAK', 'DURAK', 'durak' ),
( 300, 'SAMAT', 'SAMAT', null);

INSERT INTO debtship
VALUES
(100, 100, 200), -- durak niyazu
(200, 100, 300), -- samat niyazu
(400, 300, 100), -- niyaz samatu
(300, 200, 100); -- niyaz duraku

INSERT INTO debt
VALUES
( 100, 200, 200,
  'SAMAT NIYAZU',
  '10-10-20',
  0, NULL, NULL
),
( 200, 300, 400,
  'NIYAZ DURAKU',
  '10-10-20',
  0, NULL, NULL
),
( 300, 300, 800,
  'NIYAZ duraku',
  '10-10-20',
  0, NULL, NULL
),
( 400, 200, 200,
  'SAMAT NIYAZU',
  '10-9-20',
  0, NULL, NULL
),
( 500, 200, 200,
  'SAMAT NIYAZU',
  '10-4-20',
  0, NULL, NULL
),
( 600, 200, 200,
  'SAMAT NIYAZU',
  '11-10-20',
  200, '11-15-20', 'vernul'
),
( 700, 400, 400,
  'Niyaz samatu',
  '11-10-20',
  0, null, null
);

-- INSERT INTO debt
-- VALUES
-- ( 1, 100, 300, 200,
-- 'SAMAT NIYAZU',
--  '10-10-20',
--  NULL, NULL, NULL
-- ),
-- ( 2, 200, 100, 400,
--   'NIYAZ DURAKU',
--   '10-10-20',
--   NULL, NULL, NULL
-- ),
-- ( 3, 200, 100, 800,
--   'NIYAZ duraku',
--   '10-10-20',
--   NULL, NULL, NULL
-- );