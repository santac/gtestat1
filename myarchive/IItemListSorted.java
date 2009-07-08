package myarchive;

import wsiarchive.*;

// IItemList mit Nummern
public interface IItemListSorted {

    //Prüfen, welche Items geputtet werden können und in einer Liste festhalten
    public IItemListSorted semanticCanPut(IItemPredicate predicate);
    
    public IPutResultListSorted toFullPutResultListSorted ();
  
    //Ein Item löschen
    public IItemListSorted delete(int itemNumber);
    
    //in ItemList umwandeln
    public IItemList toItemList();
    
}