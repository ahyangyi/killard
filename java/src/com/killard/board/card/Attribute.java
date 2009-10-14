package com.killard.board.card;

import com.killard.board.environment.event.ActionListener;

public interface Attribute<T extends Attribute> extends ActionListener<T> {

    public String getName();

    public ElementSchool getElementSchool();

    public boolean isVisible();

    public boolean isUseful();

    public boolean isHarmful();
}
