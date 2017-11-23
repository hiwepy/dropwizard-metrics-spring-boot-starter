package com.codahale.metrics.spring.boot.ext.health;

import com.codahale.metrics.health.HealthCheck;

public class NginxHealthCheck extends HealthCheck {

	@Override
	protected Result check() throws Exception {
		return null;
	}

}
