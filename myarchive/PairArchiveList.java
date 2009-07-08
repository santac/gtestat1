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
    
    // Methode für Overflow: putMultiple
    public IPutResultListSorted overflowPutMultiple (IItemListSorted items, IPutResultListSorted finalResult) {
        //welche Items können in diesem Teilarchiv gesetzt werden?
        //IItemListSorted canPut = items.overflowCanPut(this);

        //Ergebnis, welche von den canPuts gesetzt wurden (OKPutResult oder FullPutResult)
        IPutResultList result = this.first.putMultiple(items.toItemList().toWSIItemList()).toMyPutResultList();
        //und: entsprechen auch der Reihenfolge von canPut
        IPutResultListSorted canResult = result.toPutResultListSorted(items);
        
        //ermitteln, welche Items mitgenommen werden müssen
        IItemListSorted notYetPut = canResult.deleteAlreadyPutFromItems(items);
        //da sie nicht gesetzt werden konnten/durften
        
        //Ergebnisse zu Ergebnisliste (Endergebnis) hinzufügen und ins nächste Archiv mitnehmen
        return this.rest.overflowPutMultiple(notYetPut, canResult.addAlreadyPutToFinalResult(finalResult));
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
    
    // Methode für RedundantArchive: putMultiple
    public IRedundantPutResultListSorted redundantPutMultiple(IItemListSorted items) {

        //Ergebnis, welche von den canPuts gesetzt wurden (OKPutResult oder FullPutResult)
        IPutResultList result = this.first.putMultiple(items.toItemList().toWSIItemList()).toMyPutResultList();
        // Umwandeln in sortierte Liste
        IPutResultListSorted sortedList = result.toPutResultListSorted(items);
        
        return new PairRedundantPutResultListSorted(sortedList, this.rest.redundantPutMultiple(items));
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
