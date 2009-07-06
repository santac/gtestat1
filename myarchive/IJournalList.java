package myarchive;

import wsiarchive.*;

// Kurzbeschreibung
public interface IJournalList {

    // Archiv eines Items zurückgeben
    public IJournalResult getArchiveById (IItemId id);
    
    // Länge der Liste
    public int length ();
    
    public IJournal getFirst();
    public IJournalList getRest();
    
}
