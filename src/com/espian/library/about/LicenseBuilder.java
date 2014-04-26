/*
 * Copyright (c) 2013 Alex Curran
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.espian.library.about;

import android.content.Context;
import android.text.SpannableStringBuilder;

/**
 * Helper class to create the {@link String} required for
 * licensing.
 */
public class LicenseBuilder {

    public static final String APACHE_2_0 = "Apache 2.0";
    public static final String CREATIVE_COMMONS = "Creative Commons";

    private final String mLicenseBase;

    private SpannableStringBuilder mBuilder;

    /**
     * Initialise a new LicenseBuilder
     */
    public LicenseBuilder(Context c) {
        mBuilder = new SpannableStringBuilder();
        mLicenseBase = c.getString(R.string.license_base);
    }

    /**
     * Add license to the currently building String
     * @param library Name of the library licensed
     * @param author Author of the library
     * @param license The license the library is licensed under
     * @return The {@link LicenseBuilder} object, to easily chain calls
     */
    public LicenseBuilder addLicense(String library, String author, String license) {
        if (mBuilder.length() != 0) mBuilder.append("\n\n");
        mBuilder.append(String.format(mLicenseBase, library, author, license));
        return this;
    }

	public LicenseBuilder addLicense(String fullText) {
		if (mBuilder.length() != 0) mBuilder.append("\n\n");
		mBuilder.append(fullText);
		return this;
	}

    /**
     * Remove all the licenses currently added
     * @return The {@link LicenseBuilder} object, to easily chain calls
     */
    public LicenseBuilder clear() {
        mBuilder = new SpannableStringBuilder();
        return this;
    }

    @Override
    public String toString() {
        return mBuilder.toString();
    }

}
