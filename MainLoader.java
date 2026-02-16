public class MainLoader {
    public static void main(String[] args) {
        // 1. Initialize Registry
        CompatibilityRegistry registry = new CompatibilityRegistry();

        // 2. Load Data (Building/Floors/Spots)
        // This is to call  DB through Data Access Object to load structure

//>>>>>>>>>>*****use constructor of spot to load them from database*****
        //then group the spot and put in the floor
        //group the floor and put in the building 

        // Initialize DAO ticket 
        TicketDAO dao = new TicketDAO(); 
//>>>>>>>>>>>>>>    // important 
        /**initialise other DAO [data access object] >> must define the class 
        * ...
        *...
        */

        // 4. Initialize Engines which is used by gates
        TicketEngine ticketEngine = new TicketEngine(dao, building);
        AllocationEngine allocEngine = new AllocationEngine(building, registry, new SimpleSorter());

        // 5. Open Windows and Pass References
        // Now everyone is looking at the SAME heap objects
        new EntryGateUI(allocEngine, ticketEngine).setVisible(true);
        new AdminWindowUI(building, ...).setVisible(true);
        //... other window ExitGateUI
        //... other windoe ReportWindowUI
    }
}