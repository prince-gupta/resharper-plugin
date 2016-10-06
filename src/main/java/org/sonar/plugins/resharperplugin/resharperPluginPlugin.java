/*
 * resharperPlugin
 * Copyright (C) 2016 xebia
 * dev@sonar.codehaus.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.plugins.resharperplugin;

import com.google.common.collect.ImmutableList;
import org.sonar.api.config.PropertyDefinition;
import org.sonar.api.resources.Qualifiers;
import org.sonar.plugins.resharperplugin.batch.resharperPluginSensor;
import org.sonar.plugins.resharperplugin.ui.resharperPluginWidget;
import org.sonar.api.Properties;
import org.sonar.api.Property;
import org.sonar.api.SonarPlugin;

import java.util.List;

/**
 * This class is the entry point for all extensions
 */
@Properties({
        @Property(key = resharperPluginPlugin.REPORTS_PATH, name = "Plugin Property", description = "A property for the plugin")})
public final class resharperPluginPlugin extends SonarPlugin {

    // modify the following property (variable name, value) to fit your plugin
    // needs
    public static final String PROPERTY_PREFIX = "sonar.resharperplugin.";

    public static final String REPORTS_PATH = PROPERTY_PREFIX + "reportsPath";

    // This is where you're going to declare all your Sonar extensions
    @SuppressWarnings({"rawtypes"})
    public List getExtensions() {
        return ImmutableList.of(
                PropertyDefinition.builder(REPORTS_PATH)
                        .name("JSIntegrationTestDriver output folder")
                        .description("Folder where JSIntegrationTestDriver test reports are located.")
                        .onQualifiers(Qualifiers.MODULE, Qualifiers.PROJECT)
                        .build(),
                resharperPluginMetrics.class,
                resharperPluginSensor.class,
                resharperPluginWidget.class);
    }
}