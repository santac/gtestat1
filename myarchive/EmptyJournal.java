package myarchive;

import wsiarchive.*;

// Leeres Journal
public class EmptyJournal implements IJournalList {

    EmptyJournal () {}
    
    public IJournalResult getArchiveById (IItemId id) {
        return new NoJournalResult();
    }
    
    public IJournalResult getIdByArchive (IArchive archive) {
        return new NoJournalResult();
    }
    
    public IJournal getFirst () {
        throw new AssertionError("Empty list: EmptyJournal.getFirst()");
    }
    public IJournalList getRest () {
        throw new AssertionError("Empty list: EmptyJournal.getRest()");
    }
    
    public int length() { return 0; }

}
