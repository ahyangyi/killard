package com.killard.parser;

/**
 * <p>
 * This class defines .
 * </p>
 * <p>
 * <strong>Thread safety:</strong>
 * This class is mutable and not thread safe.
 * </p>
 */
public class FlowControl {

    private boolean loopContinue = false;

    private boolean loopBreak = false;

    private String loopLabel;

    private boolean functionReturn = false;

    public boolean isContinue() {
        return loopContinue;
    }

    public void setContinue(String label) {
        loopLabel = label;
        loopContinue = true;
    }

    public boolean isBreak() {
        return loopBreak;
    }

    public void setBreak(String label) {
        loopLabel = label;
        loopBreak = true;
    }

    public String getLoopLabel() {
        return loopLabel;
    }

    public boolean checkBreak(String label) {
        boolean result = isBreak() || isReturn() ||
                (isContinue() && getLoopLabel() != null && !getLoopLabel().equals(label));
        if (isBreak() || isContinue() || isReturn()) {
            if (getLoopLabel() == null || getLoopLabel().equals(label)) {
                loopContinue = false;
                loopBreak = false;
                loopLabel = null;
            }
        }
        return result;
    }

    public boolean isReturn() {
        return functionReturn;
    }

    public void setReturn() {
        functionReturn = true;
    }

}
