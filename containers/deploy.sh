docker pull registry.gitlab.com/medicaldataworks/railway/central:$1

docker stop central
docker rm central

docker run -d \
    --name central \
    --network=railway \
    -p 8080:8080 \
    registry.gitlab.com/medicaldataworks/railway/central:$1