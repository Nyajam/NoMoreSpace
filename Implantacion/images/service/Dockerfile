#Imagen Servicio interno
#
FROM centos
RUN dnf install -y java-11-openjdk
COPY NoMoreSpacePleaseServicioInterno.jar /opt/
EXPOSE 443
CMD ["java", "-jar", "/opt/NoMoreSpacePleaseServicioInterno.jar"]