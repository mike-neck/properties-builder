PropertiesBuilder
===

[![Build Status](https://secure.travis-ci.org/mike-neck/properties-builder.png?branch=master)](https://travis-ci.org/mike-neck/properties-builder)

Properties-builder is an extension library for Grovy.
This library enable you to write properties with groovy's builder style.

Usage
---

For example if you want to write properties for logback, you can write as bellow.

```groovy
def builder = new PropertiesBuilder();
builder.configuration {
    appender (name : 'STDOUT', 'class' : 'ch.qos.logback.core.ConsoleAppender') {
        encoder {
            pattern '%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n'
        }
    }
    logger (name : 'chapters.configuration', level : 'INFO')
    root (level : 'debug') {
        'appender-ref' ref : 'STDOUT'
    }
}
Properties properties = builder.toProperties();
```

It looks like properties file written with Groovy with using `ConfigSlurper`.

But with this library

+ You can write properties without `=` character.
+ You can write properties of a same level properties with `Map`

This advantage enables you to writer gradle build script in the case of creating properties file.

### Example

On writing build script for Google App Engine with eclipse...

```groovy
apply plugin : 'eclipse'

buildscript {
    repositories {
        mavenCentral ()
    }
    dependencies {
        classpath 'org.mikeneck.groovy:properties-builder:0.5'
    }
}

eclipseJdt {
    doFirst {
        def builder = new org.mikeneck.groovy.PropertiesBuilder()

        builder.eclipse {
            preferences (version : 1)
        }
        builder.org {
            eclipse {
                jdt {
                    apt {
                        aptEnabled true
                        genSrcDir 'target/metamodel'
                        reconcileEnabled true
                        processorOptions {
                            '/persistenceXml' 'META-INF/another-directory/persistence.xml',
                            '/ormXml' ['orm1.xml', 'orm2.xml', 'orm3.xml'].collect {"META-INF/$it"}.join(',')
                            '/lazyXmlParsing' true
                        }
                    }
                }
            }
        }

        mkdir('.settings')
        file('.settings/org.eclipse.jdt.apt.core.prefs').write (builder.toString(), 'UTF-8')
    }
}
```

Install
---

This library will be available via maven central repository.

**pom.xml**

```xml
    <dependencies>
        <dependency>
            <groupId>org.mikeneck.groovy</groupId>
            <artifactId>properties-builder</artifactId>
            <version>0.5</version>
        </dependency>
    </dependencies>
```

**build.gradle**

```groovy
dependencies {
    compile 'org.mikeneck.groovy:properties-builder:0.5'
}
```

License
---

This library is delivered under _APL2.0_.

```
  Copyright 2013 Shinya Mochida a.k.a. @mike_neck

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
```

TODOs
---

+ creating a new level namespace on getting property from `PropertiesBuilder`

Developer
---

name : Shinya Mochida
twitter : @mike_neck

