package edu.temple.webbrowserapp;

//Bookmart struct
public class BookMarkFragment {
    private int igID;
    private String sgTitle;
    private String sgURL;

    public BookMarkFragment(){
    }

    public int setVal(int iID,String sTitle, String sURL){
        this.igID=iID;
        this.sgTitle=sTitle;
        this.sgURL=sURL;
        return 0;
    }

    public BookMarkFragment getVal(){
        BookMarkFragment rtn=new BookMarkFragment();
        rtn.setVal(this.igID,this.sgTitle,this.sgURL);
        return rtn;
    }

    public int getID(){
        return this.igID;
    }

    public String getTitle(){
        return  this.sgTitle;
    }

    public String getURL(){
        return this.sgURL;
    }
}
