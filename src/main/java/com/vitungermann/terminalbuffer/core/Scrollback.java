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

    public String getScrollback() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rowCount; i++) {
            int index;
            if (rowCount < scrollbackSize) {
                index = i;
            }
            else {
                index =  (i + topIndex) % scrollbackSize;
            }
            for (CharacterCell cc : buffer[index]) {
                sb.append(cc.getChar());
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public Scrollback(int scrollbackSize) {
        this.scrollbackSize = scrollbackSize;
        topIndex = 0;
        rowCount = 0;
        buffer = new CharacterCell[scrollbackSize][];
    }
}
