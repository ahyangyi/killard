package com.killard.card;

import com.killard.environment.event.ActionListener;

public interface Attribute extends ActionListener {

    public String getName();

    public boolean isVisible();

    public boolean isUseful();

    public boolean isHarmful();
}
