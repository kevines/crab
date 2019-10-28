#!/bin/bash
echo "开始上传"
BCSWORFSPACE=/e/ganjie/item_wentuo
cd $BCSWORFSPACE
git pull
git add .
git commit -m "再次更新"
git push

# ps x|grep ganjie-bcs-app-1.0.jar|grep -v grep |awk '{print $1}'| xargs kill -9
# nohup java -server -Xmx256m -Xms256m -DlogLevel=info -Dspring.profiles.active=testlinux -Dserver.enableLoginDevice=false
#  -Dserver.enableAutoUpdate=false   -jar /home/ganjie/workspace/ganjie/ganjie-bcs-app-1.0.jar > /home/ganjie/logs/ganjie-bcs-app.log 2>&1 &
