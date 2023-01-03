# Desafio

### Crear ejecutable de la aplicación del back end
Verificar si tiene en su ambiente la versión 11 de Java, si no, saltarse este paso y continuar con ¨Levantar el Ambiente¨

```sh
$ cd desafio-back && ./gradlew clean build && cd ..
```

### Levantar el Ambiente

```sh
$ docker-compose up --build
```

### Cargar Data (Es necesario tener mongo instalado para ocupar las tools)
```sh
$ make database-provision
```
### Test API Swagger

http://localhost:8080/swagger-ui.html

### Test front

http://localhost:80

ENJOY!!