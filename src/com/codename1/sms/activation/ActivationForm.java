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

package com.codename1.sms.activation;

import com.codename1.components.FloatingActionButton;
import com.codename1.components.InteractionDialog;
import com.codename1.components.MultiButton;
import com.codename1.io.Log;
import com.codename1.l10n.L10NManager;
import com.codename1.sms.intercept.SMSInterceptor;
import com.codename1.sms.twilio.TwilioSMS;
import com.codename1.ui.AutoCompleteTextField;
import com.codename1.ui.Button;
import com.codename1.ui.Form;
import static com.codename1.ui.CN.*;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.List;
import com.codename1.ui.TextField;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.animations.Transition;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.list.DefaultListCellRenderer;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.table.TableLayout;
import com.codename1.ui.util.Resources;
import com.codename1.util.CaseInsensitiveOrder;
import com.codename1.util.SuccessCallback;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 *Simple UI that uses SMS to verify the phone number of the device
 * @author Shai Almog
 */
public class ActivationForm {
    
    // The following five arrays were modified by Francesco Galgani, see:
    // https://stackoverflow.com/a/48910944/2670744
    
    // From the original list (with few corrections): https://www.worldatlas.com/aatlas/ctycodes.htm
    public static final String[] COUNTRY_NAMES = {"Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra", "Angola", "Anguilla", "Antarctica", "Antigua and Barbuda", "Argentina", "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia", "Bonaire", "Bosnia and Herzegovina", "Botswana", "Bouvet Island", "Brazil", "British Indian Ocean Territory", "Brunei Darussalam", "Bulgaria", "Burkina Faso", "Burundi", "Cambodia", "Cameroon", "Canada", "Cape Verde", "Cayman Islands", "Central African Republic", "Chad", "Chile", "China", "Christmas Island", "Cocos (Keeling) Islands", "Colombia", "Comoros", "Congo, Democratic Republic of (was Zaire)", "Congo, Republic of", "Cook Islands", "Costa Rica", "Croatia", "Cuba", "Curacao", "Cyprus", "Czech Republic", "Cote d'Ivoire", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Falkland Islands (Malvinas)", "Faroe Islands", "Fiji", "Finland", "France", "French Guiana", "French Polynesia", "French Southern Territories", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Gibraltar", "Greece", "Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala", "Guernsey", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Heard Island and McDonald Islands", "Holy See (Vatican City State)", "Honduras", "Hong Kong", "Hungary", "Iceland", "India", "Indonesia", "Iran, Islamic Republic of", "Iraq", "Ireland", "Isle of Man", "Israel", "Italy", "Jamaica", "Japan", "Jersey", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Korea, Democratic People's Republic of", "Korea, Republic of", "Kuwait", "Kyrgyzstan", "Lao People's Democratic Republic", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg", "Macao", "Macedonia, the Former Yugoslav Republic of", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Martinique", "Mauritania", "Mauritius", "Mayotte", "Mexico", "Micronesia, Federated States of", "Moldova, Republic of", "Monaco", "Mongolia", "Montenegro", "Montserrat", "Morocco", "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal", "Netherlands", "New Caledonia", "New Zealand", "Nicaragua", "Niger", "Nigeria", "Niue", "Norfolk Island", "Northern Mariana Islands", "Norway", "Oman", "Pakistan", "Palau", "Palestine, State of", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Pitcairn", "Poland", "Portugal", "Puerto Rico", "Qatar", "Reunion", "Romania", "Russian Federation", "Rwanda", "Saint Barthelemy", "Saint Helena", "Saint Kitts and Nevis", "Saint Lucia", "Saint Martin (French part)", "Saint Pierre and Miquelon", "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Serbia", "Seychelles", "Sierra Leone", "Singapore", "Sint Maarten (Dutch part)", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Georgia and the South Sandwich Islands", "South Sudan", "Spain", "Sri Lanka", "Sudan", "Suriname", "Svalbard and Jan Mayen", "Swaziland", "Sweden", "Switzerland", "Syrian Arab Republic", "Taiwan, Province of China", "Tajikistan", "Tanzania, United Republic of", "Thailand", "Timor-Leste", "Togo", "Tokelau", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Turks and Caicos Islands", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States", "United States Minor Outlying Islands", "Uruguay", "Uzbekistan", "Vanuatu", "Venezuela", "Viet Nam", "Virgin Islands (British)", "Virgin Islands (U.S.)", "Wallis and Futuna", "Western Sahara", "Yemen", "Zambia", "Zimbabwe"};

