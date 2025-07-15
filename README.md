# MDC-Java #

Esta aplicación la implementé en los primera años de la carrera y la subo a Github para que no se pierda.

La funcionalidad es sencilla: Buscar el máximo común denominador de 2 números enteros.

La motivación de hacer esta aplicación era por la necesidad de realizar muchas veces este tipo de calculo en las asignaturas de Matematicas Discretas en el primer curso de la universidad. Así mataba 2 pajaros de un tiro: Ahorrarte calculos tontos y aprender un poco de programación.

Le he hecho 4 retoques esteticos para que se parezca a otras aplicaciones que he implementado mas recientemente, refactorizado un poco el código, integrado con Maven y subido la versión de compilación a Java 11 ya que en su momento la implementé en Java 5. 
En cualquier caso, en Java 8 debería de compilar ya que lo unico que he actualizado al refactorizar es el uso de 4 o 5 lambdas.

### Requisitos ###

* Java 11
* LibNotify (Para las notificaciones en Linux)

### Ejecución ###

* Windows:
    * Ejecutar MCD Java.bat

* Linux:
    * Ejecutar MCD Java.sh

### Tecnologías utilizadas ###

* Librerias:
    * Apache Commons Lang http://commons.apache.org/proper/commons-lang

### Changelog ###

* 2.0.1
  * Actualización de seguridad.

* 2.0.0
    * Migracion a Java 11.
    * Refactorización sencilla del código. 
    * Integracion con Maven.
    * Look & Feel del sistema operativo en el que corrá.

### Licencia ### 

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
