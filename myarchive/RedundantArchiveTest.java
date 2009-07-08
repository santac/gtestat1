package myarchive;

import wsiarchive.*;
import org.junit.*;

public class RedundantArchiveTest extends de.tuebingen.informatik.Test {

    // Festplatten  (LimitedArchive: WORM-Archiv: Name // Groesse in MB)
    IArchive hdd1 = new LimitedArchive(new WORM("HDD1"), 1000);
    IArchive hdd2 = new LimitedArchive(new WORM("HDD2"), 1000);
    IArchive hdd3 = new LimitedArchive(new WORM("HDD3"), 1000);
    IArchive hdd4 = new LimitedArchive(new WORM("HDD4"), 500);
    IArchive hdd5 = new LimitedArchive(new WORM("HDD5"), 500);
    IArchive hdd6 = new LimitedArchive(new WORM("HDD6"), 500);
    
    // Festplatten-Listen
    IArchiveList hddlist1 = new PairArchiveList(hdd1, new EmptyArchiveList());
    IArchiveList hddlist2 = new PairArchiveList(hdd1, new PairArchiveList(hdd2, new PairArchiveList(hdd3, new EmptyArchiveList())));
    IArchiveList hddlist3 = new PairArchiveList(hdd1, new PairArchiveList(hdd2, new PairArchiveList(hdd3, new PairArchiveList(hdd4,
                        new PairArchiveList(hdd5, new EmptyArchiveList())))));
    
    // Redundant-Archive (Name // Festplatten-Liste // Quorum)
    IArchive redundant1 = new RedundantArchive("HDD", hddlist1,1);
    IArchive redundant2 = new RedundantArchive("HDD", hddlist2,2);
    IArchive redundant3 = new RedundantArchive("HDD", hddlist3,4);
    
    // Items (Inhalt // Groesse in MB)
    Item pictures = new Item("Bilder", 100);
    Item music = new Item("Musik", 200);
    Item ebooks = new Item("eBooks", 1000);
    Item movies = new Item("Filme", 2000);
    
    // Item-Listen
    IItemList items1 = new PairItemList(pictures, new EmptyItemList());
    IItemList items2 = new PairItemList(pictures, new PairItemList(music, new EmptyItemList()));
    IItemList items3 = new PairItemList(pictures, new PairItemList(music, new PairItemList(ebooks, new EmptyItemList())));
    IItemList items4 = new PairItemList(pictures, new PairItemList(music, new PairItemList(ebooks, new PairItemList(movies, new EmptyItemList()))));
    
    
    
    @Test
    public void redundant () {
        // Get-Methoden
        checkExpect(redundant1.getName(),"HDD");
        checkExpect(redundant2.getName(),"HDD");
               
        // Items hinzufuegen
        IPutResult put;
        wsiarchive.IPutResultList puts;
       
        // Überpruefen ob Items gespeichert wurden
        IGetResult get;
        
        // Bilder speichern
        put = redundant1.put(pictures);
        checkExpect(put instanceof OKPutResult, true);
        checkExpect(put instanceof FullPutResult, false);
        
        // Musik speichern
        put = redundant2.put(music);
        checkExpect(put instanceof OKPutResult, true);
        checkExpect(put instanceof FullPutResult, false);
        
        // eBooks speichern
        put = redundant3.put(ebooks);
        checkExpect(put instanceof OKPutResult, false);
        checkExpect(put instanceof FullPutResult, true);        
        
        // Filme speichern
        put = redundant1.put(movies);
        checkExpect(put instanceof FullPutResult, true);
        checkExpect(put instanceof OKPutResult, false);
        
        
      //  put = redundant1.put(pictures);
      //  IItemId id = ((OKPutResult) put).getItemId();
      //  checkExpect(get instanceof ItemResult, true);
        
       // get = redundant1.get(((OKPutResult) put).getId());
       // checkExpect(get instanceof ItemResult, true);
                
    }
   
}
