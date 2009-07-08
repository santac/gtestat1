package myarchive;

import wsiarchive.*;

// leere Liste
public class EmptyPutResultListSorted implements IPutResultListSorted {
    public EmptyPutResultListSorted() {}

    //Löschen von Items mit OKPutResult
    public IItemListSorted deleteAlreadyPutFromItems(IItemListSorted items) {
        return items;
    }
    
        //Liste hinzufügen wenn OKPutResults
    public IPutResultListSorted addAlreadyPutToFinalResult(IPutResultListSorted finalResult) {
        return finalResult;
    }

        //Liste hinzufügen
    public IPutResultListSorted addToFinalResult(IPutResultListSorted finalResult) {
        return finalResult;
    }
 
        //Ein Element sortiert einfügen
    public IPutResultListSorted addSorted(IPutResult result, int number) {
        return new PairPutResultListSorted(result, number, this);
    }
            
    //in PutResultList umwandeln
    public IPutResultList toPutResultList() {
        return new EmptyPutResultList();
    }
    
    
    // Fehler: Kein Result mit Nummer n
    public IPutResult getResultByNumber(int n) {
        throw new AssertionError("Kein Result mit Nummer n.");
    }
}
