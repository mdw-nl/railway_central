docker-compose -f railway.yml pull
docker-compose -f railway.yml down
docker rm central
docker-compose -f railway.yml up --build -d