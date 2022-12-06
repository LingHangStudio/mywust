package cn.linghang.mywust.core.request.library.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DetailInfo {

    @JsonProperty("map")
    private Map map;

    @JsonProperty("empty")
    private boolean empty;

    public void setMap(Map map) {
        this.map = map;
    }

    public Map getMap() {
        return map;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public boolean isEmpty() {
        return empty;
    }
}