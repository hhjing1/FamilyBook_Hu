package com.example.familybook_hu;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;

public class ChartsActivity extends Activity {

    private LineChartView mChart;
    private Map<String,Integer> table=new TreeMap<>();//合并之后的数据源
    private LineChartData mData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart_view);
        mChart=(LineChartView) findViewById(R.id.chart);
        mData=new LineChartData();
        List<CostBean> allDate= (List<CostBean>) getIntent().getSerializableExtra("cost_list");
        generateValues(allDate);//合并同一天数据
        generateData();
    }

    private void generateData() {
        List<Line> lines=new ArrayList<>();
        List<PointValue> values=new ArrayList<>();
        int indeX=0;
        for(Integer value:table.values()){
            //通过循环生成相对应的点
            values.add(new PointValue(indeX,value));
            indeX++;
        }
        //将产生的点进行连接生成线
        Line line=new Line(values);
        line.setColor(ChartUtils.COLORS[0]);
        line.setShape(ValueShape.CIRCLE);
        line.setPointColor(ChartUtils.COLORS[1]);
        lines.add(line);
        mData =new LineChartData(lines);
        mData.setLines(lines);
        mChart.setLineChartData(mData);
    }

    private void generateValues(List<CostBean> allDate) {
        //将同一天数据进行累加，并且将算好放到数据源table中
        if(allDate!=null){
            for(int i=0;i<allDate.size();i++){
                CostBean costBean=allDate.get(i);//去除全部数值
                String costDate=costBean.costDate;
                int costMoney=Integer.parseInt(costBean.costMoney);
                if(!table.containsKey(costDate)){
                    table.put(costDate,costMoney);
                }
                else{
                    int originMoney=table.get(costDate);
                    table.put(costDate,originMoney+costMoney);
                }
            }
        }
    }
}

