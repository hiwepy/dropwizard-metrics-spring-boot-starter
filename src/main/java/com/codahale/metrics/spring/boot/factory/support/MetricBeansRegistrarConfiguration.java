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
package com.codahale.metrics.spring.boot.factory.support;

import java.util.Iterator;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.MetricSet;
import com.codahale.metrics.spring.boot.MetricsProperties;

/* Register metric beans (Optional) */
@Configuration
public class MetricBeansRegistrarConfiguration implements BeanFactoryAware, ImportBeanDefinitionRegistrar{
	
	private static final Logger LOG = LoggerFactory.getLogger(MetricBeansRegistrarConfiguration.class);
	private BeanFactory beanFactory;
	@Autowired
	private MetricsProperties properties;
	
	@PostConstruct
	public void afterPropertiesSet() {
		LOG.debug("No {} found.", ConsoleReporter.class.getName());
	}
	
	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata,
			BeanDefinitionRegistry registry) {
		
		final RuntimeBeanReference metricRegistryBeanRef = new RuntimeBeanReference(properties.toString());
		
		Iterator<Entry<String, String>>  ite = properties.getMetrics().entrySet().iterator();
		while (ite.hasNext()) {
			
			Entry<String, String> entry = ite.next();
			final String name = entry.getKey();
			
			final RootBeanDefinition metricRegistererDef = new RootBeanDefinition(MetricRegisterer.class);
			
			metricRegistererDef.setSource(this);
			metricRegistererDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);

			try {
				Object metric = BeanDefinitionReaderUtils.createBeanDefinition(
						MetricSet.class.getName(), entry.getValue(), this.getClass().getClassLoader());
				
				final ConstructorArgumentValues args = metricRegistererDef.getConstructorArgumentValues();
				args.addIndexedArgumentValue(0, metricRegistryBeanRef);
				args.addIndexedArgumentValue(1, name);
				args.addIndexedArgumentValue(2, metric);
				
				String beanName = BeanDefinitionReaderUtils.registerWithGeneratedName(metricRegistererDef, registry);
				//registry.registerBeanDefinition(beanName, metricRegistererDef);
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	public static class MetricRegisterer implements InitializingBean {

		private final MetricRegistry metricRegistry;
		private final String name;
		private final Metric metric;

		public MetricRegisterer(MetricRegistry metricRegistry, String name, Metric metric) {
			this.metricRegistry = metricRegistry;
			this.name = name;
			this.metric = metric;

			if (!StringUtils.hasText(name) && !(metric instanceof MetricSet)) {
				throw new RuntimeException(); // TODO
			}
		}

		@Override
		public void afterPropertiesSet() throws Exception {
			metricRegistry.register(name, metric);
		}

	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	public BeanFactory getBeanFactory() {
		return beanFactory;
	}
	
}
