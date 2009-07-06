package myarchive;

import wsiarchive.*;

// Archiv, das nach Prädikaten entscheidet, in welches Teilarchiv geschrieben wird.
public class SemanticArchive implements IArchive {
    private String name;
    private IArchiveAndPredicateList archivesAndPredicates;
    private IArchive standard;
    private IJournalList journals = new EmptyJournal(); // Merke, wo welches Item gespeichert wurde
    private IJournalList standardJournals = new EmptyJournal();//ebenso im Standard-Archiv

    // Die Archive in archivesAndPredicates werden der Reihe nach durchprobiert;
    // wenn kein Prädikat passt, wird in das Archiv standard geschrieben.
    public SemanticArchive(String name,
                                               IArchiveAndPredicateList archivesAndPredicates,
                                               IArchive standard) {
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
        return items.putAll(this);
    }
    
    // Mehrere Items ins Archiv schreiben (myarchive)
    public IPutResultList putMultiple(IItemList items) {
        wsiarchive.IItemList wsiList = items.toWSIItemList();
        
        return (this.putMultiple(wsiList)).toMyPutResultList();        
    }
    
    // Item aus Archiv auslesen
    public IGetResult get(IItemId id) {
        IGetResult result = this.standard.get(id);
        
        if (result instanceof ItemResult) {
            return result;
        } else return this.archivesAndPredicates.get(id);
    }

    // Item aus Archiv auslesen
//     public IGetResult get(IItemId id) {
//         IJournalResult get = this.journals.getArchiveById(id);
//         
//         if (get instanceof OKJournalResult)  {
//             IArchive archive = ((OKJournalResult) get).getArchive();
//             
//             return archive.get(id);
//                         
//         } else {
//             return this.archives.getAll(id);
//         }
//     }
    
    // Neues ItemID-Archiv-Paar zum Journal hinzufügen
//     public void addJournal (IItemId id, IArchive archive) {
//         if (this.journals instanceof JournalList) {
//             ((JournalList) this.journals).add(id, archive);
//             
//         } else {
//             Journal j = new Journal (id, new PairArchiveList(archive, new EmptyArchiveList()));
//             this.journals = new JournalList(j, new EmptyJournal());
//         }
//     }
    
    // Neues ItemID-Archiv-Paar zum Journal hinzufügen
    public void addJournal (IItemId id, IArchive archive) {
        this.journals = new JournalList(new Journal(id, archive), this.journals);        
    }
    
    // Neues ItemID-Archiv-Paar zum Journal für Standard-Archiv hinzufügen
//     public void addStandardJournal (IItemId id, IArchive archive) {
//         if (this.standardJournals instanceof JournalList) {
//             ((JournalList) this.standardJournals).add(id, archive);
//             
//         } else {
//             Journal j = new Journal (id, new PairArchiveList(archive, new EmptyArchiveList()));
//             this.standardJournals = new JournalList(j, new EmptyJournal());
//         }
//     }
    
    // Neues ItemID-Archiv-Paar zum Journal hinzufügen
    public void addStandardJournal (IItemId id, IArchive archive) {        
        this.standardJournals = new JournalList(new Journal(id, archive), this.standardJournals);        
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
