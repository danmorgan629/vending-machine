package com.techelevator;

import com.techelevator.view.Menu;

import java.io.File;
import java.util.Map;
import java.util.Scanner;

public class VendingMachineCLI extends Items {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE };

	//will rework this var name so it makes more sense
	static Items itemsz;

	public Scanner input;
	public Menu menu;
	static Purchase purchases;

	private Map<Items,Integer> newMap = test;

	public VendingMachineCLI() {
	}


	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
		this.input = new Scanner(System.in);
	}

	public void run() {

		while (true) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				//reference items showStock method
				itemsz.showStock();
				// display vending machine items
			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				// do purchase menu and functions
				purchases = new Purchase();
				purchases.purchase();
			}
		}
	}

	public static void main(String[] args) {
		//initialize object of item
		itemsz = new Items();
		//call the method that creates our map on program start,
		//so we are able to use the same map and change values within
		itemsz.item(5);

		//this is all preexisting code, new object of menu to be able to use Menu functions
		Menu menu = new Menu(System.in, System.out);
		//new VendingMachine object, set menu with constructor
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		//run!!
		cli.run();
	}
}
