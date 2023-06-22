/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.menu;

import javax.swing.Icon;
import javax.swing.JPanel;
import main.util.IconHelper;

/**
 *
 * @author aldes
 */
public class MenuItem {
    protected MenuType type;
    protected String title;
    protected String componentName;
    protected JPanel component;
    protected String iconName;
    protected Icon icon;
    
    public MenuItem(MenuType type) {
        this.type = type;
    }
    
    public MenuItem(MenuType type, String title) {
        this.type = type;
        this.title = title;
    }

    public MenuItem(MenuType type, String title, String component) {
        this.type = type;
        this.title = title;
        this.componentName = component;
    }

    public MenuItem(MenuType type, String title, String component, String iconName) {
        this.type = type;
        this.title = title;
        this.componentName = component;
        this.iconName = iconName;
    }
    
    

    public MenuType getType() {
        return type;
    }

    public void setType(MenuType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public MenuItem setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getComponentName() {
        return componentName;
    }

    public MenuItem setComponentName(String component) {
        this.componentName = component;
        return this;
    }

    public String getIconName() {
        return iconName;
    }

    public MenuItem setIconName(String iconName) {
        this.iconName = iconName;
        return this;
    }
    
    public Object getComponent() {        
        try {
            Object com = Class.forName(this.componentName)
                    .getConstructor()
                    .newInstance();
            
            this.component = (JPanel) com;
        } catch (Exception ex) {
            return null;
        }
        
        return this.component;
    }
    
    public Object getIcon() {
        if (this.icon == null && getIconName() != null) {
            this.icon = IconHelper.getIcon("menu/" + getIconName());
        }
        
        return this.icon;
    }
}
