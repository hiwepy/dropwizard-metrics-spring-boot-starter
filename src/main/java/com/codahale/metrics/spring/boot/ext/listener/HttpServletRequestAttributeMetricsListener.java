package com.codahale.metrics.spring.boot.ext.listener;

import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.spring.boot.factory.MetricsFactory;


public class HttpServletRequestAttributeMetricsListener implements ServletRequestAttributeListener {

	/**
     * 实例化一个registry，最核心的一个模块，相当于一个应用程序的metrics系统的容器，维护一个Map
     */
	protected MetricRegistry registry = MetricsFactory.getMetricRegistry("http-request-attribute");
	
	@Override
	public void attributeAdded(ServletRequestAttributeEvent event) {
		String prefix = MetricRegistry.name(this.getClass(), event.getServletContext().getContextPath(), "request-attribute", "attributeAdded" );
		registry.meter(prefix).mark();
	}

	@Override
	public void attributeRemoved(ServletRequestAttributeEvent event) {
		String prefix = MetricRegistry.name(this.getClass(), event.getServletContext().getContextPath(), "request-attribute", "attributeAdded" );
		registry.meter(prefix).mark();

	}

	@Override
	public void attributeReplaced(ServletRequestAttributeEvent event) {
		String prefix = MetricRegistry.name(this.getClass(), event.getServletContext().getContextPath(), "request-attribute", "attributeAdded" );
		registry.meter(prefix).mark();
	}
	
}
