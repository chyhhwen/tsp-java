import java.awt.*;
import java.util.Random;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import java.util.Vector;
import javax.swing.JFrame;

public class Main extends JFrame
{
    static int size = 20;
    static int first;
    static int second;
    static int point[][] = new int[size][2];//座標
    static float dist[][] = new float[size][size];//距離
    static float totaldist[] = new float[size*2];//總距離
    static int Chromosome[][] = new int[size][size];//染色體
    static int ChooseChromosome[][] = new int[size*2][size];//子染色體
    static int Temporary[][] = new int[size*2][size+1]; // 暫存
    static int choosetime = 0;
    static int select[] = new int[size*2];
    static int finalselect[] = new int[size];
    static int chromosomeSize;
    static int generationSize ;
    static double crossoverRate;
    static int crossoverSize;
    static double mutationRate;

    private void set()//設定參數
    {
        chromosomeSize = 8;//幾條染色體
        generationSize = 10000;//世代次數
        crossoverRate = 0.75;//交配率
        crossoverSize = 2; // 交配池大小
        mutationRate = 0.1;//突變率
    }
    public void city()//產生城市
    {
        Random rand = new Random();
        for(int i=0;i<size;i++)
        {
            int x = rand.nextInt(400)+50;
            int y = rand.nextInt(400)+50;
            point[i][0] = x;
            point[i][1] = y;
        }
    }
    public void distance()//算出距離
    {
        int x[] = new int[2];
        int y[] = new int[2];
        float ans[] = new float[3];
        for(int i=0;i<size;i++)
        {
            for(int j=0;j<size;j++)
            {
                if(i == j)
                {
                    dist[i][j] = 0;
                }
                else
                {
                    x[0] = point[i][0];
                    x[1] = point[j][0];
                    y[0] = point[i][1];
                    y[1] = point[j][1];
                    ans[1] = (float) Math.pow(Math.abs(x[0]-x[1]),2);
                    ans[2] = (float)Math.pow(Math.abs(y[0]-y[1]),2);
                    ans[0] = (float) Math.sqrt(ans[1]+ans[2]);
                    dist[i][j] = ans[0];
                }
            }
        }
    }
    public float totaldistance(int j) // 總距離
    {
        totaldist[j] = 0;
        for(int i=0;i<size-1;i++)
        {
            totaldist[j] += dist[Chromosome[j][i]][Chromosome[j][i+1]];
        }
        return totaldist[j];
    }
    public float totalChoose(int j) // 總距離
    {
        totaldist[j+size] = 0;
        for(int i=0;i<size-1;i++)
        {
            totaldist[j+size] += dist[ChooseChromosome[j][i]][ChooseChromosome[j][i+1]];
        }
        return totaldist[j+size];
    }
    public void  generateChromosome()//生成染色體
    {
        //不重覆亂數
        for(int i=0;i<chromosomeSize;i++)
        {
            Vector v = new Vector();
            for(int j=0;j<size;j++)
            {
                v.add(j);
            }
            int length = v.size();
            Random rand = new Random();
            for (int j = 0 ; j < length ; j++)
            {
                int r = rand.nextInt(size - j);
                int a = (int)v.get(r);
                Chromosome[i][j] = a;
                v.remove(r);
            }
        }
    }
    public void selection()//選擇染色體 //選四個dna
    {
        Vector v = new Vector();
        for(int j=0;j<chromosomeSize;j++)
        {
            v.add(j);
        }
        int length = crossoverSize*2;
        Random rand = new Random();
        for (int j = 0 ; j < length ; j++)
        {
            int r = rand.nextInt(chromosomeSize - j);
            int a = (int)v.get(r);
            select[j] = a;
            v.remove(r);
        }
    }
    public void selectiontwo()//選擇2條染色體
    {
        Vector v = new Vector();
        int length = crossoverSize*2;
        for(int j=0;j<length;j++)
        {
            v.add(j);
        }
        Random rand = new Random();
        for (int j = 0 ; j < crossoverSize ; j++)
        {
            int r = rand.nextInt(length - j);
            int a = (int)v.get(r);
            finalselect[j] = a;
            v.remove(r);
        }
    }
    public void swap(int a,int b) //交換
    {
        Random rand = new Random();
        first = rand.nextInt(size);
        second = rand.nextInt(size);
        Vector father = new Vector();
        Vector mother = new Vector();
        Vector baby = new Vector();
        for (int i=0;i<size;i++)
        {
            int fa_put = Chromosome[select[finalselect[a]]][i];
            father.add(fa_put);
            int ma_put = Chromosome[select[finalselect[b]]][i];
            mother.add(ma_put);
        }
        if(first>second)
        {
            int temp = first;
            first = second;
            second = temp;
        }
        int mother_length = mother.size()-1;
        int time = 0;
        for(int i=first;i<=second;i++)
        {
            int fa_get = (int) father.get(i);
            baby.add(fa_get);
            for(int j=0;j<mother_length - time;j++)
            {
                int ma_get = (int) mother.get(j);
                if(ma_get == fa_get)
                {
                    mother.remove(j);
                    time ++ ;
                }
            }
        }
        mother_length = mother.size();
        for(int i=0;i<mother_length;i++)
        {
            int ma_get = (int) mother.get(i);
            baby.add(ma_get);
        }
        for(int i=0;i<size;i++)
        {
            int ba_get = (int) baby.get(i);
            ChooseChromosome[choosetime][i] = ba_get;
        }
        choosetime ++;
    }
    public void crossover()//交配
    {
        Random rand = new Random();
        if(rand.nextDouble() < crossoverRate)
        {
            swap( finalselect[0],finalselect[1]);
        }
    }
    public void  mutation()//突變
    {

        Random rand = new Random();
        for (int i=0;i<choosetime;i++)
        {
            for(int j=0;j<size;j++)
            {
                if(rand.nextDouble() < mutationRate)
                {
                    int change = rand.nextInt(size);
                    int temp = ChooseChromosome[i][j];
                    ChooseChromosome[i][j] = ChooseChromosome[i][change];
                    ChooseChromosome[i][j] = temp;
                }
            }
        }
    }
    public void storage(int time)//暫存
    {
        switch (time)
        {
            case 1:
                for (int i=0;i < chromosomeSize;i++)
                {
                    for(int j=0;j < size;j++)
                    {
                        Temporary[i][j] = Chromosome[i][j];
                    }
                    Temporary[i][size] = (int)totaldistance(i);
                }
                break;
            case 2:
                for (int i=0;i < choosetime;i++)
                {
                    for(int j=0;j < size ;j++)
                    {
                        Temporary[i+chromosomeSize][j] = ChooseChromosome[i][j];
                    }
                    Temporary[i+chromosomeSize][size] = (int)totalChoose(i);
                }
                break;
            default:
                System.out.println("ERROR 404");
                break;
        }

    }

