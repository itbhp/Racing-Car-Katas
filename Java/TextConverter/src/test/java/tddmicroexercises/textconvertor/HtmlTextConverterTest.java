package tddmicroexercises.textconvertor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.io.IOException;

class HtmlTextConverterTest {

    private static final String RESOURCES_DIR =
            "/Users/paolosciarra/github/itbhp/Racing-Car-Katas/Java/TextConverter/src/test/resources/";
    private static final String DEFAULT_HTML_NEWLINE = "<br />";

    @Test
    void it_should_convert_a_one_line_file() throws IOException {
        HtmlTextConverter converter = new HtmlTextConverter(new FileReaderLinesProvider(RESOURCES_DIR + "one-line.txt"));
        assertEquals("The quick brown fox jumps over the lazy dog" + DEFAULT_HTML_NEWLINE, converter.convertToHtml());
    }

    @Test
    void it_should_convert_a_multiple_lines_file() throws IOException {
        HtmlTextConverter converter = new HtmlTextConverter(new FileReaderLinesProvider(RESOURCES_DIR + "multiple-lines.txt"));
        assertEquals("The quick brown fox jumps over the lazy dog" + DEFAULT_HTML_NEWLINE
                + "The quick brown fox jumps over the lazy dog" + DEFAULT_HTML_NEWLINE
                + "The quick brown fox jumps over the lazy dog" + DEFAULT_HTML_NEWLINE
                + "The quick brown fox jumps over the lazy dog" + DEFAULT_HTML_NEWLINE, converter.convertToHtml());
    }

    @Test
    void it_should_convert_a_file_with_escape_characters() throws IOException {
        HtmlTextConverter converter = new HtmlTextConverter(new FileReaderLinesProvider(RESOURCES_DIR + "escape.txt"));
        assertEquals("&amp;" + DEFAULT_HTML_NEWLINE
                +"&lt;" + DEFAULT_HTML_NEWLINE
                +"&gt;" + DEFAULT_HTML_NEWLINE
                +"&quot;" + DEFAULT_HTML_NEWLINE
                +"&quot;" + DEFAULT_HTML_NEWLINE, converter.convertToHtml());
    }
}
