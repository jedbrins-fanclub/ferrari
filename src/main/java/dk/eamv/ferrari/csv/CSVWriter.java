package dk.eamv.ferrari.csv;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CSVWriter {
    private FileWriter file;
    private BufferedWriter writer;
    private int columnCount;

    public CSVWriter(String filepath) {
        try {
            file = new FileWriter(filepath);
            writer = new BufferedWriter(file);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void writeHeader(String... columns) {
        assert columns.length > 0 : "CSV header mush have more than 0 columns";
        assert columnCount == 0 : "CSV header has already been written to this file";
        columnCount = columns.length;

        try {
            for (int i = 0; i < columns.length; ++i) {
                writer.write(columns[i].toString());
                if (i < columns.length - 1) {
                    writer.write(',');
                }
            }
            writer.write('\n');
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void writeRow(Object... fields) {
        assert fields.length == columnCount : "Incorrect field count not matching column count";

        try {
            for (int i = 0; i < fields.length; ++i) {
                writer.write(sanitize(fields[i].toString()));
                if (i < fields.length - 1) {
                    writer.write(',');
                }
            }
            writer.write('\n');
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    // Used to flush BufferedWriter, mainly used for testing
    public void flush() {
        try {
            writer.flush();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void close() {
        try {
            writer.close();
            file.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    // Escape quotes in string by doubling them
    // https://stackoverflow.com/a/17808731
    private String sanitize(String text) {
        text = text.replaceAll("\"", "\"\"");
        return String.format("\"%s\"", text);
    }
}
