package com.codahale.metrics.spring.boot.factory;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.codahale.metrics.Clock;
import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.spring.boot.ext.filter.FilterType;
import com.codahale.metrics.spring.boot.ext.filter.PatternFilter;
import com.codahale.metrics.spring.boot.property.ReporterProperties;
import com.ryantenney.metrics.spring.reporter.MetricPrefixSupplier;

public abstract class AbstractReporterFactoryBean<T,P extends ReporterProperties> implements FactoryBean<T>, InitializingBean, BeanFactoryAware {

	private BeanFactory beanFactory;
	private ConversionService conversionService;
	
	private MetricRegistry metricRegistry;
	private P properties;
	private T instance;

	private boolean enabled = true;
	private boolean initialized = false;
	
	private Clock clock;
	private MetricFilter metricFilter;
	private MetricPrefixSupplier prefixSupplier;
	

	public AbstractReporterFactoryBean(P properties){
		this.properties = properties;
	}
	
	@Override
	public abstract Class<? extends T> getObjectType();

	@Override
	public boolean isSingleton() {
		return true;
	}

	@Override
	public T getObject() {
		if (!this.enabled) {
			return null;
		}
		if (!this.initialized) {
			throw new IllegalStateException("Singleton instance not initialized yet");
		}
		return this.instance;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.instance = createInstance(properties);
		this.initialized = true;
	}

	protected abstract T createInstance(P properties) throws Exception;

	public P getProperties() {
		return properties;
	}

	protected String getProperty(String value, String defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		return value;
	}
	
	public <V> V getProperty(String value, Class<V> requiredType) {
		return getProperty(value, requiredType, null);
	}

	@SuppressWarnings("unchecked")
	public <V> V getProperty(String value, Class<V> requiredType, V defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		return (V) getConversionService().convert(value, TypeDescriptor.forObject(value), TypeDescriptor.valueOf(requiredType));
	}

	public MetricFilter getMetricFilter() {
		if (!ObjectUtils.isEmpty(metricFilter)) {
			return metricFilter;
		} else if (StringUtils.hasText(properties.getFilterValue())) {
			if(FilterType.PATTERN.compareTo(properties.getFilterType()) == 0) {
				return new PatternFilter(properties.getFilterValue());
			} else if(FilterType.PATTERN.compareTo(properties.getFilterType()) == 0) {
				return new PatternFilter(properties.getFilterValue());
			} else if(FilterType.PATTERN.compareTo(properties.getFilterType()) == 0) {
				return new PatternFilter(properties.getFilterValue());
			}
		}
		return MetricFilter.ALL;
	}

	public String getPrefix() {
		if (StringUtils.hasText(properties.getPrefix())) {
			return properties.getPrefix();
		}
		else if (!ObjectUtils.isEmpty(prefixSupplier)) { 
			return prefixSupplier.getPrefix();
		}
		return null;
	}

	public void setMetricRegistry(final MetricRegistry metricRegistry) {
		this.metricRegistry = metricRegistry;
	}

	public MetricRegistry getMetricRegistry() {
		return metricRegistry;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public Clock getClock() {
		return clock;
	}

	public void setClock(Clock clock) {
		this.clock = clock;
	}

	public MetricPrefixSupplier getPrefixSupplier() {
		return prefixSupplier;
	}

	public void setPrefixSupplier(MetricPrefixSupplier prefixSupplier) {
		this.prefixSupplier = prefixSupplier;
	}

	public void setMetricFilter(MetricFilter metricFilter) {
		this.metricFilter = metricFilter;
	}
	
	@Override
	public void setBeanFactory(final BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
		if (beanFactory instanceof ConfigurableBeanFactory) {
			this.conversionService = ((ConfigurableBeanFactory) beanFactory).getConversionService();
		}
	}

	public BeanFactory getBeanFactory() {
		return this.beanFactory;
	}

	public ConversionService getConversionService() {
		if (this.conversionService == null) {
			this.conversionService = new DefaultConversionService();
		}
		return this.conversionService;
	}
	

}
