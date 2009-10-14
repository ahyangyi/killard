package com.killard.board.card.attribute;

import com.killard.board.card.Action;
import com.killard.board.card.Attribute;
import com.killard.board.card.CardInstance;
import com.killard.board.card.action.AddCardAttributeAction;
import com.killard.board.card.action.RemoveCardAttributeAction;
import com.killard.board.environment.ActionValidator;
import com.killard.board.environment.AfterAction;

import java.util.ArrayList;
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
public class UniqueAttribute extends BasicAttribute {

    public UniqueAttribute() {
    }

    public UniqueAttribute(boolean hidden) {
        super(hidden);
    }

    @AfterAction(actionClass = AddCardAttributeAction.class)
    protected List<Action> after(CardInstance owner, AddCardAttributeAction action) {
        List<Action> actions = new ArrayList<Action>();
        for (Attribute attribute : owner.getVisibleAttributes()) {
            if (!attribute.isVisible() && attribute != action.getAttribute()) {
                actions.add(new RemoveCardAttributeAction(owner, owner, attribute));
            }
        }
        return actions;
    }

    @ActionValidator(actionClass = AddCardAttributeAction.class, selfTargeted = false)
    protected boolean validate(CardInstance card, AddCardAttributeAction action) {
        return false;
    }

}
