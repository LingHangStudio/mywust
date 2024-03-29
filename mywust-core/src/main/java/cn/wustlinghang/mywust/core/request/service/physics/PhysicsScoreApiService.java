package cn.wustlinghang.mywust.core.request.service.physics;

import cn.wustlinghang.mywust.exception.ApiException;
import cn.wustlinghang.mywust.exception.ParseException;
import cn.wustlinghang.mywust.core.parser.physics.PhysicsScoreListPageParser;
import cn.wustlinghang.mywust.core.request.factory.physics.PhysicsSystemRequestFactory;
import cn.wustlinghang.mywust.core.util.PageFormExtractor;
import cn.wustlinghang.mywust.network.RequestClientOption;
import cn.wustlinghang.mywust.network.Requester;
import cn.wustlinghang.mywust.network.entitys.HttpRequest;
import cn.wustlinghang.mywust.network.entitys.HttpResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PhysicsScoreApiService extends PhysicsApiServiceBase {

    private static final PhysicsScoreListPageParser scoreListPageParser = new PhysicsScoreListPageParser();

    public PhysicsScoreApiService(Requester requester) {
        super(requester);
    }

    public String getPage(String cookie, String courseId, Map<String, String> pageParams, RequestClientOption requestClientOption) throws IOException, ApiException, ParseException {
        HttpRequest scorePageRequest = PhysicsSystemRequestFactory.physicsScoreRequest(cookie, courseId, pageParams);
        HttpResponse scorePageResponse = requester.post(scorePageRequest, requestClientOption);
        checkResponse(scorePageResponse);

        return scorePageResponse.getStringBody();
    }

    public List<String> getAllPages(String cookie, RequestClientOption requestClientOption) throws IOException, ParseException, ApiException {
        HttpRequest scoreListPageRequest = PhysicsSystemRequestFactory.physicsScoreListRequest(cookie);
        HttpResponse scoreListPageResponse = requester.get(scoreListPageRequest, requestClientOption);
        String scoreListPage = scoreListPageResponse.getStringBody();

        PhysicsScoreListPageParser.PhysicsScoreListPageParseResult pageParseResult = scoreListPageParser.parse(scoreListPage);

        Map<String, String> pageParams = PageFormExtractor.searchAllParams(scoreListPage);
        pageParams.put("ID_PEE63$ddlxq", pageParseResult.getTerm());

        List<String> courseIds = pageParseResult.getCourseIds();
        List<String> scorePages = new ArrayList<>(courseIds.size());
        for (String courseId : courseIds) {
            scorePages.add(this.getPage(cookie, courseId, pageParams, requestClientOption));
        }

        return scorePages;
    }
}
