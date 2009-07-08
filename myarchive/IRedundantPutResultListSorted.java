package myarchive;



// Kurzbeschreibung
public interface IRedundantPutResultListSorted {

    // Sortierte ItemListe auf Quorum prüfen
    public IPutResultListSorted toPutResultListByItems(IItemListSorted items, int quorum);
    
    // Gebe mir alle Archive mit Nummer n aus
    public IPutResultListSorted getResultListSortedByNumber(int n);

}
