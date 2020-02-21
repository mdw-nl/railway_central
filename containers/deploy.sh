docker-compose pull -f ../src/main/docker/railway.yml

docker-compose -f ../src/main/docker/railway.yml down
docker rm central
docker-compose -f ../src/main/docker/railway.yml up --build -d