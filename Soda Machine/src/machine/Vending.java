package machine;

import java.util.HashMap;
import java.util.Map;

public class Vending {
	HashMap<String, Integer> sodas = new HashMap<>();
	String[] sodaList;
	boolean giveaway = false;
	
	public Vending( String[] sodaInvetory, int inventoryNum) {
		for(String soda: sodaInvetory) {
			sodas.put(soda, Integer.valueOf(inventoryNum));
		}
		sodaList = sodaInvetory;
	}

	public String[] getsodas() {
		return sodaList;
	}

	public int dispense(String soda) {
		Integer inv = sodas.get(soda);
		if(inv > 0) {
			sodas.put(soda, inv-1);
			return inv -1;
		}
		else return -1;
		
	}
	
	public int getInventory(String soda) {
		return sodas.get(soda);
	}
	
	public String getAllInventory() {
		String ret = "Total Inventory of Items: ";
		 for (Map.Entry soda : sodas.entrySet()) { 
			 String key = (String) soda.getKey();
			 Integer inv = (Integer) soda.getValue();
			 ret = ret+key+":"+inv+".  ";
		 }
		 return ret;
	}

	public String contest() {
		return "Congradulations You Won";
	}
}
