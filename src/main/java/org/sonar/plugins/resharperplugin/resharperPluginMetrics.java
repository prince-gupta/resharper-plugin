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

import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Metric;
import org.sonar.api.measures.Metrics;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("rawtypes")
public final class resharperPluginMetrics implements Metrics {

    // Modify the following metrics (variable name, attributes) to fit your plugin needs

    // This metric is used by Sensor
    public static final String IT_SUGGESTION = "it_suggestions";
	public static final Metric SUGGESTION = new Metric.Builder(IT_SUGGESTION, "SUGGESTION", Metric.ValueType.INT)
            .setDescription("Code Suggestion")
            .setDirection(Metric.DIRECTION_WORST)
            .setQualitative(true)
            .setDomain(CoreMetrics.DOMAIN_GENERAL)
            .create();

    public static final String IT_WARNING = "it_warning";
    public static final Metric WARNING = new Metric.Builder(IT_WARNING, "WARNING", Metric.ValueType.INT)
            .setDescription("Code WARNING")
            .setDirection(Metric.DIRECTION_WORST)
            .setQualitative(true)
            .setDomain(CoreMetrics.DOMAIN_GENERAL)
            .create();

    public static final String IT_DNS = "it_dns";
    public static final Metric DO_NOT_SHOW = new Metric.Builder(IT_DNS, "DO NOT SHOW", Metric.ValueType.INT)
            .setDescription("Code DNS")
            .setDirection(Metric.DIRECTION_WORST)
            .setQualitative(true)
            .setDomain(CoreMetrics.DOMAIN_GENERAL)
            .create();

    public static final String IT_ERROR = "it_error";
    public static final Metric ERROR = new Metric.Builder(IT_ERROR, "ERROR", Metric.ValueType.INT)
            .setDescription("Code Error")
            .setDirection(Metric.DIRECTION_WORST)
            .setQualitative(true)
            .setDomain(CoreMetrics.DOMAIN_GENERAL)
            .create();

    public static final String IT_HINT = "it_hint";
    public static final Metric HINT = new Metric.Builder(IT_HINT, "HINT", Metric.ValueType.INT)
            .setDescription("Code hint")
            .setDirection(Metric.DIRECTION_WORST)
            .setQualitative(true)
            .setDomain(CoreMetrics.DOMAIN_GENERAL)
            .create();

    // getMetrics() method is defined in the Metrics interface and is used by
    // Sonar to retrieve the list of new metrics
    public List<Metric> getMetrics() {
        return Arrays.asList(SUGGESTION, WARNING, DO_NOT_SHOW, ERROR, HINT);
    }
}
