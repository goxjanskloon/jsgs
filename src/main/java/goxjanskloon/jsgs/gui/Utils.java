package goxjanskloon.jsgs.gui;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.text.Font;
import javafx.stage.Window;
public interface Utils{
    static Font loadFont(String family,double size){
        if(!Font.getFamilies().contains(family))
            Font.loadFont(Utils.class.getResourceAsStream(family+".ttf"),size);
        return Font.font(family,size);
    }
    static void confirmOnClose(Window window,Runnable onClose){
        window.setOnCloseRequest(event->{
            var alert=new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("确定要关闭?");
            alert.showAndWait().ifPresent(type->{
                if(type==ButtonType.OK){
                    window.hide();
                    onClose.run();
                }
                else
                    event.consume();
            });
        });
    }
}