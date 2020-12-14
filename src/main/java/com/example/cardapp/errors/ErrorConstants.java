package com.example.cardapp.errors;

import java.net.URI;

public final class ErrorConstants {
	public static final String ERR_CONCURRENCY_FAILURE = "error.concurrencyFailure";
	public static final String ERR_VALIDATION = "error.validation";
	public static final String PROBLEM_BASE_URL = "https://www.sushant.com/problem";
    public static final URI DEFAULT_TYPE = URI.create(PROBLEM_BASE_URL + "/problem-with-message");
	private ErrorConstants() {
	}
}
