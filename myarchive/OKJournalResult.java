package myarchive;

import wsiarchive.*;

// Rückgabewert für Journalzugriffe
public class OKJournalResult implements IJournalResult {

    private IJournal journal; // Journal das gefunden wurde
    private IArchive archive; // Archiv des Journals
    private IItemId id; // Id des Items
    
    OKJournalResult (IJournal journal) {
        this.journal = journal;
        this.archive = journal.getArchive();
        this.id = journal.getItemId();
    }
    
    // Journal
    public IJournal getJournal () { return this.journal; }
    
    public IItemId getItemId () { return this.id; }
    
    public IArchive getArchive () { return this.archive; }

}
