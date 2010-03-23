package com.killard.board.card;

import com.killard.board.environment.event.ActionListener;

public interface Attribute<T extends Attribute> extends ActionListener<T> {

    public String getName();

    public Element getElement();

    public boolean isVisible();
}
