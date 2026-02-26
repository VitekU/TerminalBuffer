package com.vitungermann.terminalbuffer.core;

import com.vitungermann.terminalbuffer.helper.CursorPosition;

import java.util.LinkedList;

public class TerminalBuffer {
    private int height;
    private int width;

    private LinkedList<CharacterCell[]> screen;
    private LinkedList<CharacterCell> scrollback;

}
