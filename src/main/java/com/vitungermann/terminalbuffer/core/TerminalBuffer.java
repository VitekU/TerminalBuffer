package com.vitungermann.terminalbuffer.core;

import com.vitungermann.terminalbuffer.helper.CursorPosition;
import com.vitungermann.terminalbuffer.helper.Direction;
import com.vitungermann.terminalbuffer.helper.Style;
import com.vitungermann.terminalbuffer.helper.TerminalColor;
import java.util.ArrayList;

import static com.vitungermann.terminalbuffer.helper.MathHelp.clamp;

public class TerminalBuffer {
    private final int height;
    private final int width;

    private final int maxScrollback;
    private CursorPosition cursorPosition;
    private ArrayList<CharacterCell[]> screen;
    private Scrollback scrollback;

    private TerminalColor currentForeground;
    private TerminalColor currentBackground;
    private Style currentStyle;

    public String getScrollback() {
        return scrollback.getScrollback();
    }
    public String getScreen() {
        StringBuilder sb = new StringBuilder();
        for (CharacterCell[] row : screen) {
            for (CharacterCell cc : row) {
               if (cc == null) {
                   sb.append(" ");
               }
               else {
                   sb.append(cc.getChar());
               }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public CursorPosition getCursorPosition() {
        return cursorPosition;
    }

    public void addEmptyLine() {
        CharacterCell[] row = new CharacterCell[width];
        this.screen.add(row);
    }

    public void write(String text) {
        for (int i = 0; i < text.length(); i++) {
            CharacterCell newChar = new CharacterCell(text.charAt(i), currentForeground, currentBackground, currentStyle);
            this.screen.get(cursorPosition.row)[cursorPosition.column] = newChar;
            int newLines = moveCursor(Direction.RIGHT, 1);
            for (int j = 0; j < newLines; j++) {
                this.scrollback.add(this.screen.removeFirst());
                addEmptyLine();
            }
        }
    }

    public int moveCursor(Direction direction, int n) {
        int newRow = cursorPosition.row;
        int newColumn = cursorPosition.column;
        int linesToAdd = 0;
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

        if (newRow >= height) {
            linesToAdd = newRow - height + 1;
        }
        this.cursorPosition.row = clamp(newRow, height);
        this.cursorPosition.column = clamp(newColumn, width);
        return linesToAdd;
    }


    public TerminalBuffer(int height, int width, int maxScrollback) {
        this.height = height;
        this.width = width;
        this.maxScrollback = maxScrollback;
        cursorPosition = new CursorPosition(0,0);

        this.scrollback = new Scrollback(maxScrollback);
        this.screen = new ArrayList<>();

        for (int i = 0; i < height; i++) {
            addEmptyLine();
        }

    }

}
