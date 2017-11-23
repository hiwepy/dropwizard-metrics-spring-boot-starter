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

import static java.util.Arrays.asList;

import java.io.IOException;
import java.util.EnumSet;
import java.util.Locale;

import org.coursera.metrics.datadog.DatadogReporter;
import org.coursera.metrics.datadog.DatadogReporter.Expansion;
import org.coursera.metrics.datadog.DynamicTagsCallback;
import org.coursera.metrics.datadog.MetricNameFormatter;
import org.coursera.metrics.datadog.transport.HttpTransport;
import org.coursera.metrics.datadog.transport.Transport;
import org.coursera.metrics.datadog.transport.UdpTransport;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.codahale.metrics.spring.boot.property.DatadogReporterProperties;
import com.codahale.metrics.spring.boot.property.DatadogReporterProperties.TransportEnum;

public class DatadogReporterFactoryBean extends AbstractScheduledReporterFactoryBean<DatadogReporter, DatadogReporterProperties> {

	private DynamicTagsCallback tagsCallback;
	private MetricNameFormatter formatter;
	
	public DatadogReporterFactoryBean(DatadogReporterProperties properties) {
		super(properties);
	}

	@Override
	public Class<DatadogReporter> getObjectType() {
		return DatadogReporter.class;
	}

	@Override
	protected DatadogReporter createInstance(DatadogReporterProperties properties) {
		
		final DatadogReporter.Builder reporter = DatadogReporter.forRegistry(getMetricRegistry())
				.convertDurationsTo(properties.getDurationUnit())
				.convertRatesTo(properties.getRateUnit())
				.filter(getMetricFilter())
				.withPrefix(getPrefix());
		
		final Transport transport;
		if ( TransportEnum.HTTP.equals(properties.getTransport())) {
			HttpTransport.Builder builder = new HttpTransport.Builder();
			builder.withApiKey(properties.getApiKey());
			if (StringUtils.hasText(properties.getConnectTimeout())) {
				builder.withConnectTimeout(getProperty(properties.getConnectTimeout(), Integer.class));
			}
			if (StringUtils.hasText(properties.getSocketTimeout())) {
				builder.withSocketTimeout(getProperty(properties.getSocketTimeout(), Integer.class));
			}
			transport = builder.build();
		} else if (TransportEnum.UDP.equals(properties.getTransport()) || TransportEnum.STATSD.equals(properties.getTransport())) {
			UdpTransport.Builder builder = new UdpTransport.Builder();
			if (StringUtils.hasText(properties.getStatsdHost())) {
				builder.withStatsdHost(properties.getStatsdHost());
			}
			if (StringUtils.hasText(properties.getStatsdPort())) {
				builder.withPort(getProperty(properties.getStatsdPort(), Integer.class));
			}
			if (StringUtils.hasText(properties.getStatsdPrefix())) {
				builder.withPrefix(properties.getStatsdPrefix());
			}
			transport = builder.build();
		} else {
			throw new IllegalArgumentException("Invalid Datadog Transport: " + properties.getTransport().get());
		}
		reporter.withTransport(transport);

		if (StringUtils.hasText(properties.getTags())) {
			reporter.withTags(asList(StringUtils.tokenizeToStringArray(properties.getTags(), ",", true, true)));
		}

		if (StringUtils.hasText(properties.getHost())) {
			reporter.withHost(properties.getHost());
		}
		else if (properties.isUseEc2Host()) {
			try {
				reporter.withEC2Host();
			}
			catch (IOException e) {
				throw new IllegalStateException("DatadogReporter.Builder.withEC2Host threw an exception", e);
			}
		}

		if (StringUtils.hasText(properties.getExpansions())) {
			String configString = properties.getExpansions().trim().toUpperCase(Locale.ENGLISH);
			final EnumSet<Expansion> expansions;
			if ("ALL".equals(configString)) {
				expansions = Expansion.ALL;
			}
			else {
				expansions = EnumSet.noneOf(Expansion.class);
				for (String expandedMetricStr : StringUtils.tokenizeToStringArray(configString, ",", true, true)) {
					expansions.add(Expansion.valueOf(expandedMetricStr.replace(' ', '_')));
				}
			}
			reporter.withExpansions(expansions);
		}

		if (!ObjectUtils.isEmpty(getTagsCallback())) {
			reporter.withDynamicTagCallback(getTagsCallback());
		}
		
		if (!ObjectUtils.isEmpty(getFormatter())) {
			reporter.withMetricNameFormatter(getFormatter());
		}

		if (!ObjectUtils.isEmpty(getClock())) {
			reporter.withClock(getClock());
		}
		
		return reporter.build();
	}

	public DynamicTagsCallback getTagsCallback() {
		return tagsCallback;
	}

	public void setTagsCallback(DynamicTagsCallback tagsCallback) {
		this.tagsCallback = tagsCallback;
	}

	public MetricNameFormatter getFormatter() {
		return formatter;
	}

	public void setFormatter(MetricNameFormatter formatter) {
		this.formatter = formatter;
	}
	
}
