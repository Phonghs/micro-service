package com.example.common.proxy.decoder;

import feign.Response;
import feign.codec.ErrorDecoder;

import java.util.Objects;

public class OpenFeignErrorDecoder implements ErrorDecoder {
	@Override
	public Exception decode(String s, Response response) {
		String r;
		try {
			if (Objects.isNull(response) || Objects.isNull(response.body())) {
				r = null;
			} else {
				r = response.body().toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
			r = null;
		}

		return new Exception(r);
	}
}
