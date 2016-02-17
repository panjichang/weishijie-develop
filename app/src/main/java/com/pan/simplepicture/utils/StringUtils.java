package com.pan.simplepicture.utils;

import android.util.Base64;
import android.util.Patterns;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    public static String timeFormatter(String mm) {
        float f = Float.parseFloat(mm);
        DecimalFormat df = new DecimalFormat("00.00");
        return df.format(f / 60);
    }

    public static String stringFormatter(int count) {
        if (count % 10000 < 100) {
            return String.valueOf(count / 10000) + "万次";
        } else {
            return String.format("%.2f", count / 10000d) + "万次";
        }
    }

    public static String timeFormatter(int mm) {
        DecimalFormat df;
        if (mm / 60 > 100) {
            df = new DecimalFormat("000.00");
        } else {
            df = new DecimalFormat("00.00");
        }
        return df.format(mm / 60);
    }

    /**
     * yyyyMMddHHmmss
     *
     * @param time
     * @return
     */
    public static String dayFormatter(String time) {
        long xTime = new Date().getTime() - Long.parseLong(time);
        // 秒
        if ((xTime = xTime / 1000) < 60) {
            return "刚刚";
        }
        // 分
        if ((xTime = xTime / 60) < 60) {
            return xTime + "分钟前";
        }
        // 小时
        if ((xTime = xTime / 60) < 24) {
            return xTime + "小时前";
        }
        // 天
        if ((xTime = xTime / 24) < 7) {
            return xTime + "天前";
        }
        if (xTime < 30 && xTime >= 7) {
            return xTime / 7 + "周前";
        }
        // 月
        if (xTime >= 30 && xTime < 365) {
            return xTime / 30 + "月前";
        }
        if (xTime >= 365) {
            return xTime / 365 + "年前";
        }
        return "";
    }

    public static String formatFileSize(long len) {
        return formatFileSize(len, false);
    }

    public static String formatFileSize(long len, boolean keepZero) {
        String size;
        DecimalFormat formatKeepTwoZero = new DecimalFormat("#.00");
        DecimalFormat formatKeepOneZero = new DecimalFormat("#.0");
        if (len < 1024) {
            size = String.valueOf(len + "B");
        } else if (len < 10 * 1024) {
            // [0, 10KB)，保留两位小数
            size = String.valueOf(len * 100 / 1024 / (float) 100) + "KB";
        } else if (len < 100 * 1024) {
            // [10KB, 100KB)，保留一位小数
            size = String.valueOf(len * 10 / 1024 / (float) 10) + "KB";
        } else if (len < 1024 * 1024) {
            // [100KB, 1MB)，个位四舍五入
            size = String.valueOf(len / 1024) + "KB";
        } else if (len < 10 * 1024 * 1024) {
            // [1MB, 10MB)，保留两位小数
            if (keepZero) {
                size = String.valueOf(formatKeepTwoZero.format(len * 100 / 1024
                        / 1024 / (float) 100))
                        + "MB";
            } else {
                size = String.valueOf(len * 100 / 1024 / 1024 / (float) 100)
                        + "MB";
            }
        } else if (len < 100 * 1024 * 1024) {
            // [10MB, 100MB)，保留一位小数
            if (keepZero) {
                size = String.valueOf(formatKeepOneZero.format(len * 10 / 1024
                        / 1024 / (float) 10))
                        + "MB";
            } else {
                size = String.valueOf(len * 10 / 1024 / 1024 / (float) 10)
                        + "MB";
            }
        } else if (len < 1024 * 1024 * 1024) {
            // [100MB, 1GB)，个位四舍五入
            size = String.valueOf(len / 1024 / 1024) + "MB";
        } else {
            // [1GB, ...)，保留两位小数
            size = String.valueOf(len * 100 / 1024 / 1024 / 1024 / (float) 100)
                    + "GB";
        }
        return size;
    }

    public static String getRealUrl(String s) {
        //(\\d+)为分组
        try {
            s = decryptFlvcd(s);
            return s.substring(s.indexOf("<U><![CDATA[")+12, s.indexOf("]]></U>"));
        } catch (Exception e) {
            return "";
        }
    }

    private static String decryptFlvcd(String paramString) throws Exception {
        char[] arrayOfChar = paramString.toCharArray();
        int i = paramString.length();
        byte[] arrayOfByte = new byte[i >> 1];
        int j = 0;
        int k = 0;
        while (true) {
            if (k >= i)
                return new String(
                        Base64.decode(transformFlvcd(arrayOfByte), 0), "utf-8");
            int m = Character.digit(arrayOfChar[k], 16) << 4;
            int n = k + 1;
            int i1 = m | Character.digit(arrayOfChar[n], 16);
            k = n + 1;
            arrayOfByte[j] = (byte) (i1 & 0xFF);
            j++;
        }
    }

    private static byte[] transformFlvcd(byte[] paramArrayOfByte)
            throws Exception {
        byte[] arrayOfByte1 = new byte[256];
        arrayOfByte1[0] = 63;
        arrayOfByte1[1] = 121;
        arrayOfByte1[2] = -44;
        arrayOfByte1[3] = 54;
        arrayOfByte1[4] = 86;
        arrayOfByte1[5] = -68;
        arrayOfByte1[6] = 114;
        arrayOfByte1[7] = 15;
        arrayOfByte1[8] = 108;
        arrayOfByte1[9] = 94;
        arrayOfByte1[10] = 77;
        arrayOfByte1[11] = -15;
        arrayOfByte1[12] = 89;
        arrayOfByte1[13] = 46;
        arrayOfByte1[14] = -81;
        arrayOfByte1[15] = 4;
        arrayOfByte1[16] = -114;
        arrayOfByte1[17] = 69;
        arrayOfByte1[18] = -88;
        arrayOfByte1[19] = -79;
        arrayOfByte1[20] = -26;
        arrayOfByte1[21] = 91;
        arrayOfByte1[22] = 50;
        arrayOfByte1[23] = -19;
        arrayOfByte1[24] = -37;
        arrayOfByte1[25] = 38;
        arrayOfByte1[26] = 27;
        arrayOfByte1[27] = -80;
        arrayOfByte1[28] = 7;
        arrayOfByte1[29] = 32;
        arrayOfByte1[30] = -64;
        arrayOfByte1[31] = 127;
        arrayOfByte1[32] = -41;
        arrayOfByte1[33] = 27;
        arrayOfByte1[34] = -49;
        arrayOfByte1[35] = -89;
        arrayOfByte1[36] = 3;
        arrayOfByte1[37] = 42;
        arrayOfByte1[38] = 52;
        arrayOfByte1[39] = 29;
        arrayOfByte1[40] = 86;
        arrayOfByte1[41] = 122;
        arrayOfByte1[42] = 6;
        arrayOfByte1[43] = -35;
        arrayOfByte1[44] = -110;
        arrayOfByte1[45] = -1;
        arrayOfByte1[46] = -57;
        arrayOfByte1[47] = 41;
        arrayOfByte1[48] = 52;
        arrayOfByte1[49] = -13;
        arrayOfByte1[50] = -73;
        arrayOfByte1[51] = 10;
        arrayOfByte1[52] = 48;
        arrayOfByte1[53] = 49;
        arrayOfByte1[54] = 92;
        arrayOfByte1[55] = 117;
        arrayOfByte1[56] = 67;
        arrayOfByte1[57] = 72;
        arrayOfByte1[58] = 45;
        arrayOfByte1[59] = 121;
        arrayOfByte1[60] = 93;
        arrayOfByte1[61] = -63;
        arrayOfByte1[62] = 101;
        arrayOfByte1[63] = -90;
        arrayOfByte1[64] = 73;
        arrayOfByte1[65] = 108;
        arrayOfByte1[66] = -29;
        arrayOfByte1[67] = -91;
        arrayOfByte1[68] = 7;
        arrayOfByte1[69] = 46;
        arrayOfByte1[70] = -110;
        arrayOfByte1[71] = 85;
        arrayOfByte1[73] = 81;
        arrayOfByte1[74] = 67;
        arrayOfByte1[75] = 83;
        arrayOfByte1[76] = 113;
        arrayOfByte1[77] = 67;
        arrayOfByte1[78] = 9;
        arrayOfByte1[79] = -57;
        arrayOfByte1[80] = 116;
        arrayOfByte1[81] = -102;
        arrayOfByte1[82] = -26;
        arrayOfByte1[83] = 15;
        arrayOfByte1[84] = 92;
        arrayOfByte1[85] = -14;
        arrayOfByte1[86] = -91;
        arrayOfByte1[87] = 90;
        arrayOfByte1[88] = 56;
        arrayOfByte1[89] = -76;
        arrayOfByte1[90] = 18;
        arrayOfByte1[91] = 1;
        arrayOfByte1[92] = 57;
        arrayOfByte1[93] = 95;
        arrayOfByte1[94] = -1;
        arrayOfByte1[95] = 83;
        arrayOfByte1[96] = 67;
        arrayOfByte1[97] = -84;
        arrayOfByte1[98] = 52;
        arrayOfByte1[99] = 117;
        arrayOfByte1[100] = -93;
        arrayOfByte1[101] = 86;
        arrayOfByte1[102] = 116;
        arrayOfByte1[103] = -58;
        arrayOfByte1[104] = 120;
        arrayOfByte1[105] = -112;
        arrayOfByte1[106] = 70;
        arrayOfByte1[107] = -88;
        arrayOfByte1[108] = -123;
        arrayOfByte1[109] = -45;
        arrayOfByte1[110] = -122;
        arrayOfByte1[111] = 10;
        arrayOfByte1[112] = 38;
        arrayOfByte1[113] = 39;
        arrayOfByte1[114] = -10;
        arrayOfByte1[115] = -60;
        arrayOfByte1[116] = -114;
        arrayOfByte1[117] = 93;
        arrayOfByte1[118] = 31;
        arrayOfByte1[119] = 25;
        arrayOfByte1[120] = 1;
        arrayOfByte1[121] = -120;
        arrayOfByte1[122] = -121;
        arrayOfByte1[123] = -66;
        arrayOfByte1[124] = -40;
        arrayOfByte1[125] = 74;
        arrayOfByte1[126] = -69;
        arrayOfByte1[127] = 83;
        arrayOfByte1[''] = 101;
        arrayOfByte1[''] = -86;
        arrayOfByte1[''] = 107;
        arrayOfByte1[''] = 121;
        arrayOfByte1[''] = -6;
        arrayOfByte1[''] = 109;
        arrayOfByte1[''] = 50;
        arrayOfByte1[''] = 111;
        arrayOfByte1[''] = -33;
        arrayOfByte1[''] = 62;
        arrayOfByte1[''] = 27;
        arrayOfByte1[''] = -63;
        arrayOfByte1[''] = -33;
        arrayOfByte1[''] = 1;
        arrayOfByte1[''] = 52;
        arrayOfByte1[''] = 81;
        arrayOfByte1[''] = 83;
        arrayOfByte1[''] = 109;
        arrayOfByte1[''] = -59;
        arrayOfByte1[''] = 122;
        arrayOfByte1[''] = 11;
        arrayOfByte1[''] = -57;
        arrayOfByte1[''] = -75;
        arrayOfByte1[''] = 34;
        arrayOfByte1[''] = 58;
        arrayOfByte1[''] = 38;
        arrayOfByte1[''] = -75;
        arrayOfByte1[''] = -115;
        arrayOfByte1[''] = 62;
        arrayOfByte1[''] = -46;
        arrayOfByte1[''] = 7;
        arrayOfByte1[''] = -114;
        arrayOfByte1[' '] = -60;
        arrayOfByte1['¡'] = -20;
        arrayOfByte1['¢'] = 55;
        arrayOfByte1['£'] = 4;
        arrayOfByte1['¤'] = -107;
        arrayOfByte1['¥'] = -110;
        arrayOfByte1['¦'] = -62;
        arrayOfByte1['§'] = 103;
        arrayOfByte1['¨'] = -21;
        arrayOfByte1['©'] = 40;
        arrayOfByte1['ª'] = 56;
        arrayOfByte1['«'] = -62;
        arrayOfByte1['¬'] = -110;
        arrayOfByte1['­'] = -91;
        arrayOfByte1['®'] = -64;
        arrayOfByte1['¯'] = 53;
        arrayOfByte1['°'] = -69;
        arrayOfByte1['±'] = 123;
        arrayOfByte1['²'] = -87;
        arrayOfByte1['³'] = 66;
        arrayOfByte1['´'] = -67;
        arrayOfByte1['µ'] = 57;
        arrayOfByte1['¶'] = 91;
        arrayOfByte1['·'] = 74;
        arrayOfByte1['¸'] = 82;
        arrayOfByte1['¹'] = 13;
        arrayOfByte1['º'] = 14;
        arrayOfByte1['»'] = 109;
        arrayOfByte1['¼'] = -77;
        arrayOfByte1['½'] = -108;
        arrayOfByte1['¾'] = -28;
        arrayOfByte1['¿'] = -78;
        arrayOfByte1['À'] = 103;
        arrayOfByte1['Á'] = -85;
        arrayOfByte1['Â'] = -37;
        arrayOfByte1['Ã'] = -47;
        arrayOfByte1['Ä'] = -33;
        arrayOfByte1['Å'] = -33;
        arrayOfByte1['Æ'] = 97;
        arrayOfByte1['Ç'] = -103;
        arrayOfByte1['È'] = 102;
        arrayOfByte1['É'] = -96;
        arrayOfByte1['Ê'] = -78;
        arrayOfByte1['Ë'] = -116;
        arrayOfByte1['Ì'] = 57;
        arrayOfByte1['Í'] = 55;
        arrayOfByte1['Î'] = 91;
        arrayOfByte1['Ï'] = 20;
        arrayOfByte1['Ð'] = 80;
        arrayOfByte1['Ñ'] = -66;
        arrayOfByte1['Ò'] = -82;
        arrayOfByte1['Ó'] = -77;
        arrayOfByte1['Ô'] = -78;
        arrayOfByte1['Õ'] = 39;
        arrayOfByte1['Ö'] = -63;
        arrayOfByte1['×'] = 19;
        arrayOfByte1['Ø'] = 12;
        arrayOfByte1['Ù'] = -2;
        arrayOfByte1['Ú'] = 93;
        arrayOfByte1['Û'] = -32;
        arrayOfByte1['Ü'] = 65;
        arrayOfByte1['Ý'] = -25;
        arrayOfByte1['Þ'] = 89;
        arrayOfByte1['ß'] = 104;
        arrayOfByte1['à'] = -51;
        arrayOfByte1['á'] = -102;
        arrayOfByte1['â'] = 76;
        arrayOfByte1['ã'] = -68;
        arrayOfByte1['ä'] = -86;
        arrayOfByte1['å'] = -90;
        arrayOfByte1['æ'] = 121;
        arrayOfByte1['ç'] = 39;
        arrayOfByte1['è'] = -83;
        arrayOfByte1['é'] = -118;
        arrayOfByte1['ê'] = -102;
        arrayOfByte1['ë'] = 110;
        arrayOfByte1['ì'] = 113;
        arrayOfByte1['í'] = -3;
        arrayOfByte1['î'] = -23;
        arrayOfByte1['ï'] = 52;
        arrayOfByte1['ð'] = -71;
        arrayOfByte1['ñ'] = -16;
        arrayOfByte1['ò'] = -21;
        arrayOfByte1['ó'] = 72;
        arrayOfByte1['ô'] = -99;
        arrayOfByte1['õ'] = -86;
        arrayOfByte1['ö'] = -120;
        arrayOfByte1['÷'] = -16;
        arrayOfByte1['ø'] = 2;
        arrayOfByte1['ù'] = 114;
        arrayOfByte1['ú'] = 72;
        arrayOfByte1['û'] = -50;
        arrayOfByte1['ü'] = 56;
        arrayOfByte1['ý'] = 73;
        arrayOfByte1['þ'] = -56;
        arrayOfByte1['ÿ'] = 117;
        byte[] arrayOfByte2 = new byte[paramArrayOfByte.length];
        for (int i = 0; ; i++) {
            if (i >= paramArrayOfByte.length)
                return arrayOfByte2;
            arrayOfByte2[i] = (byte) (arrayOfByte1[(i % 256)] ^ paramArrayOfByte[i]);
        }
    }
}
