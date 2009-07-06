package myarchive;

import wsiarchive.*;

// Liste von Archiven
public interface IArchiveList {

    // Methode für Overflow: put
    // Effekt: Journal updaten
    public IPutResult overflowPut (Item item, OverflowArchive current);
    
    // Methode für Redundant: put
    // Effekt: Journal updaten
    public IPutResultList redundantPut (Item item, RedJournal redJ);
    
    
    
    public IGetResult getAll (IItemId id);
    
}
