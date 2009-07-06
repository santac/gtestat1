package myarchive;

import wsiarchive.*;

// nichtleere Liste von Archive/Prädikat-Paaren
public class PairArchiveAndPredicateList implements IArchiveAndPredicateList {
    private IItemPredicate predicate;
    private IArchive archive;
    private IArchiveAndPredicateList rest;
    
    public PairArchiveAndPredicateList(IItemPredicate predicate, IArchive archive,
                                       IArchiveAndPredicateList rest) {
        this.predicate = predicate;
        this.archive = archive;
        this.rest = rest;
    }
    
    //Prüfen, ob Prädikat passt; wenn ja, speichern, wenn nicht in Standard-Archiv von SemanticArchive speichern
    public IPutResult put(Item item, SemanticArchive semantic) {
        IPutResult put = this.archive.put(item);   //sonst:
        if ((this.predicate.matches(item)) && (put instanceof OKPutResult)) {
            //Journal updaten:
            semantic.addJournal( ((OKPutResult) put).getId(), this.archive);
            //wenn Prädikat auf erstes Archiv passt und put OKPutResult liefert, put ausgeben
            return put;
        } else return this.rest.put(item, semantic);//sonst rekursiver Aufruf
    }

    // Item aus Archiv auslesen
    public IGetResult get(IItemId id) {
        IGetResult result = this.archive.get(id);
        
        if (result instanceof NoItemResult) {
            return result;
        } else return this.rest.get(id);
    }
}
