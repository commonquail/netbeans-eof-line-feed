package org.commonquail.linefeed;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.Document;
import org.netbeans.api.annotations.common.NonNull;
import org.netbeans.api.editor.mimelookup.MimeRegistration;
import org.netbeans.spi.editor.document.OnSaveTask;

public final class EofLineFeed implements OnSaveTask {

    private static final int AFTER_TRAILING_WHITESPACE_REMOVAL = 1100;

    public static final String LF = "\n";

    private static final Logger logger = Logger.getLogger(
            EofLineFeed.class.getName()
    );

    private final Document document;

    public EofLineFeed() {
        this(null);
    }

    public EofLineFeed(Document document) {
        this.document = document;
    }

    @Override
    public void performTask() {
        logger.log(Level.INFO, "EofLineFeed.performTask()");
        logger.log(Level.INFO, "Document: {0}", document);
    }

    @Override
    public void runLocked(@NonNull Runnable run) {
        logger.log(Level.INFO, "EofLineFeed.runLocked()");
        run.run();
    }

    @Override
    public boolean cancel() {
        logger.log(Level.INFO, "EofLineFeed.cancel()");
        return false;
    }

    @MimeRegistration(mimeType = "", service = OnSaveTask.Factory.class,
            position = AFTER_TRAILING_WHITESPACE_REMOVAL)
    public static final class FactoryImpl implements Factory {
        @Override
        public OnSaveTask createTask(@NonNull Context context) {
            return new EofLineFeed(context.getDocument());
        }
    }

}
