package com.codahale.metrics.spring.boot.event;

/**
 * 
 * @className	： MetricGaugeEvent
 * @description	：业务计量事件对象
 * @author 		： <a href="https://github.com/vindell">vindell</a>
 * @date		： 2017年6月9日 下午5:23:57
 * @version 	V1.0
 */
@SuppressWarnings("serial")
public class GaugeEvent extends MetricEvent<MetricEventPoint> {

	public GaugeEvent(Object source, MetricEventPoint bind) {
		super(source, bind);
	}
	
	public GaugeEvent(Object source, String name, Long value) {
		super(source, new MetricEventPoint(name, null, value));
	}
	
	public GaugeEvent(Object source, String name, String message, Long value) {
		super(source, new MetricEventPoint(name, message, value));
	}
	
}