    public static final String[] COUNTRY_CODES = {"93", "355", "213", "1684", "376", "244", "1264", "672", "1268", "54", "374", "297", "61", "43", "994", "1242", "973", "880", "1246", "375", "32", "501", "229", "1441", "975", "591", "599", "387", "267", "47", "55", "246", "673", "359", "226", "257", "855", "237", "1", "238", "1345", "236", "235", "56", "86", "61", "61", "57", "269", "243", "242", "682", "506", "385", "53", "599", "357", "420", "225", "45", "253", "1767", "18", "593", "20", "503", "240", "291", "372", "251", "500", "298", "679", "358", "33", "594", "689", "262", "241", "220", "995", "49", "233", "350", "30", "299", "1473", "590", "1671", "502", "44", "224", "245", "592", "509", "672", "379", "504", "852", "36", "354", "91", "62", "98", "964", "353", "44", "972", "39", "1876", "81", "44", "962", "7", "254", "686", "850", "82", "965", "996", "856", "371", "961", "266", "231", "218", "423", "370", "352", "853", "389", "261", "265", "60", "960", "223", "356", "692", "596", "222", "230", "262", "52", "691", "373", "377", "976", "382", "1664", "212", "258", "95", "264", "674", "977", "31", "687", "64", "505", "227", "234", "683", "672", "1670", "47", "968", "92", "680", "970", "507", "675", "595", "51", "63", "870", "48", "351", "1", "974", "262", "40", "7", "250", "590", "290", "1869", "1758", "590", "508", "1784", "685", "378", "239", "966", "221", "381", "248", "232", "65", "1721", "421", "386", "677", "252", "27", "500", "211", "34", "94", "249", "597", "47", "268", "46", "41", "963", "886", "992", "255", "66", "670", "228", "690", "676", "1868", "216", "90", "993", "1649", "688", "256", "380", "971", "44", "1", "1", "598", "998", "678", "58", "84", "1284", "1340", "681", "212", "967", "260", "263"};

