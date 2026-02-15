import java.util.List;

public interface AllocStrategy {
    // Takes a big list of potential IDs, returns the top 5
    List<Long> sortAndLimit(List<Long> potentialSpots);
}