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

import com.codahale.metrics.spring.boot.MetricsReportProperties;

public class LibratoReporterProperties extends ReporterProperties {

	public static final String PREFIX = MetricsReportProperties.PREFIX + ".librato";
	// Required
	private String email;
	private String username;
	private String token;

	// Optional
	private String url = "https://metrics-api.librato.com";
	private String name = "librato";
	private String timeout;
	private String sourceRegex;

	private boolean deleteIdleStats = true;
	private boolean omitComplexGauges;
	private String prefixDelimiter = ".";
	private String expansionConfig = "ALL";
	private String source;
	private String readTimeout;
	private String connectTimeout;
	private Map<String /* name */, String /* value */> tags = new HashMap<String, String>();
	private boolean enableLegacy = true;
	private boolean enableTagging;
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTimeout() {
		return timeout;
	}

	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}

	public String getSourceRegex() {
		return sourceRegex;
	}

	public void setSourceRegex(String sourceRegex) {
		this.sourceRegex = sourceRegex;
	}

	public boolean isDeleteIdleStats() {
		return deleteIdleStats;
	}

	public void setDeleteIdleStats(boolean deleteIdleStats) {
		this.deleteIdleStats = deleteIdleStats;
	}

	public boolean isOmitComplexGauges() {
		return omitComplexGauges;
	}

	public void setOmitComplexGauges(boolean omitComplexGauges) {
		this.omitComplexGauges = omitComplexGauges;
	}

	public String getPrefixDelimiter() {
		return prefixDelimiter;
	}

	public void setPrefixDelimiter(String prefixDelimiter) {
		this.prefixDelimiter = prefixDelimiter;
	}

	public String getExpansionConfig() {
		return expansionConfig;
	}

	public void setExpansionConfig(String expansionConfig) {
		this.expansionConfig = expansionConfig;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getReadTimeout() {
		return readTimeout;
	}

	public void setReadTimeout(String readTimeout) {
		this.readTimeout = readTimeout;
	}

	public String getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(String connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public Map<String, String> getTags() {
		return tags;
	}

	public void setTags(Map<String, String> tags) {
		this.tags = tags;
	}

	public boolean isEnableLegacy() {
		return enableLegacy;
	}

	public void setEnableLegacy(boolean enableLegacy) {
		this.enableLegacy = enableLegacy;
	}

	public boolean isEnableTagging() {
		return enableTagging;
	}

	public void setEnableTagging(boolean enableTagging) {
		this.enableTagging = enableTagging;
	}

}
