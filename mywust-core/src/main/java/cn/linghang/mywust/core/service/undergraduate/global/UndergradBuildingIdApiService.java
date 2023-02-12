package cn.linghang.mywust.core.service.undergraduate.global;

import cn.linghang.mywust.core.api.ConstantParams;
import cn.linghang.mywust.core.exception.ApiException;
import cn.linghang.mywust.core.request.undergrade.BkjxRequestFactory;
import cn.linghang.mywust.core.service.undergraduate.UndergradApiServiceBase;
import cn.linghang.mywust.data.global.Building;
import cn.linghang.mywust.data.global.Campus;
import cn.linghang.mywust.network.RequestClientOption;
import cn.linghang.mywust.network.Requester;
import cn.linghang.mywust.network.entitys.HttpRequest;
import cn.linghang.mywust.network.entitys.HttpResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UndergradBuildingIdApiService extends UndergradApiServiceBase {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public UndergradBuildingIdApiService(Requester requester) {
        super(requester);
    }

    @Override
    public String getPage(String cookie, Map<String, String> params, RequestClientOption option) throws ApiException, IOException {
        List<Building> buildings = this.getBuildings(cookie, params.get("campus"), option);

        return objectMapper.writeValueAsString(buildings);
    }

    @Override
    public String getPage(String cookie, Map<String, String> params) throws ApiException, IOException {
        List<Building> buildings = this.getBuildings(cookie, params.get("campus"));

        return objectMapper.writeValueAsString(buildings);
    }

    public List<Building> getBuildings(String cookie, String campusId, RequestClientOption option) throws ApiException, IOException {
        HttpRequest request = BkjxRequestFactory.buildingListRequest(campusId, cookie);
        HttpResponse response = requester.post(request, option);
        this.checkResponse(response);

        JsonNode rootNode = objectMapper.readTree(response.getStringBody());
        List<Building> buildings = new ArrayList<>(rootNode.size());
        for (JsonNode buildingObject : rootNode) {
            Building building = new Building(
                    buildingObject.get("dm").asText(), buildingObject.get("dmmc").asText(),
                    new Campus(campusId, ConstantParams.CAMPUS.get(campusId)));

            buildings.add(building);
        }

        return buildings;
    }

    public List<Building> getBuildings(String cookie, String campusId) throws ApiException, IOException {
        return this.getBuildings(cookie, campusId, null);
    }

    public String getBuildingsJson(String cookie, String campusId, RequestClientOption option) throws ApiException, IOException {
        List<Building> buildings = this.getBuildings(cookie, campusId, option);

        return objectMapper.writeValueAsString(buildings);
    }

    public String getBuildingsJson(String cookie, String campusId) throws ApiException, IOException {
        return this.getBuildingsJson(cookie, campusId, null);
    }
}