    public static final String[] COUNTRY_FLAGS = {"af.png", "al.png", "dz.png", "as.png", "ad.png", "ao.png", "ai.png", "aq.png", "ag.png", "ar.png", "am.png", "aw.png", "au.png", "at.png", "az.png", "bs.png", "bh.png", "bd.png", "bb.png", "by.png", "be.png", "bz.png", "bj.png", "bm.png", "bt.png", "bo.png", "bq.png", "ba.png", "bw.png", "bv.png", "br.png", "io.png", "bn.png", "bg.png", "bf.png", "bi.png", "kh.png", "cm.png", "ca.png", "cv.png", "ky.png", "cf.png", "td.png", "cl.png", "cn.png", "cx.png", "cc.png", "co.png", "km.png", "cd.png", "cg.png", "ck.png", "cr.png", "hr.png", "cu.png", "cw.png", "cy.png", "cz.png", "ci.png", "dk.png", "dj.png", "dm.png", "do.png", "ec.png", "eg.png", "sv.png", "gq.png", "er.png", "ee.png", "et.png", "fk.png", "fo.png", "fj.png", "fi.png", "fr.png", "gf.png", "pf.png", "tf.png", "ga.png", "gm.png", "ge.png", "de.png", "gh.png", "gi.png", "gr.png", "gl.png", "gd.png", "gp.png", "gu.png", "gt.png", "gg.png", "gn.png", "gw.png", "gy.png", "ht.png", "hm.png", "va.png", "hn.png", "hk.png", "hu.png", "is.png", "in.png", "id.png", "ir.png", "iq.png", "ie.png", "im.png", "il.png", "it.png", "jm.png", "jp.png", "je.png", "jo.png", "kz.png", "ke.png", "ki.png", "kp.png", "kr.png", "kw.png", "kg.png", "la.png", "lv.png", "lb.png", "ls.png", "lr.png", "ly.png", "li.png", "lt.png", "lu.png", "mo.png", "mk.png", "mg.png", "mw.png", "my.png", "mv.png", "ml.png", "mt.png", "mh.png", "mq.png", "mr.png", "mu.png", "yt.png", "mx.png", "fm.png", "md.png", "mc.png", "mn.png", "me.png", "ms.png", "ma.png", "mz.png", "mm.png", "na.png", "nr.png", "np.png", "nl.png", "nc.png", "nz.png", "ni.png", "ne.png", "ng.png", "nu.png", "nf.png", "mp.png", "no.png", "om.png", "pk.png", "pw.png", "ps.png", "pa.png", "pg.png", "py.png", "pe.png", "ph.png", "pn.png", "pl.png", "pt.png", "pr.png", "qa.png", "re.png", "ro.png", "ru.png", "rw.png", "bl.png", "sh.png", "kn.png", "lc.png", "mf.png", "pm.png", "vc.png", "ws.png", "sm.png", "st.png", "sa.png", "sn.png", "rs.png", "sc.png", "sl.png", "sg.png", "sx.png", "sk.png", "si.png", "sb.png", "so.png", "za.png", "gs.png", "ss.png", "es.png", "lk.png", "sd.png", "sr.png", "sj.png", "sz.png", "se.png", "ch.png", "sy.png", "tw.png", "tj.png", "tz.png", "th.png", "tl.png", "tg.png", "tk.png", "to.png", "tt.png", "tn.png", "tr.png", "tm.png", "tc.png", "tv.png", "ug.png", "ua.png", "ae.png", "gb.png", "us.png", "um.png", "uy.png", "uz.png", "vu.png", "ve.png", "vn.png", "vg.png", "vi.png", "wf.png", "eh.png", "ye.png", "zm.png", "zw.png"};

    public static final String[] COUNTRY_ISO2 = {"AF", "AL", "DZ", "AS", "AD", "AO", "AI", "AQ", "AG", "AR", "AM", "AW", "AU", "AT", "AZ", "BS", "BH", "BD", "BB", "BY", "BE", "BZ", "BJ", "BM", "BT", "BO", "BQ", "BA", "BW", "BV", "BR", "IO", "BN", "BG", "BF", "BI", "KH", "CM", "CA", "CV", "KY", "CF", "TD", "CL", "CN", "CX", "CC", "CO", "KM", "CD", "CG", "CK", "CR", "HR", "CU", "CW", "CY", "CZ", "CI", "DK", "DJ", "DM", "DO", "EC", "EG", "SV", "GQ", "ER", "EE", "ET", "FK", "FO", "FJ", "FI", "FR", "GF", "PF", "TF", "GA", "GM", "GE", "DE", "GH", "GI", "GR", "GL", "GD", "GP", "GU", "GT", "GG", "GN", "GW", "GY", "HT", "HM", "VA", "HN", "HK", "HU", "IS", "IN", "ID", "IR", "IQ", "IE", "IM", "IL", "IT", "JM", "JP", "JE", "JO", "KZ", "KE", "KI", "KP", "KR", "KW", "KG", "LA", "LV", "LB", "LS", "LR", "LY", "LI", "LT", "LU", "MO", "MK", "MG", "MW", "MY", "MV", "ML", "MT", "MH", "MQ", "MR", "MU", "YT", "MX", "FM", "MD", "MC", "MN", "ME", "MS", "MA", "MZ", "MM", "NA", "NR", "NP", "NL", "NC", "NZ", "NI", "NE", "NG", "NU", "NF", "MP", "NO", "OM", "PK", "PW", "PS", "PA", "PG", "PY", "PE", "PH", "PN", "PL", "PT", "PR", "QA", "RE", "RO", "RU", "RW", "BL", "SH", "KN", "LC", "MF", "PM", "VC", "WS", "SM", "ST", "SA", "SN", "RS", "SC", "SL", "SG", "SX", "SK", "SI", "SB", "SO", "ZA", "GS", "SS", "ES", "LK", "SD", "SR", "SJ", "SZ", "SE", "CH", "SY", "TW", "TJ", "TZ", "TH", "TL", "TG", "TK", "TO", "TT", "TN", "TR", "TM", "TC", "TV", "UG", "UA", "AE", "GB", "US", "UM", "UY", "UZ", "VU", "VE", "VN", "VG", "VI", "WF", "EH", "YE", "ZM", "ZW"};

