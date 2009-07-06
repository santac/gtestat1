package myarchive;

import wsiarchive.*;


// Rückgabewerte für Journalzugriffe
public interface IJournalResult {

    public IJournal getJournal ();
    
    public IArchive getArchive ();

}
