/*
 * Copyright (c) 2018, hiwepy (https://github.com/hiwepy).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.codahale.metrics.spring.boot.factory;

import org.springframework.util.StringUtils;

import com.codahale.metrics.spring.boot.property.KafkaReporterProperties;

import io.github.hengyunabc.metrics.KafkaReporter;
import kafka.producer.ProducerConfig;

public class KafkaReporterFactoryBean extends AbstractScheduledReporterFactoryBean<KafkaReporter,KafkaReporterProperties> {

	private ProducerConfig producerConfig;
	
	public KafkaReporterFactoryBean(KafkaReporterProperties properties) {
		super(properties);
	}

	@Override
	public Class<KafkaReporter> getObjectType() {
		return KafkaReporter.class;
	}

	@Override
	protected KafkaReporter createInstance(KafkaReporterProperties properties) {
		
		final KafkaReporter.Builder reporter = KafkaReporter.forRegistry(getMetricRegistry())
				.convertDurationsTo(properties.getDurationUnit())
				.convertRatesTo(properties.getRateUnit())
				.filter(getMetricFilter())
				.prefix(getPrefix());

		reporter.showSamples(properties.isShowSamples());

		if (StringUtils.hasText(properties.getIp())) {
			reporter.ip(properties.getIp());
		}
		
		if (StringUtils.hasText(properties.getHostName())) {
			reporter.hostName(properties.getHostName());
		}
		
		if (StringUtils.hasText(properties.getName())) {
			reporter.name(properties.getName());
		}
		
		if (StringUtils.hasText(properties.getTopic())) {
			reporter.topic(properties.getTopic());
		}
		

		if (getProducerConfig() != null) {
			reporter.config(getProducerConfig());
		}

		return reporter.build();
	}

	public ProducerConfig getProducerConfig() {
		return producerConfig;
	}

	public void setProducerConfig(ProducerConfig producerConfig) {
		this.producerConfig = producerConfig;
	}

}