    public static final String[] COUNTRY_ISO3 = {"AFG", "ALB", "DZA", "ASM", "AND", "AGO", "AIA", "ATA", "ATG", "ARG", "ARM", "ABW", "AUS", "AUT", "AZE", "BHS", "BHR", "BGD", "BRB", "BLR", "BEL", "BLZ", "BEN", "BMU", "BTN", "BOL", "BES", "BIH", "BWA", "BVT", "BRA", "IOT", "BRN", "BGR", "BFA", "BDI", "KHM", "CMR", "CAN", "CPV", "CYM", "CAF", "TCD", "CHL", "CHN", "CXR", "CCK", "COL", "COM", "COD", "COG", "COK", "CRI", "HRV", "CUB", "CUW", "CYP", "CZE", "CIV", "DNK", "DJI", "DMA", "DOM", "ECU", "EGY", "SLV", "GNQ", "ERI", "EST", "ETH", "FLK", "FRO", "FJI", "FIN", "FRA", "GUF", "PYF", "ATF", "GAB", "GMB", "GEO", "DEU", "GHA", "GIB", "GRC", "GRL", "GRD", "GLP", "GUM", "GTM", "GGY", "GIN", "GNB", "GUY", "HTI", "HMD", "VAT", "HND", "HKG", "HUN", "ISL", "IND", "IDN", "IRN", "IRQ", "IRL", "IMN", "ISR", "ITA", "JAM", "JPN", "JEY", "JOR", "KAZ", "KEN", "KIR", "PRK", "KOR", "KWT", "KGZ", "LAO", "LVA", "LBN", "LSO", "LBR", "LBY", "LIE", "LTU", "LUX", "MAC", "MKD", "MDG", "MWI", "MYS", "MDV", "MLI", "MLT", "MHL", "MTQ", "MRT", "MUS", "MYT", "MEX", "FSM", "MDA", "MCO", "MNG", "MNE", "MSR", "MAR", "MOZ", "MMR", "NAM", "NRU", "NPL", "NLD", "NCL", "NZL", "NIC", "NER", "NGA", "NIU", "NFK", "MNP", "NOR", "OMN", "PAK", "PLW", "PSE", "PAN", "PNG", "PRY", "PER", "PHL", "PCN", "POL", "PRT", "PRI", "QAT", "REU", "ROU", "RUS", "RWA", "BLM", "SHN", "KNA", "LCA", "MAF", "SPM", "VCT", "WSM", "SMR", "STP", "SAU", "SEN", "SRB", "SYC", "SLE", "SGP", "SXM", "SVK", "SVN", "SLB", "SOM", "ZAF", "SGS", "SSD", "ESP", "LKA", "SDN", "SUR", "SJM", "SWZ", "SWE", "CHE", "SYR", "TWN", "TJK", "TZA", "THA", "TLS", "TGO", "TKL", "TON", "TTO", "TUN", "TUR", "TKM", "TCA", "TUV", "UGA", "UKR", "ARE", "GBR", "USA", "UMI", "URY", "UZB", "VUT", "VEN", "VNM", "VGB", "VIR", "WLF", "ESH", "YEM", "ZMB", "ZWE"};
    
    
    private Form activationForm;
    private Button countryCode;
    private Resources flagResource;
    private Label enterNumberLabel;
    private TextField phone;
    
    private int codeDigits = 6;
    private SuccessCallback<String> phoneNumber;
    
    /**
     * When true includes a floating action button to go to the next step
     */
    private boolean includeFab;

    /**
     * When true includes a "next" button in the title bar
     */
    private boolean includeTitleBarNext;
    
    
    private ActivationForm() {
    }
        
