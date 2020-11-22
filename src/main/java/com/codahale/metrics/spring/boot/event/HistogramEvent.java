package com.codahale.metrics.spring.boot.event;

/**
 * 业务运行状态事件对象
 */
@SuppressWarnings("serial")
public class HistogramEvent extends MetricEvent<MetricEventPoint> {

	public HistogramEvent(Object source, MetricEventPoint bind) {
		super(source, bind);
	}
	
	public HistogramEvent(Object source, String name, Long value) {
		super(source, new MetricEventPoint(name, null, value));
	}
	
	public HistogramEvent(Object source, String name, String message, Long value) {
		super(source, new MetricEventPoint(name, message, value));
	}
	
}
