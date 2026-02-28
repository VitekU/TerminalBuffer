package com.vitungermann.terminalbuffer.core;
import com.vitungermann.terminalbuffer.helper.CursorPosition;
import com.vitungermann.terminalbuffer.helper.Direction;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class TerminalBufferTest {
    @Test
    void testCursorMoveRight() {
        TerminalBuffer tb = new TerminalBuffer(2, 4, 100);
        int eRow = 1;
        int eColumn = 0;

        tb.moveCursor(Direction.RIGHT, 4);

        assertEquals(eRow, tb.getCursorPosition().row);
        assertEquals(eColumn, tb.getCursorPosition().column);
    }

    @Test
    void testCursorMoveDown() {
        TerminalBuffer tb = new TerminalBuffer(2, 4, 100);
        int eRow = 1;
        int eColumn = 0;

        tb.moveCursor(Direction.DOWN, 1);

        assertEquals(eRow, tb.getCursorPosition().row);
        assertEquals(eColumn, tb.getCursorPosition().column);
    }

    @Test
    void testCursorMoveLeft() {
        TerminalBuffer tb = new TerminalBuffer(2, 4, 100);
        int eRow = 0;
        int eColumn = 0;

        tb.moveCursor(Direction.LEFT, 1);

        assertEquals(eRow, tb.getCursorPosition().row);
        assertEquals(eColumn, tb.getCursorPosition().column);
    }

    @Test
    void testCursorMoveUp() {
        TerminalBuffer tb = new TerminalBuffer(2, 4, 100);
        int eRow = 0;
        int eColumn = 0;

        tb.moveCursor(Direction.UP, 1);

        assertEquals(eRow, tb.getCursorPosition().row);
        assertEquals(eColumn, tb.getCursorPosition().column);
    }
}
