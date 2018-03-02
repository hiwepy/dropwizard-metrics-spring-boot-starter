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

import javax.management.MBeanServer;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.SmartLifecycle;
import org.springframework.util.StringUtils;

import com.codahale.metrics.jmx.JmxReporter;
import com.codahale.metrics.spring.boot.property.JmxReporterProperties;

public class JmxReporterFactoryBean extends AbstractReporterFactoryBean<JmxReporter,JmxReporterProperties> implements SmartLifecycle, DisposableBean {

	private boolean running = false;
	private MBeanServer mBeanServer;
	
	public JmxReporterFactoryBean(JmxReporterProperties properties) {
		super(properties);
	}

	@Override
	public Class<JmxReporter> getObjectType() {
		return JmxReporter.class;
	}

	@Override
	protected JmxReporter createInstance(JmxReporterProperties properties) {
		final JmxReporter.Builder reporter = JmxReporter.forRegistry(getMetricRegistry())
				.convertDurationsTo(properties.getDurationUnit())
				.convertRatesTo(properties.getRateUnit())
				.filter(getMetricFilter());

		if (StringUtils.hasText(properties.getDomain())) {
			reporter.inDomain(properties.getDomain());
		}

		if (getmBeanServer() != null) {
			reporter.registerWith(getmBeanServer());
		}

		return reporter.build();
	}

	@Override
	public void start() {
		if (isEnabled() && !isRunning()) {
			getObject().start();
			running = true;
		}
	}

	@Override
	public void stop() {
		if (isRunning()) {
			getObject().stop();
			running = false;
		}
	}

	@Override
	public boolean isRunning() {
		return running;
	}

	@Override
	public void destroy() throws Exception {
		stop();
	}

	@Override
	public boolean isAutoStartup() {
		return true;
	}

	@Override
	public void stop(Runnable runnable) {
		stop();
		runnable.run();
	}

	@Override
	public int getPhase() {
		return 0;
	}

	public MBeanServer getmBeanServer() {
		return mBeanServer;
	}

	public void setmBeanServer(MBeanServer mBeanServer) {
		this.mBeanServer = mBeanServer;
	}

}
