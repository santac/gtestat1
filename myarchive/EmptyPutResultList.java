package myarchive;



// leere Liste von IPutResults
public class EmptyPutResultList implements IPutResultList {
    public EmptyPutResultList() {
    }
    
    // in IPutResultList aus wsiarchive umwandeln
    public wsiarchive.IPutResultList toWSIPutResultList() {
        return new wsiarchive.EmptyPutResultList();
    }
    
    // OKs zählen: 0
    public int countOKs () { return 0; }
    
        //nummerieren einer Ergebnisliste entsprechend einer IItemListSorted
    public IPutResultListSorted toPutResultListSorted(IItemListSorted putList) {
            return new EmptyPutResultListSorted();
        }

}
