package src;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Chaine implements _Chaine {
	
	private List<Hotel> possession = new LinkedList<Hotel>();
	
	public Chaine (List<Hotel> l) {
		this.possession = l;
	}
	
	@Override
	public List<Hotel> get(String localisation) {
		Iterator<Hotel> i = possession.iterator();
		List<Hotel> region = new LinkedList<Hotel>();
		while(i.hasNext()){
			Hotel hCourant = i.next();
			if(hCourant.localisation == localisation){
				region.add(hCourant);
			};	
		}
		return region;
	}

}
