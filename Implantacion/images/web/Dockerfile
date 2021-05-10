#Imagen aplicación web
#
FROM centos
RUN dnf install -y java-11-openjdk
COPY NoMoreSpacePlease.jar /opt/
EXPOSE 8443
ARG SERVICIOINTERNO
CMD ["java", "-jar", "/opt/NoMoreSpacePlease.jar", "--SERVICIOINTERNO"]