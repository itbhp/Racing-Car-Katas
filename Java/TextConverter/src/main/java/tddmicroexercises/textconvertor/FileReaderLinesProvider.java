package tddmicroexercises.textconvertor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileReaderLinesProvider implements LinesProvider {
    private final String fullFilenameWithPath;

    public FileReaderLinesProvider(String fullFilenameWithPath) {
        this.fullFilenameWithPath = fullFilenameWithPath;
    }

    @Override
    public List<String> read() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fullFilenameWithPath));

        String line;
        List<String> lines = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        return lines;
    }
}
