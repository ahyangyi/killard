package com.killard.board.card;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public enum SkillTarget {

    all("all"),

    self("self"),

    owncard("owncard"),

    other("other"),

    otherscard("otherscard");

    private final String name;

    private SkillTarget(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(SkillTarget.valueOf("all"));
    }
}
