package com.killard.board.jdo;

import com.killard.board.card.AttackType;
import com.killard.board.jdo.board.AttributeDO;
import com.killard.board.jdo.board.ElementDO;
import com.killard.board.jdo.board.MetaCardDO;
import com.killard.board.jdo.board.PackageDO;
import com.killard.board.jdo.board.SkillDO;
import com.killard.board.jdo.context.BoardContext;
import com.killard.board.parser.Expression;
import com.killard.board.parser.Function;
import com.killard.board.parser.Node;
import com.killard.board.parser.StringLiteral;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class JdoCardBuilder {

    public void buildRule(PackageDO pack, Map map) throws InvalidCardException {
        pack.rule(buildAttributeHandlers(map, "validate"), buildAttributeHandlers(map, "before"),
                buildAttributeHandlers(map, "after"));
    }

    public List<AttributeHandler> buildAttributeHandlers(Map map, String name) throws InvalidCardException {
        List<AttributeHandler> handlers = new LinkedList<AttributeHandler>();
        Object value = map.get(name);
        if (value instanceof Function) buildAttributeHandler((Function) value, handlers);
        if (value instanceof List) {
            for (Object function : (List) value) buildAttributeHandler((Function) function, handlers);
        }
        return handlers;
    }

    public boolean buildAttributeHandler(Function function, List<AttributeHandler> handlers)
            throws InvalidCardException {
        try {
            Node[] args = function.getArguments().getChildren();
            if (args.length < 1) return false;
            Class actionClass = Class.forName("com.killard.board.card.action." + ((Expression) args[0]).getText());
            Boolean selfTargeted = true;
            if (args.length > 1 && ((Expression) args[1]).getText().equalsIgnoreCase("All")) selfTargeted = false;
            AttributeHandler handler = new AttributeHandler(actionClass, selfTargeted, function);
            return handlers.add(handler);
        } catch (ClassNotFoundException e) {
            throw new InvalidCardException(e);
        }
    }

    public void buildCard(ElementDO element, MetaCardDO card, Map map) throws InvalidCardException {

        card.setLevel(getInt(map, "level"));
        card.setMaxHealth(getInt(map, "health"));
        card.setAttackType(AttackType.PHYSICAL);
        card.setAttackValue(getInt(map, "attack"));

        buildSkills(element, card, map);
        buildAttributes(element, map);

        if (map.containsKey("descriptor")) {
            Map descriptors = (Map) map.get("descriptor");
            for (Object key : descriptors.keySet()) {
                String locale = key.toString();
                String name = ((Expression) map.get(locale)).getText();
                card.newDescriptor(locale, name, name);
            }
        }
    }

    public void buildSkills(ElementDO element, MetaCardDO card, Map map) throws InvalidCardException {
        Object value = map.get("skill");
        if (value instanceof Map) {
            buildSkill(element, card, (Map) value);
        }
        if (value instanceof List) {
            for (Object def : (List) value) {
                if (def instanceof Map) {
                    buildSkill(element, card, (Map) def);
                }
            }
        }
    }

    public void buildSkill(ElementDO element, MetaCardDO card, Map map) throws InvalidCardException {
        String name = getString(map, "name");
        int cost = getInt(map, "cost");
        Function function = (Function) map.get("execute");
        Object value = map.get("targets");
        if (value instanceof List) {
            List<String> targets = new LinkedList<String>();
            for (Object v : (List) value) targets.add(((StringLiteral)v).getText());
            SkillDO skill = element.newSkill(name, targets, cost, function);
            card.addSkill(skill);
        }
        else {
            SkillDO skill = element.newSkill(name, Collections.<String>emptyList(), cost, function);
            card.addSkill(skill);
        }
    }

    public void buildAttributes(ElementDO element, Map map) throws InvalidCardException {
        Object value = map.get("attribute");
        if (value instanceof Map) {
            buildAttribute(element, (Map) value);
        }
        if (value instanceof List) {
            for (Object def : (List) value) {
                if (def instanceof Map) {
                    buildAttribute(element, (Map) def);
                }
            }
        }
    }

    public void buildAttribute(ElementDO element, Map map) throws InvalidCardException {
        AttributeDO attribute = element.newAttribute(getString(map, "name"),
                getBoolean(map, "visible"),
                buildAttributeHandlers(map, "validate"),
                buildAttributeHandlers(map, "before"),
                buildAttributeHandlers(map, "after"));

        if (map.containsKey("descriptor")) {
            Map descriptors = (Map) map.get("descriptor");
            for (Object key : descriptors.keySet()) {
                String locale = key.toString();
                String name = ((Expression) map.get(locale)).getText();
                attribute.newDescriptor(locale, name, name);
            }
        }
        if (attribute.getDescriptor() == null) {
            String name = ((Expression) map.get("name")).getText();
            attribute.newDescriptor(BoardContext.getLocale(), name, name);
        }
        PersistenceHelper.getPersistenceManager().makePersistent(attribute);
        PersistenceHelper.commit();
    }

    protected int getInt(Map map, String name) {
        Object value = map.get(name);
        if (value != null && value instanceof Expression) {
            Expression expr = (Expression) value;
            return Integer.parseInt(expr.getText());
        }
        return 0;
    }

    protected boolean getBoolean(Map map, String name) {
        Object value = map.get(name);
        if (value != null && value instanceof Expression) {
            Expression expr = (Expression) value;
            return Boolean.parseBoolean(expr.getText());
        }
        return false;
    }

    protected String getString(Map map, String name) {
        Object value = map.get(name);
        if (value != null && value instanceof Expression) {
            Expression expr = (Expression) value;
            return expr.getText();
        }
        return "Anonymous";
    }
}
