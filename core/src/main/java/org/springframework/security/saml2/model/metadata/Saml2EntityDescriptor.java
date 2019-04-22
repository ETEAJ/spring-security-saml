/*
 * Copyright 2002-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package org.springframework.security.saml2.model.metadata;

import java.util.LinkedList;
import java.util.List;
import javax.xml.datatype.Duration;

import org.springframework.security.saml2.model.Saml2SignableObject;
import org.springframework.security.saml2.model.key.Saml2KeyData;
import org.springframework.security.saml2.model.Saml2ImplementationHolder;
import org.springframework.security.saml2.model.signature.Saml2AlgorithmMethod;
import org.springframework.security.saml2.model.signature.Saml2DigestMethod;
import org.springframework.security.saml2.model.signature.Saml2Signature;

import org.joda.time.DateTime;

/**
 * Defines EntityDescriptor as defined by
 * https://www.oasis-open.org/committees/download.php/35391/sstc-saml-metadata-errata-2.0-wd-04-diff.pdf
 * Page 13, Line 268
 */
public class Saml2EntityDescriptor<T extends Saml2EntityDescriptor> extends Saml2ImplementationHolder
implements Saml2SignableObject<T> {

	private String id;
	private String entityId;
	private String entityAlias;
	private DateTime validUntil;
	private Duration cacheDuration;
	private List<? extends Saml2Provider> providers;
	private Saml2Signature signature;
	private Saml2KeyData signingKey;
	private Saml2AlgorithmMethod algorithm;
	private Saml2DigestMethod digest;

	Saml2EntityDescriptor() {
	}

	Saml2EntityDescriptor(Saml2EntityDescriptor other) {
		this.id = other.id;
		this.entityId = other.entityId;
		this.entityAlias = other.entityAlias;
		this.validUntil = other.validUntil;
		this.cacheDuration = other.cacheDuration;
		this.providers = other.providers;
		this.signature = other.signature;
		this.signingKey = other.signingKey;
		this.algorithm = other.algorithm;
		this.digest = other.digest;
	}

	public String getId() {
		return id;
	}

	public T setId(String id) {
		this.id = id;
		return _this();
	}

	@SuppressWarnings("unchecked")
	protected T _this() {
		return (T) this;
	}

	public String getEntityId() {
		return entityId;
	}

	public T setEntityId(String entityId) {
		this.entityId = entityId;
		return _this();
	}

	/**
	 * @return the timestamp of the metadata expiration date. null if this value has not been set.
	 */
	public DateTime getValidUntil() {
		return validUntil;
	}

	public T setValidUntil(DateTime validUntil) {
		this.validUntil = validUntil;
		return _this();
	}

	/**
	 * The time interval in format "PnYnMnDTnHnMnS"
	 * <ul>
	 * <li>P indicates the period (required)</li>
	 * <li>nY indicates the number of years</li>
	 * <li>nM indicates the number of months</li>
	 * <li>nD indicates the number of days</li>
	 * <li>T indicates the start of a time section (required if you are going to specify hours, minutes, or
	 * seconds)</li>
	 * <li>nH indicates the number of hours</li>
	 * <li>nM indicates the number of minutes</li>
	 * <li>nS indicates the number of seconds</li>
	 * </ul>
	 *
	 * @return the cache duration for the metadata. null if no duration has been set.
	 */
	public Duration getCacheDuration() {
		return cacheDuration;
	}

	public T setCacheDuration(Duration cacheDuration) {
		this.cacheDuration = cacheDuration;
		return _this();
	}

	public List<Saml2SsoProvider> getSsoProviders() {
		List<Saml2SsoProvider> result = new LinkedList<>();
		if (getProviders() != null) {
			getProviders()
				.stream()
				.filter(p -> p instanceof Saml2SsoProvider)
				.forEach(p -> result.add((Saml2SsoProvider) p));
		}
		return result;
	}

	public List<? extends Saml2Provider> getProviders() {
		return providers;
	}

	public T setProviders(List<? extends Saml2Provider> providers) {
		this.providers = providers;
		return _this();
	}

	public Saml2Signature getSignature() {
		return signature;
	}

	public T setSignature(Saml2Signature signature) {
		this.signature = signature;
		return _this();
	}

	public Saml2KeyData getSigningKey() {
		return signingKey;
	}

	public Saml2AlgorithmMethod getAlgorithm() {
		return algorithm;
	}

	public Saml2DigestMethod getDigest() {
		return digest;
	}

	public String getEntityAlias() {
		return entityAlias;
	}

	public T setEntityAlias(String entityAlias) {
		this.entityAlias = entityAlias;
		return _this();
	}

	@Override
	public T setSigningKey(Saml2KeyData signingKey, Saml2AlgorithmMethod algorithm, Saml2DigestMethod digest) {
		this.signingKey = signingKey;
		this.algorithm = algorithm;
		this.digest = digest;
		return _this();
	}

	@Override
	public String getOriginEntityId() {
		return getEntityId();
	}

	@Override
	public String getDestinationEntityId() {
		return null;
	}
}