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

public class DatabaseReporterProperties extends ReporterProperties {

	/**
	 * Whether to roll back the transaction when an exception is thrown.
	 */
	private boolean rollbackOnException;
	/**
	 * Whether to close the connection when a statement is commit.
	 */
	private boolean closeOnCommit;

	private String caugeTable = "cauge_metrics";
	private String histogramTable = "histogram_metrics";
	private String meterTable = "meter_metrics";
	private String timerTable = "timer_metrics";

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

}
