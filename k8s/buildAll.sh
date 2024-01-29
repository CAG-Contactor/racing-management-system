#! /bin/bash

if [ ! -d "./k8s" ]; then
  echo "You need to be in the root of the git repository"
  exit 1
fi

mvn clean install
cd user-manager/target
docker buildx build -f ../src/main/docker/Dockerfile --platform linux/amd64,linux/arm64 --push  --tag 192.168.0.50:32000/cag/user-manager:latest .
cd ../../client-api/target
docker buildx build -f ../src/main/docker/Dockerfile --platform linux/amd64,linux/arm64 --push  --tag 192.168.0.50:32000/cag/client-api:latest .
cd ../../current-race/target
docker buildx build -f ../src/main/docker/Dockerfile --platform linux/amd64,linux/arm64 --push  --tag 192.168.0.50:32000/cag/current-race:latest .
cd ../../leaderboard/target
docker buildx build -f ../src/main/docker/Dockerfile --platform linux/amd64,linux/arm64 --push  --tag 192.168.0.50:32000/cag/leaderboard:latest .
cd ../../race-administrator/target
docker buildx build -f ../src/main/docker/Dockerfile --platform linux/amd64,linux/arm64 --push  --tag 192.168.0.50:32000/cag/race-administrator:latest .
cd ../../react-racing
docker buildx build --platform linux/amd64,linux/arm64 --push  --tag 192.168.0.50:32000/cag/react-racing:latest .

