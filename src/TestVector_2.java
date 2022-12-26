import java.util.*;
public class TestVector_2 {
    public static void main(String args[])
    {
        Vector v = new Vector();
        for(int i=0;i<=15;i++)
        {
            v.add(i);
        }
        int length = v.size();
        Random rand = new Random();
        for (int i = 0 ; i < length ; i++)
        {
            int r = rand.nextInt(16-i);
            int a = (int)v.get(r);
            System.out.println(a);
            v.remove(r);
        }

    }
}