package cn.wustlinghang.mywust.core.parser.undergraduate.school;

import cn.wustlinghang.mywust.data.common.Classroom;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlaceNameParser {
    public interface Rule {
        /**
         * 处理当前规则，并将解析后的数据写入result
         *
         * @param placeText 教室文本
         * @param result    解析结果对象
         * @return 是否匹配
         */
        boolean process(String placeText, Classroom result);
    }

    private final List<Rule> rules;

    public PlaceNameParser() {
        rules = new ArrayList<>();
    }

    PlaceNameParser(List<Rule> rules) {
        this.rules = rules;
    }

    public void registerRule(Rule rule) {
        rules.add(rule);
    }

    public Classroom parse(String placeName) {
        Classroom result = new Classroom("", "", "", placeName);
        for (Rule rule : rules) {
            if (rule.process(placeName, result)) {
                break;
            }
        }

        return result;
    }

    public static class ParserRules {
        public static final Rule huangjiahuBuildingWithAreaRule = new HuangjiahuBuildingWithAreaRule();
        public static final Rule buildingWithRoomRule = new BuildingWithRoomRule();
        public static final Rule buildingWithCampusRule = new BuildingWithCampusRule();
        public static final Rule buildingWithCollegeRule = new BuildingWithCollegeRule();

        public static class HuangjiahuBuildingWithAreaRule implements PlaceNameParser.Rule {
            private static final Pattern pattern = Pattern.compile("(?<building>.*楼)(?<area>.*区)(?<room>\\d+)");   // xx楼x区xxx

            @Override
            public boolean process(String placeText, Classroom result) {
                Matcher matcher = pattern.matcher(placeText);
                if (matcher.find()) {
                    result.setCampus("黄家湖");
                    result.setBuilding(matcher.group("building"));
                    result.setArea(matcher.group("area"));
                    result.setRoom(matcher.group("room"));

                    return true;
                }

                return false;
            }
        }

        public static class BuildingWithRoomRule implements PlaceNameParser.Rule {
            private static final Pattern pattern = Pattern.compile("(?<building>.*楼)(?<room>\\d+)");   // xx楼xxx

            @Override
            public boolean process(String placeText, Classroom result) {
                Matcher matcher = pattern.matcher(placeText);
                if (matcher.find()) {
                    // 一般两个校区都有的楼会标注校区（第一个匹配规则）
                    // 如果没有默认黄家湖，另外，青山的主楼就需要单独判断
                    String campus = "黄家湖";
                    if ("主楼".equals(matcher.group("building"))) {
                        campus = "青山";
                    }
                    result.setCampus(campus);
                    result.setBuilding(matcher.group("building"));
                    result.setArea("");
                    result.setRoom(matcher.group("room"));

                    return true;
                }

                return false;
            }
        }

        public static class BuildingWithCampusRule implements PlaceNameParser.Rule {
            private static final Pattern pattern = Pattern.compile("(?<building>.*楼)(?<room>\\d+)\\((?<campus>.*)\\)");  // xx楼xxx(黄家湖/青山)

            @Override
            public boolean process(String placeText, Classroom result) {
                Matcher matcher = pattern.matcher(placeText);
                if (matcher.find()) {
                    result.setCampus(matcher.group("campus"));
                    result.setBuilding(matcher.group("building"));
                    result.setArea("");
                    result.setRoom(matcher.group("room"));

                    return true;
                }

                return false;
            }
        }

        public static class BuildingWithCollegeRule implements PlaceNameParser.Rule {
            private static final Pattern pattern = Pattern.compile("(?<building>.*楼)\\((.*)\\)(?<room>\\d+)");  //xx楼(xxx)xxx

            @Override
            public boolean process(String placeText, Classroom result) {
                Matcher matcher = pattern.matcher(placeText);
                if (matcher.find()) {
                    result.setCampus("黄家湖");
                    result.setBuilding(matcher.group("building"));
                    result.setArea("");
                    result.setRoom(matcher.group("room"));

                    return true;
                }

                return false;
            }
        }
    }
}