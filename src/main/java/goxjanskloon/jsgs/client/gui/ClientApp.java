package goxjanskloon.jsgs.client.gui;
import javafx.application.Application;
import javafx.stage.Stage;
public class ClientApp extends Application{
    @Override public void start(Stage stage)throws InterruptedException{
        var opt=new ClientBootstrapDialog().showAndWait();
        if(opt.isPresent()){
            var client=opt.get();
            client.start();
            Thread.sleep(5000);
            client.close();
        }
    }
    public static void main(String[] args){
        launch(args);
    }
}
