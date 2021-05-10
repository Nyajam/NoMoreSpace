#Imagen Servicio interno
#
FROM centos
RUN dnf install -y java-11-openjdk
RUN mkdir /opt/DADfiles
COPY NoMoreSpacePleaseServicioInterno.jar /opt/
CMD ["java", "-jar", "/opt/NoMoreSpacePleaseServicioInterno.jar"]