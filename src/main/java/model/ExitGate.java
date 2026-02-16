package model;

import db.*;
import service.FineManager;

public class ExitGate {
    private int gateID;
    private Building building;
    private TicketEngine ticketEngine;
    private FineManager fineManager;

    public ExitGate(int gateID, Building building) {
        this.gateID = gateID;
        this.building = building;
        this.ticketEngine = new TicketEngine(building);
        this.fineManager = new FineManager();
    }

    public Ticket retrieveTicket(String licensePlate) {
        Ticket ticket = TicketDAO.findActiveByLicensePlate(licensePlate);
        
        if (ticket != null) {
            // Load the spot information
            Spot spot = null;
            for (int i = 0; i < building.getTotalFloors(); i++) {
                Floor floor = building.getFloor(i);
                if (floor != null) {
                    spot = floor.getSpot(ticket.getSpotID());
                    if (spot != null) {
                        ticket.setSpot(spot);
                        break;
                    }
                }
            }
        }
        
        return ticket;
    }

    public double calculateBasePrice(Ticket ticket, SpotType spotType) {
        long hours = ticket.getDurationHours();
        double baseRate = PriceRegistry.getBaseRate(spotType);
        return hours * baseRate;
    }

    public double applyDiscount(double subtotal) {
        // For now, no discount
        // Can be extended to check membership, promotional codes, etc.
        return 0.0;
    }

    public double retrieveFines(String licensePlate) {
        Ticket ticket = retrieveTicket(licensePlate);
        if (ticket == null) {
            return 0.0;
        }
        
        long hoursParked = ticket.getDurationHours();
        return fineManager.getTotalFines(licensePlate, hoursParked);
    }

    public Invoice generateInvoice(Ticket ticket, double basePrice, 
                                   double floorPremium, double discount, 
                                   double fines) {
        Invoice invoice = new Invoice();
        invoice.setLicensePlate(ticket.getLicensePlate());
        invoice.setBasePrice(basePrice);
        invoice.setFloorPremium(floorPremium);
        invoice.setDiscountAmount(discount);
        invoice.setFines(fines);
        invoice.calculateTotal();
        
        return invoice;
    }

    public boolean commitInvoice(Invoice invoice) {
        try {
            Ticket ticket = retrieveTicket(invoice.getLicensePlate());
            if (ticket == null) {
                return false;
            }

            Spot spot = ticket.getSpot();
            if (spot == null) {
                return false;
            }

            // Save invoice
            InvoiceDAO.save(invoice, ticket.getTicketID());

            // Mark fines as paid
            fineManager.markFinesAsPaid(invoice.getLicensePlate());

            // Update exit time
            VehicleDAO.updateExitTime(invoice.getLicensePlate(), 
                java.time.LocalDateTime.now());

            // Close ticket and free spot
            ticketEngine.processExit(ticket, spot);

            return true;

        } catch (Exception e) {
            System.err.println("Error committing invoice: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public int getGateID() {
        return gateID;
    }
}
