#!/bin/sh
### env 설정 ###

### app 설정 ###
JAVA_OPTS="${JAVA_OPTS} -Xms${JAVA_HEAP_XMS} -Xmx${JAVA_HEAP_XMX}"
JAVA_OPTS="${JAVA_OPTS} -XX:MaxMetaspaceSize=256m"
# JAVA_OPTS="${JAVA_OPTS} ${JAVA_AGENT}"
JAVA_OPTS="${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom"
JAVA_OPTS="${JAVA_OPTS} -Dspring.backgroundpreinitializer.ignore=true"
JAVA_OPTS="${JAVA_OPTS} -Xlog:gc*=debug:file=/var/log/gc.log:time,level,tags"
JAVA_OPTS="${JAVA_OPTS} -Duser.timezone=Asia/Seoul"
JAVA_OPTS="${JAVA_OPTS} -Dopentracing.jaeger.enable-b3-propagation=true"
JAVA_OPTS="${JAVA_OPTS} -Dopentracing.jaeger.remote-controlled-sampler.host-port=http://localhost:5778/sampling"

### app 실행 ###
exec java ${JAVA_OPTS} -Dspring.profiles.active=${PROFILE} -jar /source001/boot.war
