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
package com.codahale.metrics.spring.boot;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.spring.boot.factory.MetricsFactory;
import com.codahale.metrics.spring.boot.factory.support.MetricBeansRegistrarConfiguration;
import com.codahale.metrics.spring.boot.factory.support.MetricsAnnotationDrivenRegistrar;

/**
 * 
 * @className	： MetricsAutoConfiguration
 * @description	： TODO(描述这个类的作用)
 * @author 		： <a href="https://github.com/vindell">vindell</a>
 * @date		： 2017年11月21日 下午3:07:08
 * @version 	V1.0
 */
@Configuration
@ConditionalOnProperty(prefix = MetricsProperties.PREFIX, value = "enabled", havingValue = "true")
@EnableConfigurationProperties(MetricsProperties.class)
@Import({ MetricsAnnotationDrivenRegistrar.class, MetricBeansRegistrarConfiguration.class })
public class MetricsAutoConfiguration implements DisposableBean {

	//private Logger logger = LoggerFactory.getLogger(MetricsAutoConfiguration.class);

	@Bean
	@ConditionalOnMissingBean
	public MetricRegistry metricRegistry() {
		return new MetricRegistry();
	}
	
	@Bean
	@ConditionalOnMissingBean
	public MetricsFactory metricsFactory(MetricRegistry registry) {
		MetricsFactory metricsFactory = new MetricsFactory();
		if(registry != null) {
			metricsFactory.setRegistry(registry);
		}
		return metricsFactory;
	}
	
	
	@Override
	public void destroy() throws Exception {
		 

	}

}