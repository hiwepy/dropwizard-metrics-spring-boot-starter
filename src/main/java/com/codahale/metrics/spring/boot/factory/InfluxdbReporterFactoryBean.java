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
package com.codahale.metrics.spring.boot.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.activation.UnsupportedDataTypeException;

import com.codahale.metrics.spring.boot.property.InfluxdbReporterProperties;
import com.izettle.metrics.influxdb.InfluxDbHttpSender;
import com.izettle.metrics.influxdb.InfluxDbReporter;
import com.izettle.metrics.influxdb.InfluxDbTcpSender;
import com.izettle.metrics.influxdb.InfluxDbUdpSender;


/*
 * A factory for {@link InfluxDbReporter} instances.
 * <p/>
 * <b>Configuration Parameters:</b>
 * <table>
 *     <tr>
 *         <td>Name</td>
 *         <td>Default</td>
 *         <td>Description</td>
 *     </tr>
 *     <tr>
 *         <td>protocol</td>
 *         <td>http</td>
 *         <td>The protocol (http or https) of the InfluxDb server to report to.</td>
 *     </tr>
 *     <tr>
 *         <td>host</td>
 *         <td>localhost</td>
 *         <td>The hostname of the InfluxDb server to report to.</td>
 *     </tr>
 *     <tr>
 *         <td>port</td>
 *         <td>8086</td>
 *         <td>The port of the InfluxDb server to report to.</td>
 *     </tr>
 *     <tr>
 *         <td>prefix</td>
 *         <td><i>None</i></td>
 *         <td>The prefix for Metric key names (measurement) to report to InfluxDb.</td>
 *     </tr>
 *     <tr>
 *         <td>tags</td>
 *         <td><i>None</i></td>
 *         <td>tags for all metrics reported to InfluxDb.</td>
 *     </tr>
 *     <tr>
 *         <td>fields</td>
 *         <td>timers = p50, p75, p95, p99, p999, m1_rate<br>meters = m1_rate</td>
 *         <td>fields by metric type to reported to InfluxDb.</td>
 *     </tr>
 *     <tr>
 *         <td>database</td>
 *         <td><i>None</i></td>
 *         <td>The database that metrics will be reported to InfluxDb.</td>
 *     </tr>
 *     <tr>
 *         <td>precision</td>
 *         <td>1m</td>
 *         <td>The precision of timestamps. Does not take into account the quantity, so for example `5m` will be minute precision</td>
 *     </tr>
 *     <tr>
 *         <td>connectTimeout</td>
 *         <td>1500</td>
 *         <td>The connect timeout in milliseconds for connecting to InfluxDb.</td>
 *     </tr>
 *     <tr>
 *         <td>readTimeout</td>
 *         <td>1500</td>
 *         <td>The read timeout in milliseconds for reading from InfluxDb.</td>
 *     </tr>
 *     <tr>
 *         <td>auth</td>
 *         <td><i>None</i></td>
 *         <td>An auth string of format username:password to authenticate with when reporting to InfluxDb.</td>
 *     </tr>
 *     <tr>
 *         <td>groupGauges</td>
 *         <td><i>None</i></td>
 *         <td>A boolean to signal whether to group gauges when reporting to InfluxDb.</td>
 *     </tr>
 *     <tr>
 *         <td>measurementMappings</td>
 *         <td><i>None</i></td>
 *         <td>A map for measurement mappings to be added, overridden or removed from the defaultMeasurementMappings.</td>
 *     </tr>
 *     <tr>
 *         <td>defaultMeasurementMappings</td>
 *         <td>
 *             health = .*\.health(\..*)?<br>
 *             auth = .*\.auth\..*<br>
 *             dao = *\.(jdbi|dao)\..*<br>
 *             resources = *\.resources?\..*<br>
 *             datasources = io\.dropwizard\.db\.ManagedPooledDataSource.*<br>
 *             clients = org\.apache\.http\.client\.HttpClient.*<br>
 *             client_connections = org\.apache\.http\.conn\.HttpClientConnectionManager.*<br>
 *             connections = org\.eclipse\.jetty\.server\.HttpConnectionFactory.*<br>
 *             thread_pools = org\.eclipse\.jetty\.util\.thread\.QueuedThreadPool.*<br>
 *             logs = ch\.qos\.logback\.core\.Appender.*<br>
 *             http_server = io\.dropwizard\.jetty\.MutableServletContextHandler.*<br>
 *             raw_sql = org\.skife\.jdbi\.v2\.DBI\.raw-sql<br>
 *             jvm = ^jvm$<br>
 *             jvm_attribute = jvm\.attribute.*?<br>
 *             jvm_buffers = jvm\.buffers\..*<br>
 *             jvm_classloader = jvm\.classloader.*<br>
 *             jvm_gc = jvm\.gc\..*<br>
 *             jvm_memory = jvm\.memory\..*<br>
 *             jvm_threads = jvm\.threads.*<br>
 *             event_handlers = .*Handler.*
 *          </td>
 *         <td>A map with default measurement mappings.</td>
 *     </tr>
 *     <tr>
 *         <td>excludes</td>
 *         <td><i>defaultExcludes</i></tr>
 *         <td>A set of pre-calculated metrics like usage and percentage, and unchanging JVM
 *             metrics to exclude by default</td>
 *     </tr>
 * </table>
 */
