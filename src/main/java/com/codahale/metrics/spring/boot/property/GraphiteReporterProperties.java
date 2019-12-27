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
package com.codahale.metrics.spring.boot.property;

import java.util.NoSuchElementException;

import com.codahale.metrics.spring.boot.MetricsReportProperties;

public class GraphiteReporterProperties extends ReporterProperties {

	public static final String PREFIX = MetricsReportProperties.PREFIX + ".graphite";
	
	public enum Transport {

		RABBITMQ("rabbitmq"), TCP("tcp"), UDP("udp"), PICKLE("pickle");

		private final String transport;

		Transport(String transport) {
			this.transport = transport;
		}

		public String get() {
			return transport;
		}
		
		public boolean equals(Transport transport){
			return this.compareTo(transport) == 0;
		}
		
		public boolean equals(String transport){
			return this.compareTo(Transport.valueOfIgnoreCase(transport)) == 0;
		}
		
		public static Transport valueOfIgnoreCase(String key) {
			for (Transport transport : Transport.values()) {
				if(transport.get().equalsIgnoreCase(key)) {
					return transport;
				}
			}
	    	throw new NoSuchElementException("Cannot found transport with key '" + key + "'.");
	    }
		
	}

	// Required
	private String host = "";
	private String port = "";

	// Optional
	private Transport transport = Transport.TCP;
	private String charset = "UTF-8";

	// Pickle Optional
	private int batchSize = 100;

	// RabbitMQ Required
	private String exchange = "";

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public Transport getTransport() {
		return transport;
	}

	public void setTransport(Transport transport) {
		this.transport = transport;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public int getBatchSize() {
		return batchSize;
	}

	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}

	public String getExchange() {
		return exchange;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

}
