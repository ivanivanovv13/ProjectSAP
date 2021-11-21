package View;

import java.util.Scanner;

import Controller.AccountController;
import Controller.ItemsController;
import Model.Item;
import Model.NotEmailAddressException;
import Model.User;

public class App {

	public static void main(String[] args) throws NotEmailAddressException {
		 
		
		String userId,email="",firstName="",lastName="",password="",phoneNum,name="",description="";double price=0.0;
		String itemId;
		Scanner in = new Scanner(System.in);
		
		System.out.println("Press 1 for register.");
		System.out.println("Press 2 for Log in.");
		System.out.println("Press 0 for Stopping the program.");
		
		while(true) {
			switch(in.nextInt()) {
			case 1:{
				System.out.println("Insert email:");	
				email=in.next();
				System.out.println("Insert First Name:");
				firstName=in.next();
				System.out.println("Insert Last Name:");
				lastName=in.next();
				System.out.println("Insert Password:");
				password= in.next();
				System.out.println("Insert Phone Number:");
				phoneNum=in.next();
				User obj= new User(firstName,lastName,email,phoneNum,password);
			AccountController.addAccount(obj);
			break;
			}
			case 2:{
				System.out.println("Insert email:");	
				email=in.next();
				System.out.println("Insert Password:");
				password=in.next();
				userId=AccountController.logIn(email, password);
				if(userId!="false") {
					System.out.println("Logged in successfully");
					while(true) {
						System.out.println("Press 1 for all items.");
						System.out.println("Press 2 for your items.");
						System.out.println("Press 3 for add a item.");
						System.out.println("Press 0 for Stopping the program.");
						switch (in.nextInt()) {
						case 1: {
							ItemsController.getItems();
							break;
						}
                        case 2: {
                        	ItemsController.getUsersItems(userId);
                        	System.out.println("Press 1 for update item.");
    						System.out.println("Press 2 for deleting item.");
    						System.out.println("Press 0 for going back.");
    						switch(in.nextInt()) {
    						case 1:
    							System.out.println("Insert id of the item you want to update :");
    							itemId=in.next();
    							System.out.println("Insert name:");	
    		         			name=in.next();
    		         			System.out.println("Insert price:");
    		         			price=in.nextDouble();
    		         			System.out.println("Insert descrption:");
    		         			description=in.next();
    							ItemsController.updateItem(userId,itemId ,name,price,description);
    							
    							break;
    						case 2:
    							System.out.println("Insert id of the item you want to delete :");
    							ItemsController.deleteItem(userId, in.next());
    							break;
    						case 0:{
    							break;
    						}
    						}
    						break;
                        	
						}
                         case 3: {
                        	System.out.println("Insert name:");	
                 			name=in.next();
                 			System.out.println("Insert price:");
                 			price=in.nextDouble();
                 			System.out.println("Insert descption:");
                 			description=in.next();
                 			Item item= new Item(userId,name,price,description);
                        	ItemsController.addItem(item);
                 			break;
                         }
                         case 0:{
                        	 System.exit(0);
                        	 break;
                         }
						
					    }
					}
					
				}
				else {
					System.out.println("Invalid email or password");
				}
				break;
			}
			
			case 0:
				
				 System.exit(0);
			}
			
		}		
	}
}
