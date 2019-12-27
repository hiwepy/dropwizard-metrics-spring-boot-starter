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

import com.codahale.metrics.spring.boot.property.ZabbixReporterProperties;

import io.github.hengyunabc.metrics.ZabbixReporter;
import io.github.hengyunabc.zabbix.sender.ZabbixSender;

public class ZabbixReporterFactoryBean
		extends AbstractScheduledReporterFactoryBean<ZabbixReporter, ZabbixReporterProperties> {

	public ZabbixReporterFactoryBean(ZabbixReporterProperties properties) {
		super(properties);
	}

	@Override
	public Class<ZabbixReporter> getObjectType() {
		return ZabbixReporter.class;
	}

	@Override
	protected ZabbixReporter createInstance(ZabbixReporterProperties properties) {

		final ZabbixReporter.Builder reporter = ZabbixReporter.forRegistry(getMetricRegistry())
				.convertDurationsTo(properties.getDurationUnit()).convertRatesTo(properties.getRateUnit())
				.filter(getMetricFilter()).prefix(getPrefix());

		if (StringUtils.hasText(properties.getName())) {
			reporter.name(properties.getName());
		}

		if (StringUtils.hasText(properties.getHostName())) {
			reporter.hostName(properties.getHostName());
		}

		if (StringUtils.hasText(properties.getSuffix())) {
			reporter.suffix(properties.getSuffix());
		}

		if (StringUtils.hasText(properties.getName())) {
			reporter.name(properties.getName());
		}

		return reporter.build(new ZabbixSender(properties.getHost(), properties.getPort(),
				properties.getConnectTimeout(), properties.getSocketTimeout()));
	}

}
