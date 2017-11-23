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

import java.util.UUID;

import org.springframework.util.StringUtils;

import com.codahale.metrics.ganglia.GangliaReporter;
import com.codahale.metrics.spring.boot.property.GangliaReporterProperties;

import info.ganglia.gmetric4j.gmetric.GMetric;

public class GangliaReporterFactoryBean extends AbstractScheduledReporterFactoryBean<GangliaReporter, GangliaReporterProperties> {

	public GangliaReporterFactoryBean(GangliaReporterProperties properties) {
		super(properties);
	}

	@Override
	public Class<GangliaReporter> getObjectType() {
		return GangliaReporter.class;
	}

	@Override
	protected GangliaReporter createInstance(GangliaReporterProperties properties) throws Exception {
		
		final GangliaReporter.Builder reporter = GangliaReporter.forRegistry(getMetricRegistry())
				.convertDurationsTo(properties.getDurationUnit())
				.convertRatesTo(properties.getRateUnit())
				.filter(getMetricFilter())
				.prefixedWith(getPrefix());

		if (properties.getDmax() > -1) {
			reporter.withDMax(properties.getDmax());
		}

		if (properties.getTmax() > -1) {
			reporter.withTMax(properties.getTmax());
		}

		UUID uuid = StringUtils.hasText(properties.getUuid()) ? java.util.UUID.fromString(properties.getUuid()) : null;
						
		final GMetric gMetric = new GMetric(properties.getGroup(), Integer.valueOf(properties.getPort()),
				properties.getUdpMode(), Integer.valueOf(properties.getTtl()), 
				!StringUtils.hasText(properties.getProtocol()) || properties.getProtocol().contains("3.1"), 
				
				uuid, properties.getSpoof());

		return reporter.build(gMetric);
	}

}
