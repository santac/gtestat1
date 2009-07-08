package myarchive;

import wsiarchive.*;

// Kurzbeschreibung
public interface IJournalList {

    // Archiv eines Items zurückgeben
    public IJournalResult getArchiveById (IItemId id);
    
    // Id eines Archives ausgeben
    public IJournalResult getIdByArchive (IArchive archive);
    
    // Länge der Liste
    public int length ();
    
    public IJournal getFirst();
    public IJournalList getRest();
    
}
