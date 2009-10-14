package com.killard.board.card.attribute;

import com.killard.board.card.Action;
import com.killard.board.card.Attribute;
import com.killard.board.card.CardInstance;
import com.killard.board.card.ElementSchool;

import java.util.List;

/**
 * <p>
 * This class defines basic base for all card attributes.
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public abstract class BasicAttribute implements Attribute {

    private final ElementSchool elementSchool;
    
    private final boolean visible;

    private final boolean useful;

    private final boolean harmful;

    protected BasicAttribute(ElementSchool elementSchool) {
        this.elementSchool = elementSchool;
        visible = false;
        useful = false;
        harmful = false;
    }

    protected BasicAttribute(ElementSchool elementSchool, boolean visible) {
        this.elementSchool = elementSchool;
        this.visible = visible;
        useful = false;
        harmful = false;
    }

    protected BasicAttribute(ElementSchool elementSchool, boolean visible, boolean useful) {
        this.elementSchool = elementSchool;
        this.visible = visible;
        this.useful = useful;
        harmful = false;
    }

    protected BasicAttribute(ElementSchool elementSchool, boolean visible, boolean useful, boolean harmful) {
        this.elementSchool = elementSchool;
        this.visible = visible;
        this.useful = useful;
        this.harmful = harmful;
    }

    public String getName() {
        return getClass().getSimpleName();
    }

    public ElementSchool getElementSchool() {
        return elementSchool;
    }

    public boolean isVisible() {
        return visible;
    }

    public boolean isUseful() {
        return useful;
    }

    public boolean isHarmful() {
        return harmful;
    }

    public Class action() {
        return Action.class;
    }

    public boolean selfTargeted() {
        return true;
    }

    public List<Action> validate(CardInstance owner, Action action) {
        return null;
    }

    public List<Action> before(CardInstance owner, Action action) {
        return null;
    }

    public List<Action> after(CardInstance owner, Action action) {
        return null;
    }
}
