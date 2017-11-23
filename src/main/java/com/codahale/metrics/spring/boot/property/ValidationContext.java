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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.util.StringUtils;

public class ValidationContext {

	private String lastKey;
	private final Map<String, String> properties;
	private final Set<String> allowedProperties;

	public ValidationContext(Map<String, String> properties) {
		this.properties = properties;
		this.allowedProperties = new HashSet<String>();
	}

	public boolean has(String key) {
		return properties.get(key) != null;
	}

	public String get(String key) {
		this.lastKey = key;
		return properties.get(key);
	}

	public void reject(String message) {
		reject(lastKey, message);
	}

	public void reject(String message, Throwable cause) {
		reject(lastKey, message, cause);
	}

	public void reject(String key, String message) {
		throw new ValidationException(errorMessage(key, message));
	}

	public void reject(String key, String message, Throwable cause) {
		throw new ValidationException(errorMessage(key, message), cause);
	}

	public String require(String key) {
		return require(key, null, "is required");
	}

	public String require(String key, String pattern) {
		return require(key, pattern, "must match the pattern '" + pattern + "'");
	}

	public String require(String key, String pattern, String message) {
		allowedProperties.add(key);
		final String value = get(key);
		if (!StringUtils.hasText(value)) {
			reject(key, message);
		}
		check(key, value, pattern, message);
		return value;
	}

	public boolean optional(String key) {
		return optional(key, null, null);
	}

	public boolean optional(String key, String pattern) {
		return optional(key, pattern, "must match the pattern '" + pattern + "'");
	}

	public boolean optional(String key, String pattern, String message) {
		allowedProperties.add(key);
		final String value = get(key);
		if (StringUtils.hasText(value)) {
			check(key, value, pattern, message);
			return true;
		}
		return false;
	}

	public void rejectUnmatchedProperties() {
		if (!allowedProperties.containsAll(properties.keySet())) {
			final Set<String> unmatchedProperties = new HashSet<String>(properties.keySet());
			unmatchedProperties.removeAll(allowedProperties);
			throw new ValidationException("Properties " + Arrays.toString(unmatchedProperties.toArray()) + " are not permitted on this element.");
		}
	}

	private String errorMessage(String key, String message) {
		return "Attribute '" + key + "'" + (message != null ? ": " + message : "");
	}

	private void check(String key, String value, String pattern, String message) {
		if (pattern != null && !(value.matches(pattern) || value.matches("^\\$\\{.*\\}$"))) {
			reject(key, message);
		}
	}
}
