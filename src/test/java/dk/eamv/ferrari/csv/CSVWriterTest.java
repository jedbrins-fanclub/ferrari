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
        String[] columns = {"firstname", "lastname", "birthyear"};
        writer.writeHeader(columns);

        Object[] row1 = {"Fred", "Nietzsche", 1844};
        Object[] row2 = {"Lao", "Tzu", -6000};

        writer.writeRow(row1);
        writer.writeRow(row2);
        writer.flush();

        String expected = "firstname,lastname,birthyear\n"
                        + "\"Fred\",\"Nietzsche\",\"1844\"\n"
                        + "\"Lao\",\"Tzu\",\"-6000\"\n";

        assertEquals(expected, readTestFile());
    }

    @Test
    public void testWriteQuoted() {
        String[] columns = {"firstname", "lastname"};
        writer.writeHeader(columns);

        Object[] row1 = {"\"Marcus", "Aurelius"};
        Object[] row2 = {"Noam", "\"Chomsky\""};

        writer.writeRow(row1);
        writer.writeRow(row2);
        writer.flush();

        String expected = "firstname,lastname\n"
                        + "\"\"\"Marcus\",\"Aurelius\"\n"
                        + "\"Noam\",\"\"\"Chomsky\"\"\"\n";

        assertEquals(expected, readTestFile());
    }

    @Test
    public void testWriteComma() {
        String[] columns = {"address"};
        writer.writeHeader(columns);

        Object[] row = {"Longyearbyen Vei 223-2, 9171"};
        writer.writeRow(row);
        writer.flush();

        String expected = "address\n"
                        + "\"Longyearbyen Vei 223-2, 9171\"\n";

        assertEquals(expected, readTestFile());
    }

    @Test
    public void testWriteIncorrectFieldCount() {
        String[] columns = {"first", "second"};
        writer.writeHeader(columns);

        Object[] row = {"too", "many", "fields"};
        assertThrows(
            RuntimeException.class,
            () -> writer.writeRow(row),
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
