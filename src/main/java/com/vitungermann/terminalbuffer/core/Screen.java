package com.vitungermann.terminalbuffer.core;

import com.vitungermann.terminalbuffer.helper.CursorPosition;
import com.vitungermann.terminalbuffer.helper.Style;
import com.vitungermann.terminalbuffer.helper.TerminalColor;

import java.util.ArrayList;
import java.util.List;

public class Screen {
    private List<List<CharacterCell>> rows;
    private int height;
    private int width;
    private TerminalColor currentForeground;
    private TerminalColor currentBackground;
    private Style currentStyle;

    public String getScreen() {
        StringBuilder sb = new StringBuilder();
        for (var row : rows) {
            for (var cell : row) {
                sb.append(cell.getChar());
            }
        }
        return sb.toString();
    }

    public void insertEmptyLine() {
        List<CharacterCell> emptyRow = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            emptyRow.add(new CharacterCell(' ', DefaultValues.defaultForeground, DefaultValues.defaultBackground, DefaultValues.defaultStyle));
        }
    }

    public void fillLine(char character, int line) {
        int index = line * width;

        for (int i = 0; i < rows.size(); i++) {
            List<CharacterCell> currentRow = rows.get(i);
            int currentSize = currentRow.size();

            if (index < currentSize) {
                int row = i;
                int column = index;

                for (int j = 0; j < width; j++) {
                    rows.get(row).set(column, new CharacterCell(character, currentForeground, currentBackground, currentStyle));
                    column++;
                    if (column >= rows.get(row).size()) {
                        column = 0;
                        row++;
                    }
                }
                return;
            }
            index -= currentSize;
        }
    }

    public void write(String text) {
        List<CharacterCell> newRow = new ArrayList<>();
        var textArr = text.toCharArray();
        for (char character : textArr) {

            newRow.add(new CharacterCell(character, currentForeground, currentBackground, currentStyle));
        }
        this.rows.add(newRow);
    }

    public void insert(String text, CursorPosition startingPos) {
        int blockIndex = 0;
        int count = 0;
        int posIndex = startingPos.row * width + startingPos.column;
        while (count < posIndex) {
            count += rows.get(blockIndex).size();
            blockIndex++;
        }
        blockIndex--;
        List<CharacterCell> splitRow = rows.get(blockIndex);
        List<CharacterCell> firstPart = new ArrayList<>(splitRow.subList(0, startingPos.column));
        List<CharacterCell> secondPart = new ArrayList<>(splitRow.subList(startingPos.column, splitRow.size()));

        rows.set(blockIndex, firstPart);
        rows.add(blockIndex + 1, secondPart);

        List<CharacterCell> newRow = new ArrayList<>();
        var textArr = text.toCharArray();
        for (char character : textArr) {

            newRow.add(new CharacterCell(character, currentForeground, currentBackground, currentStyle));
        }
        this.rows.add(blockIndex + 1, newRow);
    }

    public Screen(int height, int width, TerminalColor currentForeground, TerminalColor currentBackground, Style currentStyle)
    {
        this.height = height;
        this.width = width;
        this.currentForeground = currentForeground;
        this.currentBackground = currentBackground;
        this.currentStyle = currentStyle;

        rows = new ArrayList<>();
    }
}
