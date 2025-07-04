package goxjanskloon.jsgs.client.gui;
import goxjanskloon.jsgs.client.ClientMain;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
public class ClientBootstrapDialog extends Dialog<ClientMain>{
    public ClientBootstrapDialog(){
        setTitle("JSGS客户端");
        setHeaderText("启动JSGS客户端");
        var pane=getDialogPane();
        var bootButtonType=new javafx.scene.control.ButtonType("启动");
        pane.getButtonTypes().addAll(bootButtonType,ButtonType.CANCEL);
        var grid=new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("名称"),0,0);
        var name=new TextField();
        grid.add(name,1,0);
        grid.add(new Label("服务器地址"),0,1);
        var host=new TextField();
        grid.add(host,1,1);
        grid.add(new Label("端口"),0,2);
        var port=new TextField();
        grid.add(port,1,2);
        pane.setContent(grid);
        setResultConverter(type->type==bootButtonType?new ClientMain(name.getText(),host.getText(),Integer.parseInt(port.getText())):null);
    }
}
