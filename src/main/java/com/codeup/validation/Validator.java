/**
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.codeup.validation;

import java.util.List;
import java.util.Map;

public interface Validator {
    boolean isValid();
    Map<String, List<String>> messages();
}
