package org.commonquail.linefeed;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import org.junit.Test;
import org.netbeans.junit.NbTestCase;
import org.netbeans.modules.editor.NbEditorDocument;

public class EofLineFeedTest extends NbTestCase {

    public EofLineFeedTest(String testName) {
        super(testName);
    }

    @Test
    public void testShouldDoNothingWithNullConstructorArgument() {
        EofLineFeed instance = new EofLineFeed(null);
        instance.performTask();
    }

    @Test
    public void testShouldDoNothingWithNoConstructorArgument() {
        EofLineFeed instance = new EofLineFeed();
        instance.performTask();
    }

    @Test
    public void testShouldAppendLineFeedToEmptyDocument() {
        Document d = createNewDocument();
        assertEquals("Document is empty before line-feed parsing.",
                0, d.getLength());

        EofLineFeed instance = new EofLineFeed(d);
        instance.performTask();

        assertEquals("Document text length is 1 after line-feed parsing.",
                1, d.getLength());
        try {
            assertEquals("Document text is line-feed after line-feed parsing.",
                    EofLineFeed.LF, d.getText(0, 1));
        } catch (BadLocationException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testShouldAppendLineFeedToNonEmptyDocumentThatDoesNotEndWithLineFeed() {
        Document d = createNewDocument();
        try {
            String doesNotEndWithLineFeed = "a";
            d.insertString(0, doesNotEndWithLineFeed, null);
        } catch (BadLocationException ex) {
            fail(ex.getMessage());
        }

        int textLengthBefore = d.getLength();

        EofLineFeed instance = new EofLineFeed(d);
        instance.performTask();

        int textLengthAfter = d.getLength();
        assertEquals(
                "Document text length has increased by 1 after line-feed parsing.",
                1, textLengthAfter - textLengthBefore);

        try {
            assertEquals(
                    "Document text ends with line-feed after line-feed parsing.",
                    EofLineFeed.LF, d.getText(textLengthBefore, 1));
        } catch (BadLocationException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testShouldAppendNothingToDocumentThatEndsWithLineFeed() {
        Document d = createNewDocument();
        try {
            d.insertString(0, EofLineFeed.LF, null);
        } catch (BadLocationException ex) {
            fail(ex.getMessage());
        }

        try {
            int textLength = d.getLength();
            assertEquals("Document text length is 1 before line-feed parsing.",
                    1, textLength);
            assertEquals("Document text is line-feed before line-feed parsing.",
                    EofLineFeed.LF, d.getText(0, textLength));
        } catch (BadLocationException ex) {
            fail(ex.getMessage());
        }

        EofLineFeed instance = new EofLineFeed(d);
        instance.performTask();

        try {
            int textLength = d.getLength();
            assertEquals("Document text length is 1 after line-feed parsing.",
                    1, textLength);
            assertEquals("Document text is line-feed after line-feed parsing.",
                    EofLineFeed.LF, d.getText(0, textLength));
        } catch (BadLocationException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testCanRunUnderLockWhenDefaultConstructed() {
        EofLineFeed instance = new EofLineFeed();
        final Runnable run = new Runnable() {
            @Override
            public void run() {
            }
        };
        instance.runLocked(run);
    }

    @Test
    public void testCanRunUnderLockWhenNotDefaultConstructed() {
        EofLineFeed instance = new EofLineFeed(createNewDocument());
        final Runnable run = new Runnable() {
            @Override
            public void run() {
            }
        };
        instance.runLocked(run);
    }

    @Test
    public void testCanNotCancelTask() {
        EofLineFeed instance = new EofLineFeed(createNewDocument());
        boolean expResult = false;
        boolean result = instance.cancel();
        assertEquals(expResult, result);
    }

    private Document createNewDocument() {
        return new NbEditorDocument("text/plain");
    }
}
