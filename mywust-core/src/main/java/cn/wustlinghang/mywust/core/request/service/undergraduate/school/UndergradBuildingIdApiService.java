package cn.wustlinghang.mywust.core.request.service.undergraduate.school;

import cn.wustlinghang.mywust.urls.ConstantParams;
import cn.wustlinghang.mywust.core.request.factory.undergrade.BkjxRequestFactory;
import cn.wustlinghang.mywust.core.request.service.undergraduate.UndergradApiServiceBase;
import cn.wustlinghang.mywust.data.common.Building;
import cn.wustlinghang.mywust.data.common.Campus;
import cn.wustlinghang.mywust.exception.ApiException;
import cn.wustlinghang.mywust.network.RequestClientOption;
import cn.wustlinghang.mywust.network.Requester;
import cn.wustlinghang.mywust.network.entitys.HttpRequest;
import cn.wustlinghang.mywust.network.entitys.HttpResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>获取教学楼列表的接口服务，其实际接口对应于『教室课表查询』中选择校区后请求的教学楼接口</p>
 */
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
