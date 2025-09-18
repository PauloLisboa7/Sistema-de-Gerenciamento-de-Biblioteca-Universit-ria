# TODO: Switch to PostgreSQL Database

## Completed Tasks
- [x] Updated `src/main/resources/application.properties` to use PostgreSQL configuration (with placeholders for connection details).
- [x] Removed H2 dependency from `pom.xml` to avoid conflicts.

## Pending Tasks
- [ ] Replace placeholders in `application.properties` with actual PostgreSQL connection details (host, port, database name, username, password).
- [ ] Ensure PostgreSQL server is running and the database exists.
- [ ] Build and run the application to verify the connection works.
- [ ] Test basic CRUD operations for Livro, Usuario, and Emprestimo entities.
- [ ] Update any tests that rely on H2 to use PostgreSQL or mock the database.

## Notes
- The `spring.jpa.hibernate.ddl-auto` is set to `update` to create/update tables on startup. Adjust if needed (e.g., to `validate` for production).
- If you encounter connection issues, check PostgreSQL logs and ensure the database is accessible.
