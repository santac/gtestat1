package myarchive;



// Kurzbeschreibung
public class EmptyRedundantPutResultListSorted implements IRedundantPutResultListSorted {

    EmptyRedundantPutResultListSorted() {}
    
    public IPutResultListSorted toPutResultListByItems(IItemListSorted items, int quorum) {
        return items.toFullPutResultListSorted();
    }
    
    
    public IPutResultListSorted getResultListSortedByNumber(int n) {
        return new EmptyPutResultListSorted();
    }

}
