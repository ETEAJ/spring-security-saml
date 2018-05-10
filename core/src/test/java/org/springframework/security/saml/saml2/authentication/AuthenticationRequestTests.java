/*
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.springframework.security.saml.saml2.authentication;

import java.util.Collections;

import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;
import org.opensaml.saml.saml2.core.NameID;
import org.springframework.security.saml.saml2.metadata.Binding;
import org.springframework.security.saml.saml2.metadata.MetadataBase;
import org.w3c.dom.Node;

import static java.lang.Boolean.FALSE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.springframework.security.saml.init.Defaults.authenticationRequest;
import static org.springframework.security.saml.init.SpringSecuritySaml.getInstance;
import static org.springframework.security.saml.saml2.metadata.NameId.PERSISTENT;
import static org.springframework.security.saml.util.XmlTestUtil.assertNodeAttribute;
import static org.springframework.security.saml.util.XmlTestUtil.assertNodeCount;
import static org.springframework.security.saml.util.XmlTestUtil.getNodes;

class AuthenticationRequestTests extends MetadataBase {

    @Test
    public void create() throws Exception {

        AuthenticationRequest request = authenticationRequest(serviceProviderMetadata, identityProviderMetadata);
        String xml = getInstance().toXml(request);

        assertNodeCount(xml, "//samlp:AuthnRequest", 1);
        Iterable<Node> nodes = getNodes(xml, "//samlp:AuthnRequest");
        assertNodeAttribute(nodes.iterator().next(), "Version", equalTo("2.0"));
        assertNodeAttribute(nodes.iterator().next(), "IssueInstant", notNullValue(String.class));
        assertNodeAttribute(nodes.iterator().next(), "ForceAuthn", equalTo("false"));
        assertNodeAttribute(nodes.iterator().next(), "IsPassive", equalTo("false"));
        assertNodeAttribute(nodes.iterator().next(), "ProtocolBinding", equalTo("urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST"));
        assertNodeAttribute(nodes.iterator().next(), "AssertionConsumerServiceURL", equalTo("http://sp.localhost:8080/uaa/saml/sp/SSO"));
        assertNodeAttribute(nodes.iterator().next(), "Destination", equalTo("http://idp.localhost:8080/uaa/saml/idp/SSO"));

        assertNodeCount(xml, "//samlp:NameIDPolicy", 1);
        nodes = getNodes(xml, "//samlp:NameIDPolicy");
        assertNodeAttribute(nodes.iterator().next(), "Format", equalTo(NameID.PERSISTENT.toString()));

        assertNodeCount(xml, "//samlp:RequestedAuthnContext", 1);
        nodes = getNodes(xml, "//samlp:RequestedAuthnContext");
        assertNodeAttribute(nodes.iterator().next(), "Comparison", equalTo("exact"));
    }

    @Test
    public void parse() throws Exception {
        AuthenticationRequest request = authenticationRequest(serviceProviderMetadata, identityProviderMetadata);
        String xml = getInstance().toXml(request);
        AuthenticationRequest data = (AuthenticationRequest) getInstance().resolve(xml, Collections.singletonList(idpVerifying));
        assertNotNull(data);

        assertNotNull(data.getSignature());


        assertSame(Binding.POST, data.getBinding());
        assertEquals("http://sp.localhost:8080/uaa/saml/sp/SSO", data.getAssertionConsumerService().getLocation());
        assertSame(RequestedAuthenticationContext.exact, data.getRequestedAuthenticationContext());
        assertSame(PERSISTENT, data.getNameIDPolicy().getFormat());

        assertThat(data.getVersion(), equalTo("2.0"));
        assertThat(data.getIssueInstant(), notNullValue(DateTime.class));
        assertThat(data.isForceAuth(), equalTo(FALSE));
        assertThat(data.isPassive(), equalTo(FALSE));
        assertThat(data.getBinding().toString(), equalTo("urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST"));
        assertThat(data.getAssertionConsumerService().getLocation(), equalTo("http://sp.localhost:8080/uaa/saml/sp/SSO"));
    }
}