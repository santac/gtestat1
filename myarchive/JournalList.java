package myarchive;

import wsiarchive.*;

// Kurzbeschreibung
public class JournalList implements IJournalList {

    private IJournal first; // 1. Journal der Liste
    private IJournalList rest; // Rest
    
    JournalList (IJournal first, IJournalList rest) {
        this.first = first;
        this.rest = rest;
    }
    
    public IJournal getFirst () { return this.first; }
    public IJournalList getRest() { return this.rest; }
    
    // Archiv des Items
    public IJournalResult getArchiveById (IItemId id) {
        if (this.first.getItemId() == id) {
            return new OKJournalResult(this.first); // OK mit gefundenem Journal zurückgeben
        } else {
            return this.rest.getArchiveById(id);
        }
    }

    // Länge
    public int length() { return 1+this.rest.length(); }
}
