package com.study.microservices.employeeservice.config.toggles;

import lombok.experimental.UtilityClass;
import org.togglz.core.Feature;
import org.togglz.core.util.NamedFeature;

@UtilityClass
public class FeatureToggles {

    public static final Feature GET_ALL_EMPLOYEES_FEATURE = new NamedFeature("get_all_employees");

}
