/**
 * Copyright (C) 2012 Ryan W Tenney (ryan@10e.us)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.codahale.metrics.spring.boot.factory;

import org.slf4j.LoggerFactory;
import org.slf4j.MarkerFactory;
import org.springframework.util.StringUtils;

import com.codahale.metrics.Slf4jReporter;
import com.codahale.metrics.Slf4jReporter.LoggingLevel;
import com.codahale.metrics.spring.boot.property.Slf4jReporterProperties;

public class Slf4jReporterFactoryBean extends AbstractScheduledReporterFactoryBean<Slf4jReporter, Slf4jReporterProperties> {

	public Slf4jReporterFactoryBean(Slf4jReporterProperties properties) {
		super(properties);
	}
	
	@Override
	public Class<Slf4jReporter> getObjectType() {
		return Slf4jReporter.class;
	}

	@Override
	protected Slf4jReporter createInstance(Slf4jReporterProperties properties) {

		
		final Slf4jReporter.Builder reporter = Slf4jReporter.forRegistry(getMetricRegistry())
				.convertDurationsTo(properties.getDurationUnit())
				.convertRatesTo(properties.getRateUnit())
				.filter(getMetricFilter())
				.prefixedWith(getPrefix());

		if (StringUtils.hasText(properties.getMarker())) {
			reporter.markWith(MarkerFactory.getMarker(properties.getMarker()));
		}

		if (StringUtils.hasText(properties.getLogger())) {
			reporter.outputTo(LoggerFactory.getLogger(properties.getLogger()));
		}

		if (StringUtils.hasText(properties.getLevel())) {
			reporter.withLoggingLevel(getProperty(properties.getLevel(), LoggingLevel.class));
		}

		return reporter.build();
	}

}
