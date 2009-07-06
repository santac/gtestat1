package myarchive;

import wsiarchive.*;

// nichtleere Liste von Archiven

public class PairArchiveList implements IArchiveList {
    private IArchive first;
    private IArchiveList rest;
    
    public PairArchiveList(IArchive first, IArchiveList rest) {
        this.first = first;
        this.rest = rest;
    }
    
    // Gets
    public IArchive getFirst () { return this.first; }
    public IArchiveList getRest () { return this.rest; }
    
    
    // Methode für Overflow: put
    // Effekt: Journal updaten
    public IPutResult overflowPut (Item item, OverflowArchive current) {
        IPutResult put = this.first.put(item);
        
        if (put instanceof OKPutResult) {
        
            // Journal updaten
            current.addJournal( ((OKPutResult) put).getId(), this.first);
            
            return put;
            
        // Rekursion
        } else {
            return this.rest.overflowPut(item, current);
        }
    }
    
    
    // Methode für RedundantArchive: put
    // Effekt: Journal updaten
    public IPutResultList redundantPut (Item item, RedJournal journal) {
        IPutResult put = this.first.put(item);
        
        // Journal updaten
        if (put instanceof OKPutResult) {
            journal.addJournal(((OKPutResult) put).getId(), this.first);
        }
        
        return new PairPutResultList(put, this.rest.redundantPut(item, journal));
    }
    
    
    
    
    // In allen Archiven get aufrufen
    public IGetResult getAll (IItemId id) {
        IGetResult get = this.first.get(id);
        
        if (get instanceof ItemResult) {
            return get;
        } else {
            return this.rest.getAll(id);
        }
    }
        
}
