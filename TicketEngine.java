public class TicketEngine {
    private final TicketDAO dao;                        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< dao declared 

    public TicketEngine(TicketDAO dao) {              //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<dao passed during construction!
        this.dao = dao;
    }

    /**
     * Handles the Entry Transaction
     */
    public Ticket processEntry(String plate, Spot spot) {
        // 1. Create the Ticket (ID, Time, and Status handled by constructor)
        Ticket newTicket = new Ticket(plate, spot.getDBSpotID(), true);
        
        // 2. Update the Spot object state
        spot.setOccupied(true, plate);
        
        // 3. Persist the changes
        //pass both so the DAO can perform an atomic update
        dao.saveNewTicketAndOccupySpot(newTicket, spot);             //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< dao call!
        
        return newTicket;
    }

    /**
     * Handles the Exit Transaction
     */
    public void processExit(Ticket ticket, Spot spot) {
        // 1.Update Ticket using its own internal method
        ticket.changeStat(false); 
        // calling setPaid for the business logic
        ticket.setPaid(); 
        
        // 2.Update Spot using its internal method
        spot.releaseVehicle();
        
        // 3. Finalize in DB
        dao.closeTicketAndFreeSpot(ticket, spot); //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< dao call !
    }
}