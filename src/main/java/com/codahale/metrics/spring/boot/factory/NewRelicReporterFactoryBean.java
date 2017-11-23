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

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.spring.boot.property.NewRelicReporterProperties;
import com.palominolabs.metrics.newrelic.AllEnabledMetricAttributeFilter;
import com.palominolabs.metrics.newrelic.MetricAttributeFilter;
import com.palominolabs.metrics.newrelic.NewRelicReporter;

public class NewRelicReporterFactoryBean
		extends AbstractScheduledReporterFactoryBean<NewRelicReporter, NewRelicReporterProperties> {

	private static final Logger LOG = LoggerFactory.getLogger(NewRelicReporterFactoryBean.class);
	private static final String EMPTY_STRING = "";
	private MetricAttributeFilter attributeFilter;

	public NewRelicReporterFactoryBean(NewRelicReporterProperties properties) {
		super(properties);
	}

	@Override
	public Class<NewRelicReporter> getObjectType() {
		return NewRelicReporter.class;
	}

	@Override
	protected NewRelicReporter createInstance(NewRelicReporterProperties properties) throws Exception {

		String prefix = this.getProperty(getPrefix(), EMPTY_STRING);
		MetricFilter metricFilter = getMetricFilter();
		TimeUnit duration = properties.getDurationUnit();
		TimeUnit rateUnit = properties.getRateUnit();
		String name = this.getProperty(properties.getName(), "NewRelic reporter");
		MetricAttributeFilter attributeFilter = getAttributeFilter() != null ? getAttributeFilter()
				: new AllEnabledMetricAttributeFilter();

		LOG.debug("Creating instance of NewRelicReporter with name '{}', prefix '{}', rate unit '{}', duration '{}', filter '{}' and attribute filter '{}'",
				name, prefix, rateUnit, duration, metricFilter, attributeFilter.getClass().getSimpleName());

		return new NewRelicReporter(this.getMetricRegistry(), name, metricFilter, attributeFilter, rateUnit, duration,
				prefix);
	}

	public MetricAttributeFilter getAttributeFilter() {
		return attributeFilter;
	}

	public void setAttributeFilter(MetricAttributeFilter attributeFilter) {
		this.attributeFilter = attributeFilter;
	}

}
