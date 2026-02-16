package model;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SimpleSorter implements AllocStrategy {
    @Override
    public List<Long> sortAndLimit(List<Long> potentialSpots) {
        if (potentialSpots == null || potentialSpots.isEmpty()) {
            return Collections.emptyList();
        }
        
        return potentialSpots.stream()
                .sorted()
                .limit(5)
                .collect(Collectors.toList());
    }
}
