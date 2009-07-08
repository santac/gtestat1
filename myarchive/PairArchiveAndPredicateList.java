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
        if (this.predicate.matches(item)) {
            IPutResult put = this.archive.put(item);   //sonst:
            
            // Speicherung erfolgreich, ergebnis im Journal merken
            if (put instanceof OKPutResult) {
                semantic.addJournal( ((OKPutResult) put).getId(), this.archive);
            }
            
            //wenn Prädikat auf erstes Archiv passt put ausgeben
            return put;
        } else {
            return this.rest.put(item, semantic);//sonst rekursiver Aufruf
        }
    }

    
    // Item aus Archiv auslesen
    public IGetResult getAll (IItemId id, IArchive standard) {
        
        IGetResult result = this.archive.get(id);
        
        if (result instanceof ItemResult) {
            return result;
        } else {
            return this.rest.getAll(id, standard);
        }
    }
    
    // Prüfen, ob items aus Liste reinpassen und diejenigen mit putMultiple setzen
    public IPutResultListSorted semanticPutMultiple(IItemListSorted items, IPutResultListSorted finalResult, IArchive standard) {
        IItemListSorted canPut = items.semanticCanPut(this.predicate);//welche Items können in diesem Teilarchiv gesetzt werden?
        
        //Ergebnis, welche von den canPuts gesetzt wurden (OKPutResult oder FullPutResult)
        IPutResultList result = this.archive.putMultiple(canPut.toItemList().toWSIItemList()).toMyPutResultList();
        IPutResultListSorted canResult = result.toPutResultListSorted(canPut); //und: entsprechen auch der Reihenfolge von canPut
        
        IItemListSorted notYetPut = canResult.deleteAlreadyPutFromItems(items);//ermitteln, welche Items mitgenommen werden müssen
        //da sie nicht gesetzt werden konnten/durften
        
        //Ergebnisse zu Ergebnisliste (Endergebnis) hinzufügen und ins nächste Archiv mitnehmen
        return this.rest.semanticPutMultiple(notYetPut, canResult.addAlreadyPutToFinalResult(finalResult), standard);
    }
}
