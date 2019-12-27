package com.codahale.metrics.spring.boot.event;

/**
 * 
 * @className	： MetricCountedEvent
 * @description	： 业务运行状态事件对象
 * @author 		： <a href="https://github.com/hiwepy">hiwepy</a>
 * @date		： 2017年6月9日 下午5:23:02
 * @version 	V1.0
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
