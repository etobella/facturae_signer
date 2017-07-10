package com.creublanca.facturae;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;

import xades4j.providers.KeyingDataProvider;
import xades4j.providers.SigningCertChainException;
import xades4j.providers.SigningKeyException;
import xades4j.verification.UnexpectedJCAException;

public class CBKeyingDataProvider implements KeyingDataProvider{

	private String certificatePassword;
	private String certificateAlias;
	private KeyStore ks;

		
	@Override
	public List<X509Certificate> getSigningCertificateChain() throws SigningCertChainException, UnexpectedJCAException {
		try {
			return Arrays.asList((X509Certificate) ks.getCertificate(certificateAlias));
		} catch (KeyStoreException e) {
			throw new UnexpectedJCAException(e.getMessage());
		}
	}

	@Override
	public PrivateKey getSigningKey(X509Certificate arg0) throws SigningKeyException, UnexpectedJCAException {
		try {
			return (PrivateKey) ks.getKey(certificateAlias,certificatePassword.toCharArray());
		} catch (UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException e) {
			throw new UnexpectedJCAException(e.getMessage());
		}
	}
	
	public CBKeyingDataProvider(KeyStore ks, String certificateAlias, String certificatePassword){
		this.ks=ks;
		this.certificateAlias=certificateAlias;
		this.certificatePassword=certificatePassword;
	}

}
