import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class GroceryList implements Comparator <Character> {

    private Map <String, Meals> book = new HashMap<>();
    protected Map <String, Meals> texmex = new HashMap<>();
    protected Map <String, Meals> italian = new HashMap<>();
    protected Map <String, Meals> asian = new HashMap<>();
    protected Map <String, Meals> fish = new HashMap<>();
    protected Map <String, Meals> home = new HashMap<>();
    protected Map <String, Meals> misc = new HashMap<>();
    protected List <String> proteins = new LinkedList<>();
    protected int totMeals = 0;

    public static void main(String[] args) throws FileNotFoundException {
        GroceryList list = new GroceryList();
        list.toBook();
        list.menuPrompt();
    }

// This function takes input from Recipe.txt and seperates every meal by its names and the ingredients it requires.
    public void toBook () throws FileNotFoundException {						// ***************************************************************************************************
        File file = new File("Insert File Path here :D"); // <------ CHANGE THIS FILE PATH TO MATCH WHERE YOU STORE YOUR GROCERY PROGRAM FOLDER
        Scanner scan = new Scanner (file);                                                //  (NOTE: COPYING FILE PATHS THAT HAVE "\" FOR DIRECTORY CHANGES MUST BE REPLEACED BY "/")
														      // ***************************************************************************************************
        int i=0;
        String name = "";
        Map <String, Double> items = new HashMap<>(); // Storage for every Ingredient and its quantity in the recipe
        Map <String, String> units = new HashMap<>(); // Storage for every Ingredient's quantity unit of measurement in the recipe
        String protein = "";
        String style = "";

    // Scan through word document. Splits any into meals with ingredients and quantities for those ingredients.
    // Also, meal is added to the recipe book in GroceryList instance and is accessable using the meals name from the file.    
        while (scan.hasNextLine()) {
            String line = scan.nextLine();

            if (line.isEmpty()) {
                i=-1;
                book.put(name, new Meals(this, name, protein, style, Map.copyOf(items), Map.copyOf(units)));
                name = "";
                items.clear();
            }    
                else if (i==0) 
                    name = line;
                else if (i==1)
                    style = line;
                else if (i > 1) { 
                    String [] str = line.split(" ");
                    if (i==2)
                        protein = str[0];
                    if (str.length == 3)
                        units.put(str[0], str[2]);
                    
                    items.put(str[0], Double.parseDouble(str[1]));
                    }
            i++;    
        }
        scan.close();
    }

    private void menuPrompt () {
        Scanner scan = new Scanner(System.in);
        boolean end = false;
        System.out.println("Welcome to your Grocery List Program!");

        while (end == false) {
        System.out.println("Please choose from the following options:\n(TYPE 1) Randomly Generate a Weekly Food Plan and Grocery List \n(Type 2) Select Your Own Meals with Random Days Selected\n");
        int choice = 0;

        try {
            choice = scan.nextInt();

            if (choice != 1 && choice != 2) 
                throw new IllegalArgumentException("You must choose option 1 or 2!");

            else {
                if (choice == 1)
                    end = randSelection ();
                else if (choice == 2) 
                    end = mealSelection(); 
                
            }    
        }
            catch (InputMismatchException e) {
                System.out.println("You must type a number! Please try again!");
                scan.nextLine();
            }
            catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        

        }

        scan.close();
    }    

    private boolean mealSelection () {
        Scanner scan = new Scanner (System.in);
        Meals [] mealChoices = new Meals [book.values().size()];
        List <Meals> meals = new LinkedList<>();

        int i=0;
        for (Meals meal: book.values()) { 
            mealChoices [i] = meal;
            i++;
        }
        boolean end = false;
        boolean reset = false;

        while (end == false && totMeals < 6) {

            System.out.println("Please select a meal from the following styles!");
            System.out.println("(TYPE 1) TEXMEX\n(TYPE 2) ITALIAN\n(TYPE 3) ASIAN\n(TYPE 4) FISH\n(TYPE 5) HOMESTYLE\n(TYPE 6) OTHER\n(TYPE 0) RESET MEALS AND RETURN\nYOUR TOTAL MEALS SLECETED IS: " + totMeals);
            int choice = 0;
        
            try {
                choice = scan.nextInt();

                if (choice < 0 || choice > 6) 
                    throw new IllegalArgumentException("You must choose options 0-6!");
                
            }
                catch (InputMismatchException e) {
                    System.out.println("You must type a number! Please try again!");
                    scan.nextLine();
                }
                catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }

            switch (choice) {
                case 0: 
                    meals.clear();
                    end = true;
                    reset = true;
                    break;
                case 1: 
                    List <String> tmList = new LinkedList<>();
                    System.out.println("WHAT TEXMEX MEALS WOULD YOU LIKE?");
                    i=1;
                    for (String t: texmex.keySet()) {
                        tmList.add(t);
                        System.out.println("(TYPE " + i + ") " + t.toUpperCase());
                        i++;
                    }

                    System.out.println("(TYPE 0) RETURN");

                    try {
                        choice = scan.nextInt();

                        if (choice < 0 || choice > tmList.size()) 
                            throw new IllegalArgumentException("You must choose options 0-6!");
                        if (choice == 0)
                            break;
                        else {
                            meals.add(texmex.get(tmList.get(choice-1)));
                            totMeals++;
                        }    

                    }
                    catch (InputMismatchException e) {
                        System.out.println("You must type a number! Please try again!");
                        scan.nextLine();
                    }
                    catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }  
                    break; 
                case 2: 
                    List <String> itList = new LinkedList<>();
                    System.out.println("WHAT ITALIAN MEALS WOULD YOU LIKE?");
                    i=1;
                    for (String t: italian.keySet()) {
                        itList.add(t);
                        System.out.println("(TYPE " + i + ") " + t.toUpperCase());
                        i++;
                    }

                    System.out.println("(TYPE 0) RETURN");

                    try {
                        choice = scan.nextInt();
                        if (choice < 0 || choice > itList.size()) 
                            throw new IllegalArgumentException("You must choose options 0-6!");
                        if (choice == 0)
                            break;
                        else {
                            meals.add(italian.get(itList.get(choice-1)));
                            totMeals++;
                        }    
                    }
                    catch (InputMismatchException e) {
                        System.out.println("You must type a number! Please try again!");
                        scan.nextLine();
                    }
                    catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }  
                    break; 
                case 3: 
                    List <String> asList = new LinkedList<>();
                    System.out.println("WHAT ASIAN MEALS WOULD YOU LIKE?");
                    i=1;
                    for (String t: asian.keySet()) {
                        asList.add(t);
                        System.out.println("(TYPE " + i + ") " + t.toUpperCase());
                        i++;
                    }

                    System.out.println("(TYPE 0) RETURN");

                    try {
                        choice = scan.nextInt();
                        if (choice < 0 || choice > asList.size()) 
                            throw new IllegalArgumentException("You must choose options 0-6!");
                        if (choice == 0)
                            break;
                        else {
                            meals.add(asian.get(asList.get(choice-1)));
                            totMeals++;
                        }    
                    }
                    catch (InputMismatchException e) {
                        System.out.println("You must type a number! Please try again!");
                        scan.nextLine();
                    }
                    catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }  
                    break;
                case 4: 
                    List <String> fsList = new LinkedList<>();
                    System.out.println("WHAT FISH MEALS WOULD YOU LIKE?");
                    i=1;
                    for (String t: fish.keySet()) {
                        fsList.add(t);
                        System.out.println("(TYPE " + i + ") " + t.toUpperCase());
                        i++;
                    }

                    System.out.println("(TYPE 0) RETURN");

                    try {
                        choice = scan.nextInt();
                        if (choice < 0 || choice > fsList.size()) 
                            throw new IllegalArgumentException("You must choose options 0-6!");
                        if (choice == 0)
                            break;
                        else {
                            meals.add(fish.get(fsList.get(choice-1)));
                            totMeals++;
                        }    
                    }
                    catch (InputMismatchException e) {
                        System.out.println("You must type a number! Please try again!");
                        scan.nextLine();
                    }
                    catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }  
                    break;
                case 5: 
                    List <String> hmList = new LinkedList<>();
                    System.out.println("WHAT HOMESTYLE MEALS WOULD YOU LIKE?");
                    i=1;
                    for (String t: home.keySet()) {
                        hmList.add(t);
                        System.out.println("(TYPE " + i + ") " + t.toUpperCase());
                        i++;
                    }

                    System.out.println("(TYPE 0) RETURN");

                    try {
                        choice = scan.nextInt();
                        if (choice < 0 || choice > hmList.size()) 
                            throw new IllegalArgumentException("You must choose options 0-6!");
                        if (choice == 0)
                            break;
                        else {
                            meals.add(home.get(hmList.get(choice-1)));
                            totMeals++;
                        }    
                    }
                    catch (InputMismatchException e) {
                        System.out.println("You must type a number! Please try again!");
                        scan.nextLine();
                    }
                    catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }  
                    break;
                case 6: 
                    List <String> mscList = new LinkedList<>();
                    System.out.println("WHAT UNCATEGORIZED MEALS WOULD YOU LIKE?");
                    i=1;
                    for (String t: misc.keySet()) {
                        mscList.add(t);
                        System.out.println("(TYPE " + i + ") " + t.toUpperCase());
                        i++;
                    }

                    System.out.println("(TYPE 0) RETURN");

                    try {
                        choice = scan.nextInt();
                        if (choice < 0 || choice > mscList.size()) 
                            throw new IllegalArgumentException("You must choose options 0-6!");
                        if (choice == 0)
                            break;
                        else {
                            meals.add(misc.get(mscList.get(choice-1)));
                            totMeals++;
                        }    
                    }
                    catch (InputMismatchException e) {
                        System.out.println("You must type a number! Please try again!");
                        scan.nextLine();
                    }
                    catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }  
                    break;  
            }

            totMeals = meals.size();
        }   
        
        if (reset == true) 
            return false;  
        else {
            addIngredients (meals);
            return true;
        }    
    }

    private boolean randSelection () {
        Random rand = new Random();
        Meals [] mealChoices = new Meals [book.values().size()];
        List <Meals> meals = new LinkedList<>();
    // Create array if meals in book
    // Access the array at random locations and add them to the weekely scedule if they dont already exist

        int i=0;
        for (Meals meal: book.values()) { 
            mealChoices [i] = meal;
            i++;
        }

        for (i=0; i<6; i++) { // 
            int num = rand.nextInt(book.values().size());
 
            if (i==0) // If the first entry to list, add random index from Meals array to list
                meals.add(mealChoices [num]);   
            else { // if meal is not in list, but the previous meal in a list has the same style or protein, loop retries. Otherwise, meal is added to list.
                if (mealChoices [num].style.equals(meals.get(meals.size()-1).style) || mealChoices [num].protein.equals(meals.get(meals.size()-1).protein))
                    i--;    
                else if (!meals.contains(mealChoices[num]))
                    meals.add(mealChoices [num]);
                else 
                    i--;
            }    
        } 

        addIngredients(meals);
        return true;
    }

    private void addIngredients (List <Meals> meals) {

        String [] days = {"SUNDAY", "MONDAY", "TUESDAY", "THURSDAY", "FRIDAY", "SATURDAY"};
        StringBuilder toFile = new StringBuilder();
        toFile.append("WEEKLY MEAL SCHEDULE: \n");
        Map <String, Meals> ingredients = new HashMap<>();
        List <Meals> copyMeals = new LinkedList<>();

        for (Meals n: meals) {
            Meals nm = new Meals(n);
            copyMeals.add(nm);
        }
        
        int index = 0;
        for (Meals m: meals) { // Sorts through all meals in the passed through argument's list.
            for (String i: m.items.keySet()) { // Sorts through all the ingredient names of a specific meal.
                String newi = i;
                if (!ingredients.containsKey(i)) // If the ingredient is not a key in the ingredients map
                    ingredients.put(newi, copyMeals.get(index)); // Add to ingredients map
                else { // If ingredient not a key in ingredients map
                    ingredients.get(newi).setAmount(i, copyMeals.get(index).items.get(i)); // retrieves meal ingredients belongs to and adds to the total needed
                    copyMeals.get(index).items.replace(i, 0.0);
                }    
            } 
            index++;  
        }  
        
        for (int i=0; i < 6; i++) {
            if (i==2) {
                toFile.append(days[2] + ": " + meals.get(2).name + "\n"+ "WEDNESDAY: TAKE OUT\n");
            }    
            else {
                toFile.append(days[i] + ": " + meals.get(i).name + "\n");
            }    
        }

        toFile.append("\nINGREDIENTS NEEDED: \n");

        List <String> list = new LinkedList<>();
        for (String m: ingredients.keySet()) {
            list.add(m);
        }

        list.sort(String::compareTo);

        for (String m: list) { // Places Prtoein at top of the list
            if (proteins.contains(m)) {
                if (ingredients.get(m).units.get(m) != null)
                    toFile.append(m + " " + ingredients.get(m).items.get(m) + " " + ingredients.get(m).units.get(m) + "\n");
                else 
                    toFile.append(m + " " + ingredients.get(m).items.get(m) + "\n");    
            }    
        }
        for (String m: list) { // Everything else below 
            if (!proteins.contains(m)) {
                if (ingredients.get(m).units.get(m) != null)
                    toFile.append(m + " " + ingredients.get(m).items.get(m) + " " + ingredients.get(m).units.get(m) + "\n");
                else 
                    toFile.append(m + " " + ingredients.get(m).items.get(m) + "\n");    
            }    
        }

        toFile(toFile.toString());
    }


    private void toFile(String toFile) {				  //************************************************************************************************************************
        File file = new File("Insert File PAth here :D"); // <---- CHANGE THIS FILE PATH TO MATCH WHERE YOU WANT YOUR GROCERY LIST TO BE SAVED
        try (PrintWriter write = new PrintWriter(file)) {    // (NOTE: COPYING FILE PATHS THAT HAVE "\" FOR DIRECTORY CHANGES MUST BE REPLEACED BY "/")
            write.println(toFile);						  //************************************************************************************************************************
            write.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("File has been created!'");
    }

    @Override
    public int compare(Character a, Character b) {
        return a-b;
    }

    

}
