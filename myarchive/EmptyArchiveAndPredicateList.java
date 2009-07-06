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
        //Journal updaten:
        semantic.addStandardJournal( ((OKPutResult) put).getId(), standard);
        
        return standard.put(item);
    }
    
    // Item aus Archiv auslesen
    public IGetResult get(IItemId id) {
        return new NoItemResult();
    }
}
