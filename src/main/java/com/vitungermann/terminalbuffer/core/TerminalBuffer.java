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

    private Screen screen;

    public String getScrollback() {
        return scrollback.getScrollback();
    }
    public String getScreen() {return  screen.getScreen();}

    public CursorPosition getCursorPosition() {
        return cursorPosition;
    }

    public void setCursorPosition(int row, int column) {
        cursorPosition.row = row;
        cursorPosition.column = column;
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
            if (newColumn < 0 && newRow != 0) {
                newRow -= (-newColumn / width + 1);
                newColumn = (newColumn % width) + width;

            }
        }

        cursorPosition.row = clamp(newRow, height);
        cursorPosition.column = clamp(newColumn, width);
    }
    
    public void write(String text) {
        for (var row : screen.write(text)) {
            scrollback.add((CharacterCell[]) row.toArray());
        }
    }

    public void insert(String text, CursorPosition cursorPosition) {
        for (var row : screen.insert(text, cursorPosition)) {
            scrollback.add((CharacterCell[]) row.toArray());
        }
    }

    public void fillLine(char character, int line) {
        for (var row: screen.fillLine(character, line)) {
            scrollback.add((CharacterCell[]) row.toArray());
        }
    }

    public void insertEmptyLine() {
        for (var row : screen.insertEmptyLine()) {
            scrollback.add((CharacterCell[]) row.toArray());
        }
    }

    public void clearScreen() {
        screen.clear();
    }

    public void clearScreenAndScrollback() {
        screen.clear();
        scrollback.clear();
    }

    public void setScreenForeground(TerminalColor foreground) {
        screen.setCurrentForeground(foreground);
    }

    public void setScreenBackground(TerminalColor background) {
        screen.setCurrentBackground(background);
    }

    public void setScreenStyle(Style style) {
        screen.setCurrentStyle(style);
    }

    public TerminalBuffer(int height, int width, int maxScrollback) {
        this.height = height;
        this.width = width;
        this.maxScrollback = maxScrollback;
        cursorPosition = new CursorPosition(0,0);
        screen = new Screen(height, width, DefaultValues.defaultForeground, DefaultValues.defaultBackground, DefaultValues.defaultStyle);
        scrollback = new Scrollback(maxScrollback);
    }

}
