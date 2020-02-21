docker-compose pull -f railway.yml
docker-compose -f railway.yml down
docker rm central
docker-compose -f railway.yml up --build -d