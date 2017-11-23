/**
 * Copyright (C) 2012 Ryan W Tenney (ryan@10e.us)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.codahale.metrics.spring.boot.factory;

import java.nio.charset.Charset;

import javax.net.SocketFactory;

import org.springframework.util.ObjectUtils;

import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteRabbitMQ;
import com.codahale.metrics.graphite.GraphiteReporter;
import com.codahale.metrics.graphite.GraphiteSender;
import com.codahale.metrics.graphite.GraphiteUDP;
import com.codahale.metrics.graphite.PickledGraphite;
import com.codahale.metrics.spring.boot.property.GraphiteReporterProperties;
import com.codahale.metrics.spring.boot.property.GraphiteReporterProperties.Transport;
import com.rabbitmq.client.ConnectionFactory;

public class GraphiteReporterFactoryBean extends AbstractScheduledReporterFactoryBean<GraphiteReporter,GraphiteReporterProperties> {

	private ConnectionFactory connectionFactory;
	
	public GraphiteReporterFactoryBean(GraphiteReporterProperties properties) {
		super(properties);
	}

	@Override
	public Class<GraphiteReporter> getObjectType() {
		return GraphiteReporter.class;
	}

	@Override
	protected GraphiteReporter createInstance(GraphiteReporterProperties properties) {
		
		final GraphiteReporter.Builder builder = GraphiteReporter.forRegistry(getMetricRegistry())
				.convertDurationsTo(properties.getDurationUnit())
				.convertRatesTo(properties.getRateUnit())
				.filter(getMetricFilter())
				.prefixedWith(getPrefix());
		
		if (!ObjectUtils.isEmpty(getClock())) {
			builder.withClock(getClock());
		}

		final Charset charset = Charset.forName(getProperty(properties.getCharset(), "UTF-8"));
		final GraphiteSender graphite;
		if (Transport.RABBITMQ.equals(properties.getTransport())) {
			graphite = new GraphiteRabbitMQ(getConnectionFactory(), properties.getExchange());
		}
		else {
			final String hostname = properties.getHost();
			final int port = Integer.parseInt(properties.getPort());

			if (Transport.TCP.equals(properties.getTransport())) {
				graphite = new Graphite(hostname, port, SocketFactory.getDefault(), charset);
			}
			else if (Transport.UDP.equals(properties.getTransport())) {
				graphite = new GraphiteUDP(hostname, port);
			}
			else if (Transport.PICKLE.equals(properties.getTransport())) {
				graphite = new PickledGraphite(hostname, port, SocketFactory.getDefault(), charset, properties.getBatchSize());
			}
			else {
				throw new IllegalArgumentException("Invalid graphite transport: " + properties.getTransport().get());
			}
		}

		return builder.build(graphite);
	}

	public ConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}

	public void setConnectionFactory(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

}
