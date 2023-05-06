package dk.eamv.ferrari.csv;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CSVWriter {
    private FileWriter file;
    private BufferedWriter writer;

    public CSVWriter(String filepath) {
        try {
            file = new FileWriter(filepath);
            writer = new BufferedWriter(file);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void writeHeader(String[] columns) {
        try {
            for (int i = 0; i < columns.length; ++i) {
                writer.write(columns[i].toString());
                if (i < columns.length - 1) {
                    writer.write(',');
                }
            }
            writer.newLine();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void writeRow(Object[] fields) {
        try {
            for (int i = 0; i < fields.length; ++i) {
                writer.write(sanitize(fields[i].toString()));
                if (i < fields.length - 1) {
                    writer.write(',');
                }
            }
            writer.newLine();
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

    private String sanitize(String text) {
        text.replaceAll("\"", "\\\"");
        return String.format("\"%s\"", text);
    }
}
