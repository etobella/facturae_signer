package com.creublanca.facturae;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import xades4j.properties.ObjectIdentifier;
import xades4j.properties.SignaturePolicyBase;
import xades4j.properties.SignaturePolicyIdentifierProperty;
import xades4j.providers.SignaturePolicyInfoProvider;

public class SignatureFacturaePolicyInfoProvider implements SignaturePolicyInfoProvider {

	private static String FACTURAE_URL = "http://www.facturae.es/politica_de_firma_formato_facturae/politica_de_firma_formato_facturae_v3_1.pdf";
	@Override
	public SignaturePolicyBase getSignaturePolicy() {
		InputStream is = null;
		try {
			URL url = new URL(FACTURAE_URL);
			URLConnection conn = url.openConnection();
			is = conn.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		SignaturePolicyBase base = new SignaturePolicyIdentifierProperty(new ObjectIdentifier(FACTURAE_URL),is);
		return base;
	}

}
