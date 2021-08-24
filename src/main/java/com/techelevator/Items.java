package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Items {
    private String selectionKey;
    private String snackName;
    private String price;
    private String snackType;
    private int quantity = 5;
    public Items items1;

    String[] splitByPipe = new String[20];

    public Map<String,Items> itemsMap = new HashMap<String,Items>();
    public Map<String,String> priceMap = new HashMap<>();
    public Map<String,String> snackMap = new HashMap<>();
    public static Map<Items,Integer> test = new HashMap<>();

    //in items we will create a map with A1, snackName, price, type
    public Items(){
    }

    public Items(String selectionKey, String snackName, String price, String snackType, int quantity){
        this.selectionKey = selectionKey;
        this.snackType = snackType;
        this.snackName = snackName;
        this.price = price;
        this.quantity = quantity;
        // starting value
    }

    public void item(int quantity){
        String currDirectory =  System.getProperty("user.dir");

        File readFile = new File(currDirectory, "/vendingmachine.csv");
            try(Scanner readIn = new Scanner(readFile)){
                while(readIn.hasNext()){
                    splitByPipe = readIn.nextLine().split("\\|");
                    selectionKey = splitByPipe[0];
                    snackName = splitByPipe[1];
                    price = splitByPipe[2];
                    snackType = splitByPipe[3];

                    items1 = new Items(selectionKey,snackName,price,snackType,quantity);

                    if(!itemsMap.containsKey(selectionKey)){
                        test.put(items1, quantity);
                        itemsMap.put(selectionKey,items1);
                        priceMap.put(selectionKey,price);
                    }
                }

            }catch(FileNotFoundException e){
                System.out.println(e);
            }catch(Exception ex){
                System.out.println(ex);
            }
    }

    public String toString(){
        return this.selectionKey + "|"+ this.snackName+ "|" +this.price + "|" +this.snackType;

    }
    public void showStock() {
        for (Map.Entry<Items, Integer> en : test.entrySet()) {
            //The whole object of Items is stored in the map, and we check every quantity (en.getValue),
            //if it's empty. Return SOLD OUT
            if(en.getValue() < 1){
                System.out.println(en.getKey().toString() + "|Quantity: " + "SOLD OUT");
            } else {
                System.out.println(en.getKey().toString() + "|Quantity: " + en.getValue());
            }
            }
        }

}
