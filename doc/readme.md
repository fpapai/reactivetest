STS
    --name=A --version=100

Docker

docker container run --publish 80:8080 --name rt1 -d reactivetest:0.1.0 --app.name=r1


docker rmi $(docker images --filter "dangling=true" -q --no-trunc)
