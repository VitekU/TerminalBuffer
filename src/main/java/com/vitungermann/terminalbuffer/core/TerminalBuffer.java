package com.vitungermann.terminalbuffer.core;

import com.vitungermann.terminalbuffer.helper.CursorPosition;
import com.vitungermann.terminalbuffer.helper.Direction;
import com.vitungermann.terminalbuffer.helper.Style;
import com.vitungermann.terminalbuffer.helper.TerminalColor;

import java.util.LinkedList;

public class TerminalBuffer {
    private final int height;
    private final int width;

    private final int maxScrollback;
    private CursorPosition cursorPosition;
    private LinkedList<CharacterCell[]> screen;
    private LinkedList<CharacterCell[]> scrollback;

    private TerminalColor currentForeground;
    private TerminalColor currentBackground;
    private Style currentStyle;

    public void MoveCursor(Direction direction, int n) {
        int newRow = cursorPosition.row;
        int newColumn = cursorPosition.column;

        if (direction == Direction.UP) {
            newRow = cursorPosition.row - n;
        }
        else if (direction == Direction.DOWN) {
            newRow = cursorPosition.row + n;
        }
        else if (direction == Direction.RIGHT) {
            newColumn = (cursorPosition.column + n) % width;
            newRow = (cursorPosition.row + n) / width;
        }
        else {
            newColumn = cursorPosition.column - n;
            if (newColumn < 0) {
                newColumn += width;
                newRow -= (newColumn % width + 1);
            }
        }
        cursorPosition.row = cursorClamp(newRow, height);
        cursorPosition.column = cursorClamp(newColumn, width);
    }

    private static int cursorClamp(int n, int max) {
        return Math.max(0, Math.min(max - 1, n));
    }

    public TerminalBuffer(int height, int width, int maxScrollback) {
        this.height = height;
        this.width = width;
        this.maxScrollback = maxScrollback;
        cursorPosition = new CursorPosition(0,0);

        this.screen = new LinkedList<>();
        for (int i = 0; i < height; i++) {
            CharacterCell[] row = new CharacterCell[width];
            for (int j = 0; j < width; j++) {
                row[j] = new CharacterCell(DefaultValues.defaultChar, DefaultValues.defaultForeground, DefaultValues.defaultBackground, DefaultValues.defaultStyle);
            }
            this.screen.add(row);
        }

    }

}
