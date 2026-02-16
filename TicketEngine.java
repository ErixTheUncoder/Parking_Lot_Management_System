public class TicketEngine {
    private final TicketDAO dao;  
    private final Building building;                      //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< dao declared 

    public TicketEngine(TicketDAO dao, Building bld) {              //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<dao passed during construction!
        this.dao = dao;
        this.building = bld;
    }

    /**
     * Handles the Entry Transaction
     */
    public Ticket processEntry(Spot spot , Vehicle newVehicle) {

        String plate = newVehicle.getLicensePlate();
        // 1.Create the Ticket (ID, Time, and Status handled by constructor)
        Ticket newTicket = new Ticket(plate, spot.getDBSpotID(), true);
        
        // 2. Update the Spot object state
        spot.setOccupied(true, plate);

        //3. update the flatSearchMap index to update search Index inside floor  
        int floorI=spot.getFloorNum();
       
        Floor floor= building.getFloor(floorI);
        floor.updateFlatSearchMap(spot, true);  //true indicates it is from entry which remove the spot
        
         //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< changing  down and TicketDAO>>>>>>>>>>>>>>>>>>>>
        // 4. Persist the changes
        dao.saveNewTicketAndOccupySpot(newTicket, spot , newVehicle);            
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

         //3. update the flatSearchMap index to update search Index inside floor  
        int floorI=spot.getFloorNum();
        Floor floor= building.getFloor(floorI);
        floor.updateFlatSearchMap(spot, false);  //false indicates it is from exit which will add the spot
        
        // 3. Finalize in DB
        dao.closeTicketAndFreeSpot(ticket, spot); //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< dao call !
    }
}