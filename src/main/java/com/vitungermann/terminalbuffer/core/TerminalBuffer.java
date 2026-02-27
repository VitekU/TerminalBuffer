package com.vitungermann.terminalbuffer.core;

import com.vitungermann.terminalbuffer.helper.CursorPosition;
import com.vitungermann.terminalbuffer.helper.Direction;
import com.vitungermann.terminalbuffer.helper.Style;
import com.vitungermann.terminalbuffer.helper.TerminalColor;
import java.util.ArrayList;
import java.util.List;

import static com.vitungermann.terminalbuffer.helper.MathHelp.clamp;

public class TerminalBuffer {
    private final int height;
    private final int width;

    private final int maxScrollback;
    private CursorPosition cursorPosition;

    private Scrollback scrollback;

    public String getScrollback() {
        return scrollback.getScrollback();
    }

    public CursorPosition getCursorPosition() {
        return cursorPosition;
    }

    public void setCursorPosition(int row, int column) {
        this.cursorPosition.row = row;
        this.cursorPosition.column = column;
    }

    public void moveCursor(Direction direction, int n) {
        int newRow = cursorPosition.row;
        int newColumn = cursorPosition.column;
        if (direction == Direction.UP) {
            newRow -= n;
        }
        else if (direction == Direction.DOWN) {
            newRow += n;
        }
        else if (direction == Direction.RIGHT) {
            newColumn = (cursorPosition.column + n) % width;
            newRow += (cursorPosition.column + n) / width;
        }
        else {
            newColumn = cursorPosition.column - n;
            if (newColumn < 0) {
                newRow -= (-newColumn / width + 1);
                newColumn = (newColumn % width) + width;

            }
        }

        this.cursorPosition.row = clamp(newRow, height);
        this.cursorPosition.column = clamp(newColumn, width);
    }


    public TerminalBuffer(int height, int width, int maxScrollback) {
        this.height = height;
        this.width = width;
        this.maxScrollback = maxScrollback;
        cursorPosition = new CursorPosition(0,0);

        this.scrollback = new Scrollback(maxScrollback);
    }

}
