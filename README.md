# kometsales
Codigo fuente del proceso de carga de camiones

-- El fron-end esta consumiendo los servicios rest para la carga de archivo por el puerto 8080, para que sea tenido en cuenta al momento de desplegar los war. 
-- El scrip de base de datos no se adjunta porque al API esta configurada de tal forma que cree las tablas si no existen, solo es necesario contar con los mismos
	datos de base de datos, mirar la informacion que se detalla mas abajo
	
-- La configuracion de correo se debe modificar ya que en el repositorio quedaron datos de mentiras (email y pass)
-- En la carpeta docs, se encuentra una archivo de prueba para configurar el archivo de carga.
-- La estructura del archivo quedo de la siguiente manera:
	[CARPLATE,MODEL,REGISTRATION,COLOR,DATELOAD]



URL importantes

URL para acceder a la plicación web: http://localhost:8080/webApp/
URL para acceder a la api: http://localhost:8080/appApi/
URL para acceder a la documentacion: http://localhost:8080/appApi/swagger-ui.html

Configuración


Archivos de configuracion: resources/main/resources
application.properties

si se desea ejecutar como una aplicacion autocontenida como jar, puede modificar el puerto en
server.port=8080

La api esta configurado para crear la tabla si no existe, si se desea cambiar modificar
spring.jpa.hibernate.ddl-auto=update

Datos de acceso a datos
spring.datasource.url = jdbc:mysql://localhost:3306/appApi_dllo
spring.datasource.driver-class-name = com.mysql.jdbc.Driver
spring.datasource.username=appApi_dllo
spring.datasource.password=appApi_dllo

Configuracion para el manejo de inserts o update batch
spring.jpa.properties.hibernate.jdbc.batch_size = 50

Tamaño de carga de los archivos
spring.servlet.multipart.max-file-size=2048KB
spring.servlet.multipart.max-request-size=2048KB

encode de los textos
spring.messages.encoding=UTF-8
server.tomcat.uri-encoding=UTF-8
spring.http.encoding.charset=UTF-8

configuracion de envio de correos
spring.mail.host: smtp.gmail.com
spring.mail.port: 465
spring.mail.username: xxxxxx@gmail.com
spring.mail.password: xxxxxx
spring.mail.properties.mail.smtp.auth: true
spring.mail.properties.mail.smtp.starttls.enable: true
spring.mail.properties.mail.smtp.starttls.required: true
spring.mail.properties.mail.smtp.ssl.enable: true
spring.mail.test-connection: true
