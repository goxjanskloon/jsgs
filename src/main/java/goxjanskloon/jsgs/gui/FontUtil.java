package goxjanskloon.jsgs.gui;
import javafx.scene.text.Font;
public interface FontUtil{
    static Font load(String family,double size){
        if(!Font.getFamilies().contains(family))
            Font.loadFont(FontUtil.class.getResourceAsStream(family+".ttf"),size);
        return Font.font(family,size);
    }
}