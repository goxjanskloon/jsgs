package goxjanskloon.jsgs.server.gui;
import goxjanskloon.jsgs.gui.FontUtil;
import goxjanskloon.jsgs.gui.logging.TextAreaAppender;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LoggerContext;
public class ServerApp extends Application{
    @Override public void start(Stage stage)throws Exception{
        var opt=new ServerBootstrapDialog().showAndWait();
        if(opt.isPresent()){
            var server=opt.get();
            var logCtx=LoggerContext.getContext(false);
            var logCfg=logCtx.getConfiguration();
            var logArea=new TextArea();
            logArea.setEditable(false);
            logArea.setFont(FontUtil.load("JetBrains Mono",12));
            var logAppender=new TextAreaAppender(logArea);
            logAppender.start();
            var rootLogger=logCfg.getRootLogger();
            rootLogger.setLevel(Level.DEBUG);
            for(var p:rootLogger.getAppenders().entrySet())
                rootLogger.removeAppender(p.getKey());
            rootLogger.addAppender(logAppender,Level.DEBUG,null);
            logCfg.addAppender(logAppender);
            logCtx.updateLoggers();
            var logTab=new Tab("日志",logArea);
            logTab.setClosable(false);
            stage.setScene(new Scene(new TabPane(logTab)));
            stage.setTitle("JSGS服务端 - "+server.name);
            stage.setWidth(1000);
            stage.setHeight(600);
            stage.show();
            server.start();
            server.injectAfterClose.inject(_->stage.close());
            stage.setOnCloseRequest(_->server.close());
        }else
            stop();
    }
    public static void main(String[] args){
        launch(args);
    }
}