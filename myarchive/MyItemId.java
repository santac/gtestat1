package myarchive;

import wsiarchive.*;

// Kurzbeschreibung
public class MyItemId implements IItemId {

    MyItemId () {}
    
    // Gleichheit prüfen
    public boolean same(IItemId other) {
        return this == other;
    }

}
