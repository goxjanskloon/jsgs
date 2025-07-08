package goxjanskloon.jsgs.gui.logging;
import goxjanskloon.jsgs.gui.Utils;
import javafx.scene.control.TextArea;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LoggerContext;
public class LoggingArea extends TextArea{
    public LoggingArea(){
        setEditable(false);
        setFont(Utils.loadFont("JetBrains Mono",12));
        var logAppender=new TextAreaAppender(this);
        logAppender.start();
        var logCtx=LoggerContext.getContext(false);
        var logCfg=logCtx.getConfiguration();
        var rootLogger=logCfg.getRootLogger();
        rootLogger.setLevel(Level.DEBUG);
        for(var p:rootLogger.getAppenders().entrySet())
            rootLogger.removeAppender(p.getKey());
        rootLogger.addAppender(logAppender,Level.DEBUG,null);
        logCfg.addAppender(logAppender);
        logCtx.updateLoggers();
    }
}