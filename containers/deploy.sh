docker pull registry.gitlab.com/medicaldataworks/railway/central:$1

docker stop central
docker rm central

docker run -d \
    --name central \
    --network=railway \
    -p 8042:8042 \
    registry.gitlab.com/medicaldataworks/railway/central:$1