package org.commonquail.linefeed;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import org.netbeans.api.annotations.common.NonNull;
import org.netbeans.api.editor.mimelookup.MimeRegistration;
import org.netbeans.spi.editor.document.OnSaveTask;
import org.openide.util.Exceptions;

public final class EofLineFeed implements OnSaveTask {

    private static final int AFTER_TRAILING_WHITESPACE_REMOVAL = 1100;

    public static final String LF = "\n";

    private static final Logger logger = Logger.getLogger(
            EofLineFeed.class.getName()
    );

    private final Document document;

    /**
     * Creates a no-op EOF line-feed guarantee.
     */
    public EofLineFeed() {
        this(null);
    }

    /**
     * Creates a new EOF line-feed guarantee for the given document.
     *
     * If document is null the class' task becomes a no-op.
     *
     * @param document The document to process.
     */
    public EofLineFeed(Document document) {
        this.document = document;
    }

    @Override
    public void performTask() {
        if (document == null) {
            return;
        }

        try {
            int length = document.getLength();

            if (length == 0) {
                document.insertString(0, LF, null);
            } else if (!document.getText(length - 1, 1).equals(LF)) {
                document.insertString(length, LF, null);
            }
        } catch (BadLocationException ex) {
            Exceptions.printStackTrace(ex);
            logger.log(Level.SEVERE, ex.getMessage());
        }
    }

    @Override
    public void runLocked(@NonNull Runnable run) {
        run.run();
    }

    @Override
    public boolean cancel() {
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
