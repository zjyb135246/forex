import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by yaobin on 2016/8/11.
 */
public class Main {
    private static int maxDiff = 50;
    public static void main(String[] args) {
        try {
            List<Integer> resList = getDiffList();
            System.out.println(resList.size());
            Map<Integer,Integer> map = new TreeMap<>();
            for(int i=0;i<resList.size();i++){
                int j=i+1;
                if(j<resList.size()){
                    while(resList.get(j)==resList.get(i)*-1){
                        j++;
                        if(j>resList.size()-1){
                            break;
                        }
                    }
                }
                map.put(j-i,map.get(j-i)==null?1:map.get(j-i)+1);
                i=j-1;
            }
            int total = 0;
            for(Integer i:map.keySet()){
                total+=map.get(i)*i;
                System.out.println(i+":"+map.get(i));
            }
            System.out.println(total);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static List<Integer> getDiffList() throws IOException {
        List<Integer> resList = new ArrayList<>();
        File file = new File("E:\\tick\\EURUSD\\EURUSD.csv");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String s = null;
        reader.readLine();//去除第一行
        int lineCnt = 0;
        long curBid=getBidPrice(reader.readLine());
        while ((s=reader.readLine())!=null){
            lineCnt++;
            if(lineCnt%1000000==0){
//                System.out.println(lineCnt);
            }
            long tmpBid = getBidPrice(s);
            long tmpDiff = tmpBid-curBid;
            if(tmpDiff>=maxDiff){
                resList.add(1);
                curBid = tmpBid;
            }
            if(tmpDiff<=maxDiff*(-1)){
                resList.add(-1);
                curBid = tmpBid;
            }
        }
        return resList;
    }

    private static long getBidPrice(String line){
        String s = line.split(",")[1];
        double d = Double.valueOf(s);
        long  l = (long)(d*100000);
        return l;
    }
}
