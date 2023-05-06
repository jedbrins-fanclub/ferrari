package dk.eamv.ferrari.csv;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

public class CSVWriterTest {
    private CSVWriter writer;

    @BeforeEach
    public void init() {
        writer = new CSVWriter("test.csv");
    }

    @Test
    public void testWrite() {
        writer.writeHeader("firstname", "lastname", "birthyear");
        writer.writeRow("Fred", "Nietzsche", 1844);
        writer.writeRow("Lao", "Tzu", -6000);
        writer.flush();

        String expected = "firstname,lastname,birthyear\n"
                        + "\"Fred\",\"Nietzsche\",\"1844\"\n"
                        + "\"Lao\",\"Tzu\",\"-6000\"\n";

        assertEquals(expected, readTestFile());
    }

    @Test
    public void testWriteQuoted() {
        writer.writeHeader("firstname", "lastname");
        writer.writeRow("\"Marcus\"", "Aurelius");
        writer.writeRow("Noam", "\"Chomsky\"");
        writer.flush();

        String expected = "firstname,lastname\n"
                        + "\"\"\"Marcus\",\"Aurelius\"\n"
                        + "\"Noam\",\"\"\"Chomsky\"\"\"\n";

        assertEquals(expected, readTestFile());
    }

    @Test
    public void testWriteComma() {
        writer.writeHeader("address");
        writer.writeRow("Longyearbyen Vei 223-2, 9171");
        writer.flush();

        String expected = "address\n"
                        + "\"Longyearbyen Vei 223-2, 9171\"\n";

        assertEquals(expected, readTestFile());
    }

    @Test
    public void testWriteIncorrectFieldCount() {
        writer.writeHeader("first", "second");

        assertThrows(
            RuntimeException.class,
            () -> writer.writeRow("too", "many", "fields"),
            "Expected CSVWriter.writeRow() to throw due to incorrect field count"
        );
    }

    @AfterEach
    public void close() {
        writer.close();
        File file = new File("test.csv");
        file.delete();
    }

    private String readTestFile() {
        String out = "";
        try {
            out = Files.readString(Path.of("test.csv"));
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return out;
    }
}
