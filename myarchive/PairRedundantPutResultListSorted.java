package myarchive;

import wsiarchive.*;

// Kurzbeschreibung
public class PairRedundantPutResultListSorted implements IRedundantPutResultListSorted {

    private IPutResultListSorted first; // Liste der Ergebnisse
    private IRedundantPutResultListSorted rest; // Rest

    PairRedundantPutResultListSorted (IPutResultListSorted first, IRedundantPutResultListSorted rest) {
        this.first = first;
        this.rest = rest;
    }
    
    // "Übersetze" Liste von ResultListen in eine Liste von einzelnen PutResults
    public IPutResultListSorted toPutResultListByItems(IItemListSorted items, int quorum) {
        
        if (items instanceof PairItemListSorted) {
            Item item = ((PairItemListSorted) items).getItem();
            int number = ((PairItemListSorted) items).getNumber();
            
            // Prüfe: Wurde für item das quorum erfüllt?
            
            // Dazu:
            // Hole alle Archive mit Nummer n
            IPutResultList resultListN = this.getResultListSortedByNumber(number).toPutResultList();
            
            // Zähle OK's
            int oks = resultListN.countOKs();
            
            // Ergebnis für item:
            IPutResult putResult;
            if (oks >= quorum) {
                putResult = new OKPutResult(new MyItemId());
            } else {
                putResult = new FullPutResult();
            }
            
            // Rekursiver Aufruf
            IItemListSorted rest = ((PairItemListSorted) items).getRest();
            
            return new PairPutResultListSorted(putResult, number, this.toPutResultListByItems(rest, quorum));
        } else {
            return new EmptyPutResultListSorted();
        }
        
    }
    
    
    // Alle Archive der Nummer n ausgeben
    public IPutResultListSorted getResultListSortedByNumber(int n) {
        
        IPutResult result = this.first.getResultByNumber(n);
        
        return new PairPutResultListSorted(result, n, this.rest.getResultListSortedByNumber(n));
    }

    

}
