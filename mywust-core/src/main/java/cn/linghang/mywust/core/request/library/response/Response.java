package cn.linghang.mywust.core.request.library.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Response{

	@JsonProperty("map")
	private Map map;

	public void setMap(Map map){
		this.map = map;
	}

	public Map getMap(){
		return map;
	}
}