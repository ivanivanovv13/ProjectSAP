package Controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import Model.Item;


public class ItemsController {
	private  List<Item> items = new ArrayList<Item>();
	
	public  void getItems() {
		for(Item item:items) {
			System.out.println(item.toString());
		}
	}
	public  void getUsersItems(String userId) {
		for(Item item:items) {
			if(item.getUserId().equals(userId)) {
				System.out.println(item.toString());
			}
		}
	}
	
	public  void deleteItem(String userId,String itemId) {
		Iterator<Item> itr = items.iterator();            
		while(itr.hasNext()){
		    Item currItem = itr.next();
		    if(currItem.getUserId().equals(userId)){
		    	if(currItem.getId().equals(itemId))
		        itr.remove();
		    }
		}
	}
	
	public  void updateItem(String userId,String itemId,String name,double price,String description) {
		for(Item item:items) {
			if(item.getUserId().equals(userId)) {
				if(item.getId().equals(itemId)) {
					
         			Item newItem= new Item(userId,name,price,description);
         		    items.set(items.indexOf(item), newItem);
         				 System.out.println("Item updated successfully");	
				}
			}
		}
	}
	
	
	public  void addItem(Item item) {
			if(item!=null) {
				items.add(item);
				System.out.println("Succsesfullfy add a item.");
			}
		
	}
}
