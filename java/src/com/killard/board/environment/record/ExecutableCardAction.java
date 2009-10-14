package com.killard.board.environment.record;

import com.killard.board.card.Action;
import com.killard.board.environment.ExecutableAction;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public abstract class ExecutableCardAction<A extends Action> implements ExecutableAction<AbstractMetaCardRecord, A> {

}
