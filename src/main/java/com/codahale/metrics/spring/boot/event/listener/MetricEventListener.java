/*
 * Copyright (c) 2010-2020, vindell (https://github.com/vindell).
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
package com.codahale.metrics.spring.boot.event.listener;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import com.codahale.metrics.spring.boot.ext.MetricsFactory;

public abstract class MetricEventListener<E extends ApplicationEvent> implements ApplicationListener<E>, InitializingBean {

	@Autowired
	protected MetricsFactory metricsFactory;
	
	protected long initialDelay = 0; 
	
	protected long period = 1;
	
	protected TimeUnit unit = TimeUnit.SECONDS;
	
	@Override
	public void afterPropertiesSet() throws Exception {
	}
	
	public MetricsFactory getMetricsFactory() {
		return metricsFactory;
	}

	public void setMetricsFactory(MetricsFactory metricsFactory) {
		this.metricsFactory = metricsFactory;
	}

	public long getInitialDelay() {
		return initialDelay;
	}

	public void setInitialDelay(long initialDelay) {
		this.initialDelay = initialDelay;
	}

	public long getPeriod() {
		return period;
	}

	public void setPeriod(long period) {
		this.period = period;
	}

	public TimeUnit getUnit() {
		return unit;
	}

	public void setUnit(TimeUnit unit) {
		this.unit = unit;
	}
	
}
