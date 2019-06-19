package comparators;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;

public class StrDateComparator implements Comparator<String> {

	private SimpleDateFormat sdf;
	
	public StrDateComparator() {
		sdf = new SimpleDateFormat("dd.MM.yyyy.");
	}
	
	@Override
	public int compare(String arg0, String arg1) {
		
		try {
			return sdf.parse(arg0).compareTo(sdf.parse(arg1));
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
		
	}

}
