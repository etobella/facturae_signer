package com.creublanca.facturae;

import org.apache.xml.security.algorithms.MessageDigestAlgorithm;

import xades4j.providers.impl.DefaultAlgorithmsProviderEx;

public class AlgorithmsProviderExFacturae extends DefaultAlgorithmsProviderEx {
	@Override
	public String getDigestAlgorithmForDataObjsReferences() {
		return MessageDigestAlgorithm.ALGO_ID_DIGEST_SHA1;
	}

	@Override
	public String getDigestAlgorithmForReferenceProperties() {
		return MessageDigestAlgorithm.ALGO_ID_DIGEST_SHA1;
	}

	@Override
	public String getDigestAlgorithmForTimeStampProperties() {
		return MessageDigestAlgorithm.ALGO_ID_DIGEST_SHA1;
	}
}
