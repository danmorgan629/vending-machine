package com.techelevator;

import com.techelevator.view.Menu;

import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;



//extended VendingMachineCLI to use the same object we created on program start, better way to do this?
public class Purchase extends VendingMachineCLI {

    private String selection;
    private static double machineFunds;
    private double change = 0;
    private double cost = 0;
    private static final String PURCHASE_MENU_FEED = "Feed Money";
    private static final String PURCHASE_MENU_SELECT = "Select Product";
    private static final String PURCHASE_MENU_FINISH = "Finish Transaction";

    static int moneyAdded = 0;



   /* public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }*/

    public void purchase() {
        boolean keepRunning = true;
        while (keepRunning == true) {//starting a loop so we stay in the purchase function

            Menu purchaseMenu = new Menu(System.in, System.out);
            // items.item(5);
            input = new Scanner(System.in);
            //store option possibilities
            String purchaseOption1 = "Feed Money";
            String purchaseOption2 = "Select Product";
            String purchaseOption3 = "Finish Transaction";
            String[] purchaseOptions = {purchaseOption1, purchaseOption2, purchaseOption3};

            String purchaseChoice = (String) purchaseMenu.getChoiceFromOptions(purchaseOptions);

            while (true) { // purchase choice does not equals 3
                //Show the list of products available and allow the customer to enter a code to select an item.
                //If the product code does not exist, the customer is informed and returned to the Purchase menu.
                //If a product is sold out, the customer is informed and returned to the Purchase menu.


                if (purchaseChoice.equals(PURCHASE_MENU_FEED)) {
                    System.out.println("Please feed money: $1, $2, $5, $10, $20, $50, $100");
                    System.out.println(machineFunds());
                    System.out.println("Would you like to add more? Y/N");

                    String yN = input.nextLine();
                    writeTimeToLog(" FEED MONEY: $" + machineFunds);

                    if (yN.equalsIgnoreCase("y")) {

                    } else {
                        break;
                    }
                } else if (purchaseChoice.equals(PURCHASE_MENU_SELECT)) {
                    //items showStock method
                    showStock();
                    System.out.println("**PLEASE MAKE A SELECTION**");
                    setSelection(input.nextLine());

                    if (itemsz.priceMap.containsKey(selection)){

                        //if cost is more                                        than balance(machineFunds)
                        if (Double.parseDouble(itemsz.priceMap.get(selection)) > machineFunds) {
                            System.out.println("You do not have enough money for this selection. "); //
                            System.out.println("Current Balance: $" + machineFunds + "\nItem Cost: $" + itemsz.priceMap.get(selection) + " "); // displays curr balance (machineFund) and item cost which we get from price map
                            break;
                        } else { // if we reach this else statement, it means we have enough money to do the transaction
                            cost = Double.parseDouble(itemsz.priceMap.get(selection)); // cost is equal to the cost of the item, pulled from price map
                            purchaseMessage(); //display type of message
                            machineFunds -= cost; // subtract cost from our balance when we make a purchase
                            writeTimeToLog(" "
                                    + selection + " - " + itemsz.snackMap.get(selection)
                                    + " $" + itemsz.priceMap.get(selection)); // writes to our log file

                            for (Map.Entry<Items, Integer> en : test.entrySet()) {
                                // iterate through our map
                                if (en.getKey().toString().contains(selection)) {//if the map contains the item # (ex. A2, C4, D2)
                                    System.out.println("Cost of " + selection + ": $" + cost + " Remaining balance: $" + machineFunds);
                                    test.replace(en.getKey(), en.getValue() - 1); //subtract 1 from the quantity by replacing the entry in the map
                                }
                                if (en.getValue() < 1) { //if quantity is 0, return that we are out of stock
                                    System.out.println("Sorry, we are out of " + en.getKey().toString());
                                }
                            }
                            System.out.println("Would you like to purchase another item? Y/N"); // prompt user if they would like to purchase more

                            String yN = input.nextLine();

                            if (yN.equalsIgnoreCase("y")) { // if input is y or Y, back to purchase menu

                            } else { // otherwise, break
                                break;
                            }
                        }
                    } else { // this is tied to our if statement above, checking if selection is valid
                        System.out.println("*** " + selection + " ***" + " is not a valid selection");
                    }
                }
                //if user selects Finish Transaction
                if (purchaseChoice.equals(PURCHASE_MENU_FINISH)) {
                    System.out.println("Thank you! Your change of " + changeToBeReturned(cost, machineFunds) + " will be dispensed below."); // print change
                    writeTimeToLog(" GIVE CHANGE: "
                            + changeToBeReturned(cost, machineFunds)); // write this action to our log
                    keepRunning = false;
                    break;
                }
            }
            }
        }


    public String machineFunds() {
        Scanner keypad = new Scanner(System.in);
        try {
            moneyAdded = keypad.nextInt();
            System.out.println("Current Balance: ");
            machineFunds += moneyAdded;
        } catch (Exception e){
            return "*** " + keypad.next() + " *** is not a valid entry";
        }

        return "$" + machineFunds;
    }

    public String changeToBeReturned(double cost, double balance) {

        change = machineFunds;

        int dollars = 0;
        int quarters = 0;
        int dimes = 0;
        int nickels = 0;

        while (change >= 1.00) {
            dollars += 1;
            change -= 1;
            machineFunds -= 1.00;
        }

        while (change >= 0.25) {
            quarters += 1;
            change -= 0.25;
            machineFunds -= .25;
        }
        while (change >= 0.10) {
            dimes += 1;
            change -= 0.10;
            machineFunds -= .10;
        }
        while (change >= 0.05) {
            nickels += 1;
            change -= 0.05;
            machineFunds -= 0.05;
        }
        machineFunds = 0;
            return (dollars + " dollars, " + quarters + " quarters, " + dimes + " dimes, and  " + nickels + " nickels");

        }


        public void setSelection (String input){
            this.selection = input;
            System.out.println(selection);
        }

        public void writeTimeToLog (String action){
            File log = new File(System.getProperty("user.dir"), "log.txt");
            try (FileWriter fw = new FileWriter(log, true)) {
                DateTimeFormatter dt = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm");
                LocalDateTime currentDateTime = LocalDateTime.now();
                String dateTimeFormatted = currentDateTime.format(dt);
                fw.write(">" + dateTimeFormatted + action + "\n");
            } catch (Exception e) {
                System.out.println(e);
            }

        }

        public void purchaseMessage () {
            if (itemsz.itemsMap.get(selection).toString().contains("Chips")) {
                System.out.print("Crunch Crunch, Yum!");
            } else if (itemsz.itemsMap.get(selection).toString().contains("Drink")) {
                System.out.println("Glug Glug, Yum!");
            } else if (itemsz.itemsMap.get(selection).toString().contains("Candy")) {
                System.out.println("Munch Munch, Yum!");
            } else {
                System.out.println("Chew Chew, Yum!");
            }
        }
    }


