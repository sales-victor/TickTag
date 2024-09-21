package br.com.ticktag.util;

import org.springframework.data.domain.ExampleMatcher;

public class ExampleMatcherUtil {
	
	private static ExampleMatcher caseInsensitiveAndContainedExampleMatcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
	private static ExampleMatcher caseInsensitiveExampleMatcher = ExampleMatcher.matching().withIgnoreCase();
	private static ExampleMatcher containedExampleMatcher = ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
//	ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("id") .withIgnoreCase(); 
	
	private ExampleMatcherUtil() {}

	public static ExampleMatcher getCaseInsensitiveAndContainedExampleMatcher() {
		return caseInsensitiveAndContainedExampleMatcher;
	}

	public static ExampleMatcher getCaseInsensitiveExampleMatcher() {
		return caseInsensitiveExampleMatcher;
	}


	public static ExampleMatcher getContainedExampleMatcher() {
		return containedExampleMatcher;
	}


}
