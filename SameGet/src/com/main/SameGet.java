package com.main;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SameGet {
	
	/**
	 * same查询
	 * @param str
	 */
	public static void query(String str)
	{
		String url="https://v2.same.com/channel/search?query=";
		String query_result=fetch_data(url+str);
		
		JSONObject json = JSONObject.fromObject(query_result);
		String data=json.get("data").toString();
		
		JSONObject data_json = JSONObject.fromObject(data);
		//same状态̬
		String results_str=data_json.get("results").toString();
		
		JSONArray all_user_json = JSONArray.fromObject(results_str);
		//读取每一条same状态״̬
		for(Object object:all_user_json)
		{
			String each_str=object.toString();
			JSONObject each_json = JSONObject.fromObject(each_str);
			
			String id_str=each_json.get("id").toString();
			String name_str=each_json.get("name").toString();
			String url_str="https://v2.same.com/channel/"+id_str+"/senses";
			
			System.out.println(id_str+"\t"+name_str+"\t"+url_str);
		}
	}
	
	/**
	 * 循环读取
	 * @param nexturl
	 * @param saveFolder
	 */
	public static void loop_read(String nexturl,String saveFolder,int[] splitnum) {
		
		if(nexturl!=null&&!"".equals(nexturl))
		{
			String returnData=fetch_data(nexturl);
			nexturl=read_data(returnData,saveFolder,splitnum);
			loop_read(nexturl,saveFolder,splitnum);
		}
		
	}
	
	/**
	 * 保存图片
	 */
	public static String read_data(String str,String saveFolder,int[] splitnum)
	{
		
		JSONObject json = JSONObject.fromObject(str);
		String data=json.get("data").toString();
		
		JSONObject data_json = JSONObject.fromObject(data);
		//same状态̬
		String results_str=data_json.get("results").toString();
		
		JSONArray all_user_json = JSONArray.fromObject(results_str);
		//读取每一条same状态״̬
		for(Object object:all_user_json)
		{
			String each_str=object.toString();
			JSONObject each_json = JSONObject.fromObject(each_str);
			
			String id_str=each_json.get("id").toString();
			String created_at_str=each_json.get("created_at").toString();
			String txt=each_json.get("txt").toString();
			int likes_str=Integer.valueOf(each_json.get("likes").toString());
			String photo_str=each_json.get("photo").toString();
			
			int index = photo_str.lastIndexOf("."); 
			char[] ch = photo_str.toCharArray(); 
			String file_type = String.copyValueOf(ch, index + 1, ch.length - index - 1);
			
			String level="";
			if(likes_str<splitnum[0]){
				level="a";
			}else if(likes_str<splitnum[1]){
				level="b";
			}else if(likes_str<splitnum[2]){
				level="c";
			}else if(likes_str<splitnum[3]){
				level="d";
			}else{
				level="e";
			}
			
			String filename=saveFolder+level+"/"+id_str+"_"+created_at_str+"_"+likes_str+"."+file_type;
			//url转图片
			UrlToImage.saveToFile(photo_str, filename);
			
//			System.out.println(id_str);
//			System.out.println(created_at_str);
//			System.out.println(likes_str);
			System.out.println(photo_str);
		}

		
		//下一条url指向
		if(data_json.get("next")==null){
			return null;
		}
		String next_str="https://v2.same.com"+data_json.get("next").toString();
		System.out.println(next_str);
		
		return next_str;
	}

	/**
	 * 通过url获取数据
	 * @param urlstr
	 * @return
	 */
	public static String fetch_data(String urlstr)
	{
		
        try{  
            URL url = new URL(urlstr);  
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();  
            urlConnection.setRequestMethod("GET");  
            urlConnection.connect();  
              
            InputStream inputStream = urlConnection.getInputStream();  
              
            String responseStr = ConvertToString(inputStream);
            return responseStr;
        }catch(IOException e){  
        	e.printStackTrace();
        } 
        return null;
	}
	
	/**
	 * 输入流转换为字符串
	 * @param inputStream
	 * @return
	 */
    public static String ConvertToString(InputStream inputStream){  
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);  
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
        StringBuilder result = new StringBuilder();  
        String line = null;  
        try {  
            while((line = bufferedReader.readLine()) != null){  
                result.append(line + "\n");  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            try{  
                inputStreamReader.close();  
                inputStream.close();  
                bufferedReader.close();  
            }catch(IOException e){  
                e.printStackTrace();  
            }  
        }  
        return result.toString();  
    }  
  
    /**
     * 文件流转换为字符串
     * @param inputStream
     * @return
     */
    public static String ConvertToString(FileInputStream inputStream){  
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);  
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
        StringBuilder result = new StringBuilder();  
        String line = null;  
        try {  
            while((line = bufferedReader.readLine()) != null){  
                result.append(line + "\n");  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            try{  
                inputStreamReader.close();  
                inputStream.close();  
                bufferedReader.close();  
            }catch(IOException e){  
                e.printStackTrace();  
            }  
        }  
        return result.toString();  
    }
	
}
