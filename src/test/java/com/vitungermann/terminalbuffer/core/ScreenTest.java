package com.vitungermann.terminalbuffer.core;
import com.vitungermann.terminalbuffer.helper.CursorPosition;
import com.vitungermann.terminalbuffer.helper.Direction;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class ScreenTest {
    @Test
    void testWrite() {
        Screen s = new Screen(10,10, DefaultValues.defaultForeground, DefaultValues.defaultBackground, DefaultValues.defaultStyle);
        s.write("aaaaaaaaaaa");
        s.write("bbbbbbbbb");
        String result = "aaaaaaaaaaabbxxxbbbbbbb";
        CursorPosition cp = new CursorPosition(1,2);
        s.insert("xxx", cp);
        assertEquals(result, s.getScreen());
    }

    @Test
    void testFill() {
        Screen s = new Screen(10,10, DefaultValues.defaultForeground, DefaultValues.defaultBackground, DefaultValues.defaultStyle);
        s.write("aaaaaaaaaaa");
        s.write("bbbbbbbbb");
        String result = "zzzzzzzzzzabbbbbbbbb";
        CursorPosition cp = new CursorPosition(1,8);
        s.fillLine('z', 0);
        assertEquals(result, s.getScreen());
    }

    @Test
    void testInsert() {
        Screen s = new Screen(10,10, DefaultValues.defaultForeground, DefaultValues.defaultBackground, DefaultValues.defaultStyle);
        s.write("aaaaaaaaaaa");
        s.write("bbbbbbbbb");
        CursorPosition cp = new CursorPosition(1,2);
        s.insert("xxx", cp);

        String result = "aaaaaaaaaaabbxxxbbbbbbb";

        assertEquals(result, s.getScreen());
    }
}
