# Microservicios para depósito

Este proyecto hace una simulación de comunicación entre microservicios con Feign para el depósito a un cliente. Los detalles de implementación están en el archivo `ATMDeposit.pdf`.

- Hacer el depósito.

## Pre-Requisitos
1. Java 8
2. Plugin Lombok (en su IDE Intellij IDEA o STS)
3. Maven

## Ejecutar servicio API RESTful de manera local

 1. Clonar el repositorio de manera local.

 2. Ubicarse en la carpeta raiz de cada proyecto y ejecutar un `clean` e `install` con maven, ya sea por comandos o desde un IDE:
 
 - bootcamp-accounts
 - bootcamp-ATMDeposit
 - bootcamp-cards
 - bootcamp-fingerprints
 - bootcamp-persons
 - bootcamp-reniec

 Por comandos sería:

```
mvn clean install
```

 3. Inicializar cada servicio, esto varía según el IDE a utilizar. Por comandos de maven sería:
 
```
mvn spring-boot:run
```

**Nota: Los servicios usarán los puertos del 8080 al 8085.**

## Ejecución de Pruebas

### Postman

Se ha creado un `collection` en postman para facilitar las pruebas. Dentro del carpeta `files` se encuentra un archvio `json` para importar en Postman. Cada request obedece a una funcionalidad:

- Hacer el depósito.
