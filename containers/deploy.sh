docker pull registry.gitlab.com/medicaldataworks/railway/central:$1

docker stop central
docker rm central

docker run -d \
    --name central \
    -p 8080:8080 \
    -v ./containers/application.yml:/app/application.yml \
    -v ./containers/railway.mv.db:app/railway.mv.db \
    registry.gitlab.com/medicaldataworks/railway/central:$1