    /**
     * Tries to initialize default country code and flag based on the locale OS locale
     */
    private void initDefaultCodeAndFlag(Button codeButton, Label flag, Image blank) {
        String code = L10NManager.getInstance().getLocale();
        if(code != null) {
            String[] countryCodes; 
            if(code.length() == 2) {
                countryCodes = COUNTRY_ISO2;
            } else {
                if(code.length() == 3) {
                    countryCodes = COUNTRY_ISO3;
                } else {
                    return;
                }
            }
            code = code.toUpperCase();
            for(int iter = 0 ; iter < countryCodes.length ; iter++) {
                if(code.equals(countryCodes[iter])) {
                    codeButton.setText(COUNTRY_CODES[iter]);
                    flag.setIcon(flagResource.getImage(COUNTRY_FLAGS[iter]));
                    if(flag.getIcon() == null) {
                        flag.setIcon(blank);
                    }
                    return;
                }
            }
        }
    }
    
    
    /**
     * Builder style factory method
     * @param title the title
     * @return the instance of the activation form
     */
    public static ActivationForm create(String title) {
        ActivationForm ac = new ActivationForm();
        ac.activationForm =  new Form(title, new BorderLayout());
        ac.activationForm.setTransitionOutAnimator(CommonTransitions.createUncover(CommonTransitions.SLIDE_VERTICAL, true, 400));
        ac.countryCode = new Button("1");
        
        ac.countryCode.setUIID("Label");
        try {
            ac.flagResource = Resources.open("/flags.res");
        } catch(IOException err) {
            Log.e(err);
        }
        Image blankIcon = Image.createImage(100, 70, 0);
        Label flag = new Label(blankIcon, "+");
        flag.setTextPosition(RIGHT);
        
        ac.initDefaultCodeAndFlag(ac.countryCode, flag, blankIcon);
        
        ac.countryCode.addActionListener(e -> {
            Dialog id = new Dialog(BoxLayout.y());
            id.setDisposeWhenPointerOutOfBounds(true);
            id.getContentPane().setScrollableY(true);
            for(int iter = 0 ; iter < COUNTRY_CODES.length ; iter++) {
                MultiButton b = new MultiButton(COUNTRY_NAMES[iter]);
                b.setTextLine2(COUNTRY_CODES[iter]);
                b.setIcon(ac.flagResource.getImage(COUNTRY_FLAGS[iter]));
                if(b.getIcon() == null) {
                    b.setIcon(blankIcon);
                }
                b.addActionListener(ee -> {
                    ac.countryCode.setText(b.getTextLine2());
                    flag.setIcon(b.getIcon());
                    id.dispose();
                });
                id.add(b);
            }
            id.showPopupDialog(ac.countryCode);
        });
        
        ac.countryCode.addActionListener(e -> {
            for(int iter = 0 ; iter < COUNTRY_CODES.length ; iter++) {
                if(ac.countryCode.getText().equals(COUNTRY_CODES[iter])) {
                    flag.setIcon(ac.flagResource.getImage(COUNTRY_FLAGS[iter]));
                }
            }
        });
        
        ac.phone = new TextField("", "Phone", 12, TextField.PHONENUMBER);
        
        ac.activationForm.getContentPane().getAllStyles().setPaddingUnit(Style.UNIT_TYPE_DIPS);
        ac.activationForm.getContentPane().getAllStyles().setPadding(10, 10, 5, 5);
        
        ac.enterNumberLabel = new Label("Enter your mobile number");
        
        TableLayout tl = new TableLayout(1, 3);
        tl.setGrowHorizontally(true);
        Container phoneNumberContainer = new Container(tl);
        phoneNumberContainer.add(flag);
        phoneNumberContainer.add(ac.countryCode);
        phoneNumberContainer.add(ac.phone);
        Container enter = BoxLayout.encloseY(ac.enterNumberLabel, phoneNumberContainer);
        ac.activationForm.getContentPane().add(NORTH, 
                FlowLayout.encloseCenter(enter));
        
        // make sensible defaults for Android and other platform
        ac.includeFab = "and".equals(Display.getInstance().getPlatformName());
        ac.includeTitleBarNext = !ac.includeFab;
        
        return ac;
    }
    
