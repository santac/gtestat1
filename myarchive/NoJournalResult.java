package myarchive;

import wsiarchive.*;

// Kurzbeschreibung
public class NoJournalResult implements IJournalResult {

    NoJournalResult () {}
    
    
    public IJournal getJournal () {
        throw new AssertionError("No journal: NoJournalResult");
    }
    
    public IArchive getArchive () {
        throw new AssertionError("No archive: NoJournalResult");
    }

}
