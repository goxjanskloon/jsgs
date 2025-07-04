package goxjanskloon.jsgs.gui.logging;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.layout.PatternLayout;
public class TextAreaAppender extends AbstractAppender{
    private final TextArea textArea;
    private static final PatternLayout LAYOUT=PatternLayout.newBuilder().withPattern("%d{yyyy-MM-dd HH:mm:ss} [%t] %-5p [%c] - %m%n").build();
    public TextAreaAppender(TextArea textArea){
        super("TextAreaAppender",null,LAYOUT,false,null);
        this.textArea=textArea;
    }
    @Override public void append(LogEvent event){
        String msg=LAYOUT.toSerializable(event);
        Platform.runLater(()->textArea.appendText(msg));
    }
}