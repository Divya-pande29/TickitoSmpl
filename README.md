# Tikito migration

- `tikito-express-backend`: Express/MySQL layered API replacing the Spring Boot service.
- `tikito-react-native`: Expo React Native customer app replacing the Android XML/Java app.

## Run

1. Copy `tikito-express-backend/.env.example` to `tikito-express-backend/.env` and set the database credentials. Apply the schema in `Tikito/src/main/java/com/sunbeam/tikito/utils/db.sql`.
2. In `tikito-express-backend`, run `npm install` and `npm run dev`.
3. Copy `tikito-react-native/.env.example` to `tikito-react-native/.env`, update the API host for a physical device, then run `npm install` and `npm start`.

The legacy `Tikito` and `Tikito_mobile_apps` folders remain intact for reference during migration.
