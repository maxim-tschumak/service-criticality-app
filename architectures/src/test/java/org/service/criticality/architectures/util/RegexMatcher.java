package org.service.criticality.architectures.util;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

public class RegexMatcher extends BaseMatcher {

    final private String regex;

    private RegexMatcher(final String regex) {
        this.regex = regex;
    }

    @Override
    public boolean matches(Object o) {
        return ((String) o).matches(regex);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("matches regex=" + regex);
    }

    public static RegexMatcher matches(String regex) {
        return new RegexMatcher(regex);
    }
}
