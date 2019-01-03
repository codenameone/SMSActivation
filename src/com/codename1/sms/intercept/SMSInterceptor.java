/*
 * Copyright (c) 2012, Codename One and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Codename One designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *  
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 * 
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 * 
 * Please contact Codename One through http://www.codenameone.com/ if you 
 * need additional information or have any questions.
 */
package com.codename1.sms.intercept;

import com.codename1.system.NativeLookup;
import com.codename1.util.SuccessCallback;

/**
 * This is a high level abstraction of the native classes and callbacks rolled
 * into one.
 */
public class SMSInterceptor {

    private static NativeSMSInterceptor nativeImpl;

    private static NativeSMSInterceptor get() {
        if (nativeImpl == null) {
            nativeImpl = NativeLookup.create(NativeSMSInterceptor.class);
            if (nativeImpl == null || !nativeImpl.isSupported()) {
                nativeImpl = null;
            }
        }
        return nativeImpl;
    }

    public static boolean isSupported() {
        return get() != null;
    }

    public static void grabNextSMS(SuccessCallback<String> onSuccess) {
        if (isSupported()) {
            SMSCallback.onSuccess = onSuccess;
            get().bindSMSListener();
        }
    }

    static void unbindListener() {
        if (isSupported()) {
            get().unbindSMSListener();
        }
    }
}
