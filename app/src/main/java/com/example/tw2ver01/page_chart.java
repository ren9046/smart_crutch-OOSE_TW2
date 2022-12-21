package com.example.tw2ver01;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class page_chart extends AppCompatActivity {
    //設定全域變數
    private static LineChart chart;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_chart); //取得介面
        chart = findViewById(R.id.lineChart);  //取得圖表
        new heartvalueget().execute();
        FlyweightFactory factory = new FlyweightFactory();
        Chart chart = (Chart) FlyweightFactory.getShape("chart");
        chart.draw();

    }

    //此介面會用到的程式
    //初始化圖表
    public interface Image {
        void draw();
    }

    public static class Chart implements Image {
        @Override
        public void draw() {
            chart.getDescription().setEnabled(false);//設置不要圖表標籤
            chart.setTouchEnabled(false);//設置不可觸碰
            chart.setDragEnabled(false);//設置不可互動
            //設置單一線數據
            LineData data = new LineData();
            data.setValueTextColor(Color.BLACK);
            chart.setData(data);
            //設置左下角標籤
            Legend l = chart.getLegend();
            l.setForm(Legend.LegendForm.LINE);
            l.setTextColor(Color.BLACK);

            //設置Ｘ軸
            XAxis x = chart.getXAxis();
            x.setTextColor(Color.BLACK);
            x.setDrawGridLines(true);//畫X軸線
            x.setPosition(XAxis.XAxisPosition.BOTTOM);//把標籤放底部
            x.setLabelCount(12, true);//設置顯示5個標籤
            //設置X軸標籤內容物
            x.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    return "第" + Math.round(value) + "筆";
                }
            });
            //
            YAxis y = chart.getAxisLeft();
            y.setTextColor(Color.BLACK);
            y.setDrawGridLines(true);
            y.setAxisMaximum(180);//最高200
            y.setAxisMinimum(0);//最低0
            chart.getAxisRight().setEnabled(false);//右邊Y軸不可視
            chart.setVisibleXRange(0, 40);//設置顯示範圍

        }
    }

    public static class ChartVaule implements Image {
        public static void addData(float inputData) {
            LineData data = chart.getData();//取得原數據
            ILineDataSet set = data.getDataSetByIndex(0);//取得曲線(因為只有一條，故為0，若有多條則需指定)
            if (set == null) {
                set = createSet();
                data.addDataSet(set);//如果是第一次跑則需要載入數據
            }
            data.addEntry(new Entry(set.getEntryCount(), inputData), 0);//新增數據點
            //更新圖表
            data.notifyDataChanged();
            chart.notifyDataSetChanged();
            chart.setVisibleXRange(0, 40);//設置可見範圍
            chart.moveViewToX(data.getEntryCount());//將可視焦點放在最新一個數據，使圖表可移動

        }

        private static ILineDataSet createSet() {
            LineDataSet set = new LineDataSet(null, "heartbeat");
            set.setAxisDependency(YAxis.AxisDependency.LEFT);
            set.setColor(Color.GRAY);
            set.setLineWidth(2);
            set.setDrawCircles(false);
            set.setFillColor(Color.BLACK);
            set.setFillAlpha(50);
            set.setDrawFilled(true);
            set.setValueTextColor(Color.BLACK);
            set.setDrawValues(false);
            return set;
        }

        @Override
        public void draw() {

        }
    }

    public static class FlyweightFactory {
        private static final HashMap<String, Image> shapeMap = new HashMap<String, Image>();

        public static Image getShape(String shapeType) {
            Image image = null;
            if (shapeType.equalsIgnoreCase("chart")) {
                image = (Chart) shapeMap.get("chart");
                if (image == null) {
                    image = new Chart();
                    shapeMap.put("chart", image);
                }
            }
            return image;
        }
    }

    //抓資料庫數值
    class heartvalueget extends AsyncTask<Void, Void, String> {
        OkHttpClient client = new OkHttpClient();

        @Override
        protected String doInBackground(Void... voids) {
            //抓哪個API
            Request request = new Request.Builder()
                    .url("http://20.194.172.51:80/api/HeartBeat/find/" + Device.getDeviceCode())
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.code() == 200) {
                    String result = response.body().string();
                    JSONArray jsonArray = new JSONArray(result);
                    //要抓哪一欄資料
                    for (int i = 41; i >= 0; i--) {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(jsonArray.length() - i);
                        System.out.println(jsonObject.getString("heartBeatValue"));
                        float a = Float.parseFloat(jsonObject.getString("heartBeatValue"));
                        ChartVaule.addData(a);
                    }
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {

        }
    }
}