package myarchive;

import wsiarchive.*;

// Größer als - Prädikat
public class GreaterThan implements IItemPredicate {
    int min;
    
    GreaterThan(int min) {
        this.min = min;
    }
    
    //matches
    public boolean matches(Item item) {
        return item.getSize() > this.min;
    }
}
