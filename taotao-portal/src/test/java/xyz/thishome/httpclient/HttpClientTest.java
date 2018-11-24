package xyz.thishome.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class HttpClientTest {

    @Test
    public void testHttpClientGet() throws Exception {
        //创建一个HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建一个get对象
        HttpGet get = new HttpGet("http://www.baidu.com?wd=java");
        //执行get请求，返回一个CloseableHttpResponse对象，包含请求返回信息
        CloseableHttpResponse httpResponse = httpClient.execute(get);
        //获取状态码
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        System.out.println(statusCode);
        if (statusCode == 200) {
            //获取返回的实体
            HttpEntity entity = httpResponse.getEntity();
            //使用工具类，把entity转化为字符串类型，指定字符编码防止乱码
            String entityString = EntityUtils.toString(entity, "utf-8");
            System.out.println(entityString);
        }

        //使用后记得关闭
        httpClient.close();
        httpResponse.close();
    }

    @Test
    public void testHttpClientWithParam() throws Exception {
        //创建一个HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建一个URIBuilder对象，传入uri
        URIBuilder uriBuilder = new URIBuilder("http://www.baidu.com/s");
        //传入参数
        uriBuilder.addParameter("wd", "java");
        //创建一个get对象,传入构造好的uri
        HttpGet get = new HttpGet(uriBuilder.build());
        //执行get请求，返回一个CloseableHttpResponse对象，包含请求返回信息
        CloseableHttpResponse httpResponse = httpClient.execute(get);
        //获取状态码
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        System.out.println(statusCode);
        if (statusCode == 200) {
            //获取返回的实体
            HttpEntity entity = httpResponse.getEntity();
            //使用工具类，把entity转化为字符串类型，指定字符编码防止乱码
            String entityString = EntityUtils.toString(entity, "utf-8");
            System.out.println(entityString);
        }

        //使用后记得关闭
        httpClient.close();
        httpResponse.close();
    }

    @Test
    public void TestPost() throws Exception {
        //创建一个httpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //模拟一个表单
        List<NameValuePair> kvList = new ArrayList<>();
        kvList.add(new BasicNameValuePair("username", "root"));
        kvList.add(new BasicNameValuePair("password", "123456"));
        //创建请求内容，指定字符编码
        StringEntity entity = new UrlEncodedFormEntity(kvList,"utf-8");

        //创建post请求对象
        HttpPost post = new HttpPost("http://localhost:8081/rest/login");
        //设置post请求内容
        post.setEntity(entity);

        //执行post请求
        CloseableHttpResponse httpResponse = httpClient.execute(post);
        //获取请求返回内容
        HttpEntity entity1 = httpResponse.getEntity();
        //把返回内容转化为字符串形式，指定字符编码
        String string = EntityUtils.toString(entity1, "utf-8");

        System.out.println(string);

        //关闭请求
        httpClient.close();
        httpResponse.close();

    }

}
