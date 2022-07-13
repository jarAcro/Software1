package Model;

/**
 * This class contains the outsourced parts and products.
 *
 * @author Chase Alan Jarvis
 */
public class Outsourced extends Part {

    /**
     * Declares company name
     */
    private String companyName;


    /**
     * Constructor for outsaourced parts.
     *
     * @param id          takes ID parameter.
     * @param name        takes name parameter
     * @param price       Takes price
     * @param stock       Takes inventory
     * @param min         takes min
     * @param max         takes max
     * @param companyName takes company name.
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * gets company name
     *
     * @return Returns the name of the company.
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * sets company name
     *
     * @param companyName company name set.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
