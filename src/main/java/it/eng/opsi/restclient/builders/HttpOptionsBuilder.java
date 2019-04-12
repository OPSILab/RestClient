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
package it.eng.opsi.restclient.builders;

import java.net.URI;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.ContentType;

public class HttpOptionsBuilder extends HttpRequestBuilder<HttpOptions> {

	private HttpOptionsBuilder(URI uri) {
		super.httpRequest = new HttpOptions(uri);
	}

	public static HttpRequestBase getInstance(URI uri, List<Header> headers, ContentType type) {
		HttpOptionsBuilder builder = new HttpOptionsBuilder(uri);
		builder.addHeaders(headers);

		return builder.httpRequest;
	}

	@Override
	protected void addPayload(ContentType type, AbstractHttpEntity data) {
		throw new RuntimeException("Payload not allowed in HTTP OPTIONS requests");
	}

}
