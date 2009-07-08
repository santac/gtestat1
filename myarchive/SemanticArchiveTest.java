package myarchive;

import wsiarchive.*;
import org.junit.*;

public class SemanticArchiveTest extends de.tuebingen.informatik.Test {
    IItemPredicate p1 = new GreaterThan(100);
    
    WORM w1 = new WORM("worm1");
    WORM w2= new WORM("worm2");
    LimitedArchive l1 = new LimitedArchive(w2, 50);
    
    SemanticArchive s1 = new SemanticArchive("s1", new PairArchiveAndPredicateList(p1, w1, new EmptyArchiveAndPredicateList()), l1);
    
    Item item1 = new Item("Sperber", 50);
    Item item2 = new Item("Crestani", 100);
    Item item3 = new Item("Klaeren", 300);
    
    @Test
    public void putMultiple() {
        IItemList list1 = new PairItemList(item1, new PairItemList(item2, new PairItemList(item3, new EmptyItemList())));
        
        IPutResultList results1 = s1.putMultiple(list1.toWSIItemList()).toMyPutResultList();

        checkExpect(results1 instanceof PairPutResultList, true);
        PairPutResultList p1 = (PairPutResultList) results1;

        IPutResultList results2 = p1.getRest();
        checkExpect(results2 instanceof PairPutResultList, true);
        PairPutResultList p2 = (PairPutResultList) results2;

        IPutResultList results3 = p2.getRest();
        checkExpect(results3 instanceof PairPutResultList, true);
        PairPutResultList p3 = (PairPutResultList) results3;
        
        IPutResult result1 = p1.getFirst();
        IPutResult result2 = p2.getFirst();
        IPutResult result3 = p3.getFirst();
        checkExpect(result1 instanceof OKPutResult, true);
        checkExpect(result2 instanceof OKPutResult, true);
        checkExpect(result3 instanceof OKPutResult, true);
        
        IItemId id1 = ((OKPutResult) result1).getId();
        IItemId id3 = ((OKPutResult) result3).getId();
        
        IGetResult get1 = s1.get(id1);
        checkExpect(get1 instanceof ItemResult, true);
        checkExpect(((ItemResult)get1).getItem(), item1);
        IGetResult get3 = s1.get(id3);
        checkExpect(get3 instanceof ItemResult, true);
        checkExpect(((ItemResult)get3).getItem(), item3);
    }
    
    @Test
    public void put1() {
        IArchive archive1 = new WORM("WORM1");
        IPutResult put1 = archive1.put(item1);
        checkExpect(put1 instanceof OKPutResult, true);
        IItemId id1 = ((OKPutResult)put1).getId();
        IPutResult put2 = archive1.put(item2);
        checkExpect(put2 instanceof OKPutResult, true);
        IItemId id2 = ((OKPutResult)put2).getId();
        IGetResult get1 = archive1.get(id1);
        checkExpect(get1 instanceof ItemResult, true);
        checkExpect(((ItemResult)get1).getItem(), item1);
        IGetResult get2 = archive1.get(id2);
        checkExpect(get2 instanceof ItemResult, true);
        checkExpect(((ItemResult)get2).getItem(), item2);
    }
    
    @Test
    public void get1() {
        IArchive archive1 = new WORM("WORM1");
        IItemId id1 = new WORMItemId();
        IGetResult get1 = archive1.get(id1);
        checkExpect(get1 instanceof NoItemResult, true);
    }
}
