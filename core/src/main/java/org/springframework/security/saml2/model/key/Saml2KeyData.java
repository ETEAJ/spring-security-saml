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

package org.springframework.security.saml2.model.key;

public class Saml2KeyData {

	private final String id;
	private final String privateKey;
	private final String certificate;
	private final String passphrase;
	private final Saml2KeyType type;

	public Saml2KeyData(String id,
						String privateKey,
						String certificate,
						String passphrase,
						Saml2KeyType type) {
		this.id = id;
		this.privateKey = privateKey;
		this.certificate = certificate;
		this.passphrase = passphrase;
		this.type = type;
	}

	public Saml2KeyData(Saml2KeyData other) {
		this(
			other.getId(),
			other.getPrivateKey(),
			other.getCertificate(),
			other.getPassphrase(),
			other.getType()
		);
	}

	public String getId() {
		return id;
	}

	public Saml2KeyType getType() {
		return type;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public String getCertificate() {
		return certificate;
	}

	public String getPassphrase() {
		return passphrase;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static Builder builder(Saml2KeyData data) {
		return builder()
			.certificate(data.getCertificate())
			.id(data.getId())
			.passphrase(data.getPassphrase())
			.privateKey(data.getPrivateKey())
			.type(data.getType());
	}

	public static final class Builder {
		private String id;
		private String privateKey;
		private String certificate;
		private String passphrase;
		private Saml2KeyType type = Saml2KeyType.SIGNING;

		private Builder() {
		}

		public Builder id(String name) {
			this.id = name;
			return this;
		}

		public Builder privateKey(String privateKey) {
			this.privateKey = privateKey;
			return this;
		}

		public Builder certificate(String certificate) {
			this.certificate = certificate;
			return this;
		}

		public Builder passphrase(String passphrase) {
			this.passphrase = passphrase;
			return this;
		}

		public Builder type(Saml2KeyType type) {
			this.type = type;
			return this;
		}

		public Saml2KeyData build() {
			return new Saml2KeyData(id, privateKey, certificate, passphrase, type);
		}
	}
}