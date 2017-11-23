/*
 * Copyright (c) 2010-2020, vindell (https://github.com/vindell).
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

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import com.codahale.metrics.spring.boot.ext.filter.FilterType;

public abstract class ReporterProperties {

	// Required
	private String period = "1m";
	
	// Optional
	private TimeUnit durationUnit = TimeUnit.MILLISECONDS;
	private TimeUnit rateUnit = TimeUnit.SECONDS;
	private String locale = Locale.SIMPLIFIED_CHINESE.toString();
	private String clockRef;
	
	private FilterType filterType = FilterType.PATTERN;
	private String filterValue = null;
	
	private String prefix = "";
	
	
	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public TimeUnit getRateUnit() {
		return rateUnit;
	}

	public void setRateUnit(TimeUnit rateUnit) {
		this.rateUnit = rateUnit;
	}

	public TimeUnit getDurationUnit() {
		return durationUnit;
	}

	public void setDurationUnit(TimeUnit durationUnit) {
		this.durationUnit = durationUnit;
	}
	
	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getClockRef() {
		return clockRef;
	}

	public void setClockRef(String clockRef) {
		this.clockRef = clockRef;
	}

	public FilterType getFilterType() {
		return filterType;
	}

	public void setFilterType(FilterType filterType) {
		this.filterType = filterType;
	}

	public String getFilterValue() {
		return filterValue;
	}

	public void setFilterValue(String filterValue) {
		this.filterValue = filterValue;
	}
	
	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
}
