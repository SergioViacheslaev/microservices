package com.study.microservices.employeeservice.util.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CsvConstants {

    public static final String INVALID_CSV_FORMAT_MSG = "Invalid file format. Please upload an CSV file with an extension .csv";

    public static final String CSV_CONTENT_TYPE = "text/csv";
    public static final String MS_EXCEL_CONTENT_TYPE = "application/vnd.ms-excel";
}
