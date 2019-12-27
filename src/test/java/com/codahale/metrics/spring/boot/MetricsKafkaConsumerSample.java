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
package com.codahale.metrics.spring.boot;

import java.io.IOException;

import io.github.hengyunabc.metrics.MessageListener;
import io.github.hengyunabc.metrics.MetricsKafkaConsumer;

public class MetricsKafkaConsumerSample {

	String zookeeper;
	String topic;
	String group;

	MetricsKafkaConsumer consumer;

	public static void main(String[] args) throws IOException {

		String zookeeper = "localhost:2181";
		String topic = "test-kafka-reporter";
		String group = "consumer-test";

		MetricsKafkaConsumer consumer = new MetricsKafkaConsumer();

		consumer = new MetricsKafkaConsumer();
		consumer.setZookeeper(zookeeper);
		consumer.setTopic(topic);
		consumer.setGroup(group);
		consumer.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(String message) {
				System.err.println(message);
			}
		});
		consumer.init();

		System.in.read();

		consumer.desotry();
	}
}
