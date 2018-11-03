/*
 * Copyright (c) 2018, vindell (https://github.com/vindell).
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

import javax.management.MBeanServer;
import javax.sql.DataSource;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.codahale.metrics.Clock;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.ScheduledReporter;
import com.codahale.metrics.spring.boot.factory.ConsoleReporterFactoryBean;
import com.codahale.metrics.spring.boot.factory.DatabaseReporterFactoryBean;
import com.codahale.metrics.spring.boot.factory.JmxReporterFactoryBean;
import com.codahale.metrics.spring.boot.factory.Slf4jReporterFactoryBean;
import com.codahale.metrics.spring.boot.property.ConsoleReporterProperties;
import com.codahale.metrics.spring.boot.property.DatabaseReporterProperties;
import com.codahale.metrics.spring.boot.property.JmxReporterProperties;
import com.codahale.metrics.spring.boot.property.Slf4jReporterProperties;
import com.codahale.metrics.spring.boot.utils.SystemClock;

/**
 * 
 * @className ： MetricsReportAutoConfiguration
 * @description ： TODO(描述这个类的作用)
 * @author ： <a href="https://github.com/vindell">vindell</a>
 * @date ： 2017年11月23日 下午9:28:27
 * @version V1.0
 */
@Configuration
@ConditionalOnClass({ MetricRegistry.class, ScheduledReporter.class })
@EnableConfigurationProperties(MetricsReportProperties.class)
@AutoConfigureAfter(MetricsAutoConfiguration.class)
public class MetricsReportAutoConfiguration implements DisposableBean {

	@Bean
	@ConditionalOnMissingBean
	public Clock clock() {
		return SystemClock.instance();
	}
	
	@Bean
	@ConditionalOnProperty(prefix = ConsoleReporterProperties.PREFIX, value = "enabled", havingValue = "true")
	public ConsoleReporterFactoryBean consoleReporterFactoryBean(MetricsReportProperties properties, Clock clock,
			MetricRegistry metricRegistry) {
		ConsoleReporterFactoryBean factoryBean = new ConsoleReporterFactoryBean(properties.getConsole());
		factoryBean.setClock(clock);
		factoryBean.setMetricRegistry(metricRegistry);
		return factoryBean;
	}

	@Bean
	@ConditionalOnProperty(prefix = Slf4jReporterProperties.PREFIX, value = "enabled", havingValue = "true")
	public Slf4jReporterFactoryBean slf4jReporterFactoryBean(MetricsReportProperties properties, Clock clock,
			MetricRegistry metricRegistry) {

		Slf4jReporterFactoryBean factoryBean = new Slf4jReporterFactoryBean(properties.getSlf4j());
		factoryBean.setClock(clock);
		factoryBean.setMetricRegistry(metricRegistry);

		return factoryBean;

	}

	@Bean
	@ConditionalOnProperty(prefix = JmxReporterProperties.PREFIX, value = "enabled", havingValue = "true")
	public JmxReporterFactoryBean jmxReporterFactoryBean(MetricsReportProperties properties, Clock clock,
			MetricRegistry metricRegistry, @Autowired(required = false) MBeanServer mBeanServer) {

		JmxReporterFactoryBean factoryBean = new JmxReporterFactoryBean(properties.getJmx());
		factoryBean.setClock(clock);
		factoryBean.setMetricRegistry(metricRegistry);
		factoryBean.setmBeanServer(mBeanServer);

		return factoryBean;
	}

	@Bean
	@ConditionalOnProperty(prefix = DatabaseReporterProperties.PREFIX , value = "enabled", havingValue = "true")
	@ConditionalOnBean(DataSource.class)
	public DatabaseReporterFactoryBean databaseReporterFactoryBean(MetricsReportProperties properties,
			DataSource dataSource, Clock clock, MetricRegistry metricRegistry) {

		DatabaseReporterFactoryBean factoryBean = new DatabaseReporterFactoryBean(properties.getDatabase(), dataSource);
		factoryBean.setClock(clock);
		factoryBean.setMetricRegistry(metricRegistry);

		return factoryBean;
	}

	@Override
	public void destroy() throws Exception {

	}

}