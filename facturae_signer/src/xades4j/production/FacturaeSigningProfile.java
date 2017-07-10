/*
 * XAdES4j - A Java library for generation and verification of XAdES signatures.
 * Copyright (C) 2010 Luis Goncalves.
 *
 * XAdES4j is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or any later version.
 *
 * XAdES4j is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License along
 * with XAdES4j. If not, see <http://www.gnu.org/licenses/>.
 */
package xades4j.production;

import xades4j.production.XadesSigner;
import xades4j.production.XadesSigningProfile;
import xades4j.providers.KeyingDataProvider;
import xades4j.providers.SignaturePolicyInfoProvider;

/**
 * A profile for producing XAdES-BES signatures. A {@link KeyingDataProvider}
 * has to be supplied.
 * 
 * @see XadesSigningProfile
 * @author Luís
 */
public final class FacturaeSigningProfile extends XadesSigningProfile {
	public FacturaeSigningProfile(KeyingDataProvider keyingProvider) {
		super(keyingProvider);
	}

	public FacturaeSigningProfile(Class<? extends KeyingDataProvider> keyingProviderClass) {
		super(keyingProviderClass);
	}

	@Override
	protected Class<? extends XadesSigner> getSignerClass() {
		return SignerFacturae.class;
	}

	public FacturaeSigningProfile withPolicyProvider(SignaturePolicyInfoProvider policyInfoProvider) {
		withBinding(SignaturePolicyInfoProvider.class, policyInfoProvider);
		return this;
	}

	public FacturaeSigningProfile withPolicyProvider(
			Class<? extends SignaturePolicyInfoProvider> policyInfoProviderClass) {
		withBinding(SignaturePolicyInfoProvider.class, policyInfoProviderClass);
		return this;
	}
}