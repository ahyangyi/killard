package com.killard.jdo;

import com.killard.board.card.AttackType;
import com.killard.jdo.card.AttributeDO;
import com.killard.jdo.card.descriptor.AttributeDescriptorDO;
import com.killard.jdo.card.CardDO;
import com.killard.jdo.card.descriptor.CardDescriptorDO;
import com.killard.jdo.card.ElementSchoolDO;
import com.killard.jdo.card.PackageDO;
import com.killard.jdo.card.RuleDO;
import com.killard.jdo.card.SkillDO;
import com.killard.jdo.context.BoardContext;
import com.killard.board.parser.Expression;
import com.killard.board.parser.Function;
import com.killard.board.parser.Node;

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

    public RuleDO buildRule(PackageDO pack, Map map) throws InvalidCardException {
        return new RuleDO(pack,
                buildAttributeHandlers(map, "validate"),
                buildAttributeHandlers(map, "before"),
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

    public void buildCard(ElementSchoolDO elementSchool, CardDO card, Map map) throws InvalidCardException {

        card.setLevel(getInt(map, "level"));
        card.setMaxHealth(getInt(map, "health"));
        card.setAttackType(AttackType.PHYSICAL);
        card.setAttackValue(getInt(map, "attack"));

        buildSkills(card, map);
        buildAttributes(elementSchool, map);

        if (map.containsKey("descriptor")) {
            Map descriptors = (Map) map.get("descriptor");
            for (Object key : descriptors.keySet()) {
                CardDescriptorDO descriptor = new CardDescriptorDO(card, key.toString());
                Map descMap = (Map) descriptors.get(key);
                descriptor.setName(((Expression) descMap.get("name")).getText());
                card.addDescriptor(descriptor);
            }
        }
    }

    public void buildSkills(CardDO card, Map map) throws InvalidCardException {
        Object value = map.get("skill");
        if (value instanceof Map) {
            buildSkill(card, (Map) value);
        }
        if (value instanceof List) {
            for (Object def : (List) value) {
                if (def instanceof Map) {
                    buildSkill(card, (Map) def);
                }
            }
        }
    }

    public void buildSkill(CardDO card, Map map) throws InvalidCardException {
        String id = getString(map, "id");
        int cost = getInt(map, "cost");
        Function function = (Function) map.get("execute");
        SkillDO skill = new SkillDO(id, card, cost, function);
        card.addSkill(skill);
    }

    public void buildAttributes(ElementSchoolDO elementSchool, Map map) throws InvalidCardException {
        Object value = map.get("attribute");
        if (value instanceof Map) {
            buildAttribute(elementSchool, (Map) value);
        }
        if (value instanceof List) {
            for (Object def : (List) value) {
                if (def instanceof Map) {
                    buildAttribute(elementSchool, (Map) def);
                }
            }
        }
    }

    public void buildAttribute(ElementSchoolDO elementSchool, Map map) throws InvalidCardException {
        String id = getString(map, "id");
        boolean visible = getBoolean(map, "visible");
        boolean useful = getBoolean(map, "useful");
        boolean harmful = getBoolean(map, "harmful");
        AttributeDO attribute = new AttributeDO(id, elementSchool, visible, useful, harmful,
                buildAttributeHandlers(map, "validate"),
                buildAttributeHandlers(map, "before"),
                buildAttributeHandlers(map, "after"));
        PersistenceHelper.getPersistenceManager().makePersistent(attribute);
        elementSchool.addAttribute(attribute);

        if (map.containsKey("descriptor")) {
            Map descriptors = (Map) map.get("descriptor");
            for (Object key : descriptors.keySet()) {
                AttributeDescriptorDO descriptor = new AttributeDescriptorDO(attribute, key.toString());
                Map descMap = (Map) descriptors.get(key);
                descriptor.setName(((Expression) descMap.get("name")).getText());
                attribute.addDescriptor(descriptor);
            }
        }
        if (attribute.getDescriptor() == null) {
            AttributeDescriptorDO descriptor = new AttributeDescriptorDO(attribute, BoardContext.getLocale());
            descriptor.setName(((Expression) map.get("id")).getText());
            attribute.addDescriptor(descriptor);
        }
        PersistenceHelper.getPersistenceManager().makePersistent(attribute);
        PersistenceHelper.doTransaction();
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
