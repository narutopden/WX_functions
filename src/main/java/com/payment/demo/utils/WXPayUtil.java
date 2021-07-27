package com.payment.demo.utils;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * wechatPay Utilities [ XML to MAP] and [MAP to XML] convertion
 * production of signature
 * @author Blue_Sky 7/24/21
 */
public class WXPayUtil {

    /**
     * XML格式字符串转换为Map
     *
     * @param strXML XML字符串
     * @return XML数据转换后的Map
     * @throws Exception from MD5
     */
    public static Map<String, String> xmlToMap(String strXML) throws Exception {
        try {
            Map<String, String> data = new HashMap<>();
            DocumentBuilder documentBuilder = WXPayXmlUtil.newDocumentBuilder();
            InputStream stream = new ByteArrayInputStream(strXML.getBytes(StandardCharsets.UTF_8));
            org.w3c.dom.Document doc = documentBuilder.parse(stream);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getDocumentElement().getChildNodes();
            for (int idx = 0; idx < nodeList.getLength(); ++idx) {
                Node node = nodeList.item(idx);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    org.w3c.dom.Element element = (org.w3c.dom.Element) node;
                    data.put(element.getNodeName(), element.getTextContent());
                }
            }
            try {
                stream.close();
            } catch (Exception ex) {
                // do nothing
            }
            return data;
        } catch (Exception ex) {
            throw ex;
        }

    }

    /**
     * 将Map转换为XML格式的字符串
     *
     * @param data Map类型数据
     * @return XML格式的字符串
     * @throws Exception
     */
    public static String mapToXml(Map<String, String> data) throws Exception {
        org.w3c.dom.Document document = WXPayXmlUtil.newDocument();
        org.w3c.dom.Element root = document.createElement("xml");
        document.appendChild(root);
        for (String key: data.keySet()) {
            String value = data.get(key);
            if (value == null) {
                value = "";
            }
            value = value.trim();
            org.w3c.dom.Element filed = document.createElement(key);
            filed.appendChild(document.createTextNode(value));
            root.appendChild(filed);
        }
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        DOMSource source = new DOMSource(document);
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        transformer.transform(source, result);
        String output = writer.getBuffer().toString(); //.replaceAll("\n|\r", "");
        try {
            writer.close();
        }
        catch (Exception ex) {
        }
        return output;
    }

    /**
     * producing wechat pay signature
     * @return
     */
    public static String createSignature(SortedMap<String, String> sortedMap, String key) throws Exception {
        StringBuilder stringBuilderSignTmp = new StringBuilder();
        Set<Map.Entry<String, String>> set = sortedMap.entrySet();
        Iterator<Map.Entry<String, String>> iterator = set.iterator();

        /**
         * code to produce this format [ stringA="appid=wxd930ea5d5a258f4f&body=test&device_info=1000&mch_id=10000100&nonce_str=ibuaiVcKdpRxkhJA"; ]
         */
        while (iterator.hasNext()){
            Map.Entry<String, String> entry = iterator.next();
            String k = entry.getKey();
            String v = entry.getValue();
            if (v != null && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)){
                stringBuilderSignTmp.append(k+"="+v+"&");
            }
        }
        stringBuilderSignTmp.append("key=").append(key);
        String sign = CommonUtils.MD5(stringBuilderSignTmp.toString()).toUpperCase();
        return sign;
    }

    public static Boolean isSignCorrect(SortedMap<String, String> params, String key) throws Exception {
        String sign = createSignature(params, key);
        String wxPaySign = params.get("sign").toUpperCase();
        return sign.equals(wxPaySign);
    }

    /**
     * convertion of map into sorted map
     * @param map
     * @return
     */
    public static SortedMap<String ,String> getSortedMap(Map<String, String> map){
        SortedMap<String,String> sortedMap = new TreeMap<>();
        Iterator <String> iterator = map.keySet().iterator();
        while (iterator.hasNext()){
            String key = (String) iterator.next();
            String value = (String) map.get(key);
            String tmpValue ="";
            if (null != value){
                tmpValue = value.trim();
            }
            sortedMap.put(key, tmpValue);
        }
        return sortedMap;
    }

    public static String successMessage(){
        String xmlSuccess = "";
        Map<String, String> map = new HashMap<>();
        map.put("return_code","SUCCESS");
        map.put("return_msg","OK");
        try {
            xmlSuccess=mapToXml(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return xmlSuccess;
    }

}
