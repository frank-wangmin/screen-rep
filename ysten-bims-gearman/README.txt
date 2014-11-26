打包命令
mvn package

在target下生成可执行程序ysten-bims-gearman.jar

定义脚本执行
%JDK1.7%/bin/java -jar -Xms256m -Xmx512m ysten-bims-gearman.jar -h host -p port -redisList redisHost1:redisPort1,redisHost2:redisPort2


运行环境必须是JDK7.0, host为Gearman server地址（默认192.168.2.207，需要运行时指定），port为Gearman server端口地址（默认4730，一般无需修改）

mysql gearman trigger
sql/gearman_trigger.sql
