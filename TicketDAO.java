// TicketDAO.java

 // To be implemented by the DB person
 //implement each of these method to retreive data/sava data to Database
public class TicketDAO {  

    public TicketDAO() {
    }
    
   void saveNewTicketAndOccupySpot(Ticket t, Spot s, Vehicle v);  //used in TicketEngine >>refer
   void closeTicketAndFreeSpot(Ticket t, Spot s);  //used in TicketEngine >>refer
} 


//long is used in DBspotID of the spot ; Database type must match!