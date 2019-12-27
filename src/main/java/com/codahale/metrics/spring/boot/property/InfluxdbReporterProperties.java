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

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import com.codahale.metrics.spring.boot.MetricsReportProperties;
import com.codahale.metrics.spring.boot.factory.support.SenderType;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

public class InfluxdbReporterProperties extends ReporterProperties  {
	
	public static final String PREFIX = MetricsReportProperties.PREFIX + ".influxdb";
	
	public enum Protocol {

		HTTP("http"), HTTPS("https");
		
		private final String protocol;

		Protocol(String protocol) {
			this.protocol = protocol;
		}

		public String get() {
			return protocol;
		}
		
		public boolean equals(Protocol protocol){
			return this.compareTo(protocol) == 0;
		}
		
		public boolean equals(String protocol){
			return this.compareTo(Protocol.valueOfIgnoreCase(protocol)) == 0;
		}
		
		public static Protocol valueOfIgnoreCase(String key) {
			for (Protocol protocol : Protocol.values()) {
				if(protocol.get().equalsIgnoreCase(key)) {
					return protocol;
				}
			}
	    	throw new NoSuchElementException("Cannot found transport with key '" + key + "'.");
	    }
		
	}
	
	private Protocol protocol = Protocol.HTTP;

	private String host = "localhost";

	private int port = 8086;

	private Map<String, String> tags = new HashMap<>();

	private ImmutableMap<String, ImmutableSet<String>> fields = ImmutableMap.of("timers",
			ImmutableSet.of("p50", "p75", "p95", "p99", "p999", "m1_rate"), "meters", ImmutableSet.of("m1_rate"));

	private String database = "";

	private String auth = "";

	private int connectTimeout = 1500;

	private int readTimeout = 1500;

	private SenderType senderType = SenderType.HTTP;

	private boolean groupGauges = true;

	private ImmutableMap<String, String> measurementMappings = ImmutableMap.of();

	private ImmutableMap<String, String> defaultMeasurementMappings = ImmutableMap.<String, String>builder()
			.put("health", ".*\\.health(\\..*)?$").put("auth", ".*\\.auth\\..*").put("dao", ".*\\.(jdbi|dao)\\..*")
			.put("resources", ".*\\.resources?\\..*").put("event_handlers", ".*\\.messaging\\..*")
			.put("datasources", "io\\.dropwizard\\.db\\.ManagedPooledDataSource.*")
			.put("clients", "org\\.apache\\.http\\.client\\.HttpClient.*")
			.put("client_connections", "org\\.apache\\.http\\.conn\\.HttpClientConnectionManager.*")
			.put("connections", "org\\.eclipse\\.jetty\\.server\\.HttpConnectionFactory.*")
			.put("thread_pools", "org\\.eclipse\\.jetty\\.util\\.thread\\.QueuedThreadPool.*")
			.put("logs", "ch\\.qos\\.logback\\.core\\.Appender.*")
			.put("http_server", "io\\.dropwizard\\.jetty\\.MutableServletContextHandler.*")
			.put("raw_sql", "org\\.skife\\.jdbi\\.v2\\.DBI\\.raw-sql").put("jvm", "^jvm$")
			.put("jvm_attribute", "jvm\\.attribute.*?").put("jvm_buffers", "jvm\\.buffers\\..*")
			.put("jvm_classloader", "jvm\\.classloader.*").put("jvm_gc", "jvm\\.gc\\..*")
			.put("jvm_memory", "jvm\\.memory\\..*").put("jvm_threads", "jvm\\.threads.*").build();

	private ImmutableSet<String> excludes = ImmutableSet.<String>builder().add("ch.qos.logback.core.Appender.debug")
			.add("ch.qos.logback.core.Appender.trace")
			.add("io.dropwizard.jetty.MutableServletContextHandler.percent-4xx-15m")
			.add("io.dropwizard.jetty.MutableServletContextHandler.percent-4xx-1m")
			.add("io.dropwizard.jetty.MutableServletContextHandler.percent-4xx-5m")
			.add("io.dropwizard.jetty.MutableServletContextHandler.percent-5xx-15m")
			.add("io.dropwizard.jetty.MutableServletContextHandler.percent-5xx-1m")
			.add("io.dropwizard.jetty.MutableServletContextHandler.percent-5xx-5m").add("jvm.attribute.name")
			.add("jvm.attribute.vendor").add("jvm.memory.heap.usage").add("jvm.memory.non-heap.usage")
			.add("jvm.memory.pools.Code-Cache.usage").add("jvm.memory.pools.Compressed-Class-Space.usage")
			.add("jvm.memory.pools.Metaspace.usage").add("jvm.memory.pools.PS-Eden-Space.usage")
			.add("jvm.memory.pools.PS-Old-Gen.usage").add("jvm.memory.pools.PS-Survivor-Space.usage").build();

	public Protocol getProtocol() {
		return protocol;
	}

	public void setProtocol(Protocol protocol) {
		this.protocol = protocol;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public Map<String, String> getTags() {
		return tags;
	}

	public void setTags(Map<String, String> tags) {
		this.tags = tags;
	}

	public ImmutableMap<String, ImmutableSet<String>> getFields() {
		return fields;
	}

	public void setFields(ImmutableMap<String, ImmutableSet<String>> fields) {
		this.fields = fields;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public int getReadTimeout() {
		return readTimeout;
	}

	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}

	public boolean getGroupGauges() {
		return groupGauges;
	}

	public void setGroupGauges(boolean groupGauges) {
		this.groupGauges = groupGauges;
	}

	public Map<String, String> getMeasurementMappings() {
		return measurementMappings;
	}

	public void setMeasurementMappings(ImmutableMap<String, String> measurementMappings) {
		this.measurementMappings = measurementMappings;
	}

	public Map<String, String> getDefaultMeasurementMappings() {
		return defaultMeasurementMappings;
	}

	public ImmutableSet<String> getExcludes() {
		return this.excludes;
	}

	public void setExcludes(ImmutableSet<String> excludes) {
		this.excludes = excludes;
	}

	public void setDefaultMeasurementMappings(ImmutableMap<String, String> defaultMeasurementMappings) {
		this.defaultMeasurementMappings = defaultMeasurementMappings;
	}

	public void setSenderType(SenderType senderType) {
		this.senderType = senderType;
	}

	public SenderType getSenderType() {
		return senderType;
	}

	
}
