package logic;

import java.util.ArrayList;
import java.util.List;

public class Cart {
	
	private List<ItemSet> itemSetList = new ArrayList<>();
	public List<ItemSet> getItemSetList(){
		return itemSetList;
	}
	public void push(ItemSet itemSet) {
		// TODO Auto-generated method stub
		for(ItemSet set : itemSetList) {
			if(set.getItem().getId().equals(itemSet.getItem().getId())) {
				set.setQuantity(set.getQuantity() + itemSet.getQuantity());
				return;
			}
		}
		itemSetList.add(itemSet);
	}
	public long getTotal() {
		long sum = 0;
		for(ItemSet itemSet : itemSetList) {
			sum += itemSet.getItem().getPrice() * itemSet.getQuantity();
		}
		return sum;
	}
	public void delete(int index) {
		// TODO Auto-generated method stub
		System.out.println("delete activated" + index);
		itemSetList.remove(index);
	}
	@Override
	public String toString() {
		return "Cart [itemSetList=" + itemSetList + "]";
	}

}
