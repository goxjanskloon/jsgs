package goxjanskloon.jsgs.server.gui;
import goxjanskloon.jsgs.gui.Utils;
import goxjanskloon.jsgs.gui.logging.LoggingArea;
import goxjanskloon.jsgs.server.Client;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
public class ServerApp extends Application{
    @Override public void start(Stage stage){
        var opt=new ServerBootstrapDialog().showAndWait();
        if(opt.isPresent()){
            var server=opt.get();
            var logTab=new Tab("日志",new LoggingArea());
            logTab.setClosable(false);
            var clientsList=new ListView<Client>();
            Client.AFTER_REGISTRATION.inject(client->{
                Platform.runLater(()->clientsList.getItems().add(client));
                client.afterDisconnection.inject(_->Platform.runLater(()->clientsList.getItems().remove(client)));
            });
            var disconnectButton=new Button("断开连接");
            disconnectButton.setDisable(true);
            disconnectButton.setOnMouseClicked(_->{
                for(var client:clientsList.getSelectionModel().getSelectedItems())
                    if(client!=null)
                        server.disconnect(client);
            });
            clientsList.setCellFactory(_->new ListCell<>(){
                @Override protected void updateItem(Client client,boolean empty){
                    super.updateItem(client,empty);
                    setText(client==null?null:client.name+" (ID:"+client.id+")");
                }
                @Override public void updateSelected(boolean selected){
                    super.updateSelected(selected);
                    disconnectButton.setDisable(!selected);
                }
            });
            var clientsTab=new Tab("客户端列表",new VBox(clientsList,disconnectButton));
            clientsTab.setClosable(false);
            stage.setScene(new Scene(new TabPane(logTab,clientsTab)));
            stage.setTitle("JSGS服务端 - "+server.name);
            stage.setWidth(1000);
            stage.setHeight(600);
            Utils.confirmOnClose(stage,server::close);
            stage.show();
            server.afterClose.inject(_->Platform.exit());
            server.start();
        }else
            Platform.exit();
    }
    public static void main(String[] args){
        launch(args);
    }
}