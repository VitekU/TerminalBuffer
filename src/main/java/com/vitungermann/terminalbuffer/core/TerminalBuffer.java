package com.vitungermann.terminalbuffer.core;

import com.vitungermann.terminalbuffer.helper.CursorPosition;
import com.vitungermann.terminalbuffer.helper.Direction;
import com.vitungermann.terminalbuffer.helper.Style;
import com.vitungermann.terminalbuffer.helper.TerminalColor;

import java.util.LinkedList;

public class TerminalBuffer {
    private final int height;
    private final int width;

    private int maxScrollback;
    private CursorPosition cursorPosition;
    private LinkedList<CharacterCell[]> screen;
    private LinkedList<CharacterCell[]> scrollback;

    private TerminalColor currentForeground;
    private TerminalColor currentBackground;
    private Style currentStyle;

    public void MoveCursor(Direction direction, int n) {
        if (direction == Direction.UP) {
            cursorPosition.row = cursorClamp(cursorPosition.row - n, height);
        }
        else if (direction == Direction.DOWN) {
            cursorPosition.row = cursorClamp(cursorPosition.row + n, height);
        }
        else if (direction == Direction.RIGHT) {
            cursorPosition.column = cursorClamp(cursorPosition.column + n, width);
        }
        else {
            cursorPosition.column = cursorClamp(cursorPosition.column - n, width);
        }

    }

    private static int cursorClamp(int n, int max) {
        return Math.max(0, Math.min(max - 1, n));
    }

    public TerminalBuffer(int height, int width, int maxScrollback) {
        this.height = height;
        this.width = width;
        this.maxScrollback = maxScrollback;

    }

}
