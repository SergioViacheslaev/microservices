package com.study.microservices.employeeservice.util.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AsyncConstants {

    public static final Long SSE_CONNECTION_TIMEOUT_MS = 1200000L;  // 20 minutes

    public static final String FILE_UPLOAD_COMPLETED = "FILE_UPLOAD_COMPLETED";
    public static final String FILE_UPLOAD_FAILED = "FILE_UPLOAD_FAILED";
    public static final String FILE_UPLOAD_PARTIAL_INFO = "FILE_UPLOAD_PARTIAL_INFO";
}
