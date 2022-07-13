package Model;


//extends = inheritance

/**
 * This class hold the information for the In house part.
 *
 * @author Chase Alan Jarvis
 */
public class InHouse extends Part {


    /**
     * Declares machine ID.
     */
    private int machineId;

    /**
     * @param id        In house part ID
     * @param name      In house part name
     * @param price     In house part price
     * @param stock     In house part stock
     * @param min       In house part Min
     * @param max       In house part Max
     * @param machineId In house part machine id.
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * Returns machine ID.
     *
     * @return The machine ID of this part.
     */
    public int getMachineid() {
        return machineId;
    }

    /**
     * Sets machine ID.
     *
     * @param machineid the machine ID to be set.
     */
    public void setMachineid(int machineid) {
        this.machineId = machineId;
    }


}
