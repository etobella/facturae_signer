package com.creublanca.facturae;

import xades4j.providers.BasicSignatureOptionsProvider;

public class CBBasicSignatureOptionsProvider implements BasicSignatureOptionsProvider{

	@Override
	public boolean includePublicKey() {
		return true;
	}

	@Override
	public boolean includeSigningCertificate() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean signSigningCertificate() {
		// TODO Auto-generated method stub
		return true;
	}

}
