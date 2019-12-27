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
package com.codahale.metrics.spring.boot.factory.support;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.codahale.metrics.spring.boot.EnableEventMetrics;
import com.codahale.metrics.spring.boot.event.listener.CountedEventListener;
import com.codahale.metrics.spring.boot.event.listener.GaugeEventListener;
import com.codahale.metrics.spring.boot.event.listener.HistogramEventListener;
import com.codahale.metrics.spring.boot.event.listener.MeterEventListener;

/**
 * 
 * @className	： MetricsEventListenerRegistrar
 * @description	： TODO(描述这个类的作用)
 * @author 		： <a href="https://github.com/hiwepy">hiwepy</a>
 * @date		： 2017年11月22日 上午10:32:22
 * @version 	V1.0
 */
@Configuration
@ConditionalOnClass(EnableEventMetrics.class)
public class MetricsEventListenerRegistrar{

	@Bean
	public CountedEventListener countedEventListener() {
		return new CountedEventListener();
	}
	
	@Bean
	public GaugeEventListener gaugeEventListener() {
		return new GaugeEventListener();
	}
	
	@Bean
	public HistogramEventListener histogramEventListener() {
		return new HistogramEventListener();
	}
	
	@Bean
	public MeterEventListener meterEventListener() {
		return new MeterEventListener();
	}
	
}