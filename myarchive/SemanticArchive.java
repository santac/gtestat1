package myarchive;

import wsiarchive.*;

// Archiv, das nach Prädikaten entscheidet, in welches Teilarchiv geschrieben wird.
public class SemanticArchive implements IArchive {

    private String name;
    private IArchiveAndPredicateList archivesAndPredicates;
    private IArchive standard;
    private IJournalList journals = new EmptyJournal(); // Merke, wo welches Item gespeichert wurde
    //private IJournalList standardJournals = new EmptyJournal();//ebenso im Standard-Archiv

    // Die Archive in archivesAndPredicates werden der Reihe nach durchprobiert;
    // wenn kein Prädikat passt, wird in das Archiv standard geschrieben.
    public SemanticArchive(String name,
                           IArchiveAndPredicateList archivesAndPredicates,
                           IArchive standard) {
        this.name = name;
        this.archivesAndPredicates = archivesAndPredicates;
        this.standard = standard;
    }
    
    // Name des Archivs liefern
    public String getName() {
        return this.name;
    }

    // Item ins Archiv schreiben
    public IPutResult put(Item item) {
          return this.archivesAndPredicates.put(item, this);
    }
    
    // Mehrere Items ins Archiv schreiben (wsiarchive)
    public wsiarchive.IPutResultList putMultiple(wsiarchive.IItemList items) {
        return this.putMultiple(items.toMyItemList()).toWSIPutResultList();
    }
    
    // Mehrere Items ins Archiv schreiben (myarchive)
    public IPutResultList putMultiple(IItemList items) {
        return this.archivesAndPredicates.semanticPutMultiple(items.toItemListSorted(1), new EmptyPutResultListSorted(), this.standard).toPutResultList();
    }
    
    // Item aus Archiv auslesen
    public IGetResult get(IItemId id) {
        IJournalResult get = this.journals.getArchiveById(id);
        
        if (get instanceof OKJournalResult) {
            IArchive archive = get.getArchive();
            
            return archive.get(id);
            
        } else {
            return this.archivesAndPredicates.getAll(id, this.standard);
        }
    }
    
    // Neues ItemID-Archiv-Paar zum Journal hinzufügen
    public void addJournal (IItemId id, IArchive archive) {
        this.journals = new JournalList(new Journal(id, archive), this.journals);        
    }
    
    //IArchiveAndPredicateList auslesen
    public IArchiveAndPredicateList getArchivesAndPredicates() {
        return this.archivesAndPredicates;
    }
    
    //Standard-Archive auslesen
    public IArchive getStandard() {
        return this.standard;
    }
}
