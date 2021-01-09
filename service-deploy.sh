source ~/.bash_profile
cd /home/jenkinszhang1998
cd online-video
git pull
mvn install
cd eureka/
docker build -t online-video-eureka .
cd ../
cd system
docker build -t online-video-system .
cd ../
cd gateway/
docker build -t online-video-gateway .
cd ../
cd file
docker build -t online-video-file .
cd ../
cd business/
docker build -t online-video-business .
cd ../
docker-compose -f docker-compose-production.yml  up -d
docker rmi $(docker images | grep "none")