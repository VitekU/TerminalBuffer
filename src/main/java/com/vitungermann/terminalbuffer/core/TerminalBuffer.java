package com.vitungermann.terminalbuffer.core;

import com.vitungermann.terminalbuffer.helper.CursorPosition;
import com.vitungermann.terminalbuffer.helper.Direction;
import com.vitungermann.terminalbuffer.helper.Style;
import com.vitungermann.terminalbuffer.helper.TerminalColor;
import com.vitungermann.terminalbuffer.core.Scrollback;
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

    public void write(String text) {
        for (int i = 0; i < text.length(); i++) {
            CharacterCell newChar = new CharacterCell(text.charAt(i), currentForeground, currentBackground, currentStyle);
            this.screen.get(cursorPosition.row)[cursorPosition.column] = newChar;
            int linesToAdd = moveCursor(Direction.RIGHT, 1);
            for (int j = 0; j < linesToAdd; j++) {
                this.scrollback.add(screen.get(j));
            }
            this.screen.subList(0, linesToAdd).clear();
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
            newRow += (cursorPosition.row + n) / width;
        }
        else {
            newColumn = cursorPosition.column - n;
            if (newColumn < 0) {
                newRow -= (-newColumn / width + 1);
                newColumn = (newColumn % width) + width;

            }
        }

        if (newRow > height) {
            linesToAdd = newRow - height;
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
        this.screen = new ArrayList<>(height);
        for (int i = 0; i < height; i++) {
            CharacterCell[] row = new CharacterCell[width];
            for (int j = 0; j < width; j++) {
                row[j] = new CharacterCell(DefaultValues.defaultChar, DefaultValues.defaultForeground, DefaultValues.defaultBackground, DefaultValues.defaultStyle);
            }
            this.screen.set(i, row);
        }

    }

}
