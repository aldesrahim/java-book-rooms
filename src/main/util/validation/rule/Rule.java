package main.util.validation.rule;

/**
 *
 */
public interface Rule {
    public boolean validate(Object component);
    
    public String getErrorMessage();
}
