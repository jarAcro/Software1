package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class contain the methods and data structure for the application.
 *
 * @author Chase Alan Jarvis
 */
public class Inventory {

    /**
     * Declaring part id to value 1
     */
    private static int partId = 1;

    /**
     * Declaring product id to 1.
     */
    private static int productId = 1;

    //This sets up the data structure for the Part Model
    /**
     * Data structure for all parts.
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    //Set up of data structure for Product
    /**
     * Data structure for all products.
     */
    private static ObservableList<Product> allProduct = FXCollections.observableArrayList();

    //Add part class set up

    /**
     * Adds a new part to all parts.
     *
     * @param newPart Triggers when new part is added to all parts.
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }


    /**
     * Gets all parts from observable list called Part.
     *
     * @return returns all parts in Part.
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }


    /**
     * @param selectedPart The part that is selected.
     * @return Deletes selected part.
     */
    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    /**
     * @param index        Passes index of part ot be updated.
     * @param selectedPart Part that is selected.
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }
    //this highlights anything with matching Id (selecting)

    /**
     * Description of method functionality, purpose not how it does
     *
     * @param partId The ID of the part to be found
     * @return Part object if part is found, null otherwise.
     */
    public static Part lookupPart(int partId) {
        for (Part part : allParts) {
            if (part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    /**
     * Description of method functionality. Find "part matches" based on partial name.
     *
     * @param partName Partial name of part to be matched.
     * @return List of matching parts, or an empty list.
     */
    public static ObservableList<Part> lookUpPart(String partName) {
        ObservableList<Part> list = FXCollections.observableArrayList();
        for (Part part : allParts) {
            if (part.getName().contains(partName)) {
                list.add(part);
            }
        }
        return list;
    }

    //all Product

    /**
     * Adds Products to newProduct lsit.
     *
     * @param newProduct adds product to newProduct.
     */
    public static void addProduct(Product newProduct) {
        allProduct.add(newProduct);
    }

    /**
     * @param productId Product ID to be matched.
     * @return returns a product by id if they match.
     */
    public static Product lookUpProduct(int productId) {
        for (Product product : allProduct) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    /**
     * Find "product matches" based on partial name.
     *
     * @param productName Partial product name to be matched.
     * @return List of matching products, or an empty list
     */
    public static ObservableList<Product> lookUpProduct(String productName) {
        ObservableList<Product> list = FXCollections.observableArrayList();
        for (Product product : allProduct) {
            if (product.getName().toLowerCase().contains(productName.toLowerCase())) {
                list.add(product);
            }
        }
        return list;
    }

    /**
     * @param selectedProduct Takes part that is selected and removes.
     * @return Removes part in selected part list.
     */
    public static boolean deleteProduct(Product selectedProduct) {
        return allProduct.remove(selectedProduct);
    }

    /**
     * Gets a list of all products.
     *
     * @return Returns an array list of all products.
     */
    public static ObservableList<Product> getAllProduct() {
        return allProduct;
    }

    /**
     * @param index           Updates a part index at selected product.
     * @param selectedProduct
     */
    public static void updateProduct(int index, Product selectedProduct) {
       allProduct.set(index, selectedProduct);

    }

    /**
     * Takes the part ID and adds 1.
     *
     * @return Returns ID incremented by 1.
     */
    public static int getNextPartId() {
        return partId++;
    }

    /**
     * ID incrementer
     *
     * @return Returns ID + 1.
     */
    public static int getNextProductId() {
        return productId++;
    }

    /**
     * @param name         Name of part or product.
     * @param max          Max value
     * @param min          Min Value
     * @param inv          Stock value
     * @param price        Price value
     * @param errorMessage Error string.
     * @return returns an error message if the below logic is violated.
     */
    public static String isDataValid(String name, int max, int min, int inv, double price, String errorMessage) {
        if (name.isBlank()) {
            errorMessage = errorMessage + ("Name is required. ");
        }
        if (price <= 0) {
            errorMessage = errorMessage + ("Price must be greater than $0. ");
        }
        if (inv < 1) {
            errorMessage = errorMessage + ("Inventory must be greater than 0. ");
        }
        if (min > max) {
            errorMessage = errorMessage + ("Min must be less than Max. ");
        }
        if (inv < min || inv > max) {
            errorMessage = errorMessage + ("Inventory must be between Min and Max. ");
        }

        return errorMessage;
    }
    /**
     * @param value Takes a number and checks it for logical conditions.
     * @return returns Min if it passes logical conditions.
     */
    public static int getMin(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException n) {
            throw new NumberFormatException("You've entered an invalid value. Min must be an integer value.");
        }
    }
    /**
     * @param value gets machine ID
     * @return Checks logical condition and returns the machine ID.
     */
    public static int getMachineId(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException n) {
            throw new NumberFormatException("You've entered an invalid value. Machine ID must be an integer value.");
        }
    }
    /**
     * @param value gets company name.
     * @return Checks logical condition and returns the company name if conditions are met.
     */
    public static String getCompanyName(String value) {

        if (value.isBlank()) {
            throw new NumberFormatException("Company Name is required.");
        }
        return value;
    }
    /**
     * @param value gets max value.
     * @return Checks logical condition and returns the max if conditions are met.
     */
    public static int getMax(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException n) {
            throw new NumberFormatException("Max must be an integer value, greater than Min.");
        }
    }
    /**
     * @param value gets inventory count.
     * @return Checks logical condition and returns the stock if conditions are met.
     */
    public static int getInv(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException n) {
            throw new NumberFormatException("Inventory must be an integer value greater in-between min and max.");
        }
    }
    /**
     * @param value gets price and checks its logic
     * @return returns the price value passed in if passes logical conditions.
     */
    public static double getPrice(String value) {

        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException n) {
            throw new NumberFormatException("Price must be an integer value greater than or equal to zero.");
        }
    }


}
