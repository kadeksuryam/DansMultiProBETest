# DansMultiProBETest
Dans Multi Pro BackEnd Test

## API Documentation
http://surya-dansbetest.koreacentral.cloudapp.azure.com/api/swagger-ui.html

## How to run locally
### Prerequisite
- Docker & docker compose

### Steps
- Create `.env` file on root directory that will contains application secrets, you can ask me for the secrets
- Run `. ./init.sh` on current terminal
- Build the jar by executing `./gradlew build`
- Run `docker compose up --build`
- Initialize DB Tables by executing `./initDB.sh`
- Application will started on port 8080 and you can see API documentation on:
http://localhost:8080/api/swagger-ui.html


## Further Development
- Observability/Logging support
- HTTPS protocol on production with nginx reverse proxy
- Decouple database container from app to avoid single point of failure
- More unit tests
