import java.util.*;
import java.lang.Thread;

public class Main
{
    public static void main(String[] args) {
Vector<String> v=new Vector<>();
v.add("hello");
v.add("hello");
v.add("B");
v.add("bit");
Enumeration<String> enumeration=v.elements();
while (enumeration.hasMoreElements())
{System.out.println(enumeration.nextElement());}




    }}
