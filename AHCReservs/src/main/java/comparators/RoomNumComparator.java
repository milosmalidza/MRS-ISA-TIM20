package comparators;

import java.util.Comparator;

import com.webapplication.Model.Room;

public class RoomNumComparator implements Comparator<Room>{

	@Override
	public int compare(Room r1, Room r2) {
		
		if (r1.getNumber() > r2.getNumber()) {
			return 1;
		} else if(r1.getNumber() < r2.getNumber()) {
			return -1;
		} else {
			return 0;
		}
	}

}