    /**
     * When true includes a "next" button in the title bar
     * @param tb true to include the titlebar next
     * @return instance of this class so creation can be chained
     */
    public ActivationForm includeTitleBarNext(boolean tb) {
        includeTitleBarNext = tb;
        return this;
    }
    
    /**
     * When true includes a floating action button to go to the next step
     * @param fab true to include the fab
     * @return instance of this class so creation can be chained
     */
    public ActivationForm includeFab(boolean fab) {
        includeFab = fab;
        return this;
    }

    /**
     * Sets the text for the enter number label
     * @param t the text for that label
     * @return instance of this class so creation can be chained
     */
    public ActivationForm enterNumberLabel(String t) {
        enterNumberLabel.setText(t);
        return this;
    }
    
    /**
     * Sets the number of digits in the SMS verification code
     * @param d the number of digits
     * @return instance of this class so creation can be chained
     */
    public ActivationForm codeDigits(int d) {
        codeDigits = d;
        return this;
    }

    private void showNumberForm(Form backForm) {
        Random r = new Random();
        String val = "";
        for(int iter = 0 ; iter < codeDigits ; iter++) {
            val += r.nextInt(10);
        }
        final String verificationValue = val;
        twilio.sendSmsAsync("+" + countryCode.getText() + phone.getText(), "Activation code " + val);

        if(SMSInterceptor.isSupported()) {
            if(Dialog.show("Intercept SMS", "We will send you an SMS verification code in order to intercept it automatically you will need to approve the next permission prompt", "OK", "No Thanks")) {
                SMSInterceptor.grabNextSMS(s -> {
                    if(s.indexOf(verificationValue) > -1) {
                        if(backForm != null) {
                            backForm.showBack();
                        }
                        phoneNumber.onSucess("+" + countryCode.getText() + phone.getText());
                    }
                });
            }
        }
        
        Form currentForm = getCurrentForm();
        TextField code = new TextField("", "Verification Code", codeDigits, TextField.NUMERIC);
        code.addDataChangedListener((i, ii) -> {
            // this handles spaces and stuff we might have from copy/paste
            if(code.getText().indexOf(verificationValue) > -1) {
                // prevent a potential hack where a user can paste in hundreds or thousands of characters
                if(code.getText().length() > codeDigits + 2) {
                    return;
                }
                backForm.showBack();
                if(phoneNumber != null) {
                    phoneNumber.onSucess("+" + countryCode.getText() + phone.getText());
                    phoneNumber = null;
                }
            }
        });
        Component cmp = currentForm.getContentPane().getComponentAt(0);
        currentForm.getContentPane().replace(cmp, code, CommonTransitions.createFade(300));
    }
    
    private TwilioSMS twilio;
    
    /**
     * Shows the activation form process and callback with the phone number when activated
     * @param phoneNumber a callback that will be invoked with the phone number when verification succeeds 
     * @param twilio sms sending gateway
     */
    public void show(SuccessCallback<String> phoneNumber, TwilioSMS twilio) {
        this.phoneNumber = phoneNumber;
        this.twilio = twilio;
        Form f = getCurrentForm();
        if(f != null) {
            Transition currentTrans = f.getTransitionOutAnimator();
            f.setTransitionOutAnimator(CommonTransitions.createCover(CommonTransitions.SLIDE_VERTICAL, false, 400));
            activationForm.addShowListener(e -> f.setTransitionOutAnimator(currentTrans));
            activationForm.getToolbar().setBackCommand("", e -> f.showBack());
        }

        phone.setDoneListener(e -> showNumberForm(f));
        
        if(includeFab) {
            FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ARROW_FORWARD);
            fab.addActionListener(e -> showNumberForm(f));
            fab.bindFabToContainer(activationForm.getContentPane());
        }
        
        if(includeTitleBarNext) {
            activationForm.getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_ARROW_FORWARD, e -> showNumberForm(f));
        }
        
        activationForm.show();
    }
}
