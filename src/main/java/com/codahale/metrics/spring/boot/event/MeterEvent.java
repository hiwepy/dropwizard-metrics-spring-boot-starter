package com.codahale.metrics.spring.boot.event;

import java.util.Map;

/**
 * 
 * @className	： MetricStatusEvent
 * @description	：业务运行状态事件对象
 * @author 		： <a href="https://github.com/hiwepy">hiwepy</a>
 * @date		： 2017年6月9日 下午5:23:57
 * @version 	V1.0
 */
@SuppressWarnings("serial")
public class MeterEvent extends MetricEvent<MetricEventPoint> {

	public MeterEvent(Object source, MetricEventPoint bind) {
		super(source, bind);
	}
	
	public MeterEvent(Object source, String name) {
		super(source, new MetricEventPoint(name, null));
	}
	
	public MeterEvent(Object source, String name, String message) {
		super(source, new MetricEventPoint(name, message));
	}
	
	public MeterEvent(Object source, String name, String message, Map<String, Object> data) {
		super(source, new MetricEventPoint(name, message, data));
	}
	
}
