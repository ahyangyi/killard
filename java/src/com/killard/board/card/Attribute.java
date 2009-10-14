package com.killard.board.card;

import com.killard.board.environment.event.ActionListener;

public interface Attribute extends ActionListener {

    public String getName();

    public boolean isVisible();

    public boolean isUseful();

    public boolean isHarmful();
}
