package myarchive;

import wsiarchive.*;

// Liste aus ArchiveAndPredicate
public interface IArchiveAndPredicateList {

    //Prüfen, ob Prädikat passt; wenn ja, speichern, wenn nicht in Standard-Archiv von SemanticArchive speichern
    public IPutResult put(Item item, SemanticArchive semantic);
    
    
    // Alle Archive durchgehen, wenn nichts gefunden wurde im Standard-Archiv gucken und das dortige Ergebnis ausgeben
    public IGetResult getAll (IItemId id, IArchive standard);
    
    // Prüfen, ob items aus reinpassen und diejenigen mit putMultiple setzen
    public IPutResultListSorted semanticPutMultiple(IItemListSorted items, IPutResultListSorted finalResult, IArchive standard);

}