    public void choose()//選擇
    {
        int length = chromosomeSize+choosetime;
        int math[] = new int [length];
        for (int i=0;i<length;i++)
        {
            math[i] = Temporary[i][size];
        }
        for(int i=0;i<length;i++)
        {
            for(int j=0;j<length;j++)
            {
                if (math[j] > math[i])
                {
                    int temp = math[j];
                    math[j] = math[i];
                    math[i] = temp;
                }
            }
        }
        for (int i=0;i<chromosomeSize;i++)
        {
            for(int j=0;j<length;j++)
            {
                if(Temporary[j][size] == math [i])
                {
                    for (int o=0;o<size;o++)
                    {
                        Chromosome[i][o] = Temporary[j][o];
                    }
                    math[i] = -1;
                }
            }
        }
    }
    public Main()
    {
        setSize(500,500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void paint(Graphics g)
    {
        /*********************************************************/
        set();//設定參數
        city();//產生城市
        for(int i=0;i<size;i++)
        {
            System.out.println("city:" + i +"("+point[i][0]+","+point[i][1]+")");
        }
        /*********************************************************/
        g.setColor(Color.red);
        for(int i=0;i<size;i++)
        {
            g.fillOval(point[i][0],point[i][1],10,10);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        /*********************************************************/
        distance();//算出距離
        for(int i=0;i<size;i++)
        {
            for(int j=0;j<size;j++)
            {
                System.out.print((int) dist[i][j] + "\t");
            }
            System.out.println("");
        }
        /*********************************************************/
        generateChromosome(); //生成染色體
        for (int i=0;i< chromosomeSize;i++)
        {
            System.out.print("[");
            for(int j=0;j<size-1;j++)
            {
                System.out.print(Chromosome[i][j] + " , ");
            }
            System.out.println(Chromosome[i][size-1] + "] total:" + totaldistance(i));
        }
        /*********************************************************/
        for(int k=0;k<generationSize;k++)
        {
            System.out.println("第"+(k+1)+"代");
            choosetime = 0;
            for(int t =0;t<crossoverSize*2;t++)
            {
                storage(1);
                selection();//選擇染色體
                selectiontwo();//選擇2條染色體
                crossover();//交配
                mutation();//突變
                storage(2);
            }
            /*********************************************************/
            choose();//選擇最佳8個
            distance();//算出距離
            for (int i = 0; i < chromosomeSize; i++)
            {
                System.out.print("[");
                for (int j = 0; j < size - 1; j++)
                {
                    System.out.print(Chromosome[i][j] + " , ");
                }
                System.out.println(Chromosome[i][size - 1] + "] total:" + totaldistance(i));
            }
            /*********************************************************/
            for(int i=0;i<size-1;i++)
            {
                g.setColor(Color.blue);
                g.drawLine(point[Chromosome[0][i]] [0]+ 5, point[Chromosome[0][i]][1] + 5,
                        point[Chromosome[0][i+1]][0] + 5, point[Chromosome[0][i+1]][1] + 5);
                try {
                    Thread.sleep(0);
                } catch (InterruptedException e)
                {
                    throw new RuntimeException(e);
                }
            }
            /*********************************************************/
            for(int i=0;i<size-1;i++)
            {
                g.setColor(Color.white);
                g.drawLine(point[Chromosome[0][i]] [0]+ 5, point[Chromosome[0][i]][1] + 5,
                        point[Chromosome[0][i+1]][0] + 5, point[Chromosome[0][i+1]][1] + 5);
                try {
                    Thread.sleep(0);
                } catch (InterruptedException e)
                {
                    throw new RuntimeException(e);
                }
            }
            /*********************************************************/
        }
        /*********************************************************/
        for(int i=0;i<size-1;i++)
        {
            g.setColor(Color.red);
            g.drawLine(point[Chromosome[0][i]] [0]+ 5, point[Chromosome[0][i]][1] + 5,
                    point[Chromosome[0][i+1]][0] + 5, point[Chromosome[0][i+1]][1] + 5);
            try {
                Thread.sleep(1);
            } catch (InterruptedException e)
            {
                throw new RuntimeException(e);
            }
        }
        /*********************************************************/
        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args)
    {
        new Main().setVisible(true);
    }
}