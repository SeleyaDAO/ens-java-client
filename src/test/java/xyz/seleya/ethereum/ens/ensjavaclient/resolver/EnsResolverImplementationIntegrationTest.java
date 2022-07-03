package xyz.seleya.ethereum.ens.ensjavaclient.resolver;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EnsResolverImplementationIntegrationTest {

    @LocalServerPort
    private int testServerPort;

    private final static String BASE_URL = "http://localhost";

    private final static String WEB3_CLIENT_VERSION = "Geth/v1.10.15-omnibus-hotfix-f4decf48/linux-amd64/go1.17.6";
    private final static String ENS_NAME_KOHORST_ETH = "kohorst.eth";
    private final static String CONTENT_HASH_FROM_KOHORST_ETH = "QmatNA86VTCzVW5UAo37gdb6KY344ZwN3ngPfe7qEBdtBe";

    private EnsResolverImplementation ensResolverImplementationTestInstance;

    private Web3j web3j;

    @BeforeEach
    void setUp() {
//        web3j = Web3j.build(new HttpService("http://localhost:8545"));
        web3j = Web3j.build(new HttpService("https://mainnet.infura.io/v3/fbf0764a9e084633a4096a3e59878300"));
        ensResolverImplementationTestInstance = EnsResolverImplementation.getInstance(web3j);
    }

    @Test
    public void findTextRecords_happycase() {
        final String actual = ensResolverImplementationTestInstance.findTextRecords(ENS_NAME_KOHORST_ETH);
        assertEquals("https://lucaskohorst.com", actual);





//        assertTrue(actual.isPresent());
//        assertEquals(CONTENT_HASH_FROM_KOHORST_ETH, actual.get());
    }
}