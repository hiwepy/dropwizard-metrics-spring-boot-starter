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
package com.codahale.metrics.spring.boot.factory.support;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionListener;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.ServletContextAttributeExporter;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.servlet.InstrumentedFilter;
import com.codahale.metrics.servlets.HealthCheckServlet;
import com.codahale.metrics.servlets.MetricsServlet;
import com.codahale.metrics.spring.boot.EnableInstrumentedMetrics;
import com.codahale.metrics.spring.boot.ext.listener.HttpServletContextAttributeMetricsListener;
import com.codahale.metrics.spring.boot.ext.listener.HttpServletRequestAttributeMetricsListener;
import com.codahale.metrics.spring.boot.ext.listener.HttpServletRequestMetricsListener;
import com.codahale.metrics.spring.boot.ext.listener.HttpSessionAttributeMetricsListener;
import com.codahale.metrics.spring.boot.ext.listener.HttpSessionMetricsListener;

@Configuration
@ConditionalOnClass({ EnableInstrumentedMetrics.class })
public class MetricsInstrumentedRegistrar {

	/*
	 * 通过该方式将metricRegistry注入到对应的属性值中以便各个组件使用
	 */
	@Bean("filterAttributeExporter")
	public ServletContextAttributeExporter filterAttributeExporter(MetricRegistry registry) {
		
		ServletContextAttributeExporter attributeExporter = new ServletContextAttributeExporter();
		
		Map<String, Object> attributes = new HashMap<String, Object>();
		
		attributes.put(InstrumentedFilter.REGISTRY_ATTRIBUTE, registry);
		attributes.put(MetricsServlet.METRICS_REGISTRY, registry);
		attributes.put(HealthCheckServlet.HEALTH_CHECK_REGISTRY, registry);
		
		attributeExporter.setAttributes(attributes);
		
		return attributeExporter;
	}
 
	@Bean
	public FilterRegistrationBean instrumentedFilter() {
		
		InstrumentedFilter filter = new InstrumentedFilter();
		
		
		//<property name="name-prefix" ref="authentication"></property>
		
		return new FilterRegistrationBean(filter);
	}

	@Bean
	public ServletListenerRegistrationBean<ServletContextAttributeListener> httpServletContextAttributeMetricsListener() {
		HttpServletContextAttributeMetricsListener linstener = new HttpServletContextAttributeMetricsListener();
		return new ServletListenerRegistrationBean<ServletContextAttributeListener>(linstener);
	}
	
	@Bean
	public ServletListenerRegistrationBean<ServletRequestAttributeListener> httpServletRequestAttributeMetricsListener() {
		HttpServletRequestAttributeMetricsListener linstener = new HttpServletRequestAttributeMetricsListener();
		return new ServletListenerRegistrationBean<ServletRequestAttributeListener>(linstener);
	}
	
	@Bean
	public ServletListenerRegistrationBean<ServletRequestListener> httpServletRequestMetricsListener() {
		HttpServletRequestMetricsListener linstener = new HttpServletRequestMetricsListener();
		return new ServletListenerRegistrationBean<ServletRequestListener>(linstener);
	}
	
	@Bean
	public ServletListenerRegistrationBean<HttpSessionAttributeListener> httpSessionAttributeMetricsListener() {
		HttpSessionAttributeMetricsListener linstener = new HttpSessionAttributeMetricsListener();
		return new ServletListenerRegistrationBean<HttpSessionAttributeListener>(linstener);
	}
	
	@Bean
	public ServletListenerRegistrationBean<HttpSessionListener> httpSessionMetricsListener() {
		HttpSessionMetricsListener linstener = new HttpSessionMetricsListener();
		return new ServletListenerRegistrationBean<HttpSessionListener>(linstener);
	}
	
}