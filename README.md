# INSTRUCTION TO RUN THIS APP LOCALLY (IF NEEDED)

## Setup Project
### 1. Navigate to the project directory
```bash
cd InteviewBE
```
#### 2. Build and start app with Docker
```bash
docker compose -f src/main/docker/docker-compose.yml up --build
```
### 3. Wait for docker to build and start 2 containers:
- PostgreSQL
- Main app
### 4. Wait for the main app to initialize db (usually takes around 20-30s)
- You can check the logs of the app container
- When the last line of logs shows: "Default accounts created with username = test and test123", the app is ready
### 5. The application is now running at http://localhost:8080

## Test app with Postman (or Bruno)
### 1. Import the postman collection located in the project dir (testapp.postman_collection.json)
### 2. Get JWT token
- This app uses jwt for basic authentication.
- Open the POST /auth/login request
- Hit the Send button
- Copy the returned accessToken value
### 3. For all other requests:
- Go to the Authorization tab
- Paste the token value into the "Token" field
- Click Send and the response will come up in the Body tab

## Available Endpoints
- POST /auth/login: simple login endpoint to get jwt token (default username is test, and password is test123)
- GET /scores/{reg_num}: retrieves scores from a registration number
- GET /scores/top10/groupA: retrieves top 10 scores from group A(math+physics+chemistry)
- GET /statistics/ : retrieve score statistics for all subjects across four groups

## NOTE!!!:
The first request is usually slower than subsequent requests (due to framework initialization and other stuff like db connection pool setup,...)

## UPDATE:
The Swagger UI for this app is available at /swagger-ui/index.html.
