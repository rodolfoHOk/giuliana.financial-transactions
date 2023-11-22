docker-compose down

# build backend image
docker build -t backend-pagnet:latest .

# build frontend image
docker build -t frontend-pagnet:latest ../financial-frontend

# start environment
docker-compose up --build --force-recreate --remove-orphans
