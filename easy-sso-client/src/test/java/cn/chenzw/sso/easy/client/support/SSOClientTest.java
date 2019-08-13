package cn.chenzw.sso.easy.client.support;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class SSOClientTest {

    @Test
    public void testGenerateSSOUrl() {
        String url = SSOClient
                .generateSSOUrl("http://localhost:8080/itm-sso", "b51d5af6c801ff4bfd31a4a154f35d35", "my",
                        "admin", "/index.jsp");

        System.out.println(url);
    }
}
