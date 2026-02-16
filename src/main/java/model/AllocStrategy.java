package model;

import java.util.List;

public interface AllocStrategy {
    List<Long> sortAndLimit(List<Long> potentialSpots);
}
