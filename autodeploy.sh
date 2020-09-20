kill $(cat ./bin/shutdown.pid)
./gradlew bootJar
kill $(ps -aux | grep gradle |grep -v grep | awk '{print $2}')
sleep 5
nohup java -Xss256m -Xmx500m -jar ./build/libs/construction-0.0.1.jar &
echo > nohup.out