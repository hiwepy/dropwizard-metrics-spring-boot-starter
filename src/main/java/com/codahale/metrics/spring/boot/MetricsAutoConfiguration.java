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

import java.util.Iterator;
import java.util.Map.Entry;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.MetricSet;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.codahale.metrics.spring.boot.ext.MetricsFactory;

/**
 * 
 * @className	： MetricsAutoConfiguration
 * @description	： TODO(描述这个类的作用)
 * @author 		： <a href="https://github.com/hiwepy">hiwepy</a>
 * @date		： 2017年11月21日 下午3:07:08
 * @version 	V1.0
 */
@Configuration
@ConditionalOnClass(MetricRegistry.class)
@EnableConfigurationProperties(MetricsProperties.class)
public class MetricsAutoConfiguration implements DisposableBean {

	//private Logger logger = LoggerFactory.getLogger(MetricsAutoConfiguration.class);

	@Bean
	@ConditionalOnMissingBean
	public MetricRegistry metricRegistry(MetricsProperties properties) {
		MetricRegistry metricRegistry = new MetricRegistry();
		
		Iterator<Entry<String, String>>  ite = properties.getMetrics().entrySet().iterator();
		while (ite.hasNext()) {
			
			Entry<String, String> entry = ite.next();
			final String name = entry.getKey();
			try {
				
				AbstractBeanDefinition metricDef = BeanDefinitionReaderUtils.createBeanDefinition(MetricSet.class.getName(), entry.getValue(), this.getClass().getClassLoader());
				
				Object metric = BeanUtils.instantiateClass(metricDef.getBeanClass());
				
				if (StringUtils.hasText(name) && (metric instanceof MetricSet)) {
					metricRegistry.register(name, (MetricSet) metric);
				}
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return metricRegistry;
	}
	
	@Bean
	@ConditionalOnMissingBean
	public HealthCheckRegistry healthCheckRegistry(MetricsProperties properties) {
		return new HealthCheckRegistry();
	}
	
	@Bean
	@ConditionalOnMissingBean
	public MetricsFactory metricsFactory(MetricRegistry metricRegistry) {
		MetricsFactory metricsFactory = new MetricsFactory();
		if(metricRegistry != null) {
			metricsFactory.setRegistry(metricRegistry);
		}
		return metricsFactory;
	}
	
	
	@Override
	public void destroy() throws Exception {
		 

	}

}