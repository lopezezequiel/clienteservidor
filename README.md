# Acerca del proyecto
Este es el trabajo integrador de la materia Cliente-Servidor. Solo tiene fines académicos, no esta pensado para un entorno de producción. Puede ser útil para ver las diferentes capas de abstracción de un proyecto java, como trabajar con spring, crear servicios rest, consumirlos desde el browser, usar raml, configurar la aplicación, busquedas full-text, etc 


TODO List
=========


- [ ] RAML
- [ ] Mejorar busquedas Full Text
- [ ] Agregar Excepciones faltantes
- [ ] Eliminar archivos temporales
- [ ] Agregar filtros
- [ ] Refactorizar, Refactorizar y Refactorizar



Instalar Github Flavored Markdown
=================================

Para poder visualizar este u otros archivos markdown correctamente en eclipse
es necesario instalar un plugin.

**Instalar**

1. Help > Eclipse Marketplace
2. Buscar markdown
3. Seleccionar Github Flavored Markdown
4. Instalar

**Ver un archivo markdown con GFM**

1. Click derecho sobre el archivo
2. Show in GFM view


Instalar Java 8 con apt-get
===========================

http://www.webupd8.org/2014/03/how-to-install-oracle-java-8-in-debian.html

Configurar Java 8 en STS
========================

**Agregar JRE 8**

1. Window > Preferences > Java > Installed JREs
2. Si no esta java-8-oracle => Add > Standard VM > Next > JRE Home = $PATHTOJAVA8(en Debian /usr/lib/jvm/java-8-oracle)
3. Seleccionar java-8-oracle

**Seleccionar el JRE para el proyecto**

1. Click derecho sobre JRE System Library > Properties
2. En Execution Enviroment elegir Java 8(1.8)

**Seleccionar el compilador**

Window > Preferences > Java > Compiler > Compiler compliance level = 1.8

Instalar raml2html
==================

**Instalamos nodejs y npm**
```
sudo apt-get install nodejs nodejs-dev npm
```

**Actualizamos nodejs**
```
sudo npm cache clean -f
sudo npm install -g n
sudo n stable
```
**Agregamos node al $PATH creando un symlink**
```
sudo ln -sf /usr/local/n/versions/node/<VERSION>/bin/node  /usr/bin/node
```

**Instalamos raml2html con npm**
```
sudo npm install -g raml2html
```

Instalar MySQL con apt-get
==========================

```
sudo apt-get install mysql-server
```

Crear el usuario en la base de datos
====================================

```
mysql -u root -p
```
```sql
GRANT ALL ON preciosclaros.* TO apppreciosclaros@localhost IDENTIFIED BY 'tjZDOJcRNYkKHPGRxtxC';
```

Construir el proyecto con Maven
===============================

Click sobre el proyecto > Run As > Maven install

**¿Que hace esto?**

1. Genera api.html a partir de api.raml
2. Crea la base de datos y carga datos de prueba



Ver linea de los 80 caracteres
==============================

Window > Preferences > General > Editors > Text Editors > Show Print Margin = true

Insertar espacios con el tabulador en archivos de texto
=======================================================

Window->Preferences->Editors->Text Editors->Insert spaces for tabs = true

Requerimientos y Recomendaciones JPA 2.0
========================================
- The entity class must have a no-arg constructor. It may have other constructors as well. The no-arg constructor must be public or protected.
- The entity class must a be top-level class. An enum or interface must not be designated as an entity.
- The entity class must not be final. No methods or persistent instance variables of the entity class may be final.
- If an entity instance is to be passed by value as a detached object (e.g., through a remote interface), the entity class must implement the Serializable interface.
- Both abstract and concrete classes can be entities. Entities may extend non-entity classes as well as entity classes, and non-entity classes may extend entity classes.


Enlaces útiles
================

*RAML*
- https://github.com/raml-org/raml-spec/blob/master/versions/raml-10/raml-10.md/
- http://raml.org/developers/raml-100-tutorial
- http://raml.org/developers/raml-200-tutorial
- https://www.sitepoint.com/raml-restful-api-modeling-language/


*Code Conventions*
- http://www.oracle.com/technetwork/java/codeconvtoc-136057.html

*Constraints*
- https://docs.jboss.org/hibernate/validator/4.3/reference/en-US/html/validator-customconstraints.html

*Json Serialization*
- https://github.com/FasterXML/jackson-annotations/wiki/Jackson-Annotations
