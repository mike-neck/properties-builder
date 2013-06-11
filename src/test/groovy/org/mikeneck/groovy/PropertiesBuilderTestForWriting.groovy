package org.mikeneck.groovy

import org.junit.Before
import org.junit.Test

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
class PropertiesBuilderTestForWriting {

    def builder

    @Before
    void writerTest () {
        builder = new PropertiesBuilder()
        builder.settings {
            lang 'java'
            groovy (version : '2.1.3')
            'git-hub' {
                repository {
                    url 'git@github.com:mike-neck/properties-builder.git'
                }
                user 'mike-neck'
            }
        }
    }

    @Test
    void testWriter() {
        def writer = new StringWriter()
        builder.write(writer)
        def properties = writer.toString()
        def list = []
        properties.eachLine {list << it}
        assert list.contains('settings.lang=java')
        assert list.contains('settings.git-hub.repository.url=git@github.com:mike-neck/properties-builder.git')
        assert list.contains('settings.groovy.version=2.1.3')
        assert list.contains('settings.git-hub.user=mike-neck')
    }
}
