package com.killard.board.jdo.board;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public enum PackageStatus {

    PUBLIC("PUBLIC"),

    PRIVATE("PRIVATE");

    private final String name;

    PackageStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
