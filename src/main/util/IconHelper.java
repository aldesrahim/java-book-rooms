package main.util;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.ui.FlatUIUtils;
import java.awt.Color;

/**
 *
 * @author aldes
 */
public class IconHelper {
    public static FlatSVGIcon getIcon(String iconName) {
        
        Color lightColor = FlatUIUtils.getUIColor("Menu.icon.lightColor", Color.red);
        Color darkColor = FlatUIUtils.getUIColor("Menu.icon.darkColor", Color.red);

        FlatSVGIcon svgIcon = new FlatSVGIcon("resource/icon/" + iconName + ".svg");

        FlatSVGIcon.ColorFilter f = new FlatSVGIcon.ColorFilter();
        f.add(Color.decode("#969696"), lightColor, darkColor);
        svgIcon.setColorFilter(f);

        return svgIcon;
    }
}
