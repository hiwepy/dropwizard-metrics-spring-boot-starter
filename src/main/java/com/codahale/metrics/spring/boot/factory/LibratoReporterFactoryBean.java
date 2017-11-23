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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.codahale.metrics.spring.boot.factory.support.MetricPrefixSupplier;
import com.codahale.metrics.spring.boot.property.LibratoReporterProperties;
import com.librato.metrics.reporter.ExpandedMetric;
import com.librato.metrics.reporter.ILibratoClientFactory;
import com.librato.metrics.reporter.LibratoReporter;
import com.librato.metrics.reporter.MetricExpansionConfig;
import com.librato.metrics.reporter.ReporterBuilder;

public class LibratoReporterFactoryBean extends AbstractScheduledReporterFactoryBean<LibratoReporter,LibratoReporterProperties> {

	private MetricPrefixSupplier sourceSupplier;
	private MetricExpansionConfig expansionConfig;
	private ILibratoClientFactory libratoClientFactory;
	
	public LibratoReporterFactoryBean(LibratoReporterProperties properties) {
		super(properties);
	}

	@Override
	public Class<LibratoReporter> getObjectType() {
		return LibratoReporter.class;
	}

	@Override
	protected LibratoReporter createInstance(LibratoReporterProperties properties) {
		final String email = properties.getEmail();
		final String token = properties.getToken();

		final ReporterBuilder reporter = LibratoReporter.builder(getMetricRegistry(), email, token)
				.setDurationUnit(properties.getDurationUnit())
				.setRateUnit(properties.getRateUnit())
				.setFilter(getMetricFilter())
				.setPrefix(getPrefix())
				.setDeleteIdleStats(properties.isDeleteIdleStats())
				.setOmitComplexGauges(properties.isOmitComplexGauges())
				.setEnableLegacy(properties.isEnableLegacy())
				.setEnableTagging(properties.isEnableTagging());

		if (StringUtils.hasText(properties.getUrl())) {
			reporter.setUrl(properties.getUrl());
		}
		
		if (StringUtils.hasText(properties.getName())) {
			reporter.setName(properties.getName());
		}
		
		if (StringUtils.hasText(properties.getExpansionConfig())) {
			String configString = properties.getExpansionConfig().trim().toUpperCase(Locale.ENGLISH);
			final MetricExpansionConfig config;
			if ("ALL".equals(configString)) {
				config = MetricExpansionConfig.ALL;
			}
			else {
				Set<ExpandedMetric> set = new HashSet<ExpandedMetric>();
				String[] expandedMetricStrs = StringUtils.tokenizeToStringArray(configString, ",", true, true);
				for (String expandedMetricStr : expandedMetricStrs) {
					set.add(ExpandedMetric.valueOf(expandedMetricStr));
				}
				config = new MetricExpansionConfig(set);
			}
			reporter.setExpansionConfig(config);
		}
		else if (getExpansionConfig() != null) {
			reporter.setExpansionConfig(getExpansionConfig());
		}
		
		if (StringUtils.hasText(properties.getConnectTimeout())) {
			reporter.setConnectTimeout(convertDurationString(properties.getConnectTimeout()), TimeUnit.NANOSECONDS);
		}
		
		if (StringUtils.hasText(properties.getReadTimeout())) {
			reporter.setReadTimeout(convertDurationString(properties.getReadTimeout()), TimeUnit.NANOSECONDS);
		}
		
		final String source;
		if (sourceSupplier != null) {
			source = sourceSupplier.getPrefix();
		}
		else {
			source = properties.getSource();
		}
		if (StringUtils.hasText(source)) {
			reporter.setSource(source);
		}
		
		if (StringUtils.hasText(properties.getSourceRegex())) {
			reporter.setSourceRegex(properties.getSourceRegex());
		}

		if (StringUtils.hasText(properties.getPrefixDelimiter())) {
			reporter.setPrefixDelimiter(properties.getPrefixDelimiter());
		}
		
		if(!CollectionUtils.isEmpty(properties.getTags())) {
			Iterator<Entry<String, String>> ite = properties.getTags().entrySet().iterator();
			while (ite.hasNext()) {
				Entry<String, String> entry = ite.next();
				reporter.addTag(entry.getKey(), entry.getValue());
			}
		}

		return reporter.build();
	}

	public MetricPrefixSupplier getSourceSupplier() {
		return sourceSupplier;
	}

	public void setSourceSupplier(MetricPrefixSupplier sourceSupplier) {
		this.sourceSupplier = sourceSupplier;
	}

	public MetricExpansionConfig getExpansionConfig() {
		return expansionConfig;
	}

	public void setExpansionConfig(MetricExpansionConfig expansionConfig) {
		this.expansionConfig = expansionConfig;
	}

	public ILibratoClientFactory getLibratoClientFactory() {
		return libratoClientFactory;
	}

	public void setLibratoClientFactory(ILibratoClientFactory libratoClientFactory) {
		this.libratoClientFactory = libratoClientFactory;
	}
 
	
	
}
