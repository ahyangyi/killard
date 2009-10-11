package com.killard.environment.record;

import com.killard.card.Action;
import com.killard.environment.ExecutableAction;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public abstract class ExecutablePlayerAction <A extends Action> implements ExecutableAction<AbstractPlayerRecord, A> {

}
