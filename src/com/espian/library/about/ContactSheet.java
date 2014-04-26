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
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class ContactSheet extends LinearLayout {

    public ContactSheet(Context context) {
        this(context, null, 0);
    }

    public ContactSheet(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ContactSheet(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        LayoutInflater.from(context).inflate(R.layout.page_contact, this);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AboutPager);
    }
}
