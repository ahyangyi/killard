package com.killard.board.card;

import java.util.List;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class DefaultMetaCard extends BasicMetaCard<DefaultMetaCard> {

    public DefaultMetaCard(ElementSchool elementSchool, int level, Skill skill) {
        super(elementSchool, level, skill);
    }

    public DefaultMetaCard(ElementSchool elementSchool, int level, int maxHealth, int attack) {
        super(elementSchool, level, maxHealth, attack);
    }

    public DefaultMetaCard(ElementSchool elementSchool, int level, int maxHealth, int attack, Skill skill) {
        super(elementSchool, level, maxHealth, attack, skill);
    }

    public DefaultMetaCard(ElementSchool elementSchool, int level, int maxHealth, int attack, Skill skill,
                        List<Attribute> attributes) {
        super(elementSchool, level, maxHealth, maxHealth, attack, skill, attributes);
    }

    public DefaultMetaCard(ElementSchool elementSchool, int level, int maxHealth, int attack,
                       Attribute[] attributes) {
        super(elementSchool, level, maxHealth, attack, attributes);
    }

    public DefaultMetaCard(ElementSchool elementSchool, int level, int maxHealth, int attack, Skill skill,
                       Attribute[] attributes) {
        super(elementSchool, level, maxHealth, maxHealth, attack, skill, attributes);
    }

    public int compareTo(DefaultMetaCard compare) {
        return getLevel() - compare.getLevel();
    }
}
