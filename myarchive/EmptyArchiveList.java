package myarchive;

import wsiarchive.*;

// leere Archivliste
public class EmptyArchiveList implements IArchiveList {
    public EmptyArchiveList() {
    }
    
    // Methode für Overflow: put
    public IPutResult overflowPut (Item item, OverflowArchive current) {
        return new FullPutResult();
    }
    
    public IGetResult getAll (IItemId id) {
        return new NoItemResult();
    }
    
    
    // Methode für RedundantArchive: put
    public IPutResultList redundantPut (Item item, RedJournal redJ) {
        return new EmptyPutResultList();
    }
    
    
}
