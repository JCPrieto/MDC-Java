# Changelog

## 2.0.4

- Integración del análisis de SonarQube en el workflow de releases sin bloquear la publicación si el análisis falla.
- Los tests unitarios deben finalizar correctamente antes de generar una release.
- Actualización de `maven-jar-plugin` a `3.5.1`.
- Corrección del nombre del proyecto en el encabezado de la documentación.

## 2.0.3

- Refactor en `Logger`: cierre correcto de recursos con `try-with-resources`.
- Actualización de `maven-compiler-plugin` a `3.15.0`.

## 2.0.2

- Actualización de seguridad.
- Migración a Java 21.

## 2.0.1

- Actualización de seguridad.

## 2.0.0

- Migración a Java 11.
- Refactorización sencilla del código.
- Integración con Maven.
- Look & Feel del sistema operativo en el que se ejecuta.
