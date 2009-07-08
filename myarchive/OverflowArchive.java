package myarchive;

import wsiarchive.*;


// Archiv, bei dem erst ein Archiv vollgeschrieben wird, dann das nächste etc.
public class OverflowArchive implements IArchive {

    private String name; // Name
    private IArchiveList archives; // Archive
    private IJournalList journals = new EmptyJournal(); // Merke, wo welches Item gespeichert wurde

    //private IPutResultList list; // Ergebnisse alle Schreibvorgänge bisher

    // Die Archive werden der Reihe nach gefüllt
    public OverflowArchive(String name, IArchiveList archives) {
        this.name = name;
        this.archives = archives;
    }
    
    // Name des Archivs liefern
    public String getName() {
        return this.name;
    }
    
    // Archive ausgeben
    public IArchiveList getArchives() {
        return this.archives;
    }
    
    public IJournalList getJournals () { return this.journals; }
    

    // Item ins Archiv schreiben
    public IPutResult put(Item item) {
        return this.archives.overflowPut(item, this);
    }
    
    // Mehrere Items ins Archiv schreiben (wsiarchive)
    public wsiarchive.IPutResultList putMultiple(wsiarchive.IItemList items) {
        return this.putMultiple(items.toMyItemList()).toWSIPutResultList();
    }
    
    // Mehrere Items ins Archiv schreiben (myarchive)
    public IPutResultList putMultiple(IItemList items) {
        return this.archives.overflowPutMultiple(items.toItemListSorted(1), new EmptyPutResultListSorted()).toPutResultList();
    }
    
    // Item aus Archiv auslesen
    public IGetResult get (IItemId id) {
        IJournalResult get = this.journals.getArchiveById(id);
        
        if (get instanceof OKJournalResult) {
            IArchive archive = get.getArchive();
            
            return archive.get(id);
            
        } else {
            return this.archives.getAll(id);
        }
    }
        
    // Neues ItemID-Archiv-Paar zum Journal hinzufügen
    public void addJournal (IItemId id, IArchive archive) {
        
        this.journals = new JournalList(new Journal(id, archive), this.getJournals());        
    }
}
