package myarchive;

import wsiarchive.*;

// ein Paar aus einem Item, einer Nummer und dem Rest
public class PairPutResultListSorted implements IPutResultListSorted {
    private IPutResult result;
    private int number;
    private IPutResultListSorted rest;
    
    public PairPutResultListSorted(IPutResult result, int number, IPutResultListSorted rest) {
        this.result = result;
        this.number = number;
        this.rest = rest;
    }
    
    // Gebe mir Result mit Nummer n zurück
    public IPutResult getResultByNumber (int n) {
        if (this.number == n) {
            return this.result;
        } else {
            return this.rest.getResultByNumber(n);
        }
    }
        
    //Löschen von Items mit OKPutResult
    public IItemListSorted deleteAlreadyPutFromItems(IItemListSorted items) {
        if (this.result instanceof OKPutResult) {
            return this.rest.deleteAlreadyPutFromItems(items.delete(this.number));
        } else return this.rest.deleteAlreadyPutFromItems(items);
    }
    
    //Liste hinzufügen wenn OKPutResults
    public IPutResultListSorted addAlreadyPutToFinalResult(IPutResultListSorted finalResult) {
        if (this.result instanceof OKPutResult) {
            return this.rest.addAlreadyPutToFinalResult(finalResult.addSorted(this.result, this.number));
        } else return this.rest.addAlreadyPutToFinalResult(finalResult);
    }
    
    //Liste hinzufügen
    public IPutResultListSorted addToFinalResult(IPutResultListSorted finalResult) {
        return this.rest.addToFinalResult(finalResult.addSorted(this.result, this.number));
    }
    
    //Ein Element sortiert einfügen
    public IPutResultListSorted addSorted(IPutResult result, int number) {
        if (this.number <= number) {
            return new PairPutResultListSorted(this.result, this.number, this.rest.addSorted(result, number));
        } else return new PairPutResultListSorted(result, number, this);
    }
    
        //in PutResultList umwandeln
    public IPutResultList toPutResultList() {
        return new PairPutResultList(this.result, this.rest.toPutResultList());
    }
}
