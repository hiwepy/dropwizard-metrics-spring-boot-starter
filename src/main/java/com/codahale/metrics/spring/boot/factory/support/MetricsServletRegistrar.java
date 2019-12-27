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

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.ServletContextAttributeExporter;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.servlets.HealthCheckServlet;
import com.codahale.metrics.servlets.MetricsServlet;
import com.codahale.metrics.spring.boot.EnableInstrumentedMetrics;

@Configuration
@ConditionalOnClass({ EnableInstrumentedMetrics.class })
public class MetricsServletRegistrar {

	/**
	 * 
	 * @description	： 通过该方式将metricRegistry注入到对应的属性值中以便各个组件使用
	 * @author 		： <a href="https://github.com/hiwepy">hiwepy</a>
	 * @date 		：2017年11月21日 下午8:18:50
	 * @param registry
	 * @return
	 */
	@Bean("servletsAttributeExporter")
	public ServletContextAttributeExporter servletsAttributeExporter(MetricRegistry registry) {
		
		ServletContextAttributeExporter attributeExporter = new ServletContextAttributeExporter();
		
		Map<String, Object> attributes = new HashMap<String, Object>();
		
		attributes.put(MetricsServlet.METRICS_REGISTRY, registry);
		attributes.put(HealthCheckServlet.HEALTH_CHECK_REGISTRY, registry);
		
		attributeExporter.setAttributes(attributes);
		
		return attributeExporter;
	}
 
	
}