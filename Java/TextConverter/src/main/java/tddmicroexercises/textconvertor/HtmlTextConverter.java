package tddmicroexercises.textconvertor;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class HtmlTextConverter
{
    private final LinesProvider linesProvider;

	/*
	  Solid principles violations
	  - SRP (converting and reading a file)
	  - OCP (strategy of conversion right now is fixed, it cannot be extended)
	  - Dependency inversion (the reading of the file is to concrete)
	 */

    public HtmlTextConverter(LinesProvider linesProvider)
    {
		this.linesProvider = linesProvider;
    }

	@SuppressWarnings("unused")
	/*
	 * Old constructor kept for retro-compatibility
	 */
	public HtmlTextConverter(String filePath)
	{
		this.linesProvider = new FileReaderLinesProvider(filePath);
	}

    public String convertToHtml() throws IOException{

		// we are coupled to the implementation of input
		List<String> lines = linesProvider.read();
//	    String html = "";
//	    while (line != null)
//	    {
//	    	html += StringEscapeUtils.escapeHtml(line);
//	        html += "<br />";
//	        line = reader.readLine();
//	    }
	    return lines.stream().map(s -> {
			String result = "";
			result += StringEscapeUtils.escapeHtml(s);
			result += "<br />";
			return result;
		}).collect(Collectors.joining());

    }

}
