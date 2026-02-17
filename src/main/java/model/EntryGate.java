package model;

import db.VehicleDAO;
import java.util.List;

public class EntryGate {
    private int gateID;
    private Building building;
    private CompatibilityRegistry compatibilityRegistry;
    private AllocationEngine allocationEngine;
    private TicketEngine ticketEngine;

    public EntryGate(int gateID, Building building, CompatibilityRegistry registry) {
        this.gateID = gateID;
        this.building = building;
        this.compatibilityRegistry = registry;
        this.allocationEngine = new AllocationEngine(building, registry, new SimpleSorter());
        this.ticketEngine = new TicketEngine(building);
    }

    public List<Spot> findAvailableSpots(VehicleType vehicleType) {
        return allocationEngine.getAvailableSpotsForVehicle(vehicleType);
    }

    public Ticket processEntry(Vehicle vehicle, String spotID) {
        // Find the spot
        Spot spot = null;
        for (int i = 0; i < building.getTotalFloors(); i++) {
            Floor floor = building.getFloor(i);
            if (floor != null) {
                for (Spot s : floor.getAllSpots()) {
                    if (s.getSpotName().equals(spotID)) {
                        spot = s;
                        break;
                    }
                }
                if (spot != null) break;
            }
        }

        if (spot == null || spot.isOccupied()) {
            return null;
        }

        // Save vehicle to database
        vehicle.setEntryTime(java.time.LocalDateTime.now());
        VehicleDAO.save(vehicle);

        // Use TicketEngine to create ticket & update spot
        Ticket ticket = ticketEngine.processEntry(vehicle.getLicensePlate(), spot);
        
        return ticket;
    }

    public int getGateID() {
        return gateID;
    }
}
