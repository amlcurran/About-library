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

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public abstract class AbsAboutActivity extends FragmentActivity {

    public void onCreate(Bundle saved) {

        super.onCreate(saved);
        setContentView(R.layout.base_title_pager);
        ViewPager pager = (ViewPager) findViewById(R.id.base_pager);
        pager.setAdapter(new AboutPager(this));
        pager.setOffscreenPageLimit(3);
        pager.setCurrentItem(1);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        else return super.onOptionsItemSelected(item);
    }

    public class AboutPager extends PagerAdapter {

        public final String[] SHORT_TITLES = new String[] { "Changes", "About", "Contact", "Licenses" };

        private Context mContext;

        public AboutPager(Context c) {
            super();
            mContext = c;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return SHORT_TITLES[position].toUpperCase();
        }

        @Override
        public void destroyItem (ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem (ViewGroup container, int position) {
            View view;
            LayoutInflater inflater = LayoutInflater.from(mContext);
            if (position == 0) {
                view = inflater.inflate(R.layout.page_changes, null);
                TextView tv = (TextView) view.findViewById(R.id.change_text);
                tv.setText(getRawAsset(R.raw.change, AbsAboutActivity.this));
            } else if (position == 1) {
                view = inflater.inflate(R.layout.page_about, null);
                TextView tv2 = (TextView) view.findViewById(R.id.textView1);
                tv2.setText(String.format(getString(R.string.version_text, getString(R.string.version))));
            } else if (position == 2) {
                view = inflater.inflate(R.layout.page_contact, null);
            } else {
                view = inflater.inflate(R.layout.page_licenses, null);
                TextView licenseBlurb = (TextView) view.findViewById(R.id.licenses_text);
                licenseBlurb.setText(createLicense().toString());
            }
            container.addView(view);
            return view;
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }
    }

    /**
     * Read a raw file and return it as a HTML-parsed object
     * @param id The ID of the raw asset
     * @param c Context to retrieve the asset from
     * @return HTML-parsed contents of the raw file
     */
    public static Spanned getRawAsset(int id, Context c) {

        InputStream mStream = c.getResources().openRawResource(id);
        InputStreamReader mRead = new InputStreamReader(mStream);
        BufferedReader mReader = new BufferedReader(mRead);
        StringBuilder returner = new StringBuilder();
        String line;
        try {
            while ((line = mReader.readLine()) != null) {

                returner.append(line);

            }

            return Html.fromHtml(returner.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * Create the license {@link String} to show
     * @return
     */
    public abstract LicenseBuilder createLicense();

    public void copyEmail(View v) {
        copy("Email", Uri.parse("mailto:alexc@espiandev.co.uk"), "alexc@espiandev.co.uk");
    }

    public void email(View v) {
        start(Uri.parse("mailto:alexc@espiandev.co.uk"));
    }

    public void copyGPlus(View v) {
        copy("Google+", Uri.parse("https://plus.google.com/110510888639261520925/posts"),
                "https://plus.google.com/110510888639261520925/posts");
    }

    public void gPlus(View v) {
        start(Uri.parse("https://plus.google.com/110510888639261520925/posts"));
    }

    public void copyTwitter(View v) {
        copy("Twitter", Uri.parse("https://twitter.com/espiandev"), "https://twitter.com/espiandev");
    }

    public void twitter(View v) {
        start(Uri.parse("https://twitter.com/espiandev"));
    }

	public void website(View v) {
		start(Uri.parse("http://www.espiandev.co.uk"));
	}

	public void copyWebsite(View v) {
		copy("Website", Uri.parse("http://www.espiandev.co.uk"), "http://www.espiandev.co.uk");
	}

    public void start(Uri uri) {
        startActivity(new Intent(Intent.ACTION_VIEW).setData(uri));
    }

    public void copy(String type, Uri uri, String backup) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

            ClipboardManager newCm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            newCm.setPrimaryClip(ClipData.newUri(getContentResolver(), type, uri));

        } else {

            android.text.ClipboardManager oldCm = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            oldCm.setText(backup);

        }
	    Toast.makeText(this, getString(R.string.copy_to_clip, backup), Toast.LENGTH_SHORT).show();

    }

}
