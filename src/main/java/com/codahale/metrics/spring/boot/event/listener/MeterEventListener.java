/*
 * Copyright (c) 2018, hiwepy (https://github.com/hiwepy).
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
package com.codahale.metrics.spring.boot.event.listener;

import org.springframework.scheduling.annotation.Async;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.spring.boot.event.MeterEvent;
import com.codahale.metrics.spring.boot.event.MetricEventPoint;
import com.codahale.metrics.spring.boot.ext.MetricsFactory;

public class MeterEventListener extends MetricEventListener<MeterEvent> {

	protected MetricRegistry metricRegistry;
	
	public MetricRegistry getMetricRegistry() {
		return metricRegistry;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if(getMetricsFactory() != null){
			metricRegistry = getMetricsFactory().getRegistry();
		} else {
			metricRegistry = MetricsFactory.getMeterMetricRegistry();
		}
	}
	
	@Async
	@Override
	public void onApplicationEvent(MeterEvent event) {
		
		//获取绑定数据对象
		MetricEventPoint data = event.getBind();
		//计算当前事件的唯一度量名称
		String name = MetricRegistry.name(event.getSource().getClass(), data.getName());
		//增加一次计数
		getMetricRegistry().meter(name).mark();
		
	}


}
