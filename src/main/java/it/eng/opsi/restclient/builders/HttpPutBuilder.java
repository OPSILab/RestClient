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
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

public class HttpPutBuilder extends HttpRequestBuilder<HttpPut> {

	private HttpPutBuilder(URI uri) {
		super.httpRequest = new HttpPut(uri);
	}

	public static HttpRequestBase getInstance(URI uri, List<Header> headers, ContentType type,
			AbstractHttpEntity data) {
		HttpPutBuilder builder = new HttpPutBuilder(uri);
		builder.addHeaders(headers);
		builder.addPayload(type, data);

		return builder.httpRequest;
	}

	@Override
	protected void addPayload(ContentType type, AbstractHttpEntity data) {
		try {
			data.setContentType(type.toString());
			super.httpRequest.setEntity(data);
		} catch (Exception e) {
			logger.warning(e.toString());
		}
	}

}
