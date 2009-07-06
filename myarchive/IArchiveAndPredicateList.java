package myarchive;

import wsiarchive.*;

// Liste aus ArchiveAndPredicate
public interface IArchiveAndPredicateList {

    //Prüfen, ob Prädikat passt; wenn ja, speichern, wenn nicht in Standard-Archiv von SemanticArchive speichern
    public IPutResult put(Item item, SemanticArchive semantic);
    
    // Item aus Archiv auslesen
    public IGetResult get(IItemId id);
}
