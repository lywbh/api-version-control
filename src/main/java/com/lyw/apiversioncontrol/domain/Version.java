package com.lyw.apiversioncontrol.domain;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Version implements Comparable<Version> {

    public static final String MIN_VERSION = "unlimited_min";

    public static final String MAX_VERSION = "unlimited_max";

    private int[] tokens;

    private final int unlimited;

    public Version(String version) {
        if (StringUtils.equals(version, MIN_VERSION)) {
            unlimited = -1;
        } else if (StringUtils.equals(version, MAX_VERSION)) {
            unlimited = 1;
        } else {
            unlimited = 0;
            String[] tokens = version.split("\\.");
            if (tokens.length == 0) {
                throw new IllegalArgumentException("Invalid version " + version);
            }
            try {
                this.tokens = new int[tokens.length];
                for (int i = 0; i < tokens.length; ++i) {
                    this.tokens[i] = Integer.parseInt(tokens[i]);
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid version " + version);
            }
        }
    }

    @Override
    public int compareTo(Version o) {
        if (unlimited == o.unlimited && unlimited != 0) {
            return 0;
        } else if (unlimited == 1) {
            return 1;
        } else if (o.unlimited == 1) {
            return -1;
        } else if (unlimited == -1) {
            return -1;
        } else if (o.unlimited == -1) {
            return 1;
        } else {
            if (tokens.length != o.tokens.length) {
                throw new IllegalArgumentException("Invalid limit range " + o);
            }
            for (int i = 0; i < tokens.length; ++i) {
                if (tokens[i] > o.tokens[i]) {
                    return 1;
                } else if (tokens[i] < o.tokens[i]) {
                    return -1;
                }
            }
            return 0;
        }
    }

    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(unlimited);
        for (int t : tokens) {
            builder.append(t);
        }
        return builder.toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        return compareTo((Version) obj) == 0;
    }

    @Override
    public String toString() {
        if (unlimited == 1) {
            return MAX_VERSION;
        } else if (unlimited == -1) {
            return MIN_VERSION;
        } else {
            return StringUtils.join(tokens, '.');
        }
    }

}
