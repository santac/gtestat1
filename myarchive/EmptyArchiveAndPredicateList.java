package myarchive;

import wsiarchive.*;

// leere Liste auf Archiv/Prädikat-Paaren
public class EmptyArchiveAndPredicateList implements IArchiveAndPredicateList {
    public EmptyArchiveAndPredicateList() {
    }
    
    //Prüfen, ob Prädikat passt; wenn ja, speichern, wenn nicht in Standard-Archiv von SemanticArchive speichern
    public IPutResult put(Item item, SemanticArchive semantic) {
        IArchive standard = semantic.getStandard();
        IPutResult put = standard.put(item);

        // Speicherung erfolgreich, ID und Archiv merken
        if (put instanceof OKPutResult) {
            semantic.addJournal( ((OKPutResult) put).getId(), standard);
        }
        
        return put;
    }
    
    // Item aus Archiv auslesen
    public IGetResult getAll (IItemId id, IArchive standard) {
        return standard.get(id);
    }
    
    // Prüfen, ob items aus reinpassen und diejenigen mit putMultiple setzen
    public IPutResultListSorted semanticPutMultiple(IItemListSorted items, IPutResultListSorted finalResult, IArchive standard) {
        //Ergebnis, welche in standard gesetzt wurden (OKPutResult oder FullPutResult)
        IPutResultList result = standard.putMultiple(items.toItemList().toWSIItemList()).toMyPutResultList();
        
        //und: zuordnen der Nummern zum Ergebnis
        IPutResultListSorted standardResult = result.toPutResultListSorted(items);
        
        return standardResult.addToFinalResult(finalResult);
    }
}
