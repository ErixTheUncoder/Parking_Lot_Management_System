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

    //might not be needed >>>>>>>>>>>
    private Ticket freshTicket;

    /**
     * Constructor
     * 
     * @param gateID Unique gate identifier
     * @param building The building this gate serves
     * 
     */
    public EntryGate(int gateID, Building building ,AllocationEngine allocE , TicketEngine tickE) {
        super(gateID, building);
        this.allocEngine=allocE;
        this.ticketEngine=tickE;
    }

     //method receives String licenseNo , VehicleType type
    public void NewArrival(String licenNo , VehicleType type){
                this.newVehicle = new Vehicle(licenNo, type);
    }

    // ask the allocationEngine to return 5 best spots
    //pass it to swing GUI
    public List<Spot> getSpotToSelect(){
        if (this.newVehicle == null) {
             return List.of(); //if no vehicle is set , empty list is returned 
        }
        VehicleType type= newVehicle.getVehicleType();
        List<Spot> viewableSpots;
        viewableSpots= allocEngine.getAvailableSpotsForVehicle(type);
        return viewableSpots;
    }
//==============================================

    // call the processEntry(..) which saves ticket,spot,vehicle in DB through TicketDAO
    //from TicketEngine and assign the returned Ticket object to freshTicket

    public Ticket MarkSpotAndGetTicket(Spot selectedSpot){   
        if (selectedSpot == null || selectedSpot.isOccupied()) {
            // it throws an exception so the UI knows exactly what went wrong
            throw new IllegalArgumentException("Selected spot is unavailable or invalid.");
        }else{
            newVehicle.setEntryTime(java.time.LocalDateTime.now());
           // VehicleDAO.save(newVehicle);   //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
            return ticketEngine.processEntry(selectedSpot, newVehicle);
        }
    }

    //=====================================
        public int getGateID() {
        return gateID;
    }
    //=======================================
}


