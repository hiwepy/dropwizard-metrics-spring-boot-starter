/*
 * Copyright (c) 2017, vindell (https://github.com/vindell).
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

import javax.sql.DataSource;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.codahale.metrics.spring.boot.ext.reporter.DatabaseReporter;
import com.codahale.metrics.spring.boot.property.DatabaseReporterProperties;

public class DatabaseReporterFactoryBean extends AbstractScheduledReporterFactoryBean<DatabaseReporter,DatabaseReporterProperties> {

	private final DataSource dataSource;
	
	public DatabaseReporterFactoryBean(DatabaseReporterProperties properties, DataSource dataSource) {
		super(properties);
		this.dataSource = dataSource;
	}
 

	@Override
	public Class<DatabaseReporter> getObjectType() {
		return DatabaseReporter.class;
	}

	@Override
	protected DatabaseReporter createInstance(DatabaseReporterProperties properties) {
		
		final DatabaseReporter.Builder reporter = DatabaseReporter.forRegistry(getMetricRegistry())
				.convertDurationsTo(properties.getDurationUnit())
				.convertRatesTo(properties.getRateUnit())
				.filter(getMetricFilter())
				.closeOnCommit(properties.isCloseOnCommit())
				.shutdownExecutorOnStop(properties.isRollbackOnException())
				.rollbackOnException(properties.isRollbackOnException());

		if (!ObjectUtils.isEmpty(getClock())) {
			reporter.withClock(getClock());
		}

		if (StringUtils.hasText(properties.getCaugeTable())) {
			reporter.caugeTable(properties.getCaugeTable());
			reporter.allowCauge(properties.isAllowCauge());
		}
		
		if (StringUtils.hasText(properties.getCounterTable())) {
			reporter.counterTable(properties.getCounterTable());
			reporter.allowCounter(properties.isAllowCounter());
		}
		
		if (StringUtils.hasText(properties.getHistogramTable())) {
			reporter.histogramTable(properties.getHistogramTable());
			reporter.allowHistogram(properties.isAllowHistogram());
		}
		
		if (StringUtils.hasText(properties.getMeterTable())) {
			reporter.meterTable(properties.getMeterTable());
			reporter.allowMeter(properties.isAllowMeter());
		}
		
		if (StringUtils.hasText(properties.getTimerTable())) {
			reporter.timerTable(properties.getTimerTable());
			reporter.allowTimer(properties.isAllowTimer());
		}

		return reporter.build(dataSource);
	}

}
