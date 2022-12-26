# 基因演算法解決TSP問題
`Main.java` 主程式\
`draw.java` 測試繪圖\
`TestVector.java` 測試向量
***
執行結果\
![image](https://github.com/chyhhwen/tsp-java/blob/main/image.png?raw=true)
***
`point[][]` city座標\
`dist[][]` city距離\
`totaldist[]` 父距離\
`Chromosome[][]` 父染色體\
`ChooseChromosome[][]` 子染色體\
`Temporary[][]` 暫存 用來存所有染色體\
`choosetime` 交配成功次數\
`select[]` 隨機四個染色體\
`finalselect[]` 隨機四個染色體裡面隨機兩個
***
`set();` 設定初始參數\
`city();` 生成city座標\
`distance();` 求出city所有距離\
`generateChromosome();` 生成初始染色體\
`storage(1);`存放父染色體\
`storage(2);`存放子染色體\
`selection();` 隨機抓取四個染色體\
`selectiontwo();` 四個染色體裡面隨機抓取兩個\
`crossover()` 交配\
`mutation();` 突變
***
`g.setColor()` 設定筆刷顏色\
`g.drawLine` 畫直線\
` g.fillOval` 畫圓