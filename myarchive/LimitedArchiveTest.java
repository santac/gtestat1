package myarchive;

import wsiarchive.*;
import org.junit.*;

public class LimitedArchiveTest extends de.tuebingen.informatik.Test {

    // LimitedArchive (WORM-Archiv: Name // Groesse in MB)
    IArchive sda1 = new LimitedArchive(new WORM("SDA1"), 5000);
    IArchive hdd1 = new LimitedArchive(new WORM("HDD1"), 1000);
    IArchive hdd2 = new LimitedArchive(new WORM("HDD1"), 2000);
    
    // Items (Inhalt // Groesse in MB)
    Item mails = new Item("eMails", 100);
    Item mp3s = new Item("MP3s", 1800);
    Item bilder = new Item("Bilder", 62);
    Item videos = new Item("Videos", 4320);
    Item ebooks = new Item("eBooks", 3023);
    Item backup = new Item("Sicherung", 6430);
    
    @Test
    public void limited() {
        
        // Items hinzufügen, bei Videos sollte die Platte voll sein!
        IPutResult put;
        
        // Prüfen ob items auch hinzugefügt wurden
        IGetResult get;
        
        put = sda1.put(mp3s);
        checkExpect(put instanceof OKPutResult, true);
        get = sda1.get(((OKPutResult) put).getId());
        checkExpect(get instanceof ItemResult, true);
        
        put = sda1.put(bilder);
        checkExpect(put instanceof OKPutResult, true);
        get = sda1.get(((OKPutResult) put).getId());
        checkExpect(get instanceof ItemResult, true);
        
        put = sda1.put(videos);
        checkExpect(put instanceof FullPutResult, true);
        
        // Weitere Testfaelle
        put = hdd1.put(ebooks); // eBooks zu gross
        checkExpect(put instanceof FullPutResult, true);
        
        put = hdd1.put(bilder); // Bilder abspeichern
        checkExpect(put instanceof OKPutResult, true);
        get = hdd1.get(((OKPutResult) put).getId());
        checkExpect(get instanceof ItemResult, true);
        
         put = hdd1.put(mails); // Mails abspeichern
        checkExpect(put instanceof OKPutResult, true);
        get = hdd1.get(((OKPutResult) put).getId());
        checkExpect(get instanceof ItemResult, true);
        
        put = hdd1.put(backup); // Sicherung zu gross
        checkExpect(put instanceof FullPutResult, true);
        
    }
        

}
