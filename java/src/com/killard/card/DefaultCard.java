package com.killard.card;

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
public class DefaultCard extends BasicCard {

    public DefaultCard(ElementSchool elementSchool, int level, Skill skill) {
        super(elementSchool, level, skill);
    }

    public DefaultCard(ElementSchool elementSchool, int level, int maxHealth, int attack) {
        super(elementSchool, level, maxHealth, attack);
    }

    public DefaultCard(ElementSchool elementSchool, int level, int maxHealth, int attack, Skill skill) {
        super(elementSchool, level, maxHealth, attack, skill);
    }

    public DefaultCard(ElementSchool elementSchool, int level, int maxHealth, int attack, Skill skill,
                        List<Attribute> attributes) {
        super(elementSchool, level, maxHealth, maxHealth, attack, skill, attributes);
    }

    public DefaultCard(ElementSchool elementSchool, int level, int maxHealth, int attack,
                       Attribute[] attributes) {
        super(elementSchool, level, maxHealth, attack, attributes);
    }

    public DefaultCard(ElementSchool elementSchool, int level, int maxHealth, int attack, Skill skill,
                       Attribute[] attributes) {
        super(elementSchool, level, maxHealth, maxHealth, attack, skill, attributes);
    }

}
