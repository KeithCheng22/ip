package keef.parser;

import keef.exception.KeefException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParserTest {

    @Test
    public void parseTaskIndex_validInput_success() throws KeefException {
        int index = Parser.parseTaskIndex("3", 5);
        assertEquals(3, index);
    }

    @Test
    public void parseTaskIndex_invalidNumber_throwsException() {
        Exception exception = assertThrows(KeefException.class, () -> {
            Parser.parseTaskIndex("abc", 5);
        });
        assertEquals("Uhm bro, that's not a valid task number!", exception.getMessage());
    }

    @Test
    public void parseTaskIndex_outOfBounds_throwsException() {
        Exception exception = assertThrows(KeefException.class, () -> {
            Parser.parseTaskIndex("6", 5);
        });
        assertEquals("Uhm bro, you only have 5 task(s) in your list.", exception.getMessage());
    }
}