public class InfluxdbReporterFactoryBean
		extends AbstractScheduledReporterFactoryBean<InfluxDbReporter, InfluxdbReporterProperties> {

	public InfluxdbReporterFactoryBean(InfluxdbReporterProperties properties) {
		super(properties);
	}

	@Override
	public Class<InfluxDbReporter> getObjectType() {
		return InfluxDbReporter.class;
	}

	@Override
	protected InfluxDbReporter createInstance(InfluxdbReporterProperties properties) {

		final InfluxDbReporter.Builder reporter = InfluxDbReporter.forRegistry(getMetricRegistry())
				.convertDurationsTo(properties.getDurationUnit())
				.convertRatesTo(properties.getRateUnit())
				.includeMeterFields(properties.getFields().get("meters"))
				.includeTimerFields(properties.getFields().get("timers"))
				.filter(getMetricFilter())
				.groupGauges(properties.getGroupGauges())
				.withTags(properties.getTags())
				.measurementMappings(buildMeasurementMappings());

		try {
			
			switch (properties.getSenderType()) {
				case HTTP:
					return reporter.build(
							new InfluxDbHttpSender(
									properties.getProtocol().get(), 
									properties.getHost(), 
									properties.getPort(),
									properties.getDatabase(), 
									properties.getAuth(), 
									properties.getDurationUnit(),
									properties.getConnectTimeout(), 
									properties.getReadTimeout(), 
									properties.getPrefix()));
				case TCP:
					return reporter.build(new InfluxDbTcpSender(
							properties.getHost(), 
							properties.getPort(),
							properties.getReadTimeout(), 
							properties.getDatabase(), 
							properties.getPrefix()));
				case UDP:
					return reporter.build(new InfluxDbUdpSender(
							properties.getHost(), 
							properties.getPort(),
							properties.getReadTimeout(), 
							properties.getDatabase(), 
							properties.getPrefix()));
				default:
					throw new UnsupportedDataTypeException("The Sender Type is not supported. ");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, String> buildMeasurementMappings() {
		
		Map<String, String> mappings = new HashMap<String, String>(getProperties().getDefaultMeasurementMappings());

		for (Map.Entry<String, String> entry : getProperties().getMeasurementMappings().entrySet()) {
			String mappingKey = entry.getKey();
			String mappingValue = entry.getValue();

			if (mappingValue.isEmpty()) {
				mappings.remove(mappingKey);
				continue;
			}

			mappings.put(mappingKey, mappingValue);
		}

		return mappings;
	}

	public boolean isMeasurementMappingRegularExpressions() {
		for (Map.Entry<String, String> entry : buildMeasurementMappings().entrySet()) {
			try {
				Pattern.compile(entry.getValue());
			} catch (PatternSyntaxException e) {
				return false;
			}
		}
		return true;
	}
 
}
