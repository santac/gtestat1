package myarchive;

import wsiarchive.*;


// Redundantes Archiv, das in mehrere Unterarchive gleichzeitig schreibt
public class RedundantArchive implements IArchive {
           
        private String name; // Name
        private IArchiveList archives; // Archive
        private IJournalList journals = new EmptyJournal(); // ItemSpeicherungsPlatz
        private int quorum; // Quorum
        
    // Das Quorum sagt, auf wievielen Unterarchiven der Schreibvorgang
    // erfolgreich sein muss, damit der Gesamtschreibvorgang erfolgreich ist.
    public RedundantArchive(String name, IArchiveList archives, int quorum) {
        this.name = name;
        this.archives = archives;
        this.quorum = quorum;
    }

    // Get-Methoden
    // Für Name
    public String getName() {
        return this.name;
    }
    
    // Für Archive
    public IArchiveList getArchives() {
        return this.archives;
    }    
        
    // Für Quorum
    public int getQuorum() {
        return this.quorum;
    }
        
    // Item ins Archiv schreiben
    public IPutResult put(Item item) {
        
        IItemId id = new MyItemId();
        RedJournal redJ = new RedJournal(id);
        
        IPutResultList putList = this.archives.redundantPut(item, redJ);
        
        if (putList.countOKs() >= this.getQuorum()) {
            this.journals = new JournalList(redJ, this.journals);
            
            return new OKPutResult(id);
        } else {
            return new FullPutResult();
        }
    }
        
    // Mehrere Items ins Archiv schreiben
    public wsiarchive.IPutResultList putMultiple(wsiarchive.IItemList items) {
        
        IItemListSorted myItems = items.toMyItemList().toItemListSorted(1);
        IRedundantPutResultListSorted redlist = this.archives.redundantPutMultiple(myItems);
        
        IPutResultList finalResult = redlist.toPutResultListByItems(myItems, this.quorum).toPutResultList();
        
        return finalResult.toWSIPutResultList();
    }
    
    // Item aus Archiv auslesen
    public IGetResult get(IItemId id) {
        IJournalResult get = this.journals.getArchiveById(id);
        
        if (get instanceof OKJournalResult)  {
            IArchive archive = ((OKJournalResult) get).getArchive();
            
            IJournalResult idResult = this.journals.getIdByArchive(archive);
            
            if (idResult instanceof OKJournalResult) {
                IItemId idRed = ((OKJournalResult) idResult).getItemId();
                IGetResult finalGet = archive.get(idRed);
                
                if (finalGet instanceof ItemResult) {
                    Item item = ((ItemResult) finalGet).getItem();
                    return new ItemIdResult(item, id);
                    
                } else {
                    return new NoItemResult();
                }
                
            } else {
                throw new AssertionError("Keine ID gefunden.");
            }
                        
        } else {
            return this.archives.getAll(id);
        }
    }

    
}
