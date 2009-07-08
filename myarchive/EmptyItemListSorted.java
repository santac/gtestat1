package myarchive;

import wsiarchive.*;

// leere ItemListSorted
public class EmptyItemListSorted implements IItemListSorted {
    public EmptyItemListSorted(){}
    
    //Prüfen, welche Items geputtet werden können und in einer Liste festhalten
    public IItemListSorted semanticCanPut(IItemPredicate predicate) {
        return new EmptyItemListSorted();
    }
    
    public IPutResultListSorted toFullPutResultListSorted () {
        return new EmptyPutResultListSorted();
    }
    
    //Ein Item löschen
    public IItemListSorted delete(int itemNumber) {
        return new EmptyItemListSorted();
    }
    
    //in ItemList umwandeln
    public IItemList toItemList() {
        return new EmptyItemList();
    }
}
