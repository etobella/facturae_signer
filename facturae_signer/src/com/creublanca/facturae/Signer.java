package com.creublanca.facturae;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.security.KeyStore;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.codec.binary.Base64;
import org.w3c.dom.Document;

import xades4j.algorithms.EnvelopedSignatureTransform;
import xades4j.production.DataObjectReference;
import xades4j.production.FacturaeSigningProfile;
import xades4j.production.SignedDataObjects;
import xades4j.production.XadesSigner;
import xades4j.production.XadesSigningProfile;
import xades4j.properties.DataObjectDesc;
import xades4j.properties.DataObjectFormatProperty;
import xades4j.properties.SignerRoleProperty;
import xades4j.properties.SigningTimeProperty;
import xades4j.providers.AlgorithmsProviderEx;
import xades4j.providers.KeyingDataProvider;
import xades4j.providers.SignaturePropertiesCollector;
import xades4j.providers.SignaturePropertiesProvider;



public class Signer {
	public static void main(String[] args) throws Exception{
		String MESSAGE = args[0];
		String KEYSTORE_BASE64 = args[1];
		String KEYSTORE_TYPE = args[2];
		String KEYSTORE_PASS = args[3];
		String CERTIFICATE_ALIAS;
		String CERTIFICATE_PASSWORD;
		KeyStore ks = KeyStore.getInstance(KEYSTORE_TYPE);
		ks.load(new ByteArrayInputStream(Base64.decodeBase64(KEYSTORE_BASE64)), KEYSTORE_PASS.toCharArray());
		if(KEYSTORE_TYPE.equals("pkcs12")){
			CERTIFICATE_ALIAS = ks.aliases().nextElement();
			CERTIFICATE_PASSWORD = KEYSTORE_PASS;
		}
		else{
			CERTIFICATE_ALIAS = args[5];
			CERTIFICATE_PASSWORD = args[6];
		}
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		documentBuilderFactory.setNamespaceAware(true);
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document doc = documentBuilder.parse(new ByteArrayInputStream(Base64.decodeBase64(MESSAGE)));
        
		KeyingDataProvider keyingProvider = new CBKeyingDataProvider(ks, CERTIFICATE_ALIAS, CERTIFICATE_PASSWORD);
		CBBasicSignatureOptionsProvider providerOptions = new CBBasicSignatureOptionsProvider();
		AlgorithmsProviderEx algorithms = new AlgorithmsProviderExFacturae();
		XadesSigningProfile profile = new FacturaeSigningProfile(keyingProvider)
				.withPolicyProvider(new SignatureFacturaePolicyInfoProvider())
				.withBasicSignatureOptionsProvider(providerOptions).withAlgorithmsProviderEx(algorithms)
				.withSignaturePropertiesProvider(new SignaturePropertiesProvider() {
					@Override
					public void provideProperties(SignaturePropertiesCollector signedPropsCol) {
						signedPropsCol.setSignerRole(new SignerRoleProperty("supplier"));
						signedPropsCol.setSigningTime(new SigningTimeProperty());
					}
				});
		DataObjectFormatProperty format = new DataObjectFormatProperty("application/xml")
				.withDescription("Factura");
		DataObjectDesc obj = new DataObjectReference("").withDataObjectFormat(format)
				.withTransform(new EnvelopedSignatureTransform());
		XadesSigner signer = profile.newSigner();
		signer.sign(new SignedDataObjects(obj), doc.getDocumentElement());
		// Transformamos el Document resultante a un ByteArray
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer t = tf.newTransformer();
		StringWriter sw = new StringWriter();
		StreamResult result = new StreamResult(sw);
		t.transform(new DOMSource(doc), result);
		System.out.println(sw.toString());
	}
}
