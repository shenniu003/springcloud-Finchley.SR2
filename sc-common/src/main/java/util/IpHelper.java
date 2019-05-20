package util;

import org.apache.http.HttpHost;
import org.elasticsearch.common.Strings;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2019/4/18.
 */
public class IpHelper {

    private static final String strHosts = "(?<h>[^:]+)://(?<ip>[^:]+):(?<port>[^/|,]+)";
    private static final Pattern hostPattern = Pattern.compile(strHosts);

    public static Optional<String> getHostIp() {
        try {
            return Optional.ofNullable(InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public static Optional<String> getHostName() {
        try {
            return Optional.ofNullable(InetAddress.getLocalHost().getHostName());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /**
     * strHosts："http://192.168.0.1:9200","http://192.168.0.1:9200","http://192.168.0.1:9200"
     *
     * @return
     */
    public static List<HttpHost> getHostsByStr(String... strHosts) {
        List<HttpHost> hosts = new ArrayList<>();
        for (int i = 0; i < strHosts.length; i++) {
            String[] hostArr = strHosts[i].split(",");
            for (String strHost : hostArr) {
                Matcher matcher = hostPattern.matcher(strHost);
                if (matcher.find()) {
                    String http = matcher.group("h");
                    String ip = matcher.group("ip");
                    String port = matcher.group("port");

                    if (Strings.isEmpty(http) || Strings.isEmpty(ip) || Strings.isEmpty(port)) {
                        continue;
                    }
                    hosts.add(new HttpHost(ip, Integer.valueOf(port), http));
                }
            }
        }
        return hosts;
    }

    public static HttpHost[] getHostArrByStr(String... strHosts) {
        List<HttpHost> list = getHostsByStr(strHosts);
        return Arrays.copyOf(list.toArray(), list.size(), HttpHost[].class);
    }
}
