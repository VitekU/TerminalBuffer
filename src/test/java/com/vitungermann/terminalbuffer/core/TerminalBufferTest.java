package com.vitungermann.terminalbuffer.core;
import com.vitungermann.terminalbuffer.helper.CursorPosition;
import com.vitungermann.terminalbuffer.helper.Direction;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class TerminalBufferTest {
    @Test
    void testWriteToScreen() {
        TerminalBuffer tb = new TerminalBuffer(2, 4, 100);
        tb.write("hello world");
        String result = "o wo\nrld \n";
        assertEquals(result, tb.getScreen());
    }

    @Test
    void testScrollback() {
        TerminalBuffer tb = new TerminalBuffer(2, 4, 100);
        tb.write("hello world");
        String result = "hell\n";
        assertEquals(result, tb.getScrollback());
    }

    @Test
    void testCursorMove() {
        TerminalBuffer tb = new TerminalBuffer(2, 4, 100);
        int eRow = 1;
        int eColumn = 0;

        tb.moveCursor(Direction.RIGHT, 4);

        assertEquals(eRow, tb.getCursorPosition().row);
        assertEquals(eColumn, tb.getCursorPosition().column);
    }
}
