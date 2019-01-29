package org.succlz123.commoncode.bt;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by succlz123 on 2017/3/25.
 */

public class TorrentHelper {

    public void parser(Context context) {
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = null;

        try {
            inputStream = assetManager.open("929.5.torrent");
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] b = new byte[50];
            int len = 0;
            while ((len = inputStream.read(b)) >= 0) {
                out.write(b, 0, len);
            }
            inputStream.close();

            ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
            out.flush();
            out.close();

            Object o = MapBtom(in);

            if (o instanceof Map) {
                BOutputStream bOutputStream = new BOutputStream();
                byte[] info = bOutputStream.Edi(((Map) o).get("info"));
                String xx = "magnet:?xt=urn:btih:" + Sha1Hash.toHex(info);
                Log.d("cc", xx);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String onebyte(int spk, ByteArrayInputStream in) throws IOException { // 单独写个方法 更加好处理
        byte[] b = new byte[spk];  //Spk  多少个byte
        int len = in.read(b);   //在临时输入流里 读多少个byte  bin输入流是 全局定义的。
        if (len == -1) {
            throw new IOException("文档不完整 退出！");   //数据异常
        }
        return new String(b, 0, len);  //byte 转 字符串
    }


    public long integmr(ByteArrayInputStream in) throws IOException {  //整数类型开始先
        String thlen = "";
        while (true) {
            String ni = onebyte(1, in);   // 你看我这里直接调用上面的方法去读  1 byte
            if (ni.equals("e")) {  // i 之后一个一个字节（数字）去读取 读到e结束。
                break;
            } else { //没到e结束符的话  将它们拼在一起，最后不就形成一个数了。
                thlen += ni;
            }
        }
        return Long.parseLong(thlen);   //long类型最大，把它强转成long来放，因为不知道往后还有多少个。
    }

    private Charset cn = Charset.forName("utf-8"); //这是全局定义的

    public Object Strimgorbyte(String s1x, ByteArrayInputStream in) throws IOException {
        while (true) {
            String ni = onebyte(1, in);   // 我这里又直接调用上面的方法去读  1 byte
            if (ni.equals(":")) {  //读到冒号为止结束，因为冒号前面是数字就是字符串长度
                break;
            } else {
                s1x += ni;    //那么将一个一个数字，拼在一起。
            }
        }

        int len = Integer.parseInt(s1x);  //读出来是字符串 转 整数  = 该字符串有几位（几个byte）
        byte[] b = new byte[len];  //临时byte  = 要读的长度 byte
        in.read(b);  //读取
        int ts = 0;   //之后我做了一个判断  判断这个字符串是不是特殊字符，用来看看是不是二进制，就是那些看似乱码的东西。
        for (int i = 0; i < b.length; i++) {
            if (b[i] < 0) { //小于的话    等于不正常的字符，特殊的。
                ts = 1;  //它是特殊的
                break;
            }
        }

        if (ts == 0) {  //如果不特殊的字符串  转 UTF-8
            CharBuffer cf = cn.decode(ByteBuffer.wrap(b, 0, len));  //这就不会出现乱码，坑爹情况了。我之前都不知道了。
            return new String(cf.array(), 0, cf.limit());
        } else {  //如果特殊的 就是二进制的话直接byte数组返回。
            return b;
        }
    }

    public Object MapBtom(ByteArrayInputStream in) throws IOException {
        String ni = onebyte(1, in);  //读取1个byte 又是调用上面的方法。
        if (ni.equals("d")) { ///如果是字典类型
            Object key = MapBtom(in);  //键 key   MapBtom()读回自己的方法重点也是这全是自我调用产生的结果。 自我调用的时候，又会判断，直到完成为止。
            Map<String, Object> mapnodes = new LinkedHashMap<>();  //创建一个有序的map去装
            while (key != null) {  ///循环到全部装完为止。       1
                Object value = MapBtom(in);    //自我调用 读值 value
                mapnodes.put(key.toString(), value);  //键 值  插入
                key = MapBtom(in);  //又 读键key 因为循环模式 又回到1那。
            }
            //读到没有为止，就返回终极Map。
            return mapnodes;
        } else if (ni.equals("l")) {  //如果是列表类型
            Object value = MapBtom(in); //又自我调用 读值
            List<Object> nva = new ArrayList<>();  //用个List 去 装它们。
            while (value != null) {  //读到没有为止 返回
                nva.add(value);
                value = MapBtom(in);  //又自我调用看看还有没有值是 属于这个List的
            }
            return nva;
        } else if (ni.equals("i")) {   ///如果是数字类型、int类型的话直接掉用上面的方法。
            return integmr(in);   //调用上面的方法
        } else if (ni.equals("e")) {  ///如果是e开始的话、表示该字段、列表、数字、字典处理完毕了 结束符
            return null;   //返回空  帮助上面的死循环体 得以跳出、
        } else {   //如果不是以上类型的 就是字符串或二进制 ， 字符串开头判断不了 ， 它是中间判断的。
            return Strimgorbyte(ni, in);  //调用上面的方法
        }
    }

}
