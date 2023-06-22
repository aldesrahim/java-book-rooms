/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.validation.rule;

/**
 *
 * @author aldes
 */
public interface Rule {
    public boolean validate(Object component);
    
    public String getErrorMessage();
}
