FROM 870289670239.dkr.ecr.ap-northeast-2.amazonaws.com/aip-devops-all/amzn-openjdk:latest
ENV LC_ALL=C.UTF-8

RUN mkdir deploy
COPY build/libs/yogig-ecard-*-SNAPSHOT.war /source001/boot.war
RUN chmod +x source001/boot.war
COPY docker-entrypoint.sh .

RUN chown -R nobody:nobody source001/
RUN chown -R nobody:nobody docker-entrypoint.sh
RUN chmod 700 source001/
RUN chmod 700 docker-entrypoint.sh

USER nobody

ENTRYPOINT ["./docker-entrypoint.sh"]
