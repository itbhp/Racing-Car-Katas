package tddmicroexercises.textconvertor;

import java.io.IOException;
import java.util.List;

public interface LinesProvider {
    List<String> read() throws IOException;
}
