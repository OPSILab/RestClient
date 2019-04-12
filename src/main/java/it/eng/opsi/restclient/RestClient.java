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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.ContentType;

public class RestClient extends RestClientBaseImpl implements IRestClient {

	public HttpResponse sendGetRequest(URI uri, List<Header> headers) throws MalformedURLException {
		return invoke(HTTPMethods.GET, uri, headers, null, null);
	}

	public HttpResponse sendPostRequest(URI uri, List<Header> headers, ContentType type, AbstractHttpEntity data)
			throws MalformedURLException {
		return invoke(HTTPMethods.POST, uri, headers, type, data);
	}

	public HttpResponse sendDeleteRequest(URI uri, List<Header> headers) throws MalformedURLException {
		return invoke(HTTPMethods.DELETE, uri, headers, null, null);
	}

	public HttpResponse sendPutRequest(URI uri, List<Header> headers, ContentType type, AbstractHttpEntity data)
			throws MalformedURLException {
		return invoke(HTTPMethods.PUT, uri, headers, type, data);
	}

	public HttpResponse sendHeadRequest(URI uri, List<Header> headers) throws MalformedURLException {
		return invoke(HTTPMethods.HEAD, uri, headers, null, null);
	}

	public String getHttpResponseBody(HttpResponse httpResponse) throws Exception {

		BufferedReader rd = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		httpclient.close();

		return result.toString();
	}

	public int getStatus(HttpResponse httpresponse) {
		return httpresponse.getStatusLine().getStatusCode();
	}

}
