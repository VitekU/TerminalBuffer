package com.vitungermann.terminalbuffer.core;

import static com.vitungermann.terminalbuffer.helper.MathHelp.clamp;

public class Scrollback {
    private int scrollbackSize;
    private int topIndex;
    private int rowCount;
    private CharacterCell[][] buffer;
    public void add(CharacterCell[] row) {
        buffer[topIndex] = row;
        topIndex = (topIndex + 1) % scrollbackSize;
        rowCount = clamp(rowCount + 1, scrollbackSize);
    }

    public Scrollback(int scrollbackSize) {
        this.scrollbackSize = scrollbackSize;
        topIndex = 0;
        rowCount = 0;
        buffer = new CharacterCell[scrollbackSize][];
    }
}
