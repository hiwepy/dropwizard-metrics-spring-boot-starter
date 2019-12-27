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

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import com.codahale.metrics.spring.boot.property.ConsoleReporterProperties;
import com.codahale.metrics.spring.boot.property.CsvReporterProperties;
import com.codahale.metrics.spring.boot.property.DatabaseReporterProperties;
import com.codahale.metrics.spring.boot.property.DatadogReporterProperties;
import com.codahale.metrics.spring.boot.property.GangliaReporterProperties;
import com.codahale.metrics.spring.boot.property.GraphiteReporterProperties;
import com.codahale.metrics.spring.boot.property.InfluxdbReporterProperties;
import com.codahale.metrics.spring.boot.property.JmxReporterProperties;
import com.codahale.metrics.spring.boot.property.KafkaReporterProperties;
import com.codahale.metrics.spring.boot.property.LibratoReporterProperties;
import com.codahale.metrics.spring.boot.property.NewRelicReporterProperties;
import com.codahale.metrics.spring.boot.property.RocketmqReporterProperties;
import com.codahale.metrics.spring.boot.property.Slf4jReporterProperties;
import com.codahale.metrics.spring.boot.property.ZabbixReporterProperties;

/**
 * 
 * @className ： MetricsProperties
 * @description ： TODO(描述这个类的作用)
 * @author ： <a href="https://github.com/hiwepy">hiwepy</a>
 * @date ： 2017年11月17日 下午2:24:47
 * @version V1.0
 */
@ConfigurationProperties(prefix = MetricsReportProperties.PREFIX)
public class MetricsReportProperties {

	public static final String PREFIX = "dropwizard.metrics.report";

	// Required
	protected String types = "console";
	
	/* 各类报表输出配置 */
	
	@NestedConfigurationProperty
	private ConsoleReporterProperties console;
	@NestedConfigurationProperty
	private CsvReporterProperties csv;
	@NestedConfigurationProperty
	private DatabaseReporterProperties database;
	@NestedConfigurationProperty
	private DatadogReporterProperties datadog;
	@NestedConfigurationProperty
	private GangliaReporterProperties ganglia;
	@NestedConfigurationProperty
	private GraphiteReporterProperties graphite;
	@NestedConfigurationProperty
	private InfluxdbReporterProperties influxdb;
	@NestedConfigurationProperty
	private JmxReporterProperties jmx;
	@NestedConfigurationProperty
	private KafkaReporterProperties kafka;
	@NestedConfigurationProperty
	private LibratoReporterProperties librato;
	@NestedConfigurationProperty
	private NewRelicReporterProperties newrelic;
	@NestedConfigurationProperty
	private RocketmqReporterProperties rocketmq;
	@NestedConfigurationProperty
	private Slf4jReporterProperties slf4j;
	@NestedConfigurationProperty
	private ZabbixReporterProperties zabbix;

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public ConsoleReporterProperties getConsole() {
		return console;
	}

	public void setConsole(ConsoleReporterProperties console) {
		this.console = console;
	}

	public CsvReporterProperties getCsv() {
		return csv;
	}

	public void setCsv(CsvReporterProperties csv) {
		this.csv = csv;
	}

	public DatabaseReporterProperties getDatabase() {
		return database;
	}

	public void setDatabase(DatabaseReporterProperties database) {
		this.database = database;
	}

	public DatadogReporterProperties getDatadog() {
		return datadog;
	}

	public void setDatadog(DatadogReporterProperties datadog) {
		this.datadog = datadog;
	}

	public GangliaReporterProperties getGanglia() {
		return ganglia;
	}

	public void setGanglia(GangliaReporterProperties ganglia) {
		this.ganglia = ganglia;
	}

	public GraphiteReporterProperties getGraphite() {
		return graphite;
	}

	public void setGraphite(GraphiteReporterProperties graphite) {
		this.graphite = graphite;
	}

	public InfluxdbReporterProperties getInfluxdb() {
		return influxdb;
	}

	public void setInfluxdb(InfluxdbReporterProperties influxdb) {
		this.influxdb = influxdb;
	}

	public JmxReporterProperties getJmx() {
		return jmx;
	}

	public void setJmx(JmxReporterProperties jmx) {
		this.jmx = jmx;
	}

	public KafkaReporterProperties getKafka() {
		return kafka;
	}

	public void setKafka(KafkaReporterProperties kafka) {
		this.kafka = kafka;
	}

	public LibratoReporterProperties getLibrato() {
		return librato;
	}

	public void setLibrato(LibratoReporterProperties librato) {
		this.librato = librato;
	}

	public NewRelicReporterProperties getNewrelic() {
		return newrelic;
	}

	public void setNewrelic(NewRelicReporterProperties newrelic) {
		this.newrelic = newrelic;
	}

	public RocketmqReporterProperties getRocketmq() {
		return rocketmq;
	}

	public void setRocketmq(RocketmqReporterProperties rocketmq) {
		this.rocketmq = rocketmq;
	}

	public Slf4jReporterProperties getSlf4j() {
		return slf4j;
	}

	public void setSlf4j(Slf4jReporterProperties slf4j) {
		this.slf4j = slf4j;
	}

	public ZabbixReporterProperties getZabbix() {
		return zabbix;
	}

	public void setZabbix(ZabbixReporterProperties zabbix) {
		this.zabbix = zabbix;
	}

	
}
