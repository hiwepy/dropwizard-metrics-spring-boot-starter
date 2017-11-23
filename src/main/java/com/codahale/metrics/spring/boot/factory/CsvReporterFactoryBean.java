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

import java.io.File;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.codahale.metrics.CsvReporter;
import com.codahale.metrics.spring.boot.property.CsvReporterProperties;

public class CsvReporterFactoryBean extends AbstractScheduledReporterFactoryBean<CsvReporter,CsvReporterProperties> {

	public CsvReporterFactoryBean(CsvReporterProperties properties) {
		super(properties);
	}

	@Override
	public Class<CsvReporter> getObjectType() {
		return CsvReporter.class;
	}
	
	@Override
	protected CsvReporter createInstance(CsvReporterProperties properties) {
		
		final CsvReporter.Builder reporter = CsvReporter.forRegistry(getMetricRegistry())
				.convertDurationsTo(properties.getDurationUnit())
				.convertRatesTo(properties.getRateUnit())
				.filter(getMetricFilter());

		if (!ObjectUtils.isEmpty(getClock())) {
			reporter.withClock(getClock());
		}

		if (StringUtils.hasText(properties.getLocale())) {
			reporter.formatFor(parseLocale(properties.getLocale()));
		}

		File dir = new File(properties.getDirectory());
		if (!dir.mkdirs() && !dir.isDirectory()) {
			throw new IllegalArgumentException("Directory doesn't exist or couldn't be created");
		}

		return reporter.build(dir);
	}
	
}
