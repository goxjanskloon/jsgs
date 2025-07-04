package goxjanskloon.jsgs.server.gui;
import goxjanskloon.jsgs.server.ServerMain;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
public class ServerBootstrapDialog extends Dialog<ServerMain>{
    public ServerBootstrapDialog(){
        setTitle("JSGS服务端");
        setHeaderText("启动JSGS服务端");
        var pane=getDialogPane();
        var bootButtonType=new ButtonType("启动");
        pane.getButtonTypes().addAll(bootButtonType,ButtonType.CANCEL);
        var grid=new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("名称"),0,0);
        var name=new TextField();
        grid.add(name,1,0);
        grid.add(new Label("端口"),0,1);
        var port=new TextField();
        port.setPromptText("一般1025~65535都可以");
        grid.add(port,1,1);
        pane.setContent(grid);
        setResultConverter(type->type==bootButtonType?new ServerMain(Integer.parseInt(port.getText()),name.getText()):null);
    }
}