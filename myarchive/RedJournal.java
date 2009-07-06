package myarchive;

import wsiarchive.*;
import java.util.Random;

// Journal für RedundantArchive
public class RedJournal implements IJournal  {

    private IJournalList list = new EmptyJournal(); // Liste aller Schreib
    private IItemId id; // ID über die auf das Journal zugegriffen wird
    
    RedJournal (IItemId id) {
        this.id = id;
    }
    
    // ItemID ausgeben
    public IItemId getItemId() {
        return this.id;
    }
    
    // Zufällig eines der Archive aus der Journal-Liste ausgeben
    public IArchive getArchive() {
        Random rand = new Random();
        
        // Archiv an der zufällig gewählten Stelle i wählen
        int i = rand.nextInt(list.length()) - 1;
        
        return this.getArchiveHelper(i, this.list);
    }
    
    // Helper: i-tes Element der Liste ausgeben
    public IArchive getArchiveHelper (int i, IJournalList list) {
        if (i < 0) {
            throw new AssertionError("Empty list: RedJournal.getArchive()");
        } else if (i == 0) {
            return list.getFirst().getArchive();
        } else {
            return this.getArchiveHelper(i-1, list.getRest());
        }
    }
    
    public void addJournal (IItemId id, IArchive archive) {
        this.list = new JournalList(new Journal(id, archive), this.list);
    }
    
}
