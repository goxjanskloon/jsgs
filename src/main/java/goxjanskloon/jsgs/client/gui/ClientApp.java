package goxjanskloon.jsgs.client.gui;
import goxjanskloon.jsgs.gui.Utils;
import goxjanskloon.jsgs.gui.logging.LoggingArea;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
public class ClientApp extends Application{
    @Override public void start(Stage stage){
        var opt=new ClientBootstrapDialog().showAndWait();
        if(opt.isPresent()){
            var client=opt.get();
            client.injectAfterClose.inject(_->{
                new Alert(Alert.AlertType.INFORMATION,"连接已断开").showAndWait();
                Platform.exit();
            });
            stage.setTitle("JSGS客户端 - "+client.name);
            Utils.confirmOnClose(stage,client::close);
            stage.setScene(new Scene(new LoggingArea()));
            stage.setWidth(1000);
            stage.setHeight(600);
            stage.show();
            client.start();
        }
    }
    public static void main(String[] args){
        launch(args);
    }
}
