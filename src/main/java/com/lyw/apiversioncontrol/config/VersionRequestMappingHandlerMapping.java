package com.lyw.apiversioncontrol.config;

import com.lyw.apiversioncontrol.annotation.VersionedApi;
import com.lyw.apiversioncontrol.domain.VersionRange;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

public class VersionRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

    @Override
    protected RequestCondition<?> getCustomMethodCondition(Method method) {
        VersionedApi versionMapping = AnnotationUtils.findAnnotation(method, VersionedApi.class);
        if (versionMapping == null) {
            return null;
        }
        VersionRange versionRange = new VersionRange(versionMapping.from(), versionMapping.to());
        return new VersionRequestCondition(versionMapping.name(), versionRange);
    }

}
