package myarchive;

import wsiarchive.*;

// Liste von Archiven
public interface IArchiveList {

    // Methode für Overflow: put
    // Effekt: Journal updaten
    public IPutResult overflowPut (Item item, OverflowArchive current);
    // Methode für Overflow: putMultiple
    public IPutResultListSorted overflowPutMultiple (IItemListSorted items, IPutResultListSorted finalResult);
    
    // Methode für Redundant: put
    // Effekt: Journal updaten
    public IPutResultList redundantPut (Item item, RedJournal redJ);
    // Methode für RedundantArchive: putMultiple
    public IRedundantPutResultListSorted redundantPutMultiple(IItemListSorted items);
    //public IPutResultListSorted redundantPutMultiple (IItemListSorted items, IRedundantPutResultListSorted finalResult, int quorum);
    
    
    
    public IGetResult getAll (IItemId id);
    
}
