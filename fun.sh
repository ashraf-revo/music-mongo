#!/usr/bin/env bash

echo "1 for angular build"
echo "2 for run app"
echo "3 for push"
echo "4 for services"
echo "5 for run angular"
read value
if [ $value -eq 1 ]; then
    cd src/main/resources/music-static/
    ng build --target=production
    rm -rf ../static/*.map
    rm -rf ../static/*.gz
    cd -
    if [ -d "target/classes" ]; then
            if [ -d "target/classes/static" ]; then
                rm -rf target/classes/static
            fi
        cp -r src/main/resources/static target/classes/static
    fi
elif [ $value -eq 2 ]; then
    mvn spring-boot:run
elif [ $value -eq 3 ]; then
    mvn clean package -DskipTests=true
    cf push
    mvn clean
elif [ $value -eq 4 ]; then
    cf delete-service MongoDbmusic
    cf delete-service MySqlDbmusic
    cf create-service mLab sandbox MongoDbmusic
    cf create-service cleardb spark MySqlDbmusic
elif [ $value -eq 5 ]; then
    cd src/main/resources/music-static/
    ng serve
fi
