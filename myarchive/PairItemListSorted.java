package myarchive;

import wsiarchive.*;

// Paar aus einem Item, einer Nummer und dem Rest der Liste
public class PairItemListSorted implements IItemListSorted {
    private Item item;
    private int number;
    private IItemListSorted rest;
    
    public PairItemListSorted(Item item, int number, IItemListSorted rest) {
        this.item = item;
        this.number = number;
        this.rest = rest;
    }
    
    // Gets:
    public Item getItem () { return this.item; }
    public int getNumber () { return this.number; }
    public IItemListSorted getRest() { return this.rest; }
        
            //Prüfen, welche Items geputtet werden können und in einer Liste festhalten
    public IItemListSorted semanticCanPut(IItemPredicate predicate) {
        if (predicate.matches(this.item)) {
            return new PairItemListSorted(this.item, this.number, this.rest.semanticCanPut(predicate));
        } else return this.rest.semanticCanPut(predicate);
    }
    
    // Für alle Items ein FullPutResult ausgeben
    public IPutResultListSorted toFullPutResultListSorted () {
        return new PairPutResultListSorted(new FullPutResult(), this.number, this.rest.toFullPutResultListSorted());
    }
    
   
    //Ein Item löschen
    public IItemListSorted delete(int itemNumber) {
        if (this.number == itemNumber)  {
            return this.rest.delete(itemNumber);
        } else return new PairItemListSorted(this.item, this.number, this.rest.delete(itemNumber));
    }
    
    //in ItemList umwandeln
    public IItemList toItemList() {
        return new PairItemList(this.item, this.rest.toItemList());
    }
}
