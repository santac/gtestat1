package myarchive;

import wsiarchive.*;

// Liste von IPutResult-Werten
public interface IPutResultList {
    // in IPutResultList aus wsiarchive umwandeln
    public wsiarchive.IPutResultList toWSIPutResultList();
    
    // OKs in Liste zählen
    public int countOKs ();
    
    // 
}
