package org.rawmemsearch;


import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<String> stringList = new ArrayList<>();
        String str1 = "str1:foo bar";
        stringList.add(str1);
        String str2 = "str2:foo bar foo baz";
        stringList.add(str2);
        String str3 = "str3:the Foo bar baz";
        stringList.add(str3);
        String str4 = "str4:the foo bar baz foo foo Bear";
        stringList.add(str4);
        DumbSearchEngine dumbSearch = new DumbSearchEngine(stringList);
        for (SearchDocument document : dumbSearch.search("foo", 10)) {
            System.out.println("Name: " + document.getTitle() + " Rank: " + document.getScore());
        }
    }
}
