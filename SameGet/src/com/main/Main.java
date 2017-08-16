package com.main;

public class Main {

	public static void main(String[] args) {

//		https://v2.same.com/channel/1032823/senses?offset=47345399
		
//		String url="https://v2.same.com/channel/1032823/senses";	//长腿a杯频道
//		String url="https://v2.same.com/channel/1032823/senses?offset=47345399";	//长腿a杯频道
//		String url2="https://v2.same.com/channel/1002393/senses";	//每日搭配
		String url2="https://v2.same.com/channel/1002393/senses?offset=53455743";	//每日搭配
		
//		String url="https://v2.same.com/channel/1512177/senses";	//672频道
		
//		String saveFolder="C:/Users/Administrator/Desktop/same长腿a杯/";
//		int[] splitnum={20,100,200,300};
////		
//		SameGet.loop_read(url,saveFolder,splitnum);
		
		
		String saveFolder2="F:/same/每日搭配/";
		int[] splitnum2={20,100,200,300};
//		
		SameGet.loop_read(url2,saveFolder2,splitnum2);
		
//		SameGet.query("每日搭配");
		
	}

}
