package collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class SortMapByValue {

	public static void main(String[] args) {
		Map<String,String> mapString = new HashMap<>();
		
		mapString.put("1", "vaibhav");
		mapString.put("2", "rahul");
		mapString.put("3", "piyush");
		mapString.put("4", "harshit");
		mapString.put("5", "yogesh");
		
		sortMapUsingValue(mapString);
	}

	private static void sortMapUsingValue(Map<String, String> mapString) {
		
		Set<Map.Entry<String, String>> entryMap =  mapString.entrySet();
		
		List<Map.Entry<String,String>> entryMapList = new ArrayList<Entry<String,String>>(entryMap);
		
		Collections.sort(entryMapList,new Comparator<Entry<String,String>>() {

			@Override
			public int compare(Entry<String, String> o1, Entry<String, String> o2) {
				return o1.getValue().compareTo(o2.getValue());
			}
		});
		
		System.out.println(mapString);
		System.out.println(entryMapList);
		
		
	}
	
	

}
