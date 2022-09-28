package com.lyw.apiversioncontrol.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class VersionRange {

    private final Version from;
    private final Version to;

    public VersionRange(String from, String to) {
        this.from = new Version(from);
        this.to = new Version(to);
    }

    public Version getTo() {
        return to;
    }

    public boolean includes(Version other) {
        return from.compareTo(other) <= 0 && to.compareTo(other) >= 0;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(from).append(to).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        VersionRange versionRange = (VersionRange) obj;
        return new EqualsBuilder().append(from, versionRange.from).append(to, versionRange.to).isEquals();
    }

    @Override
    public String toString() {
        return "range[" + from + "-" + to + "]";
    }

}
