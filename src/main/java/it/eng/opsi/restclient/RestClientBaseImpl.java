/*******************************************************************************
 * Rest Client
 *   Copyright (C) 2019 Engineering Ingegneria Informatica S.p.A.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package it.eng.opsi.restclient;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.List;
import java.util.logging.Logger;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;

import it.eng.opsi.restclient.builders.HttpDeleteBuilder;
import it.eng.opsi.restclient.builders.HttpGetBuilder;
import it.eng.opsi.restclient.builders.HttpHeadBuilder;
import it.eng.opsi.restclient.builders.HttpPostBuilder;
import it.eng.opsi.restclient.builders.HttpPutBuilder;

public abstract class RestClientBaseImpl {

	protected static final Logger logger = Logger.getLogger(IRestClient.class.getName());
	protected CloseableHttpClient httpclient = null;

	protected CloseableHttpClient buildClient() {

		SSLContextBuilder sshbuilder = new SSLContextBuilder();
		try {
			sshbuilder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sshbuilder.build());

			httpclient = HttpClients.custom()
					.setDefaultRequestConfig(RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build())
					.setSSLHostnameVerifier(new NoopHostnameVerifier()).setSSLSocketFactory(sslsf).build();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return httpclient;
	}

	protected HttpResponse invoke(HTTPMethods method, URI uri, List<Header> headers, ContentType type,
			AbstractHttpEntity data) throws MalformedURLException {

		HttpResponse response = null;
		httpclient = buildClient();

		try {
			HttpRequestBase httpRequest = null;

			switch (method) {
			case DELETE:
				httpRequest = HttpDeleteBuilder.getInstance(uri, headers);
				break;
			case GET:
				httpRequest = HttpGetBuilder.getInstance(uri, headers);
				break;
			case HEAD:
				httpRequest = HttpHeadBuilder.getInstance(uri, headers);
				break;
			case POST:
				httpRequest = HttpPostBuilder.getInstance(uri, headers, type, data);
				break;
			case PUT:
				httpRequest = HttpPutBuilder.getInstance(uri, headers, type, data);
				break;
			default:
				throw new Exception("Method " + method.toString() + " not supported");
			}

			response = httpclient.execute(httpRequest);

		} catch (Exception ioe) {
			logger.info(ioe.toString());
		}

		return response;

	}

	private static boolean isSet(String string) {
		return string != null && string.length() > 0;
	}
}
