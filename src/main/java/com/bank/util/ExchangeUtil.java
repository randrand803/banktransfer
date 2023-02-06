package com.bank.util;

import com.bank.model.TransferRecord;
import net.sf.json.JSONObject;
import java.io.*;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;



public class ExchangeUtil {

    public static final String DEF_CHATSET = "UTF-8";

    public static final int DEF_CONN_TIMEOUT = 30000;

    public static final int DEF_READ_TIMEOUT = 30000;

    public static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";

    //Configured KEY
    public static final String APPKEY = "eb40447f826c72de8b634a3a5b2f3b6d";

    //Get instant exchange rate from 3rd party API
    public static String getExchange(String from, String to) {

        String result = null;

        String url = "http://op.juhe.cn/onebox/exchange/currency";//Request APIs url

        Map params = new HashMap();//Request parameters

        params.put("from", from);// Currency code before conversion

        params.put("to", to);// Targeted currency code

        params.put("key", APPKEY);//API key

        try {

            result = net(url, params, "GET");

            JSONObject object = JSONObject.fromObject(result);

            if (object.getInt("error_code") == 0) {
                System.out.println(object.get("result"));
                return object.getJSONArray("result").getJSONObject(0).get("exchange").toString();
            } else {
                System.out.println(object.get("error_code") + ":" + object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) {
        System.out.println(getExchange("USD","CNY"));
    }

    /**
     * @param strUrl Request URL
     * @param params Request parameters
     * @param method Request method
     * @return 网络请求字符串
     * @throws Exception
     */

    public static String net(String strUrl, Map params, String method) throws Exception {

        HttpURLConnection conn = null;

        BufferedReader reader = null;

        String rs = null;

        try {

            StringBuffer sb = new StringBuffer();

            if (method == null || method.equals("GET")) {

                strUrl = strUrl + "?" + urlencode(params);

            }

            URL url = new URL(strUrl);

            conn = (HttpURLConnection) url.openConnection();

            if (method == null || method.equals("GET")) {

                conn.setRequestMethod("GET");

            } else {

                conn.setRequestMethod("POST");

                conn.setDoOutput(true);

            }

            conn.setRequestProperty("User-agent", userAgent);

            conn.setUseCaches(false);

            conn.setConnectTimeout(DEF_CONN_TIMEOUT);

            conn.setReadTimeout(DEF_READ_TIMEOUT);

            conn.setInstanceFollowRedirects(false);

            conn.connect();

            if (params != null && method.equals("POST")) {

                try {

                    DataOutputStream out = new DataOutputStream(conn.getOutputStream());

                    out.writeBytes(urlencode(params));

                } catch (Exception e) {

// TODO: handle exception

                }

            }

            InputStream is = conn.getInputStream();

            reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));

            String strRead = null;

            while ((strRead = reader.readLine()) != null) {

                sb.append(strRead);

            }

            rs = sb.toString();

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            if (reader != null) {

                reader.close();

            }

            if (conn != null) {

                conn.disconnect();

            }

        }

        return rs;

    }

//Converting map-data into request parameters type

    public static String urlencode(Map data) {

        StringBuilder sb = new StringBuilder();

        for (Object i : data.keySet()) {

            try {

                sb.append(i).append("=").append(URLEncoder.encode(data.get(i) + "", "UTF-8")).append("&");

            } catch (UnsupportedEncodingException e) {

                e.printStackTrace();

            }

        }

        return sb.toString();

    }

 
    public static List<TransferRecord> createRecord() {
        Random random = new Random();
//        double usd = Double.valueOf(ExchangeUtil.getExchange("USD", "CNY"));
//        double eur = Double.valueOf(ExchangeUtil.getExchange("EUR", "CNY"));
        double usd = 6.7845;
        double eur = 7.3741;
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);
        int index = 1;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        List<TransferRecord> list=new ArrayList<TransferRecord>();
        for (int i = 0; i < 500000; i++) {
            System.out.println("现在生产第"+i+"条信息---------------------------");
            TransferRecord record = new TransferRecord();
            record.setId(index);
            int userid = (int)(Math.random()*100+1);
            record.setUserid(userid);
            Number moneyNumber = (int) (Math.random() * 10000);
            record.setHoldMoney(moneyNumber.intValue());

            if (random.nextBoolean()) {
                record.setType("USD");
                record.setHoldMoneyRMB(Double.valueOf(df.format(moneyNumber.intValue() * usd)));
            } else {
                record.setType("EUR");
                record.setHoldMoneyRMB(Double.valueOf(df.format(moneyNumber.intValue() * eur)));
            }
            record.setBankAccount("100" + userid);
            record.setTransferTime(TimeUtil.getRandomTime());
            record.setDescription("");
            list.add(record);
            index++;
        }   	
        return list;
    }
    
}
