package xyz.seleya.ethereum.ens.ensjavaclient;

import org.web3j.ens.EnsResolutionException;
import org.web3j.tx.ChainIdLong;

public class EnsMigratedRegistryContractsV202002 {
    public static final String MAINNET = "0x00000000000C2E074eC69A0dFb2997BA6C7d2e1e";
    public static final String ROPSTEN = "0x00000000000C2E074eC69A0dFb2997BA6C7d2e1e";
    public static final String RINKEBY = "0x00000000000C2E074eC69A0dFb2997BA6C7d2e1e";

    public static String resolveRegistryContract(String chainId) {
        final Long chainIdLong = Long.parseLong(chainId);
        if (chainIdLong.equals(ChainIdLong.MAINNET)) {
            return MAINNET;
        } else if (chainIdLong.equals(ChainIdLong.ROPSTEN)) {
            return ROPSTEN;
        } else if (chainIdLong.equals(ChainIdLong.RINKEBY)) {
            return RINKEBY;
        } else {
            throw new EnsResolutionException(
                    "Unable to resolve ENS registry contract for network id: " + chainId);
        }
    }
}
