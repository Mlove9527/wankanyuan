package com.xtkong.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ImportBean {
	private String sourceid;//待导入的采集源
	private String userid;//给哪个用户导入数据
	private Basic basic;//采集源基础信息
	private List<Analysis> analysisList;//配置的分析结果
	public ImportBean(String jsonStr) {
		
		JSONObject jsonObject=null;
	    jsonObject= JSON.parseObject(jsonStr);
	    sourceid=jsonObject.getString("sourceid");
	    if(sourceid!=null &&sourceid.length()>0){
	        System.out.println("sourceid==="+sourceid);
	    }
	    userid=jsonObject.getString("userid");
	    String basicStr = jsonObject.getString("basic");
	    String analysisListStr = jsonObject.getString("analysis");
	    //basic
	    if(null!=basicStr) {
	    	jsonObject = JSON.parseObject(basicStr);
		    Basic basicTemp = new Basic(jsonObject);
		    String fileurl1 = basicTemp.getFileurl();
		    System.out.println("fileurl1："+fileurl1);
		    basic = basicTemp;
	    }
	    
	    //analysis
	    if(null!=analysisListStr) {
	    	JSONArray analysisArray = JSON.parseArray(analysisListStr);
		    List<Analysis> analyList = new ArrayList<Analysis>();
		    if(analysisArray.size()>0){
		    	  for(int i=0;i<analysisArray.size();i++){
		    	    JSONObject defUniqueJson = analysisArray.getJSONObject(i);
		    	    Analysis analysis = new Analysis(defUniqueJson);
		    	    String url = analysis.getFileurl();
		    	    System.out.println("url2："+url);
		    	    analyList.add(analysis);
		    	  }
		    	}
		    analysisList = analyList;
	    }
	}

	public String getSourceid() {
		return sourceid;
	}

	public void setSourceid(String sourceid) {
		this.sourceid = sourceid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Basic getBasic() {
		return basic;
	}

	public void setBasic(Basic basic) {
		this.basic = basic;
	}

	public List<Analysis> getAnalysisList() {
		return analysisList;
	}

	public void setAnalysisList(List<Analysis> analysisList) {
		this.analysisList = analysisList;
	}

}