package org.mikeneck.groovy

import spock.lang.Specification
import spock.lang.Unroll

/**
 * Copyright 2013 Shinya Mochida a.k.a. @mike_neck
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
class PropertiesBuilderTest extends Specification {

    def properties

    def setup () {
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
        properties = builder.toProperties();
    }

    @Unroll
    def 'key : #key -> value : #value' () {
        expect :
        properties[key] == value

        where  :
        key                                      | value
        'configuration.appender.name'            | 'STDOUT'
        'configuration.appender.class'           | 'ch.qos.logback.core.ConsoleAppender'
        'configuration.appender.encoder.pattern' | '%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n'
        'configuration.logger.name'              | 'chapters.configuration'
        'configuration.logger.level'             | 'INFO'
        'configuration.root.level'               | 'debug'
        'configuration.root.appender-ref.ref'    | 'STDOUT'
    }
}
