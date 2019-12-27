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
package com.codahale.metrics.spring.boot.exception;

/**
 * Exception thrown when Metrics encounters a problem.
 */
public class MetricsException extends RuntimeException {
    /**
     * Creates a new MetricsException with this message and this cause.
     *
     * @param message The exception message.
     * @param cause   The exception cause.
     */
    public MetricsException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new MetricsException with this cause. For use in subclasses that override getMessage().
     *
     * @param cause   The exception cause.
     */
    public MetricsException(Throwable cause) {
        super(cause);
    }

    /**
     * Creates a new MetricsException with this message.
     *
     * @param message The exception message.
     */
    public MetricsException(String message) {
        super(message);
    }

    /**
     * Creates a new MetricsException. For use in subclasses that override getMessage().
     */
    public MetricsException() {
        super();
    }
}
