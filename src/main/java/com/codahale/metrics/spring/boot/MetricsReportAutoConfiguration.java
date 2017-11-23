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

import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.util.StringUtils;

import com.codahale.metrics.spring.boot.factory.Slf4jReporterFactoryBean;
import com.codahale.metrics.spring.boot.factory.support.MetricsAnnotationDrivenRegistrar;
import com.codahale.metrics.spring.boot.factory.support.ReporterBeanDefinitionParser;

/**
 * 
 * @className	： MetricsReportAutoConfiguration
 * @description	： TODO(描述这个类的作用)
 * @author 		： <a href="https://github.com/vindell">vindell</a>
 * @date		： 2017年11月23日 下午9:28:27
 * @version 	V1.0
 */
@Configuration
@ConditionalOnProperty(prefix = MetricsReportProperties.PREFIX, value = "enabled", havingValue = "true")
@EnableConfigurationProperties(MetricsReportProperties.class)
@Import({ MetricsAnnotationDrivenRegistrar.class})
public class MetricsReportAutoConfiguration implements DisposableBean {

	// private static final Logger LOG =
	// LoggerFactory.getLogger(MetricsReportAutoConfiguration.class);
	private final ServiceLoader<ReporterBeanDefinitionParser> reporterParserLoader = ServiceLoader
			.load(ReporterBeanDefinitionParser.class);

	@Bean
	@ConditionalOnProperty(prefix = MetricsReportProperties.PREFIX, value = "slf4j")
	public Slf4jReporterFactoryBean slf4jReporterFactoryBean(MetricsReportProperties properties) {
		return new Slf4jReporterFactoryBean(properties.getSlf4j());
	}
	
	@PostConstruct
	protected void parseInternal(MetricsReportProperties properties) {

		try {
			String[] types = StringUtils.tokenizeToStringArray(properties.getTypes(), ",");
			for (String type : types) {
				for (ReporterBeanDefinitionParser reporterParser : reporterParserLoader) {
					if (type.equals(reporterParser.getType())) {
						// return reporterParser.parseReporter(properties);
					}
				}
			}
		} catch (ServiceConfigurationError ex) {
		}
	}

	@Override
	public void destroy() throws Exception {

	}

}