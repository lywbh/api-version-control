package com.lyw.apiversioncontrol.config;

import com.lyw.apiversioncontrol.domain.Version;
import com.lyw.apiversioncontrol.domain.VersionRange;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.mvc.condition.RequestCondition;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VersionRequestCondition implements RequestCondition<VersionRequestCondition> {

    /**
     * match content likes "0.0.0..."
     */
    private static final Pattern REGEX_PATTERN = Pattern.compile("(\\d+(\\.\\d+)*).*");

    /**
     * header key name
     */
    private final String headerName;

    /**
     * version range
     */
    private final VersionRange versionRange;

    VersionRequestCondition(String headerName, VersionRange versionRange) {
        this.headerName = headerName;
        this.versionRange = versionRange;
    }

    public VersionRequestCondition combine(VersionRequestCondition other) {
        return new VersionRequestCondition(other.headerName, other.versionRange);
    }

    public VersionRequestCondition getMatchingCondition(HttpServletRequest request) {
        String accept = request.getHeader(headerName);
        if (StringUtils.isEmpty(accept)) {
            return this;
        }
        Matcher matcher = REGEX_PATTERN.matcher(accept);
        if (matcher.matches()) {
            Version version = new Version(matcher.group());
            try {
                if (versionRange.includes(version)) {
                    return this;
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public int compareTo(VersionRequestCondition other, HttpServletRequest request) {
        return other.versionRange.getTo().compareTo(this.versionRange.getTo());
    }

}
