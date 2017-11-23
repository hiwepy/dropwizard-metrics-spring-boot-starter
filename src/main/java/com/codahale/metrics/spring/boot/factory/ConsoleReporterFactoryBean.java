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

import java.util.TimeZone;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.spring.boot.property.ConsoleReporterProperties;

public class ConsoleReporterFactoryBean extends AbstractScheduledReporterFactoryBean<ConsoleReporter,ConsoleReporterProperties> {

	public ConsoleReporterFactoryBean(ConsoleReporterProperties properties) {
		super(properties);
	}

	@Override
	public Class<ConsoleReporter> getObjectType() {
		return ConsoleReporter.class;
	}

	@Override
	protected ConsoleReporter createInstance(ConsoleReporterProperties properties) {
		
		final ConsoleReporter.Builder reporter = ConsoleReporter.forRegistry(getMetricRegistry())
				.convertDurationsTo(properties.getDurationUnit())
				.convertRatesTo(properties.getRateUnit())
				.filter(getMetricFilter())
				.outputTo(properties.getOutput().get());

		if (!ObjectUtils.isEmpty(getClock())) {
			reporter.withClock(getClock());
		}

		if (StringUtils.hasText(properties.getLocale())) {
			reporter.formattedFor(parseLocale(properties.getLocale()));
		}
		
		if (StringUtils.hasText(properties.getTimeZone())) {
			reporter.formattedFor(TimeZone.getTimeZone(properties.getTimeZone()));
		}

		return reporter.build();
	}

}
