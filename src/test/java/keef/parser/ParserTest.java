package keef.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import keef.exception.KeefException;

public class ParserTest {

    @Test
    public void parseTaskIndices_singleValidInput_success() throws KeefException {
        List<Integer> indices = Parser.parseTaskIndices("3", 5);
        assertEquals(List.of(3), indices);
    }

    @Test
    public void parseTaskIndices_invalidNumber_throwsException() {
        KeefException exception = assertThrows(KeefException.class, () -> {
            Parser.parseTaskIndices("abc", 5);
        });
        assertEquals("Uhm bro, that's not a valid task number!", exception.getMessage());
    }

    @Test
    public void parseTaskIndices_outOfBounds_throwsException() {
        KeefException exception = assertThrows(KeefException.class, () -> {
            Parser.parseTaskIndices("6", 5);
        });
        assertEquals("Uhm bro, you only have 5 task(s) in your list.", exception.getMessage());
    }

    @Test
    public void parseTaskIndices_multipleValidInputs_success() throws KeefException {
        List<Integer> indices = Parser.parseTaskIndices("1 3 5", 5);
        assertEquals(List.of(1, 3, 5), indices);
    }

    @Test
    public void parseTaskIndices_rangeInput_success() throws KeefException {
        List<Integer> indices = Parser.parseTaskIndices("2-4", 5);
        assertEquals(List.of(2, 3, 4), indices);
    }

    @Test
    public void parseTaskIndices_combinedInputs_success() throws KeefException {
        List<Integer> indices = Parser.parseTaskIndices("1 3-4 5", 5);
        assertEquals(List.of(1, 3, 4, 5), indices);
    }

    @Test
    public void parseTaskIndices_allKeyword_success() throws KeefException {
        List<Integer> indices = Parser.parseTaskIndices("all", 5);
        assertEquals(List.of(1, 2, 3, 4, 5), indices);
    }

    @Test
    public void parseTaskIndices_invalidRange_throwsException() {
        KeefException exception = assertThrows(KeefException.class, () -> {
            Parser.parseTaskIndices("4-2", 5); // start > end
        });
        assertEquals("Invalid range: 4-2", exception.getMessage());
    }
}
