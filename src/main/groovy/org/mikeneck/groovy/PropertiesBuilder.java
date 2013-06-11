package org.mikeneck.groovy;

import groovy.util.BuilderSupport;

import java.util.Map;
import java.util.Properties;
import java.util.Set;

import static org.mikeneck.groovy.PropertiesBuilderSupport.castToString;

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
public class PropertiesBuilder extends BuilderSupport {

    private final Properties properties;

    private final PropertiesBuilderSupport support;

    public PropertiesBuilder () {
        this(new Properties());
    }

    public Properties toProperties () {
        return properties;
    }

    public PropertiesBuilder(Properties properties) {
        this.properties = properties;
        this.support = new PropertiesBuilderSupport();
    }

    @Override
    protected void setParent(Object parent, Object child) {
    }

    @Override
    protected Object createNode(Object name) {
        support.addNamespace(name);
        return name;
    }

    @Override
    protected Object createNode(Object name, Object value) {
        createNode(name);
        String v = castToString(value);
        properties.setProperty(support.currentPropertyName(), v);
        return name;
    }

    @Override
    protected Object createNode(Object name, Map attributes) {
        createNode(name);
        createNode(attributes);
        return name;
    }

    @Override
    protected Object createNode(Object name, Map attributes, Object value) {
        createNode(name, value);
        createNode(attributes);
        return name;
    }

    @Override
    protected void nodeCompleted(Object parent, Object node) {
        support.removeNamespace();
    }

    void createNode (Map attributes) {
        String baseName = support.currentPropertyName();
        Set keys = attributes.keySet();
        for (Object key : keys) {
            String v = castToString(attributes.get(key));
            String k = baseName + "." + key.toString();
            properties.setProperty(k, v);
        }
    }
}
