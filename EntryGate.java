import java.util.List;
/**
 * EntryGate - Registry & Logic Class (Expert)
 * 
 * DESCRIPTION:
 * Orchestrates the vehicle entry process. Acts as "The Handshake" between
 * a vehicle and the parking system.
 * 
 * 
 * ARCHITECTURE - THE HANDSHAKE:
 * The EntryGate coordinates multiple components to safely assign a parking spot:
 * - Database: Atomic reservation to prevent double-booking
 * 
 * 
 * RELATIONSHIPS:
 * - Accesses Building to find floors and spots
 * - Processes Vehicle objects
 */
public class EntryGate extends Gate{
    
//PROTECTED : gateID , Building is saved in the abstract base class
    private TicketEngine ticketEngine;
    private AllocationEngine allocEngine;

    private Vehicle newVehicle;
    private Spot chosenSpot;
    private Ticket freshTicket;


    /**
     * Constructor
     * 
     * @param gateID Unique gate identifier
     * @param building The building this gate serves
     * 
     * TODO: Initialize all fields
     * TODO: Validate that building is not null
     */
    public EntryGate(int gateID, Building building ,AllocationEngine allocE , TicketEngine tickE) {
        super(gateID, building);
        this.allocEngine=allocE;
        this.ticketEngine=tickE;
        //TODO: need null and error handling
    }

    //method receives String licenseNo , VehicleType type
    // ask the allocationEngine to return 5 best spots
    //pass it to swing GUI
    public List<Long> getSpotToSelect(String licenNo , VehicleType type){
        this.newVehicle = new Vehicle(licenNo, type);
        List<Long> viewableSpots;
        viewableSpots= allocEngine.getAvailableSpotsForVehicle(type);
        return viewableSpots;
    }

    //method to check if the spot is truly free
    //if free it marks creates chosenSpot and call the processEntry(..) 
    //from TicketEngine and assign the returned Ticket object to freshTicket
    //save the vehicle to the DB as well
    //send a boolean message to show TRUE/FALSE
    public boolean MarkSelectedSpot(long DBspotID){

    }

    //
    public Ticket getTicketForPrint(){

    }
  
}


