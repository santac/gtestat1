package myarchive;

import wsiarchive.*;

// sortierte Ergebnisliste
public interface IPutResultListSorted {

    //Löschen von Items mit OKPutResult
    public IItemListSorted deleteAlreadyPutFromItems(IItemListSorted items);

    //Liste hinzufügen wenn OKPutResults
    public IPutResultListSorted addAlreadyPutToFinalResult(IPutResultListSorted finalResult);
    
    //Liste hinzufügen
    public IPutResultListSorted addToFinalResult(IPutResultListSorted finalResult);
    
    //Ein Element sortiert einfügen
    public IPutResultListSorted addSorted(IPutResult result, int number);

    //in PutResultList umwandeln
    public IPutResultList toPutResultList();
    
    // Archiv der Nummer n ausgeben
    public IPutResult getResultByNumber(int n);
    
}
