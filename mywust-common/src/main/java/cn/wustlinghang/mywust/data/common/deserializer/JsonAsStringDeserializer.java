package cn.wustlinghang.mywust.data.common.deserializer;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.ValueNode;

import java.io.IOException;

public class JsonAsStringDeserializer extends JsonDeserializer<String> {
    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        TreeNode tree = jsonParser.getCodec().readTree(jsonParser);
        if (tree.isValueNode()) {
            ValueNode valueNode = (ValueNode) tree;
            return valueNode.textValue();
        } else {
            return tree.toString();
        }
    }
}
