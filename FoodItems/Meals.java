import java.util.HashMap;
import java.util.Map;

public class Meals {
    protected final String name; 
    protected Map <String, Double> items; // Items represents the list of ingregients. String is the name of the ingredients and Integer is the quantity
    protected Map <String, String> units; // Gives the units for every ingredient if it exists
    protected final String protein; 
    protected final String style;
    private GroceryList list;

    public Meals (GroceryList list, String name, String protein, String style, Map <String, Double> items, Map <String, String> units) {
        this.name = name;
        this.items = items;
        this.units = units;
        this.list = list;
        this.protein = setProtein (protein);
        this.style = style;
        setStyle(style);
        
    }

    public Meals (Meals meal) {
        this.name = meal.name;
        this.items = new HashMap<>();
        this.units = new HashMap<>();

        for (String s: meal.items.keySet()) {
            items.put(s, meal.items.get(s));
            units.put(s, meal.units.get(s));
        }

        this.protein = meal.protein;
        this.style = meal.style;
        this.list = meal.list;
    }

// If a duplicate item is found throghout recipies this will take the first occurance and add to that item.
    public void setAmount (String item, double num) {
        double newTotal = items.get(item) + num;
        items.replace(item, items.get(item), newTotal);
    }

    @Override
    public String toString () {
        StringBuilder string = new StringBuilder();
        string.append("Meal: " + name + "\n" +  "Style: " + style + "\nProtein: " + protein + "\nIngredients: \n");
        
        for (String food: items.keySet()) {
            if (units.get(food) != null)
                string.append(food + " x" + items.get(food) + " " + units.get(food) + "\n");
            else
                string.append(food + " x" + items.get(food) + "\n");
        }

        return string.toString();
    }

// Takes the String assigned in the note doc for a meals protein and assigns them a protein group that is less specific    
    public String setProtein (String protein) {
        if (protein.contains("Chicken")) {
            list.proteins.add(protein);
            return "Chicken";
        }    
        else if (protein.contains("Sausage")) {
            list.proteins.add(protein);
            return "Sausage";
        }      
        else if (protein.contains("Pork")) {
            list.proteins.add(protein);
            return "Pork";
        } 
        else if (protein.contains("Salmon")) {
            list.proteins.add(protein);
            return "Salmon";  
        } 
        else if (protein.contains("Steak")) {
            list.proteins.add(protein);
            return "Steak";
        }
        else if (protein.contains("Beef")) {
            list.proteins.add(protein);
            return "Beef";
        }    
        else if (protein.contains("Chorizo")) {
            list.proteins.add(protein);
            return "Chorizo";
        }
        
        list.proteins.add(protein);
        return protein;
    }

    public void setStyle (String style) {
        if (style.equals("Italian")) 
            list.italian.put(name, this);
        else if (style.equals("Fish"))
            list.fish.put(name, this);
        else if (style.equals("Asian"))
            list.asian.put(name, this);
        else if (style.equals("Homestyle"))
            list.home.put(name, this);
        else if (style.equals("TexMex"))
            list.texmex.put(name, this);
        else 
            list.misc.put(name, this);
        }

}
