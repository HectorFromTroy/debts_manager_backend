INSERT INTO users
VALUES
( 100 , 'NIYAZ', 'NIYAZ', 'niyaz' ),
( 200 , 'DURAK', 'DURAK', 'durak' ),
( 300, 'SAMAT', 'SAMAT', null);

INSERT INTO debtship
VALUES
(100, 100, 200), -- durak niyazu
(200, 100, 300), -- samat niyazu
(300, 200, 100); -- niyaz duraku

INSERT INTO debt
VALUES
( 100, 200, 200,
  'SAMAT NIYAZU',
  '10-10-20',
  NULL, NULL, NULL
),
( 200, 300, 400,
  'NIYAZ DURAKU',
  '10-10-20',
  NULL, NULL, NULL
),
( 300, 300, 800,
  'NIYAZ duraku',
  '10-10-20',
  NULL, NULL, NULL
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