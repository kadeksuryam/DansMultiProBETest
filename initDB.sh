psql "postgresql://${TESTDB_DATASOURCE_USERNAME}:${TESTDB_DATASOURCE_PASSWORD}@localhost:5432/testdb" << EOF
BEGIN;
\i src/main/resources/sql/ddl-drop.sql
\i src/main/resources/sql/ddl-create.sql
COMMIT;
EOF
