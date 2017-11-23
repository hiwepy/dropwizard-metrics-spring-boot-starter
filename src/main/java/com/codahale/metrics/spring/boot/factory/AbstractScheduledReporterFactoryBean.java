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

import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.SmartLifecycle;

import com.codahale.metrics.ScheduledReporter;
import com.codahale.metrics.spring.boot.property.ReporterProperties;

public abstract class AbstractScheduledReporterFactoryBean<T extends ScheduledReporter,P extends ReporterProperties> extends AbstractReporterFactoryBean<T,P> implements SmartLifecycle,
		DisposableBean {

	private static final Pattern DURATION_STRING_PATTERN = Pattern.compile("^(\\d+)\\s?(ns|us|ms|s|m|h|d)?$");

	private boolean running;
	
	public AbstractScheduledReporterFactoryBean(P properties) {
		super(properties);
	}
 
	@Override
	public void start() {
		if (isEnabled() && !isRunning()) {
			getObject().start(getPeriod(), TimeUnit.NANOSECONDS);
			running = true;
		}
	}

	@Override
	public void stop() {
		if (isEnabled() && isRunning()) {
			getObject().stop();
			running = false;
		}
	}

	@Override
	public boolean isRunning() {
		return running;
	}

	@Override
	public void destroy() throws Exception {
		stop();
	}
	
	protected long getPeriod() {
		return convertDurationString(getProperties().getPeriod());
	}

	protected Locale parseLocale(String localeString) {
		final int underscore = localeString.indexOf('_');
		if (underscore == -1) {
			return new Locale(localeString);
		}
		else {
			return new Locale(localeString.substring(0, underscore), localeString.substring(underscore + 1));
		}
	}
	
	/**
	 * Parses and converts to nanoseconds a string representing
	 * a duration, ie: 500ms, 30s, 5m, 1h, etc
	 * @param duration a string representing a duration
	 * @return the duration in nanoseconds
	 */
	protected long convertDurationString(String duration) {
		final Matcher m = DURATION_STRING_PATTERN.matcher(duration);
		if (!m.matches()) {
			throw new IllegalArgumentException("Invalid duration string format");
		}

		final long sourceDuration = Long.parseLong(m.group(1));
		final String sourceUnitString = m.group(2);
		final TimeUnit sourceUnit;
		if ("ns".equalsIgnoreCase(sourceUnitString)) {
			sourceUnit = TimeUnit.NANOSECONDS;
		}
		else if ("us".equalsIgnoreCase(sourceUnitString)) {
			sourceUnit = TimeUnit.MICROSECONDS;
		}
		else if ("ms".equalsIgnoreCase(sourceUnitString)) {
			sourceUnit = TimeUnit.MILLISECONDS;
		}
		else if ("s".equalsIgnoreCase(sourceUnitString)) {
			sourceUnit = TimeUnit.SECONDS;
		}
		else if ("m".equalsIgnoreCase(sourceUnitString)) {
			sourceUnit = TimeUnit.MINUTES;
		}
		else if ("h".equalsIgnoreCase(sourceUnitString)) {
			sourceUnit = TimeUnit.HOURS;
		}
		else if ("d".equalsIgnoreCase(sourceUnitString)) {
			sourceUnit = TimeUnit.DAYS;
		}
		else {
			sourceUnit = TimeUnit.MILLISECONDS;
		}

		return sourceUnit.toNanos(sourceDuration);
	}

	@Override
	public int getPhase() {
		return 0;
	}

	@Override
	public void stop(Runnable callback) {
		stop();
		callback.run();
	}

	@Override
	public boolean isAutoStartup() {
		return true;
	}

}
