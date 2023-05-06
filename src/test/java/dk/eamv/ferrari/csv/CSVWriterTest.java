package dk.eamv.ferrari.csv;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        String[] columns = {"firstname", "lastname"};
        writer.writeHeader(columns);

        Object[] row1 = {"Fred", "Nietzsche"};
        Object[] row2 = {"Lao", "Tzu"};
        
        writer.writeRow(row1);
        writer.writeRow(row2);

        String expected = "firstname,lastname\n"
                        + "\"Fred\",\"Nietzsche\"\n"
                        + "\"Lao\",\"Tzu\"\n";

        assertEquals(expected, readTestFile());
    }

    /*
    @Test
    public void testWriteAmount() {

    }

    @Test
    public void testWriteQuoted() {

    }

    @Test
    public void testWriteComma() {

    }
    */

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
