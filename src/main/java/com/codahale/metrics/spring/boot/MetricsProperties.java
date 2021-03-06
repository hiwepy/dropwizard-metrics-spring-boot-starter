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
package com.codahale.metrics.spring.boot;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = MetricsProperties.PREFIX)
public class MetricsProperties {

	public static final String PREFIX = "dropwizard.metrics";

	private boolean exposeProxy = false;
	private boolean proxyTargetClass = false;
	
	/*
	 *jvm.gc : com.codahale.metrics.jvm.GarbageCollectorMetricSet </br>
     *jvm.memory : com.codahale.metrics.jvm.MemoryUsageGaugeSet  </br>
     *jvm.thread-states : com.codahale.metrics.jvm.ThreadStatesGaugeSet </br>
     *jvm.fd.usage : com.codahale.metrics.jvm.FileDescriptorRatioGauge </br>
	 */
	private Map<String /* name */, String /* class */> metrics = new LinkedHashMap<String, String>();

	public boolean isExposeProxy() {
		return exposeProxy;
	}

	public void setExposeProxy(boolean exposeProxy) {
		this.exposeProxy = exposeProxy;
	}

	public boolean isProxyTargetClass() {
		return proxyTargetClass;
	}

	public void setProxyTargetClass(boolean proxyTargetClass) {
		this.proxyTargetClass = proxyTargetClass;
	}

	public Map<String, String> getMetrics() {
		return metrics;
	}

	public void setMetrics(Map<String, String> metrics) {
		this.metrics = metrics;
	}

}
