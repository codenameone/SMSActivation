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
    
    private static final String[] COUNTRY_NAMES = {
        "Afghanistan",
        "Albania",
        "Algeria",
        "American Samoa",
        "Andorra",
        "Angola",
        "Anguilla",
        "Antigua and Barbuda",
        "Argentina",
        "Armenia",
        "Aruba",
        "Australia",
        "Austria",
        "Azerbaijan",
        "Bahamas",
        "Bahrain",
        "Bangladesh",
        "Barbados",
        "Belarus",
        "Belgium",
        "Belize",
        "Benin",
        "Bermuda",
        "Bhutan",
        "Bolivia",
        "Bosnia and Herzegovina",
        "Botswana",
        "Brazil",
        "British Indian Ocean Territory",
        "British Virgin Islands",
        "Brunei",
        "Bulgaria",
        "Burkina Faso",
        "Burundi",
        "Cambodia",
        "Cameroon",
        "Canada",
        "Cape Verde",
        "Cayman Islands",
        "Central African Republic",
        "Chad",
        "Chile",
        "China",
        "Christmas Island",
        "Cocos (Keeling) Islands",
        "Colombia",
        "Comoros",
        "Cook Islands",
        "Costa Rica",
        "Croatia",
        "Cuba",
        "Curaçao",
        "Cyprus",
        "Czechia",
        "Denmark",
        "Djibouti",
        "Dominica",
        "Dominican Republic",
        "DR Congo",
        "Ecuador",
        "Egypt",
        "El Salvador",
        "Equatorial Guinea",
        "Eritrea",
        "Estonia",
        "Ethiopia",
        "Falkland Islands",
        "Faroe Islands",
        "Fiji",
        "Finland",
        "France",
        "French Guiana",
        "French Polynesia",
        "Gabon",
        "Gambia",
        "Georgia",
        "Germany",
        "Ghana",
        "Gibraltar",
        "Greece",
        "Greenland",
        "Grenada",
        "Guadeloupe",
        "Guam",
        "Guatemala",
        "Guernsey",
        "Guinea",
        "Guinea-Bissau",
        "Guyana",
        "Haiti",
        "Honduras",
        "Hong Kong",
        "Hungary",
        "Iceland",
        "India",
        "Indonesia",
        "Iran",
        "Iraq",
        "Ireland",
        "Isle of Man",
        "Israel",
        "Italy",
        "Ivory Coast",
        "Jamaica",
        "Japan",
        "Jersey",
        "Jordan",
        "Kazakhstan",
        "Kenya",
        "Kiribati",
        "Kosovo",
        "Kuwait",
        "Kyrgyzstan",
        "Laos",
        "Latvia",
        "Lebanon",
        "Lesotho",
        "Liberia",
        "Libya",
        "Liechtenstein",
        "Lithuania",
        "Luxembourg",
        "Macau",
        "Macedonia",
        "Madagascar",
        "Malawi",
        "Malaysia",
        "Maldives",
        "Mali",
        "Malta",
        "Marshall Islands",
        "Martinique",
        "Mauritania",
        "Mauritius",
        "Mayotte",
        "Mexico",
        "Micronesia",
        "Moldova",
        "Monaco",
        "Mongolia",
        "Montenegro",
        "Montserrat",
        "Morocco",
        "Mozambique",
        "Myanmar",
        "Namibia",
        "Nauru",
        "Nepal",
        "Netherlands",
        "New Caledonia",
        "New Zealand",
        "Nicaragua",
        "Niger",
        "Nigeria",
        "Niue",
        "Norfolk Island",
        "North Korea",
        "Northern Mariana Islands",
        "Norway",
        "Oman",
        "Pakistan",
        "Palau",
        "Palestine",
        "Panama",
        "Papua New Guinea",
        "Paraguay",
        "Peru",
        "Philippines",
        "Pitcairn Islands",
        "Poland",
        "Portugal",
        "Puerto Rico",
        "Qatar",
        "Republic of the Congo",
        "Romania",
        "Russia",
        "Rwanda",
        "Réunion",
        "Saint Barthélemy",
        "Saint Kitts and Nevis",
        "Saint Lucia",
        "Saint Martin",
        "Saint Pierre and Miquelon",
        "Saint Vincent and the Grenadines",
        "Samoa",
        "San Marino",
        "Saudi Arabia",
        "Senegal",
        "Serbia",
        "Seychelles",
        "Sierra Leone",
        "Singapore",
        "Sint Maarten",
        "Slovakia",
        "Slovenia",
        "Solomon Islands",
        "Somalia",
        "South Africa",
        "South Georgia",
        "South Korea",
        "South Sudan",
        "Spain",
        "Sri Lanka",
        "Sudan",
        "Suriname",
        "Svalbard and Jan Mayen",
        "Swaziland",
        "Sweden",
        "Switzerland",
        "Syria",
        "São Tomé and Príncipe",
        "Taiwan",
        "Tajikistan",
        "Tanzania",
        "Thailand",
        "Timor-Leste",
        "Togo",
        "Tokelau",
        "Tonga",
        "Trinidad and Tobago",
        "Tunisia",
        "Turkey",
        "Turkmenistan",
        "Turks and Caicos Islands",
        "Tuvalu",
        "Uganda",
        "Ukraine",
        "United Arab Emirates",
        "United Kingdom",
        "United States",
        "United States Virgin Islands",
        "Uruguay",
        "Uzbekistan",
        "Vanuatu",
        "Vatican City",
        "Venezuela",
        "Vietnam",
        "Wallis and Futuna",
        "Western Sahara",
        "Yemen",
        "Zambia",
        "Zimbabwe",
        "Åland Islands",
    };
    private static final String[] COUNTRY_CODES= {
        "93",
        "355",
        "213",
        "1684",
        "376",
        "244",
        "1264",
        "1268",
        "54",
        "374",
        "297",
        "61",
        "43",
        "994",
        "1242",
        "973",
        "880",
        "1246",
        "375",
        "32",
        "501",
        "229",
        "1441",
        "975",
        "591",
        "387",
        "267",
        "55",
        "246",
        "1284",
        "673",
        "359",
        "226",
        "257",
        "855",
        "237",
        "1",
        "238",
        "1345",
        "236",
        "235",
        "56",
        "86",
        "61",
        "61",
        "57",
        "269",
        "682",
        "506",
        "385",
        "53",
        "5999",
        "357",
        "420",
        "45",
        "253",
        "1767",
        "1809",
        "243",
        "593",
        "20",
        "503",
        "240",
        "291",
        "372",
        "251",
        "500",
        "298",
        "679",
        "358",
        "33",
        "594",
        "689",
        "241",
        "220",
        "995",
        "49",
        "233",
        "350",
        "30",
        "299",
        "1473",
        "590",
        "1671",
        "502",
        "44",
        "224",
        "245",
        "592",
        "509",
        "504",
        "852",
        "36",
        "354",
        "91",
        "62",
        "98",
        "964",
        "353",
        "44",
        "972",
        "39",
        "225",
        "1876",
        "81",
        "44",
        "962",
        "76",
        "254",
        "686",
        "383",
        "965",
        "996",
        "856",
        "371",
        "961",
        "266",
        "231",
        "218",
        "423",
        "370",
        "352",
        "853",
        "389",
        "261",
        "265",
        "60",
        "960",
        "223",
        "356",
        "692",
        "596",
        "222",
        "230",
        "262",
        "52",
        "691",
        "373",
        "377",
        "976",
        "382",
        "1664",
        "212",
        "258",
        "95",
        "264",
        "674",
        "977",
        "31",
        "687",
        "64",
        "505",
        "227",
        "234",
        "683",
        "672",
        "850",
        "1670",
        "47",
        "968",
        "92",
        "680",
        "970",
        "507",
        "675",
        "595",
        "51",
        "63",
        "64",
        "48",
        "351",
        "1787",
        "974",
        "242",
        "40",
        "7",
        "250",
        "262",
        "590",
        "1869",
        "1758",
        "590",
        "508",
        "1784",
        "685",
        "378",
        "966",
        "221",
        "381",
        "248",
        "232",
        "65",
        "1721",
        "421",
        "386",
        "677",
        "252",
        "27",
        "500",
        "82",
        "211",
        "34",
        "94",
        "249",
        "597",
        "4779",
        "268",
        "46",
        "41",
        "963",
        "239",
        "886",
        "992",
        "255",
        "66",
        "670",
        "228",
        "690",
        "676",
        "1868",
        "216",
        "90",
        "993",
        "1649",
        "688",
        "256",
        "380",
        "971",
        "44",
        "1",
        "1340",
        "598",
        "998",
        "678",
        "3906698",
        "58",
        "84",
        "681",
        "212",
        "967",
        "260",
        "263",
        "358",
    };
    private static final String[] COUNTRY_FLAGS = {
        "afg.png",
        "alb.png",
        "alg.png",
        "asa.png",
        "and.png",
        "ang.png",
        null,
        "ant.png",
        "arg.png",
        "arm.png",
        "aru.png",
        "aus.png",
        "aut.png",
        "aze.png",
        "bah.png",
        "brn.png",
        "ban.png",
        "bar.png",
        "blr.png",
        "bel.png",
        "biz.png",
        "ben.png",
        "ber.png",
        "bhu.png",
        "bol.png",
        "bih.png",
        "bot.png",
        "bra.png",
        null,
        "ivb.png",
        "bru.png",
        "bul.png",
        "bur.png",
        "bdi.png",
        "cam.png",
        "cmr.png",
        "can.png",
        "cpv.png",
        "cay.png",
        "caf.png",
        "cha.png",
        "chi.png",
        "chn.png",
        null,
        null,
        "col.png",
        "com.png",
        "cok.png",
        "crc.png",
        "cro.png",
        "cub.png",
        null,
        "cyp.png",
        "cze.png",
        "den.png",
        "dji.png",
        "dma.png",
        "dom.png",
        "cod.png",
        "ecu.png",
        "egy.png",
        "esa.png",
        "geq.png",
        "eri.png",
        "est.png",
        "eth.png",
        null,
        null,
        "fij.png",
        "fin.png",
        "fra.png",
        null,
        null,
        "gab.png",
        "gam.png",
        "geo.png",
        "ger.png",
        "gha.png",
        null,
        "gre.png",
        null,
        "grn.png",
        null,
        "gum.png",
        "gua.png",
        null,
        "gui.png",
        "gbs.png",
        "guy.png",
        "hai.png",
        "hon.png",
        "hkg.png",
        "hun.png",
        "isl.png",
        "ind.png",
        "ina.png",
        "iri.png",
        "irq.png",
        "irl.png",
        null,
        "isr.png",
        "ita.png",
        "civ.png",
        "jam.png",
        "jpn.png",
        null,
        "jor.png",
        "kaz.png",
        "ken.png",
        "kir.png",
        "kos.png",
        "kuw.png",
        "kgz.png",
        "lao.png",
        "lat.png",
        "lib.png",
        "les.png",
        "lbr.png",
        "lba.png",
        "lie.png",
        "ltu.png",
        "lux.png",
        null,
        "mkd.png",
        "mad.png",
        "maw.png",
        "mas.png",
        "mdv.png",
        "mli.png",
        "mlt.png",
        "mhl.png",
        null,
        "mtn.png",
        "mri.png",
        null,
        "mex.png",
        "fsm.png",
        "mda.png",
        "mon.png",
        "mgl.png",
        "mne.png",
        null,
        "mar.png",
        "moz.png",
        "mya.png",
        "nam.png",
        "nru.png",
        "nep.png",
        "ned.png",
        null,
        "nzl.png",
        "nca.png",
        "nig.png",
        "ngr.png",
        null,
        null,
        "prk.png",
        null,
        "nor.png",
        "oma.png",
        "pak.png",
        "plw.png",
        "ple.png",
        "pan.png",
        "png.png",
        "par.png",
        "per.png",
        "phi.png",
        null,
        "pol.png",
        "por.png",
        "pur.png",
        "qat.png",
        "cgo.png",
        "rou.png",
        "rus.png",
        "rwa.png",
        null,
        null,
        "skn.png",
        "lca.png",
        null,
        null,
        "vin.png",
        "sam.png",
        "smr.png",
        "ksa.png",
        "sen.png",
        "srb.png",
        "sey.png",
        "sle.png",
        "sin.png",
        null,
        "svk.png",
        "slo.png",
        "sol.png",
        "som.png",
        "rsa.png",
        null,
        "kor.png",
        null,
        "esp.png",
        "sri.png",
        "sud.png",
        "sur.png",
        null,
        "swz.png",
        "swe.png",
        "sui.png",
        "syr.png",
        "stp.png",
        "tpe.png",
        "tjk.png",
        "tan.png",
        "tha.png",
        "tls.png",
        "tog.png",
        null,
        "tga.png",
        "tto.png",
        "tun.png",
        "tur.png",
        "tkm.png",
        null,
        "tuv.png",
        "uga.png",
        "ukr.png",
        "uae.png",
        "gbr.png",
        "usa.png",
        "isv.png",
        "uru.png",
        "uzb.png",
        "van.png",
        null,
        "ven.png",
        "vie.png",
        null,
        null,
        "yem.png",
        "zam.png",
        "zim.png",
        null,
    };
    
    private static final String[] COUNTRY_ISO2 = {
        "AF",
        "AL",
        "DZ",
        "AS",
        "AD",
        "AO",
        "AI",
        "AG",
        "AR",
        "AM",
        "AW",
        "AU",
        "AT",
        "AZ",
        "BS",
        "BH",
        "BD",
        "BB",
        "BY",
        "BE",
        "BZ",
        "BJ",
        "BM",
        "BT",
        "BO",
        "BA",
        "BW",
        "BR",
        "IO",
        "VG",
        "BN",
        "BG",
        "BF",
        "BI",
        "KH",
        "CM",
        "CA",
        "CV",
        "KY",
        "CF",
        "TD",
        "CL",
        "CN",
        "CX",
        "CC",
        "CO",
        "KM",
        "CK",
        "CR",
        "HR",
        "CU",
        "CW",
        "CY",
        "CZ",
        "DK",
        "DJ",
        "DM",
        "DO",
        "CD",
        "EC",
        "EG",
        "SV",
        "GQ",
        "ER",
        "EE",
        "ET",
        "FK",
        "FO",
        "FJ",
        "FI",
        "FR",
        "GF",
        "PF",
        "GA",
        "GM",
        "GE",
        "DE",
        "GH",
        "GI",
        "GR",
        "GL",
        "GD",
        "GP",
        "GU",
        "GT",
        "GG",
        "GN",
        "GW",
        "GY",
        "HT",
        "HN",
        "HK",
        "HU",
        "IS",
        "IN",
        "ID",
        "IR",
        "IQ",
        "IE",
        "IM",
        "IL",
        "IT",
        "CI",
        "JM",
        "JP",
        "JE",
        "JO",
        "KZ",
        "KE",
        "KI",
        "XK",
        "KW",
        "KG",
        "LA",
        "LV",
        "LB",
        "LS",
        "LR",
        "LY",
        "LI",
        "LT",
        "LU",
        "MO",
        "MK",
        "MG",
        "MW",
        "MY",
        "MV",
        "ML",
        "MT",
        "MH",
        "MQ",
        "MR",
        "MU",
        "YT",
        "MX",
        "FM",
        "MD",
        "MC",
        "MN",
        "ME",
        "MS",
        "MA",
        "MZ",
        "MM",
        "NA",
        "NR",
        "NP",
        "NL",
        "NC",
        "NZ",
        "NI",
        "NE",
        "NG",
        "NU",
        "NF",
        "KP",
        "MP",
        "NO",
        "OM",
        "PK",
        "PW",
        "PS",
        "PA",
        "PG",
        "PY",
        "PE",
        "PH",
        "PN",
        "PL",
        "PT",
        "PR",
        "QA",
        "CG",
        "RO",
        "RU",
        "RW",
        "RE",
        "BL",
        "KN",
        "LC",
        "MF",
        "PM",
        "VC",
        "WS",
        "SM",
        "SA",
        "SN",
        "RS",
        "SC",
        "SL",
        "SG",
        "SX",
        "SK",
        "SI",
        "SB",
        "SO",
        "ZA",
        "GS",
        "KR",
        "SS",
        "ES",
        "LK",
        "SD",
        "SR",
        "SJ",
        "SZ",
        "SE",
        "CH",
        "SY",
        "ST",
        "TW",
        "TJ",
        "TZ",
        "TH",
        "TL",
        "TG",
        "TK",
        "TO",
        "TT",
        "TN",
        "TR",
        "TM",
        "TC",
        "TV",
        "UG",
        "UA",
        "AE",
        "GB",
        "US",
        "VI",
        "UY",
        "UZ",
        "VU",
        "VA",
        "VE",
        "VN",
        "WF",
        "EH",
        "YE",
        "ZM",
        "ZW",
        "AX",
    };
    private static final String[] COUNTRY_ISO3 = {
        "AFG",
        "ALB",
        "DZA",
        "ASM",
        "AND",
        "AGO",
        "AIA",
        "ATG",
        "ARG",
        "ARM",
        "ABW",
        "AUS",
        "AUT",
        "AZE",
        "BHS",
        "BHR",
        "BGD",
        "BRB",
        "BLR",
        "BEL",
        "BLZ",
        "BEN",
        "BMU",
        "BTN",
        "BOL",
        "BIH",
        "BWA",
        "BRA",
        "IOT",
        "VGB",
        "BRN",
        "BGR",
        "BFA",
        "BDI",
        "KHM",
        "CMR",
        "CAN",
        "CPV",
        "CYM",
        "CAF",
        "TCD",
        "CHL",
        "CHN",
        "CXR",
        "CCK",
        "COL",
        "COM",
        "COK",
        "CRI",
        "HRV",
        "CUB",
        "CUW",
        "CYP",
        "CZE",
        "DNK",
        "DJI",
        "DMA",
        "DOM",
        "COD",
        "ECU",
        "EGY",
        "SLV",
        "GNQ",
        "ERI",
        "EST",
        "ETH",
        "FLK",
        "FRO",
        "FJI",
        "FIN",
        "FRA",
        "GUF",
        "PYF",
        "GAB",
        "GMB",
        "GEO",
        "DEU",
        "GHA",
        "GIB",
        "GRC",
        "GRL",
        "GRD",
        "GLP",
        "GUM",
        "GTM",
        "GGY",
        "GIN",
        "GNB",
        "GUY",
        "HTI",
        "HND",
        "HKG",
        "HUN",
        "ISL",
        "IND",
        "IDN",
        "IRN",
        "IRQ",
        "IRL",
        "IMN",
        "ISR",
        "ITA",
        "CIV",
        "JAM",
        "JPN",
        "JEY",
        "JOR",
        "KAZ",
        "KEN",
        "KIR",
        "UNK",
        "KWT",
        "KGZ",
        "LAO",
        "LVA",
        "LBN",
        "LSO",
        "LBR",
        "LBY",
        "LIE",
        "LTU",
        "LUX",
        "MAC",
        "MKD",
        "MDG",
        "MWI",
        "MYS",
        "MDV",
        "MLI",
        "MLT",
        "MHL",
        "MTQ",
        "MRT",
        "MUS",
        "MYT",
        "MEX",
        "FSM",
        "MDA",
        "MCO",
        "MNG",
        "MNE",
        "MSR",
        "MAR",
        "MOZ",
        "MMR",
        "NAM",
        "NRU",
        "NPL",
        "NLD",
        "NCL",
        "NZL",
        "NIC",
        "NER",
        "NGA",
        "NIU",
        "NFK",
        "PRK",
        "MNP",
        "NOR",
        "OMN",
        "PAK",
        "PLW",
        "PSE",
        "PAN",
        "PNG",
        "PRY",
        "PER",
        "PHL",
        "PCN",
        "POL",
        "PRT",
        "PRI",
        "QAT",
        "COG",
        "ROU",
        "RUS",
        "RWA",
        "REU",
        "BLM",
        "KNA",
        "LCA",
        "MAF",
        "SPM",
        "VCT",
        "WSM",
        "SMR",
        "SAU",
        "SEN",
        "SRB",
        "SYC",
        "SLE",
        "SGP",
        "SXM",
        "SVK",
        "SVN",
        "SLB",
        "SOM",
        "ZAF",
        "SGS",
        "KOR",
        "SSD",
        "ESP",
        "LKA",
        "SDN",
        "SUR",
        "SJM",
        "SWZ",
        "SWE",
        "CHE",
        "SYR",
        "STP",
        "TWN",
        "TJK",
        "TZA",
        "THA",
        "TLS",
        "TGO",
        "TKL",
        "TON",
        "TTO",
        "TUN",
        "TUR",
        "TKM",
        "TCA",
        "TUV",
        "UGA",
        "UKR",
        "ARE",
        "GBR",
        "USA",
        "VIR",
        "URY",
        "UZB",
        "VUT",
        "VAT",
        "VEN",
        "VNM",
        "WLF",
        "ESH",
        "YEM",
        "ZMB",
        "ZWE",
        "ALA",
    };
    
    
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
                        backForm.showBack();
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
        Transition currentTrans = f.getTransitionOutAnimator();
        f.setTransitionOutAnimator(CommonTransitions.createCover(CommonTransitions.SLIDE_VERTICAL, false, 400));
        activationForm.addShowListener(e -> f.setTransitionOutAnimator(currentTrans));
        activationForm.getToolbar().setBackCommand("", e -> f.showBack());
        
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
