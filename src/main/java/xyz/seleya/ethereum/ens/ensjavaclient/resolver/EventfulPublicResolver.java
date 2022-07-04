package xyz.seleya.ethereum.ens.ensjavaclient.resolver;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import org.slf4j.Logger;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import xyz.seleya.ethereum.ens.contracts.generated.PublicResolver;

import java.math.BigInteger;

import static org.slf4j.LoggerFactory.getLogger;

public class EventfulPublicResolver extends PublicResolver {

    private static final Logger logger = getLogger(EventfulPublicResolver.class);

    public EventfulPublicResolver(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public EventfulPublicResolver(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(contractAddress, web3j, credentials, contractGasProvider);
    }

    public EventfulPublicResolver(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public EventfulPublicResolver(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static EventfulPublicResolver load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new EventfulPublicResolver(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    @Override
    public Flowable<TextChangedEventResponse> textChangedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, TextChangedEventResponse>() {
            @Override
            public PublicResolver.TextChangedEventResponse apply(Log log) {
//                logger.info("received a log: " + log.getAddress() + " with Tx Hash: " + log.getTransactionHash());
//                logger.info("received a log: " + log);
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TEXTCHANGED_EVENT, log);
                PublicResolver.TextChangedEventResponse typedResponse = new PublicResolver.TextChangedEventResponse();
                typedResponse.log = log;
                if (eventValues == null) {
                    return typedResponse;
                }
                logger.info("received a TextChanged log: " + log);
                typedResponse.node = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.indexedKey = (byte[]) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.key = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }
}
