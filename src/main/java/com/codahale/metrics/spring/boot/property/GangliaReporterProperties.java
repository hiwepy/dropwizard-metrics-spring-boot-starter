/*
 * Copyright (c) 2017, vindell (https://github.com/vindell).
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

import com.codahale.metrics.spring.boot.MetricsReportProperties;

import info.ganglia.gmetric4j.gmetric.GMetric.UDPAddressingMode;

public class GangliaReporterProperties extends ReporterProperties {

	public static final String PREFIX = MetricsReportProperties.PREFIX + ".ganglia";
	
	// Required
	private String group = "group";
	private String port = "port";
	private UDPAddressingMode udpMode = UDPAddressingMode.MULTICAST;
	private String ttl = "ttl";

	// Optional
	private String protocol = "";
	private String uuid = "";
	private String spoof = "";
	private int dmax = 0;
	private int tmax = 60;
     
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public UDPAddressingMode getUdpMode() {
		return udpMode;
	}
	public void setUdpMode(UDPAddressingMode udpMode) {
		this.udpMode = udpMode;
	}
	public String getTtl() {
		return ttl;
	}
	public void setTtl(String ttl) {
		this.ttl = ttl;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getSpoof() {
		return spoof;
	}
	public void setSpoof(String spoof) {
		this.spoof = spoof;
	}
	public int getDmax() {
		return dmax;
	}
	public void setDmax(int dmax) {
		this.dmax = dmax;
	}
	public int getTmax() {
		return tmax;
	}
	public void setTmax(int tmax) {
		this.tmax = tmax;
	}
	
	
	
}
