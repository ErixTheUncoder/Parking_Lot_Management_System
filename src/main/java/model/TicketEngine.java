package model;

import db.TicketDAO;

public class TicketEngine {
    private final Building building;

    public TicketEngine(Building bld) {
        this.building = bld;
    }

    public Ticket processEntry(String plate, Spot spot) {
        // 1. Create the Ticket
        Ticket newTicket = new Ticket(plate, spot.getDBSpotID(), true);
        newTicket.setSpot(spot);
        
        // 2. Update the Spot object state
        spot.setOccupied(true, plate);

        // 3. Update the flatSearchMap index to update search Index inside floor
        Floor floor = spot.getFloor();
        if (floor != null) {
            floor.updateFlatSearchMap(spot, true);
        }
        
        // 4. Persist the changes
        TicketDAO.saveNewTicketAndOccupySpot(newTicket, spot);
        
        return newTicket;
    }

    public void processExit(Ticket ticket, Spot spot) {
        // 1. Update Ticket
        ticket.changeStat(false);
        ticket.setPaid();
        
        // 2. Update Spot
        spot.releaseVehicle();

        // 3. Update the flatSearchMap index
        Floor floor = spot.getFloor();
        if (floor != null) {
            floor.updateFlatSearchMap(spot, false);
        }
        
        // 4. Finalize in DB
        TicketDAO.closeTicketAndFreeSpot(ticket, spot);
    }
}
