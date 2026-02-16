// TicketDAO.java

 // To be implemented by the DB person
 //implement each of these method to retreive data/sava data to Database
public interface TicketDAO {  
    void saveNewTicketAndOccupySpot(Ticket t, Spot s);  //used in TicketEngine >>refer
    void closeTicketAndFreeSpot(Ticket t, Spot s);  //used in TicketEngine >>refer
    boolean isSpotActuallyFree(long id);         //used in entryGate >>refer
} 