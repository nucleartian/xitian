## Custom Dockerfile
FROM openjdk:8
RUN echo '这是一个本地构建的springboot网站镜像' 
MAINTAINER wuxitian@foxmail.com

##将当前目录的jar拷贝至容器
ADD xitian-0.0.1-SNAPSHOT.jar  dockerTest.jar 
EXPOSE 5005
ENTRYPOINT ["java","-jar","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005","/dockerTest.jar"]
CMD java -jar dockerTest.jar 