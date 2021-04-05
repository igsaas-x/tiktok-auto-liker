git pull
kill $(cat ./bin/shutdown.pid)
./gradlew bootJar
kill $(ps -aux | grep gradle |grep -v grep | awk '{print $2}')
sleep 5
nohup java -XX:+UseParallelGC -XX:InitialRAMPercentage=70 -XX:MaxRAMPercentage=70 -jar ./build/libs/construction-0.0.1.jar &
echo > nohup.out