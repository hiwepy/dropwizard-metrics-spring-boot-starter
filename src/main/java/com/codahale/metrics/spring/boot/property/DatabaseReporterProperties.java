/*
 * Copyright (c) 2018, vindell (https://github.com/vindell).
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

public class DatabaseReporterProperties extends ReporterProperties {

	public static final String PREFIX = MetricsReportProperties.PREFIX + ".database";
	/**
	 * Whether to roll back the transaction when an exception is thrown.
	 */
	private boolean rollbackOnException;
	/**
	 * Whether to close the connection when a statement is commit.
	 */
	private boolean closeOnCommit;

	private String caugeTable = "cauge_metrics";
	private String counterTable = "counter_metrics";
	private String histogramTable = "histogram_metrics";
	private String meterTable = "meter_metrics";
	private String timerTable = "timer_metrics";

	private boolean allowCauge = false;
	private boolean allowCounter = true;
	private boolean allowHistogram = false;
	private boolean allowMeter = false;
	private boolean allowTimer = false;

	public boolean isRollbackOnException() {
		return rollbackOnException;
	}

	public void setRollbackOnException(boolean rollbackOnException) {
		this.rollbackOnException = rollbackOnException;
	}

	public boolean isCloseOnCommit() {
		return closeOnCommit;
	}

	public void setCloseOnCommit(boolean closeOnCommit) {
		this.closeOnCommit = closeOnCommit;
	}

	public String getCaugeTable() {
		return caugeTable;
	}

	public void setCaugeTable(String caugeTable) {
		this.caugeTable = caugeTable;
	}

	public String getCounterTable() {
		return counterTable;
	}

	public void setCounterTable(String counterTable) {
		this.counterTable = counterTable;
	}

	public String getHistogramTable() {
		return histogramTable;
	}

	public void setHistogramTable(String histogramTable) {
		this.histogramTable = histogramTable;
	}

	public String getMeterTable() {
		return meterTable;
	}

	public void setMeterTable(String meterTable) {
		this.meterTable = meterTable;
	}

	public String getTimerTable() {
		return timerTable;
	}

	public void setTimerTable(String timerTable) {
		this.timerTable = timerTable;
	}

	public boolean isAllowCauge() {
		return allowCauge;
	}

	public void setAllowCauge(boolean allowCauge) {
		this.allowCauge = allowCauge;
	}

	public boolean isAllowCounter() {
		return allowCounter;
	}

	public void setAllowCounter(boolean allowCounter) {
		this.allowCounter = allowCounter;
	}

	public boolean isAllowHistogram() {
		return allowHistogram;
	}

	public void setAllowHistogram(boolean allowHistogram) {
		this.allowHistogram = allowHistogram;
	}

	public boolean isAllowMeter() {
		return allowMeter;
	}

	public void setAllowMeter(boolean allowMeter) {
		this.allowMeter = allowMeter;
	}

	public boolean isAllowTimer() {
		return allowTimer;
	}

	public void setAllowTimer(boolean allowTimer) {
		this.allowTimer = allowTimer;
	}

}
