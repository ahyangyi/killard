package com.killard.card;

/**
 * <p>
 * This interface defines card package.
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * The implementations are not required to be thread safe.
 * </p>
 */
public interface BoardPackage {

    public String getName();

    public ElementSchool[] getElementSchools();

}
