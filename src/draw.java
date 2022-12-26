import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import java.util.Vector;
import javax.swing.JFrame;

public class draw extends JFrame
{
    int point[] = new int[100];
    int line [] = new int[16];
    int time = 1000;
    int math[][] = new int[time][time];
    public draw()
    {
        setSize(500,500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        random();
    }
    public void random()
    {
        Random rand = new Random();
        for(int i=0;i<30;i+=2)
        {
            int x = rand.nextInt(400)+50;
            int y = rand.nextInt(400)+50;
            point[i] = x;
            point[i+1] = y;
        }
    }
    public void paint(Graphics g)
    {

        g.setColor(Color.red);
        for(int i=0;i<30;i+=2)
        {
            g.fillOval(point[i],point[i+1],10,10);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        /**********************************/
        int t = 0;
        float fix[] = new float[time];
        while(true)
        {
            if(time == 0)
            {
                int min = 0;
                for(int i=1;i<1000 ;i++)
                {
                    if(fix[i] < fix [min])
                    {
                        min = i;
                    }
                }
                int mi = 999 - min;
                System.out.println("最短:"+mi+"次");
                /**********************************/

                for(int i=0;i<=14;i++)
                {
                    g.setColor(Color.red);
                    int point1 = math[min][i] * 2;
                    int point2 = math[min][i+1] * 2;
                    g.drawLine(point[point1] + 5, point[point1+1] + 5,
                            point[point2] + 5, point[point2+1] + 5);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e)
                    {
                        throw new RuntimeException(e);
                    }
                    System.out.print(math[min][i] + " ");
                }
                /**********************************/

                try {
                    Thread.sleep(100000);
                } catch (InterruptedException e)
                {
                    throw new RuntimeException(e);
                }
                break;
            }
            else
            {
                /**********************************/
                Vector v = new Vector();
                v.clear();
                for(int i=0;i<15;i++)
                {
                    v.add(i);
                }
                int length = v.size();
                Random rand = new Random();
                for (int i = 0 ; i < length ; i++)
                {
                    int r = rand.nextInt(15-i);
                    int a = (int)v.get(r);
                    line[i] = a;
                    v.remove(r);
                }
                /**********************************/
                for(int i=0;i<14;i++)
                {
                    math[time-1][i]=line[i];
                    System.out.print(line[i] + "->");
                }
                math[time-1][14]=line[14];
                System.out.print(line[14]);
                System.out.println();
                /**********************************/
                System.out.print("總路長:");
                for(int i=0;i<=14;i++)
                {
                    int x1 = point[line[i] * 2] + 5;
                    int x2 = point[line[i] * 2 + 1] + 5;
                    int y1 = point[line[i + 1] * 2] + 5;
                    int y2 = point[line[i + 1] * 2 + 1] + 5;
                    int ans1,ans2;
                    if(x1 > x2)
                    {
                        ans1 = x1 - x2;
                    }
                    else
                    {
                        ans1 = x2 - x1;
                    }
                    if(y1 > y2)
                    {
                        ans2 = y1 - y2;
                    }
                    else
                    {
                        ans2 = y2 - y1;
                    }
                    ans1 = (int) Math.pow(ans1,2);
                    ans2 = (int) Math.pow(ans2,2);
                    float ans = ans1 + ans2;
                    ans = (float) Math.sqrt(ans);
                    fix[time - 1] += ans;
                }
                System.out.println(fix[time - 1]);
                /**********************************/

                for(int i=0;i<=14;i++)
                {
                    g.setColor(Color.blue);
                    int point1 = line[i] * 2;
                    int point2 = line[i + 1] * 2;
                    g.drawLine(point[point1] + 5, point[point1+1] + 5,
                            point[point2] + 5, point[point2+1] + 5);
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e)
                    {
                        throw new RuntimeException(e);
                    }

                }
                /**********************************/
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e)
                {
                    throw new RuntimeException(e);
                }
                /**********************************/
                for(int i=0;i<=14;i++)
                {
                    g.setColor(Color.white);
                    int point1 = line[i] * 2;
                    int point2 = line[i + 1] * 2;
                    g.drawLine(point[point1] + 5, point[point1+1] + 5,
                            point[point2] + 5, point[point2+1] + 5);
                }
                t++;
                System.out.println("第"+t+"次");
                time--;
            }
        }
    }
    public static void main(String[] args)
    {
        new draw().setVisible(true);
    }
}