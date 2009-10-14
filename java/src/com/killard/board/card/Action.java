package com.killard.board.card;

import java.io.Serializable;

/**
 * <p>
 * This class defines effect done by a skill.
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public interface Action<S extends Record, T extends Record> extends Serializable {
    public S getSource();
    public T getTarget();
}