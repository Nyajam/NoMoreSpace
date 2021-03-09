# NoMoreSpace - NoMoreSpacePlease!
Aplicación de la práctica de Desarrollo de Aplicaciones Distribuidas de la URJC, curso 2020/2021: NoMoreSpacePlease!

Este proyecto busca la creación de una web de almacenamiento de ficheros de manera distribuida, sin contar con un (por ejemplo) servidor NFS centralizado. En su lugar tendrá "instancias" permanentes que almacenen estos ficheros y pueda escalar en función de los requisitos ademas de soportar tolerancia a fallos (replicación). Cabe destacar que esas instancias, aun no tenemos la certeza de qué serán finalmente, por el momento los llamaremos "Bloques".

# Entidades previstas para la aplicación:
- Usuario(s) -> Que emplean la aplicación.
- Fichero(s) -> Que pueden ser gestionados por los usuarios (ademas de atributos internos, como un fichero que puede estar en N fragmentos).
- Pool(s) -> Agrupación o relación entre bloques y ficheros (contiene el mapa de donde esta un fichero, en cuantos bloques está dividido y donde se encuentran estos).
- Bloque(s) -> Nodos, instancias...(pendiente de desarrollo) donde se encuentran físicamente los ficheros, si un fichero es más grande que un bloque, puede estar repartido entre varios.
- Panel(s) -> Representación de como es el árbol de directorios de un usuario (una carpeta es una separación visual de un árbol de directorios).

# Servicio interno:
- El servicio interno va a ser el encargado de calcular los bloques necesarios para almacenar un archivo. Una vez calculados los bloques que necesita los solicita al servidor.El servidor responde con la localización e identificador de los bloques.
- El servicio interno realiza la conexión con los distintos proveedores de almacenamiento que tienen esos bloques y transfiere los archivos.

# Documentación fase 2
-Enlace a la documentación de las vistas/navegación: https://drive.google.com/file/d/11NsNpEN8ks14z5lCZdXWOMwQ8ZS81uo-/view?usp=sharing
-Enlace al ER: https://drive.google.com/file/d/1tjbNDfGzgjJFue4d46mj1-iMvP5z9Z-s/view?usp=sharing
-Enlace al UML: https://drive.google.com/file/d/14bYvo8Qj4CmKj-Qrtxrs-vwaUmZhEIrH/view?usp=sharing

# Autores/Integrantes del proyecto
- Ildefonso Macarro Pueyo i.macarro.2016@alumnos.urjc.es
- Javier Lamparero López j.lamparero@alumnos.urjc.es
