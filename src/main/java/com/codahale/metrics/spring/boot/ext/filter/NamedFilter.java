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
package com.codahale.metrics.spring.boot.ext.filter;


import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricFilter;

public class NamedFilter implements MetricFilter {

	protected String metricKey;
	
	public NamedFilter(String metric) {
		this.metricKey = metric;
	}
	
	@Override
	public boolean matches(String name, Metric metric) {
		return name.equals(metricKey);
	}
	
	public String getMetric() {
		return metricKey;
	}

	public void setMetric(String metric) {
		this.metricKey = metric;
	}

	@Override
	public String toString() {
		return "[NamedFilter metric=" + metricKey + "]";
	}
	
}